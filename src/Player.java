import java.util.ArrayList;
import java.util.List;
/**
 * @author Fuqing Wang, Hang Xu
 */
public class Player extends AbstractPlayer {

    private List<Integer> winMultiplier;

    public Player(String name, int balance) {
        super(name, balance, false);
        this.winMultiplier = new ArrayList<>();
    }

    public Player(String name, int balance, boolean isDealer) {
        super(name, balance, isDealer);
    }

    public void addBalance(int balance) {
        this.balance += balance;
    }

    public void subtractBalance(int balance) {
        this.balance -= balance;
    }

    public void setWinMultiplier(List<Integer> list){
        this.winMultiplier = list;
    }

    public void setWinMultiplierOf(int index, int multi){
        this.winMultiplier.set(index, multi);
    }

    public int getWinMultiplierOf(int index){
        return this.winMultiplier.get(index);
    }
}
