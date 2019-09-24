public class Card {
    private String suit;
    private int value;
    private String number;

    private static String values[] = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

    public Card(String suit, int value){
        this.suit = suit;
        this.value = value + 1;
        this.number = values[value];
    }

    public int getValue(){
        return value;
    }

    public String toString(){
        return suit + " " + number;
    }
}

