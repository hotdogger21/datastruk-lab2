import java.util.Comparator;

public class sellerComperator implements Comparator<Bid> {

    //logic used to compare bids
    //uses integer.compare to arrange the bids in the heap into a minheap
    @Override
    public int compare(Bid o1, Bid o2) {
        return Integer.compare(o1.bid, o2.bid);
    }
}
