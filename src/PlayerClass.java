public class PlayerClass {
    protected String name;
    protected String cards[];
    protected int money;

    public PlayerClass(){
        this.name = name;
        this.cards = cards;
        this.money = money;
    }

    public int getMoney(){
        return this.money;
    }

    public String getCards(){
        String card = "";
        for(int i = 0; i < cards.length; i++){
            card += cards[i] + " ";
        }
        card.trim();
        return card;
    }
}
