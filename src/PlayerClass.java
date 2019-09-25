import java.util.ArrayList;

public class PlayerClass {
    protected String name;
    protected ArrayList<Card> hand;
    protected int money;

    public PlayerClass(String name, int money){
        this.name = name;
        this.money = money;
        this.hand = new ArrayList<>();
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

    public void winMoney(int money){
        this.money += money;
    }

    public void loseMoney(int money){
        this.money -= money;
    }

    public int getMoney(){
        return this.money;
    }

    public void addCard(Card card){
        this.hand.add(card);
    }

    public ArrayList<Card> getCards(){
        return hand;
    }


    public int calculateValue(){
        int value = 0;
        int count = 0;
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

    public String cardsToString(){
        String card = "";
        for(int i = 0; i < hand.size(); i++){
            card += (hand.get(i).getSuit() + " " + hand.get(i).getNumber() + ", ");
        }
        card.trim();
        return card;
    }
}
