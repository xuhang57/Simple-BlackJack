import java.util.ArrayList;
import java.util.Scanner;

public class BlackJack {
    private static ArrayList<Card> deck = new ArrayList<>();
    private static Player player;
    private static Dealer dealer;

    private static String suits[] = {"Spade", "Diamond", "Heart", "Clubs"};

    public static void init() {
        // shuffle cards
        for(int i = 0; i < suits.length; i++){
            for(int j = 0; j < 13; j++){
                deck.add(new Card(suits[i], j));
            }
        }
        // init players
//        player = new Player('Player1',100);
//        dealer = new Dealer('Player2', Integer.MAX_VALUE);

        player = new Player("Player1", 100);
        dealer = new Dealer("Player2", Integer.MAX_VALUE);
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

    public static void main(String[] args) {
        System.out.println("Welcome to Fuqing and Hang's BlackJack table!");
        init();

        // choose play mode
        Scanner scan = new Scanner(System.in);
        System.out.println("Choose a play mode.(enter 1 for single player and 2 for versus mode)");
        int input = scan.nextInt();
        if(input == 1){
            // dealer draws a faced up card
            dealer.addCard(drawCard());
            dealer.addCard(drawCard());
            player.addCard(drawCard());
            player.addCard(drawCard());

            System.out.println("Dealer's card is: " + dealer.getCards().get(1));

            // player's turn
            while(player.calculateValue() < 22){
                // @todo split
                if(1 == 0){
                    System.out.println("Do you want to split?");
                }
                System.out.println("Your card is: " + player.cardsToString());
                System.out.println("Do you want to 1.hit 2.stand 3.double up 4.split");
                int choice = scan.nextInt();
                if(choice == 1){
                    Card newCard = drawCard();
                    player.addCard(newCard);
                    System.out.println("The new card is " + newCard.toString() + ", total points " + player.calculateValue() + ".");
                }else if(choice == 2){
                    break;
                }else if(choice == 3){
                    System.out.println("double up");
                }else{
                    break;
                }
            }

            // dealer's turn
            System.out.println("Dealer's card is: " + dealer.cardsToString() + "total points " + player.calculateValue() + ".");
            while(dealer.calculateValue() <= player.calculateValue() && player.calculateValue() <= 21){
                Card newCard = drawCard();
                dealer.addCard(newCard);
                System.out.println("Dealer draws a new card " + newCard.toString());
                System.out.println("Dealer's card is: " + dealer.cardsToString() + "total points " + player.calculateValue() + ".");
            }

            System.out.println();
            System.out.println("Player's card is: " + player.cardsToString() + "total points " + player.calculateValue() + ".");
            System.out.println("Dealer's card is: " + dealer.cardsToString() + "total points " + dealer.calculateValue() + ".");

            // check win
            int p = player.calculateValue();
            int d = dealer.calculateValue();

            if( p <= 21 && d <= 21 ){
                if(p < d){
                    System.out.println("Dealer wins!");
                }else if(p > d){
                    System.out.println("Player wins");
                }else{
                    System.out.println("The game tied");
                }
            }else if( p <= 21 && d > 21){
                System.out.println("Player wins");
            }else if( p > 21 && d <= 21){
                System.out.println("Dealer wins");
            }else{
                System.out.println("The game tied");
            }

//            System.out.println(c1.toString());
//            System.out.println(dealer.getCards().get(1));
//            System.out.println(dealer.cardsToString());
//            System.out.println(player.cardsToString());
//            System.out.println(deck.toString());
            // player draws a card

            // dealer draws a hidden card

            // player draws a card
        }
        if(input == 2){

        }
    }
}
