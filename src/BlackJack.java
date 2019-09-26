import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BlackJack {
    private static ArrayList<Card> deck = new ArrayList<>();
    private static Player player;
    private static Dealer dealer;

    private static String suits[] = {"Spade", "Diamond", "Heart", "Clubs"};

    public static void shuffle() {
        // shuffle cards
        for(int i = 0; i < suits.length; i++){
            for(int j = 0; j < 13; j++){
                deck.add(new Card(suits[i], j));
            }
        }
    }


    // this method draws a card from the deck
    public static Card drawCard(){
        int random = (int) Math.floor(Math.random() * deck.size());
        // gets the card
        Card card = deck.get(random);
        // remove card from the deck
        deck.remove(random);
        // return card
        return card;
    }

    public String toString() {
        String str = "[";
        for(int i = 0; i < deck.size(); i++){
            str += deck.get(i) + ", ";
        }
        str.trim();
        str += "]";
        return str;
    }

    public static List<List<Card>> split(List<Card> hand){
        if(hand.get(0).getNumber() == hand.get(1).getNumber()){  // if split-able, ask user if split
            System.out.println("Your card is: " + player.handsToString());
            Scanner scan = new Scanner(System.in);
            System.out.println("Do you want to split? 1.Yes 2.No ");
            int mode = scan.nextInt();
            if(mode == 1){ // if the user choose to split
                List<Card> hand1 = new ArrayList<>();
                List<Card> hand2 = new ArrayList<>();
                hand1.add(hand.get(0));
                hand1.add(drawCard());
                hand2.add(hand.get(1));
                hand2.add(drawCard());

                List<List<Card>> newList = new ArrayList<>();
                newList.addAll(split(hand1));
                newList.addAll(split(hand2));
                return newList;
            }else{ // if the user choose not to split
                List<List<Card>> newList = new ArrayList<>();
                newList.add(hand);
                return newList;
            }
        }else{
            List<List<Card>> newList = new ArrayList<>();
            newList.add(hand);
            return newList;
        }
    }

    public static Player split_new(Player player, List<Card> hand, int amt){
        if(hand.get(0).getNumber() == hand.get(1).getNumber() && player.getBalance() - amt >= 0){  // if split-able, ask user if split
            System.out.println("Your card is: " + handToString(hand));
            Scanner scan = new Scanner(System.in);
            System.out.println("Do you want to split? 1.Yes 2.No ");
            int mode = scan.nextInt();
            if(mode == 1){ // if the user choose to split
                List<List<Card>> newList = new ArrayList<>();
                player.minusBalance(amt);

                List<Card> hand1 = new ArrayList<>();
                hand1.add(hand.get(0));
                Card card1 = drawCard();
                hand1.add(card1);
                player.setHand(hand1);
                System.out.println("You drew a new card: " + card1.toString());
                newList.add(split_new(player, hand1, amt).getHand());

                List<Card> hand2 = new ArrayList<>();
                hand2.add(hand.get(1));
                Card card2 = drawCard();
                hand2.add(card2);
                player.setHand(hand2);
                System.out.println("You drew a new card: " + card2.toString());
                hand2.add(drawCard());
                newList.add(split_new(player, hand2, amt).getHand());

                player.setHands(newList);
                return player;
            }else{ // if the user choose not to split
                List<List<Card>> newList = new ArrayList<>();
                newList.add(hand);
                player.setHand(hand);
                player.setHands(newList);
                return player;
            }
        }else{
            List<List<Card>> newList = new ArrayList<>();
            newList.add(hand);
            player.setHand(hand);
            player.setHands(newList);
            return player;
        }
    }

    public static String handToString(List<Card> hand){
        String card = "";
        for(int i = 0; i < hand.size(); i++){
            card += (hand.get(i).getSuit() + " " + hand.get(i).getNumber() + ", ");
        }
        card.trim();
        card = card.substring(0, card.length()-2);
        return card;
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Fuqing and Hang's BlackJack table!");
        shuffle();
        dealer = new Dealer("Player2", Integer.MAX_VALUE);
        player = new Player("Player1", 100);

        // choose play mode
        Scanner scan = new Scanner(System.in);
        System.out.println("Choose a play mode.(enter 1 for single player and 2 for versus mode)");
        int mode = scan.nextInt();
        if(mode == 1){
            // dealer draws a faced up card
            boolean play = true;

            while(player.getBalance() >= 0 && play) {
                // game start
                System.out.println("Your current balance is: " + player.getBalance() + ". Choose a bet amount:");
                int amt = scan.nextInt();
                while(player.getBalance() - amt < 0){
                    System.out.println("You don't have enough balance for this bet. Choose a different amount.");
                    amt = scan.nextInt();
                }
                player.minusBalance(amt);
                // player and dealer each draws two cards
                dealer.addCard(drawCard());
                dealer.addCard(drawCard());
                player.addCard(drawCard());
                player.addCard(drawCard());
                dealer.setHands();
                player.setHands();

                System.out.println("Player's turn: ");
                System.out.println("Dealer shows: " + dealer.getCards().get(0));
                System.out.println("Your card is " + player.handsToString());
                // player's turn
                if(player.getBalance() - amt >= 0){
                    player.setHands(split_new(player, player.getHand(), amt).getHands());
                }else{
                    player.setHands();
                }

                for (int i = 0; i < player.getHands().size(); i++) {
                    int tmp_amt = amt;
                    while (player.calculateValue(i) < 22) {
                        List<Card> hand = player.getHands().get(i);
                        player.setHand(hand);

                        System.out.println("Your have: " + player.handToString(i) + ", total points " + player.calculateValue(i) + ".");
                        System.out.println("Do you want to 1.hit 2.stand 3.double up");
                        int choice = scan.nextInt();
                        if (choice == 1) { // hit
                            Card newCard = drawCard();
                            player.addCard(newCard);
                            System.out.println("The new card is: " + newCard.toString() + ".");
                        } else if (choice == 2) { // stand
                            break;
                        } else if (choice == 3) { // double up
                            if(player.getBalance() - tmp_amt >= 0){
                                player.minusBalance(tmp_amt);
                                tmp_amt = amt*2;
                                Card newCard = drawCard();
                                player.addCard(newCard);
                                System.out.println("The new card is: " + newCard.toString() + ".");
                                System.out.println("Your cards are now: " + player.handToString(i) + ", total points " + player.calculateValue(i) + ".");
                                break;
                            } else {
                                System.out.println("You don't have enough money to double up!");
                            }
                        } else {
                            break;
                        }
                    }

                    // dealer's turn
                    dealer.setHands();
                    System.out.println("-----------------------------------------------------------------------------");
                    System.out.println("Dealer's turn: ");
                    System.out.println("Dealer's cards are: " + dealer.handToString(0) + ", total points " + dealer.calculateValue(0) + ".");
                    while (dealer.calculateValue(0) <= player.calculateValue(i) && player.calculateValue(i) <= 21) {
                        Card newCard = drawCard();
                        dealer.addCard(newCard);
                        System.out.println(" ...... Dealer chooses to hit  ......");
                        System.out.println("Dealer draws a new card " + newCard.toString());
                        System.out.println("Dealer's cards are: " + dealer.handToString(0) + ", total points " + dealer.calculateValue(0) + ".");
                    }

                    System.out.println(" ...... Dealer chooses to stand  ......");
                    System.out.println("Player's cards are: " + player.handToString(i) + ", total points " + player.calculateValue(i) + ".");
                    System.out.println("Dealer's cards are: " + dealer.handToString(0) + ", total points " + dealer.calculateValue(0) + ".");

                    // check win
                    int p = player.calculateValue(i);
                    int d = dealer.calculateValue(0);

                    if (p <= 21 && d <= 21) {
                        if (p < d) {
                            System.out.println("Dealer wins!");
                        } else if (p > d) {
                            player.addBalance(tmp_amt*2);
                            System.out.println("Player wins");
                        } else {
                            System.out.println("The game tied");
                        }
                    } else if (p <= 21 && d > 21) {
                        player.addBalance(tmp_amt*2);
                        System.out.println("Player wins");
                    } else if (p > 21 && d <= 21) {
                        System.out.println("Dealer wins");
                    } else {
                        System.out.println("The game tied");
                    }
                }

                System.out.println("Your current balance is: " + player.getBalance() + ".");
                if(player.getBalance() > 0){
                    System.out.println("Do you want to play again? 1.Yes 2.No");
                    int input = scan.nextInt();
                    if(input == 1){
                        player.cleanHands();
                        dealer.cleanHands();
                        shuffle();
                    } else {
                        play = false;
                        System.out.println("You have cashed out.");
                    }
                }else{
                    play = false;
                    System.out.println("You have lost all your money. Better luck next time!");
                }
            }
        }

        if(mode == 2){
            // dealer draws a faced up card
            boolean play = true;
            while(player.getBalance() >= 0 && play) {
                // game start
                System.out.println("Your current balance is: " + player.getBalance() + ". Choose a bet amount:");
                int amt = scan.nextInt();
                while(player.getBalance() - amt < 0){
                    System.out.println("You don't have enough balance for this bet. Choose a different amount.");
                    amt = scan.nextInt();
                }
                player.minusBalance(amt);
                // player and dealer each draws two cards
                dealer.addCard(drawCard());
                dealer.addCard(drawCard());
                player.addCard(drawCard());
                player.addCard(drawCard());
                dealer.setHands();
                player.setHands();

                System.out.println("Dealer shows: " + dealer.getCards().get(0));

                System.out.println("Your card is " + player.handsToString());
                // player's turn
                if(player.getBalance() - amt >= 0){
                    player.setHands(split_new(player, player.getHand(), amt).getHands());
                }else{
                    player.setHands();
                }

                for (int i = 0; i < player.getHands().size(); i++) {
                    int tmp_amt = amt;
                    while (player.calculateValue(i) < 22) {
                        List<Card> hand = player.getHands().get(i);
                        player.setHand(hand);

                        System.out.println("Player has: " + player.handToString(i) + ", total points " + player.calculateValue(i) + ".");
                        System.out.println("Do you want to 1.hit 2.stand 3.double up");
                        int choice = scan.nextInt();
                        if (choice == 1) { // hit
                            Card newCard = drawCard();
                            player.addCard(newCard);
                            System.out.println("The new card is: " + newCard.toString() + ".");
                        } else if (choice == 2) { // stand
                            break;
                        } else if (choice == 3) { // double up
                            if(player.getBalance() - tmp_amt >= 0){
                                player.minusBalance(tmp_amt);
                                tmp_amt = amt*2;
                                Card newCard = drawCard();
                                player.addCard(newCard);
                                System.out.println("The new card is: " + newCard.toString() + ".");
                                System.out.println("Player's'cards are now: " + player.handToString(i) + ", total points " + player.calculateValue(i) + ".");
                                break;
                            } else {
                                System.out.println("You don't have enough money to double up!");
                            }
                        } else {
                            break;
                        }
                    }

                    // dealer's turn
                    System.out.println("Dealer's turn: ");
                    dealer.setHands();
                    while (dealer.calculateValue(i) < 22) {
                        List<Card> hand = dealer.getHands().get(i);
                        dealer.setHand(hand);

                        System.out.println("Dealer has: " + dealer.handToString(i) + ", total points " + dealer.calculateValue(i) + ".");
                        System.out.println("Do you want to 1.hit 2.stand");
                        int choice = scan.nextInt();
                        if (choice == 1) { // hit
                            Card newCard = drawCard();
                            dealer.addCard(newCard);
                            System.out.println("The new card is: " + newCard.toString() + ".");
                        } else { // stand
                            System.out.println("Dealer has: " + dealer.handToString(i) + ", total points " + dealer.calculateValue(i) + ".");
                            break;
                        }
                    }

                    // check win
                    int p = player.calculateValue(i);
                    int d = dealer.calculateValue(0);

                    if (p <= 21 && d <= 21) {
                        if (p < d) {
                            System.out.println("Dealer wins!");
                        } else if (p > d) {
                            player.addBalance(tmp_amt*2);
                            System.out.println("Player wins");
                        } else {
                            System.out.println("The game tied");
                        }
                    } else if (p <= 21 && d > 21) {
                        player.addBalance(tmp_amt*2);
                        System.out.println("Player wins");
                    } else if (p > 21 && d <= 21) {
                        System.out.println("Dealer wins");
                    } else {
                        System.out.println("The game tied");
                    }
                }

                System.out.println("Player's balance is: " + player.getBalance() + ".");
                if(player.getBalance() > 0){
                    System.out.println("Do you want to play again? 1.Yes 2.No");
                    int input = scan.nextInt();
                    if(input == 1){
                        player.cleanHands();
                        dealer.cleanHands();
                        shuffle();
                    } else {
                        play = false;
                        System.out.println("Player has cashed out.");
                    }
                }else{
                    play = false;
                    System.out.println("Player has lost all money. Game exits!");
                }
            }
        }
    }
}
