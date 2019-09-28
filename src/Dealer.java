public class Dealer extends GameParticipant {

    public Dealer(String name, int balance) {
        super(name, balance, true);
    }

    public Dealer(String name, int balance, boolean isDealer) {
        super(name, balance, isDealer);
    }


}
