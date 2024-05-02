import java.util.Comparator;

public class buyerComperator implements Comparator<Bid> {


    // We multiply by negative 1 to make sure each parent is bigger than its children
    // very similar to the comperator in sellerComperator except we multiply this value with -1 to get the opposite behaviour of integer.compare
    // in this way we get a maxheap instead of a minheap when we use the same logic on this comperator

    @Override
    public int compare(Bid o1, Bid o2) {
        return Integer.compare(o1.bid, o2.bid)*-1;
    }
}
