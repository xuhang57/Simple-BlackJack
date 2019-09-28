import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    private CardDeck deck = new CardDeck();
    private Scanner scan = new Scanner(System.in);

    public void initPlayer(Player player, int betAmount){
        player.cleanHands();
        player.subtractBalance(betAmount);
        player.addCard(deck.drawCard());
        player.addCard(deck.drawCard());
        player.initHands();

        // check if player's cards are split-able
        if (player.getBalance() - betAmount >= 0) { // if the player has enough money, the player might be able to split the hand
            player.setHands(split(player, player.getHand(), betAmount).getHands());
        } else { // not enough money
            player.initHands();
        }
        List<Integer> list = new ArrayList<>(player.getHands().size());
        while(list.size() < player.getHands().size()){
            list.add(0);
        }
        player.setwinMultiplier(list);
    }

    public void initDealer(Dealer dealer){
        dealer.cleanHands();
        dealer.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
        dealer.initHands();
    }

    public void BlackJackDealersTurn_AI(Player player, Dealer dealer) {
        // dealer's turn
        int playerMaxPoints = 0;
        for (int index = 0; index < player.getHands().size(); index++) {
            if(player.calculateRank(index) <= 21){
                playerMaxPoints = player.calculateRank(index);
            }
        }
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("Dealer's cards are: " + dealer.printHand(0) +
                ", total points " + dealer.calculateRank(0) + ".");
        while (dealer.calculateRank(0) <= playerMaxPoints) {
            Card newCard = deck.drawCard();
            dealer.addCard(newCard);
            System.out.println(" ...... Dealer chooses to hit  ......");
            System.out.println("Dealer draws a new card " + newCard.toString());
            System.out.println("Dealer's cards are: " + dealer.printHand(0) + ", total points " + dealer.calculateRank(0) + ".");
        }
        System.out.println(" ...... Dealer chooses to stand  ......");
    }

    public void BlackJackDealersTurn_Human(Player player, Dealer dealer) {
        // dealer's turn
        int playerMaxPoints = 0;
        for (int index = 0; index < player.getHands().size(); index++) {
            if(player.calculateRank(index) <= 21){
                playerMaxPoints = player.calculateRank(index);
            }
        }

        while (dealer.calculateRank(0) < 22) {
            List<Card> hand = dealer.getHands().get(0);
            dealer.setHand(hand);

            System.out.println("Player's largest hand has total points: " + playerMaxPoints);
            System.out.println("Dealer has: " + dealer.printHand(0) + ", total points " + dealer.calculateRank(0) + ".");
            System.out.println("Do you want to 1 (hit) or  2 (stand)");
            int choice = scan.nextInt();
            if (choice == 1) { // hit
                Card newCard = deck.drawCard();
                dealer.addCard(newCard);
                System.out.println("The new card is: " + newCard.toString() + ".");
            } else { // stand
                System.out.println("Dealer has: " + dealer.printHand(0) +
                        ", total points " + dealer.calculateRank(0) + ".");
                break;
            }
        }
    }

    public void BlackJackPlayersTurn(Player player, int betAmount){
        // player's turn
        for (int index = 0; index < player.getHands().size(); index++) {
            while (player.calculateRank(index) < 22) {
                List<Card> hand = player.getHands().get(index);
                // set the hand to the current hand of the player's
                player.setHand(hand);

                System.out.println("You have: [" + player.printHand(index) + "], total points " +
                        player.calculateRank(index) + ".");
                System.out.println("Do you want to 1(hit), 2 (stand), 3 (double up): ");
                int choice = scan.nextInt();
                if (choice == 1) {
                    Card newCard = deck.drawCard();
                    player.setwinMultiplierOf(index, 2);
                    // Add the new card to the current hand
                    player.addCard(newCard);
                    System.out.println("The new card is: " + newCard.toString() + ".");
                } else if (choice == 2) {
                    player.setwinMultiplierOf(index, 2);
                    break;
                } else if (choice == 3) {
                    player.setwinMultiplierOf(index, 4);
                    if (player.getBalance() - betAmount >= 0) {
                        player.subtractBalance(betAmount);
                        Card newCard = deck.drawCard();
                        player.addCard(newCard);
                        System.out.println("The new card is: " + newCard.toString() + ".");
                        System.out.println("Your cards are now: " + player.printHand(index) + ", total points " +
                                player.calculateRank(index) + ".");
                        break;
                    } else {
                        System.out.println("You don't have enough money, please try other options!");
                    }
                } else {
                    System.out.println("Invalid choice! Please 1 (hit), 2 (stand), 3 (double up).");
                }
            }
        }
    }

    public void BlackJackCompare(Player player, Dealer dealer, int currBet){
        for (int index = 0; index < player.getHands().size(); index++) {
            System.out.println("Player's cards are: " + player.printHand(index) + ", total points "
                    + player.calculateRank(index) + ".");
            System.out.println("Dealer's cards are: " + dealer.printHand(0) +
                    ", total points " + dealer.calculateRank(0) + ".");
            // Find winner!
            int playerTotal = player.calculateRank(index);
            int dealerTotal = dealer.calculateRank(0);

            if (playerTotal <= 21 && dealerTotal <= 21) {
                if (playerTotal <= dealerTotal) {
                    System.out.println("Dealer wins!");
                } else if(playerTotal > dealerTotal){
                    System.out.println("Player wins!");
                    player.addBalance(currBet * player.getwinMultiplierOf(index));
                } else {
                    // if draw, the dealer wins
                    if(playerTotal == 21 && dealerTotal == 21){ // check natural blackjack
                        if(player.getHand().size() == 2 && dealer.getHand().size() > 2){
                            player.addBalance(currBet * player.getwinMultiplierOf(index));
                            System.out.println("Player wins");
                        }else{
                            System.out.println("Dealer wins");
                        }
                    }
                }
            } else if (playerTotal <= 21 && dealerTotal > 21) {
                player.addBalance(currBet * player.getwinMultiplierOf(index));
                System.out.println("Player wins");
            } else if (playerTotal > 21 && dealerTotal <= 21){
                System.out.println("Dealer wins");
            } else {
                player.addBalance(currBet * player.getwinMultiplierOf(index)/2);
                System.out.println("The game tied");
            }
        }
    }

    public void PlayMode(int mode) {
        Player player = new Player("Player", 100);
        Dealer dealer = new Dealer("Dealer", Integer.MAX_VALUE);

        boolean play = true;

        while(player.getBalance() >= 0 && play) {
            // set budget, shuffle card, etc
            deck.prepareDeck();
            System.out.println("Your current balance is: " + player.getBalance() + ". Please choose a bet amount for this round:");
            int betAmount = scan.nextInt();
            while (player.getBalance() - betAmount < 0) {
                System.out.println("You don't have enough balance for this bet. Choose a different amount.");
                betAmount = scan.nextInt();
            }

            // game starts!
            initDealer(dealer);
            System.out.println("Dealer shows: " + dealer.getHand().get(0));

            initPlayer(player, betAmount);
            System.out.println("Player's card is " + player.printHands());

            System.out.println("It is Player's Turn!");
            // game proceeds, player can choose to 1.hit, 2.stand or 3.double up for each hand of cards
            BlackJackPlayersTurn(player, betAmount);

            System.out.println("-----------------------------------------------------------------------------");
            System.out.println("It is now dealer's Turn!");
            if(mode == 1){
                BlackJackDealersTurn_AI(player, dealer);
            }else if(mode == 2){
                BlackJackDealersTurn_Human(player, dealer);
            }

            // game proceeds, compare cards and add/subtract balance
            BlackJackCompare(player, dealer, betAmount);

            // game summary
            System.out.println(player.getName() + "'s current balance is: " + player.getBalance() + ".");
            if(player.getBalance() > 0){
                System.out.println("Do you want to play again? 1 (Yes) or 2 (No)");
                int input = scan.nextInt();
                if(input != 1){
                    System.out.println("Player has cashed out.");
                    break;
                }
            }else{
                play = false;
                System.out.println("Player has lost all money. Game exits!");
            }
        }
    }

    // run a recursion to get all possible split pairs
    private Player split(Player player, List<Card> hand, int betPerGame) {
        Card card1 = hand.get(0);
        Card card2 = hand.get(1);

        if (card1.getFace() == card2.getFace() && player.getBalance() - betPerGame >= 0) {
            System.out.println("Your hands are: " + player.printHands());
            System.out.println("Your current hand is: " + player.printHand());
            Scanner scan = new Scanner(System.in);
            System.out.println("Do you want to split? " + player.printHand() + " (type 1 for Yes or 2 for No) ");
            int isSplit = scan.nextInt();
            if (isSplit == 1) {
                List<List<Card>> newHands = new ArrayList<>();
                // bet the same amount of money to the new hand
                player.subtractBalance(betPerGame);

                List<Card> hand1 = new ArrayList<>();
                hand1.add(card1);
                Card newCard = deck.drawCard();
                hand1.add(newCard);
                System.out.println("You drew a new card: " + newCard.toString());
                player.setHand(hand1);
                System.out.println("Current hand is: " + player.printHand());
                newHands.addAll(split(player, hand1, betPerGame).getHands());

                List<Card> hand2 = new ArrayList<>();
                hand2.add(card2);
                newCard = deck.drawCard();
                System.out.println("You drew a new card: " + newCard.toString());
                hand2.add(newCard);
                player.setHand(hand2);
                System.out.println("Current hand is: " + player.printHand());
                newHands.addAll(split(player, hand2, betPerGame).getHands());

                player.setHands(newHands);
                return player;
            }
        }

        List<List<Card>> newHands = new ArrayList<>();
        newHands.add(hand);
        player.setHand(hand);
        player.setHands(newHands);
        return player;
    }
}
