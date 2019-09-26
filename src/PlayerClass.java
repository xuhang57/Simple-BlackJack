import java.util.ArrayList;
import java.util.List;

public class PlayerClass {
    private String name;
    // hand is a list of cards that a player can have
    private List<Card> hand;
    // hands contains all hand that a player might have
    private List<List<Card>> hands;
    private int balance;

    public PlayerClass(String name, int balance) {
        this.name = name;
        this.balance = balance;
        this.hand = new ArrayList<>();
        this.hands = new ArrayList<>();
    }

    public void addBalance(int balance) {
        this.balance += balance;
    }

    public void minusBalance(int balance) {
        this.balance -= balance;
    }

    public int getBalance() {
        return this.balance;
    }

    public void addCard(Card card) {
        this.hand.add(card);
    }

    public List<Card> getCards() {
        return this.hand;
    }

    public void cleanHands() {
        this.hand = new ArrayList<>();
        this.hands = new ArrayList<>();
    }

    public List<Card> getHand() {
        return this.hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public void setHands() {
        List<List<Card>> newList = new ArrayList<>();
        newList.add(hand);
        this.hands = newList;
    }

    public void setHands(List<List<Card>> hands) {
        this.hands = hands;
    }

    public List<List<Card>> getHands() {
        return this.hands;
    }

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

    /*
    Print All Hands that the Player might have

    return: A String contains all hands that the Player might have
     */
    public String handsToString() {
        String str = "[";
        for(int i = 0; i < hands.size(); i++) {
            String card = "[";
            List<Card> hand = hands.get(i);
            for(int j = 0; j < hand.size(); j++) {
                card += (hand.get(j).getSuit() + " " + hand.get(j).getNumber() + ", ");
            }
            card.trim();
            card = card.substring(0, card.length()-2);
            card += "]";
            str += card;
            str += "]";
        }
        return str;
    }

    /*
    Print the Hand that the Player possesses

    return: A String contains all Cards in the Hand that the Payer possesses
     */
    public String handToString(int index) {
        String card = "";
        List<Card> hand = this.hands.get(index);
        for (int i = 0; i < hand.size(); i++) {
            card += (hand.get(i).getSuit() + " " + hand.get(i).getNumber() + ", ");
        }
        card.trim();
        card = card.substring(0, card.length()-2);
        return card;
    }
}
