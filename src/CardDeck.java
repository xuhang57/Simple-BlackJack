import java.util.ArrayList;
import java.util.List;

public class CardDeck {
    private List<Card> deck;

    public CardDeck() {
        this.deck = new ArrayList<>();
    }

    public void prepareDeck() {
        // In a normal poker game, there are 4 suits
        int suitSize = Suit.values().length;
        for (int suit = 0; suit < suitSize; suit++) {
            // ToDo: Rank should be abstracted as well so that it is easily changeable
            for (int rank = 0; rank < 13; rank++) {
                this.deck.add(new Card(Suit.values()[suit], rank));
            }
        }
    }

    /*
     * Shuffling via using a random index and draw a Card from the card deck
     *
     * return: a Card drawn from the card deck
     */
    public Card drawCard() {
        int random = (int) Math.floor(Math.random() * this.deck.size());
        Card card = this.deck.get(random);
        this.deck.remove(random);
        return card;
    }

    public List<Card> getDeck() {
        return this.deck;
    }

    public int size() {
        return this.deck.size();
    }

}
