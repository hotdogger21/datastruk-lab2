
public class Bid {
	//name & price
	final public String name;
	final public int bid;

	public Bid(String name, int bid) {
		this.name = name;
		this.bid = bid;
	}

	//number used to identify bids
	public int hashCode() {
		return 1 + 23*bid + 31*name.hashCode();
	}

    //logic used to determine if two bids are identical
	public boolean equals(Object obj){
		if (obj == null || !(obj instanceof Bid)) return false;

		Bid bid = (Bid) obj;

		// compares the bid hashcodes
		if(bid.hashCode() == this.hashCode()) {
			return true;
		}

		return false;
	}

	// simple tostring that prints name then price
	public String toString(){
		return name + " " + bid;
	}
}

