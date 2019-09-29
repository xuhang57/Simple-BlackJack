import java.util.ArrayList;
import java.util.List;

/**
 * @author Fuqing Wang, Hang Xu
 */
public abstract class AbstractPlayer {
    /** protected so that only accessible within the application */
    protected int balance;
    private String name;
    private List<Card> hand;
    private List<List<Card>> hands;
    private boolean isDealer;
    private static final int TWENTY_ONE = 21;
    private static final String ACE = "A";

    public AbstractPlayer(String name, int balance, boolean isDealer) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.hands = new ArrayList<>();
        this.balance = balance;
        this.isDealer = isDealer;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
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

    public void setBalance(int balance) {
        this.balance = balance;
    }

    /** add a card to current player object */
    public void addCard(Card card) {
        this.hand.add(card);
    };

    /** Compute the total value of a Hand */
    public int calculateRank(int index) {
        int value = 0;
        // count how many "Ace" in the hand that we have used as a value of 11
        int count = 0;
        List<Card> hand = this.hands.get(index);
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            value += card.getRank();
            // Special case: since "Ace" could have value 11 or 1
            // Here we treat "Ace" as 11 if the total value is less than 21
            // Therefore, we add another 10 (the 1 has been added above) and increase the count counter
            if (card.getFace().equals(ACE)) {
                value += 10;
                count += 1;
            }
            // When the total value is larger than 21 and we have used "Ace" as 11 not 1, we need to treat it as 1
            // Therefore, subtract 10 from the total value and decrease the count counter
            while (value > TWENTY_ONE && count > 0) {
                value -= 10;
                count -= 1;
            }
        }
        return value;
    }

    public String printHand() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(Card card: this.hand) {
            sb.append(card.getSuit()).append(" ").append(card.getFace()).append(", ");
        }
        sb.trimToSize();
        return sb.substring(0, sb.length() - 2) + "]";
    }

    /** Print the Hand that the Player possesses */
    public String printHand(int index) {
        StringBuilder sb = new StringBuilder();
        List<Card> hand = this.hands.get(index);
        for (Card card: hand) {
            sb.append(card.getSuit()).append(" ").append(card.getFace()).append(", ");
        }
        sb.trimToSize();
        return sb.substring(0, sb.length() - 2) + "]";
    }

    /** Print All Hands that the Player might have */
    public String printHands() {
        StringBuilder res = new StringBuilder();
        res.append("[");
        for (int i = 0; i < this.hands.size(); i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            List<Card> hand = hands.get(i);
            for (Card card: hand) {
                sb.append(card.getSuit().toString()).append(" ").append(card.getFace()).append(", ");
            }
            sb.trimToSize();

            res.append(sb.substring(0, sb.length() - 2)).append("]").append("]");
        }
        return res.toString();
    }
}
