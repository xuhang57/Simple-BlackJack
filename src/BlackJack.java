import java.util.ArrayList;

public class BlackJack {
    private static ArrayList<Card> deck = new ArrayList<>();
    private static String suits[] = {"Spade", "Diamond", "Heart", "Clubs"};

    public static void init() {
        for(int i = 0; i < suits.length; i++){
            for(int j = 0; j < 13; j++){
                deck.add(new Card(suits[i], j));
            }
        }
    }

    public int cardCompare(ArrayList<Card> player, ArrayList<Card> dealer){
        int valPlayer = 0;
        int valDealer = 0;
        for(int i = 0; i < player.size(); i++){
            valPlayer += player.get(i).getValue();
        }

        for(int i = 0; i < dealer.size(); i++){
            valDealer += dealer.get(i).getValue();
        }

        if(valPlayer > valDealer){
            return 1;
        }else if(valPlayer < valDealer){
            return -1;
        }else{
            return 0;
        }
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
        System.out.println("Hello World!");
        init();
        System.out.println(deck.toString());

        //
        // check if anyone wins
    }
}
