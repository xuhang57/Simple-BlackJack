import java.util.ArrayList;
import java.util.List;

/*
 * An abstract class of Player that can be used by a HumanPlayer or a Dealer class.
 */
public abstract class Player {
    String name;
    List<Card> hand;
    List<List<Card>> hands;
    int balance;
    boolean isDealer;

    public Player(String name, int balance, boolean isDealer) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.hands = new ArrayList<>();
        this.balance = balance;
        this.isDealer = isDealer;
    }

    public void addCard(Card card) {
        this.hand.add(card);
    };

    /*
    Compute the total value of a Hand

    param: index : int, used to locate the hand in all hands that the Player might have
    return: int, total value of a hand
     */
    public int calculateValue(int index) {
        int value = 0;
        // count how many "Ace" in the hand that we have used as a value of 11
        int count = 0;
        List<Card> hand = this.hands.get(index);
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            value += card.getValue();
            // Special case: since "Ace" could have value 11 or 1
            // Here we treat "Ace" as 11 if the total value is less than 21
            // Therefore, we add another 10 (the 1 has been added above) and increase the count counter
            if (card.getNumber().equals("A")) {
                value += 10;
                count += 1;
            }
            // When the total value is larger than 21 and we have used "Ace" as 11 not 1, we need to treat it as 1
            // Therefore, subtract 10 from the total value and decrease the count counter
            while (value > 21 && count > 0) {
                value -= 10;
                count -= 1;
            }
        }
        return value;
    }

    public List<Card> getHand() {
        return this.hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public List<List<Card>> getHands() {
        return this.hands;
    }

    public void setHands(List<List<Card>> hands) {
        this.hands = hands;
    }

    public void initHands() {
        List<List<Card>> myHand = new ArrayList<>();
        myHand.add(this.hand);
        this.hands = myHand;
    }

    public void cleanHands() {
        this.hand.clear();
        this.hands.clear();
    }

    public int getBalance() {
        return this.balance;
    }

    /*
    Print the Hand that the Player possesses

    return: A String contains all Cards in the Hand that the Payer possesses
     */
    public String printHand(int index) {
        String cards = "";
        List<Card> hand = this.hands.get(index);
        for (Card card: hand) {
            cards += (card.getSuit().toString() + " " + card.getNumber() + ", ");
        }
        cards.trim();
        return cards.substring(0, cards.length() - 2);
    }

    public String printHand() {
        String cards = "";
        for(Card card: this.hand) {
            cards += (card.getSuit() + " " + card.getNumber() + ", ");
        }
        cards.trim();
        return cards.substring(0, cards.length() - 2);
    }

    /*
    Print All Hands that the Player might have

    return: A String contains all hands that the Player might have
     */
    public String printHands() {
        String res = "[";
        for (int i = 0; i < this.hands.size(); i++) {
            String cards = "[";
            List<Card> hand = hands.get(i);
            for (Card card: hand) {
                cards += (card.getSuit().toString() + " " + card.getNumber() + ", ");
            }
            cards.trim();
            res += cards.substring(0, cards.length() - 2) + "]" + "]";
        }
        return res;
    }
}
