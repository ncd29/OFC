/**
 * Enum for representing a card's value.
 * @author nathanieldavenport
 *
 */
public enum Value {
	Two(2),
	Three(3),
	Four(4),
	Five(5),
	Six(6),
	Seven(7),
	Eight(8),
	Nine(9),
	Ten(10),
	Jack(11),
	Queen(12),
	King(13),
	Ace(14);
	
	private int v;
	
	private Value(int value){
		v = value;
	}
	
	public int getValue() {
		return v;
	}
	
	public static Value setValue(int n) {
		if (n == 2) {
			return Value.Two;
		}
		if (n == 3) {
			return Value.Three;
		}
		if (n == 4) {
			return Value.Four;
		}
		if (n == 5) {
			return Value.Five;
		}
		if (n == 6) {
			return Value.Six;
		}
		if (n == 7) {
			return Value.Seven;
		}
		if (n == 8) {
			return Value.Eight;
		}
		if (n == 9) {
			return Value.Nine;
		}
		if (n == 10) {
			return Value.Ten;
		}
		if (n == 11) {
			return Value.Jack;
		}
		if (n == 12) {
			return Value.Queen;
		}
		if (n == 13) {
			return Value.King;
		}
		if (n == 14) {
			return Value.Ace;
		}
		else {
			return null;
		}
	}
	
}

