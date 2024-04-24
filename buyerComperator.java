import java.util.Comparator;

public class buyerComperator implements Comparator<Bid> {


    // We multiply by negative 1 to make sure each parent is bigger than its children

    @Override
    public int compare(Bid o1, Bid o2) {
        return Integer.compare(o1.bid, o2.bid)*-1;
    }
}
