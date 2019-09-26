public class HumanPlayer extends Player {

    public HumanPlayer(String name, int balance) {
        super(name, balance, false);
    }
    public HumanPlayer(String name, int balance, boolean isDealer) {
        super(name, balance, isDealer);
    }

    public void addBalance(int balance) {
        this.balance += balance;
    }

    public void subtractBalance(int balance) {
        this.balance -= balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
