import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;

public class PlayerClass {
    protected String name;
    protected List<Card> hand;
    protected List<List<Card>> hands;
    protected int balance;

    public PlayerClass(String name, int balance){
        this.name = name;
        this.balance = balance;
        this.hand = new ArrayList<>();
        this.hands = new ArrayList<>();
    }

    // draw a new card
    public void hit(Card card){
        this.hand.add(card);
    }

    public static void stand(){

    }

    public static void split(){

    }

    public static void doubleUp(){

    }

    public void addBalance(int balance){
        this.balance += balance;
    }

    public void minusBalance(int balance){
        this.balance -= balance;
    }

    public int getBalance(){
        return this.balance;
    }

    public void addCard(Card card){
        this.hand.add(card);
    }

    public List<Card> getCards(){
        return hand;
    }

    public void cleanHands(){
        this.hand = new ArrayList<>();
        this.hands = new ArrayList<>();
    }

    public List<Card> getHand(){
        return this.hand;
    }

    public void setHand(List<Card> hand){
        this.hand = hand;
    }

    public void setHands(List<List<Card>> hands){
        this.hands = hands;
    }

    public void setHands(){
        List<List<Card>> newList = new ArrayList<>();
        newList.add(hand);
        this.hands = newList;
    }

    public List<List<Card>> getHands(){
        return this.hands;
    }

    public int calculateValue(int index){
        int value = 0;
        int count = 0;
        List<Card> hand = this.hands.get(index);
        for(int i = 0; i < hand.size(); i++){
            Card card = hand.get(i);
            value += card.getValue();
            if(card.getNumber() == "A"){
                value += 10;
                count += 1;
            }
            while(value > 21 && count > 0){
                value -= 10;
                count -= 1;
            }
        }
        return value;
    }

    public String handsToString(){
        String str = "[";
        for(int i = 0; i < hands.size(); i++){
            String card = "[";
            List<Card> hand = hands.get(i);
            for(int j = 0; j < hand.size(); j++){
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

    public String handToString(int index){
        String card = "";
        List<Card> hand = this.hands.get(index);
        for(int i = 0; i < hand.size(); i++){
            card += (hand.get(i).getSuit() + " " + hand.get(i).getNumber() + ", ");
        }
        card.trim();
        card = card.substring(0, card.length()-2);
        return card;
    }
}
