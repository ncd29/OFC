/**
 * Enum for representing the rank of a hand
 * @author nathanieldavenport
 *
 */

public enum HandRank {
	HighCard(0),
	Pair(1),
	PairOfSixes(2),
	PairOfSevens(3),
	PairOfEights(4),
	PairOfNines(5),
	PairOfTens(6),
	PairOfJacks(7),
	PairOfQueens(8),
	PairOfKings(9),
	PairOfAces(10),
	TwoPair(11),
	TripleOfTwos(12),
	TripleOfThrees(13),
	TripleOfFours(14),
	TripleOfFives(15),
	TripleOfSixes(16),
	TripleOfSevens(17),
	TripleOfEights(18),
	TripleOfNines(19),
	TripleOfTens(20),
	TripleOfJacks(21),
	TripleOfQueens(22),
	TripleOfKings(23),
	TripleOfAces(24),
	Straight(25),
	Flush(26),
	FullHouse(27),
	Quads(28),
	StraightFlush(29),
	RoyalFlush(30);
	
	private int value;
	
	HandRank(int n) {
		value = n;
	}
	
	public int getValue() {
		return value;
	}
}
