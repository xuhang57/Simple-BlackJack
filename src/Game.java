import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    public void singlePlayerMode(CardDeck deck) {
        System.out.println("Single Player Mode");

        HumanPlayer human = new HumanPlayer("Human Player", 100);
        Dealer dealer = new Dealer("Dealer", Integer.MAX_VALUE);

        // dealer draws a faced up card so show in the console
        boolean canPlay = true;

        while(human.getBalance() >= 0 && canPlay) {
            System.out.println("Your current balance is: " + human.getBalance() +
                    ". Please choose a bet amount per game: [5]");
            Scanner scan = new Scanner(System.in);
            int betPerGame = scan.nextInt();
            while (human.getBalance() - betPerGame < 0) {
                System.out.println("You don't have enough balance for this bet. Choose a different amount.");
                betPerGame = scan.nextInt();
            }
            // Put the bet for this game
            human.subtractBalance(betPerGame);
            // draw two cards first for each player
            dealer.addCard(deck.drawCard());
            human.addCard(deck.drawCard());
            dealer.addCard(deck.drawCard());
            human.addCard(deck.drawCard());
            // initialize hands of different players
            human.initHands();
            dealer.initHands();

            System.out.println("Now, Human Player's turn: ");
            System.out.println("Dealer shows: " + dealer.getHand().get(0));
            System.out.println("Your card is " + human.printHands());

            // player's turn
            // if the Human Player has enough money, the player might be able to split the hand
            if (human.getBalance() - betPerGame >= 0) {
                human.setHands(split(human, human.getHand(), betPerGame, deck).getHands());
                // not enough money
            } else {
                human.initHands();
            }

            for (int index = 0; index < human.getHands().size(); index++) {
                int curBet = betPerGame;
                while (human.calculateValue(index) < 22) {
                    List<Card> hand = human.getHands().get(index);
                    // set the hand to the current hand of the player's
                    human.setHand(hand);

                    System.out.println("Your have: " + human.printHand(index) + ", total points " +
                            human.calculateValue(index) + ".");
                    System.out.println("Do you want to 1(hit), 2 (stand), 3 (double up): e.g. 1");
                    int choice = scan.nextInt();
                    if (choice == 1) {
                        Card newCard = deck.drawCard();
                        // Add the new card to the current hand
                        human.addCard(newCard);
                        System.out.println("The new card is: " + newCard.toString() + ".");
                    } else if (choice == 2) {
                        break;
                    } else if (choice == 3) {
                        if (human.getBalance() - curBet >= 0) {
                            human.subtractBalance(curBet);
                            curBet = betPerGame * 2;
                            Card newCard = deck.drawCard();
                            human.addCard(newCard);
                            System.out.println("The new card is: " + newCard.toString() + ".");
                            System.out.println("Your cards are now: " + human.printHand(index) + ", total points " +
                                    human.calculateValue(index) + ".");
                            break;
                        } else {
                            System.out.println("You don't have enough money, please try other options");
                        }
                    } else {
                        System.out.println("Invalid choice! Exit now.");
                        break;
                    }
                }

                // dealer's turn
                dealer.initHands();
                System.out.println("-----------------------------------------------------------------------------");
                System.out.println("Dealer's turn: ");
                System.out.println("Dealer's cards are: " + dealer.printHand(0) +
                        ", total points " + dealer.calculateValue(0) + ".");
                while (dealer.calculateValue(0) <= human.calculateValue(index) && human.calculateValue(index) <= 21) {
                    Card newCard = deck.drawCard();
                    dealer.addCard(newCard);
                    System.out.println(" ...... Dealer chooses to hit  ......");
                    System.out.println("Dealer draws a new card " + newCard.toString());
                    System.out.println("Dealer's cards are: " + dealer.printHand(0) + ", total points " + dealer.calculateValue(0) + ".");
                }

                System.out.println(" ...... Dealer chooses to stand  ......");
                System.out.println("Player's cards are: " + human.printHand(index) + ", total points "
                        + human.calculateValue(index) + ".");
                System.out.println("Dealer's cards are: " + dealer.printHand(0) +
                        ", total points " + dealer.calculateValue(0) + ".");

                // Find winner!
                int humanTotal = human.calculateValue(index);
                int dealerTotal = dealer.calculateValue(index);

                if (humanTotal <= 21 && dealerTotal <= 21) {
                    // if draw, the deal wins
                    if (humanTotal <= dealerTotal) {
                        System.out.println("Dealer wins!");
                    } else {
                        // get back own portion and the dealer's portion
                        human.addBalance(curBet * 2);
                        System.out.println("Player wins");
                    }
                } else if (humanTotal <= 21 && dealerTotal > 21) {
                    human.addBalance(curBet * 2);
                    System.out.println("Player wins");
                } else if (humanTotal > 21 && dealerTotal <= 21) {
                    System.out.println("Dealer wins");
                } else {
                    System.out.println("Dealer wins");
                }
            }

            System.out.println("Your current balance is: " + human.getBalance() + ".");
            if (human.getBalance() > 0) {
                System.out.println("Do you want to play again? 1 (Yes) or  2 (No)");
                int input = scan.nextInt();
                if (input == 1) {
                    human.cleanHands();
                    dealer.cleanHands();
                    deck.prepareDeck();
                } else {
                    canPlay = false;
                    System.out.println("You have lost all your money. Better luck next time!");
                }
            }
        }
    }

    public void doublePlayerMode(CardDeck deck) {
        HumanPlayer human = new HumanPlayer("Human Player", 100);
        Dealer dealer = new Dealer("Dealer", Integer.MAX_VALUE);

        // dealer draws a faced up card so show in the console
        boolean canPlay = true;
        while (human.getBalance() >= 0 && canPlay) {
            System.out.println("Your current balance is: " + human.getBalance() + ". Choose a bet amount:");
            Scanner scan = new Scanner(System.in);
            int betPerGame = scan.nextInt();
            while(human.getBalance() - betPerGame < 0) {
                System.out.println("You don't have enough balance for this bet. Choose a different amount.");
                betPerGame = scan.nextInt();
            }

            human.subtractBalance(betPerGame);
            // player and dealer each draws two cards
            dealer.addCard(deck.drawCard());
            human.addCard(deck.drawCard());
            dealer.addCard(deck.drawCard());
            human.addCard(deck.drawCard());

            dealer.initHands();
            human.initHands();

            System.out.println("Dealer shows: " + dealer.getHand().get(0));
            System.out.println("Your card is " + human.printHand());

            // player's turn
            if(human.getBalance() - betPerGame >= 0) {
                human.setHands(split(human, human.getHand(), betPerGame, deck).getHands());
            } else {
                human.initHands();
            }

            for(int i = 0; i < human.getHands().size(); i++) {
                int curBet = betPerGame;
                while (human.calculateValue(i) < 22) {
                    List<Card> hand = human.getHands().get(i);
                    human.setHand(hand);

                    System.out.println("Player has: " + human.printHand(i) + ", total points " + human.calculateValue(i) + ".");
                    System.out.println("Do you want to 1(hit), 2 (stand), 3 (double up): e.g. 1");
                    int choice = scan.nextInt();
                    if (choice == 1) { // hit
                        Card newCard = deck.drawCard();
                        human.addCard(newCard);
                        System.out.println("The new card is: " + newCard.toString() + ".");
                    } else if (choice == 2) { // stand
                        break;
                    } else if (choice == 3) { // double up
                        // if there is enough money
                        if (human.getBalance() - curBet >= 0) {
                            human.subtractBalance(curBet);
                            curBet = betPerGame * 2;
                            Card newCard = deck.drawCard();
                            human.addCard(newCard);
                            System.out.println("The new card is: " + newCard.toString() + ".");
                            System.out.println("Player's'cards are now: " + human.printHand(i) +
                                    ", total points " + human.calculateValue(i) + ".");
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
                dealer.initHands();

                while (dealer.calculateValue(i) < 22) {
                    List<Card> hand = dealer.getHands().get(i);
                    dealer.setHand(hand);

                    System.out.println("Dealer has: " + dealer.printHand(i) + ", total points " + dealer.calculateValue(i) + ".");
                    System.out.println("Do you want to 1 (hit) or  2 (stand)");
                    int choice = scan.nextInt();
                    if (choice == 1) { // hit
                        Card newCard = deck.drawCard();
                        dealer.addCard(newCard);
                        System.out.println("The new card is: " + newCard.toString() + ".");
                    } else { // stand
                        System.out.println("Dealer has: " + dealer.printHand(i) +
                                ", total points " + dealer.calculateValue(i) + ".");
                        break;
                    }
                }

                // check win
                int humanTotal = human.calculateValue(i);
                int dealerTotal = dealer.calculateValue(0);

                if (humanTotal <= 21 && dealerTotal <= 21) {
                    if (humanTotal <= dealerTotal) {
                        System.out.println("Dealer wins!");
                    } else {
                        human.addBalance(betPerGame*2);
                        System.out.println("Player wins");
                    }
                } else if (humanTotal <= 21 && dealerTotal > 21) {
                    human.addBalance(betPerGame*2);
                    System.out.println("Player wins");
                } else if (humanTotal > 21 && dealerTotal <= 21) {
                    System.out.println("Dealer wins");
                } else {
                    System.out.println("Dealer wins");
                }
            }

            System.out.println("Player's balance is: " + human.getBalance() + ".");
            if(human.getBalance() > 0){
                System.out.println("Do you want to play again? 1 (Yes) or 2 (No)");
                int input = scan.nextInt();
                if(input == 1){
                    human.cleanHands();
                    dealer.cleanHands();
                    deck.prepareDeck();
                } else {
                    canPlay = false;
                    System.out.println("Player has cashed out.");
                }
            }else{
                canPlay = false;
                System.out.println("Player has lost all money. Game exits!");
            }
        }
    }

    private HumanPlayer split(HumanPlayer human, List<Card> hand, int betPerGame, CardDeck deck) {
        Card firstCard = hand.get(0);
        Card secondCard = hand.get(1);
        boolean isSameCard = firstCard.getNumber() == secondCard.getNumber();
        if (isSameCard && human.getBalance() - betPerGame >= 0) {
            System.out.println("Your card is: " + human.printHand());
            Scanner scan = new Scanner(System.in);
            System.out.println("Do you want to split? type 1 for Yes or 2 for No ");
            int isSplit = scan.nextInt();
            if (isSplit == 1) {
                List<List<Card>> newHands = new ArrayList<>();
                // bet the same amount of money to the new hand
                human.subtractBalance(betPerGame);
                List<Card> firstHand = new ArrayList<>();
                firstHand.add(firstCard);
                Card newCard = deck.drawCard();
                firstHand.add(newCard);
                human.setHand(firstHand);
                System.out.println("You drew a new card: " + newCard.toString());
                newHands.add(split(human, firstHand, betPerGame, deck).getHand());

                List<Card> secondHand = new ArrayList<>();
                secondHand.add(secondCard);
                newCard = deck.drawCard();
                secondHand.add(newCard);
                human.setHand(secondHand);
                System.out.println("You drew a new card: " + newCard.toString());
                secondHand.add(deck.drawCard());
                newHands.add(split(human, firstHand, betPerGame, deck).getHand());

                human.setHands(newHands);
                return human;
            }
        }

        List<List<Card>> newHands = new ArrayList<>();
        newHands.add(hand);
        human.setHand(hand);
        human.setHands(newHands);
        return human;
    }
}
