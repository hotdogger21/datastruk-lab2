import java.util.Comparator;

public class sellerComperator implements Comparator<Bid> {

    @Override
    public int compare(Bid o1, Bid o2) {
        return Integer.compare(o1.bid, o2.bid);
    }
}
