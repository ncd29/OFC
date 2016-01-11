import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * class for doing probability calculations
 * @author nathanieldavenport
 *
 */
public class Probability {
	
	// create the constants that will be used frequently for calculations
	// Ex. probability of hitting a flush given two of the same suit in the back without other info
	// NVM don't do this there are too many, better to calculate ad-hoc
	
	//create the Point constants
	public static double FANTASYLAND = 7.5;
	public static double FOUL = -6;
	public static double STRAIGHT_IN_BACK = 2;
	public static double FLUSH_IN_BACK = 4;
	public static double FULL_HOUSE_IN_BACK = 6;
	public static double QUADS_IN_BACK = 10;
	public static double TRIPS_IN_MIDDlE = 2;
	public static double STRAIGHT_IN_MIDDLE = 4;
	public static double FLUSH_IN_MIDDLE = 8;
	public static double FULL_HOUSE_IN_MIDDLE = 12;
	public static double QUADS_IN_MIDDLE = 20;
	public static double SIXES_IN_FRONT = 1;
	public static double SEVENS_IN_FRONT = 2;
	public static double EIGHTS_IN_FRONT = 3;
	public static double NINES_IN_FRONT = 4;
	public static double TENS_IN_FRONT = 5;
	public static double JACKS_IN_FRONT = 6;
	public static double QUEENS_IN_FRONT = 7 + FANTASYLAND;
	public static double KINGS_IN_FRONT = 8 + FANTASYLAND;
	public static double ACES_IN_FRONT = 9 + FANTASYLAND;
	public static double TRIP_TWOS_IN_FRONT = 10 + FANTASYLAND;
	public static double TRIP_THREES_IN_FRONT = 11 + FANTASYLAND;
	public static double TRIP_FOURS_IN_FRONT = 12 + FANTASYLAND;
	public static double TRIP_FIVES_IN_FRONT = 13 + FANTASYLAND;
	public static double TRIP_SIXES_IN_FRONT = 14 + FANTASYLAND;;
	public static double TRIP_SEVENS_IN_FRONT = 15 + FANTASYLAND;
	public static double TRIP_EIGHTS_IN_FRONT = 16 + FANTASYLAND;
	public static double TRIP_NINES_IN_FRONT = 17 + FANTASYLAND;
	public static double TRIP_TENS_IN_FRONT = 18 + FANTASYLAND;
	public static double TRIP_JACKS_IN_FRONT = 19 + FANTASYLAND;
	public static double TRIP_QUEENS_IN_FRONT = 20 + FANTASYLAND;
	public static double TRIP_KINGS_IN_FRONT = 21 + FANTASYLAND;
	public static double TRIP_ACES_IN_FRONT = 22 + FANTASYLAND;
	
	private Probability() {} // constructor not needed?
	
	/**
	 * returns d!
	 */
	public static double factorial(double d) {
		if (d < 2) {
			return 1;
		}
		else {
			return d * factorial(d-1);
		}
	}
	
	/**
	 * return nCr
	 */
	public static double combinations(double n, double r) {
		return factorial(n)/(factorial(r)*factorial(n-r));
	}
	
	/**
	 * calculates the probability of selecting n cards from a total t of cards,
	 * and where the cards selected fit the criteria of at least i cards from the r remaining
	 * Example: If I select 12 cards from 47 without replacement, what is the probability at least
	 * 3 of those cards are hearts, given that there are only 11 hears remaining in the total of 47
	 * The calculation of this is: SUM from 3 (i) to 11 (11Ci*36C12-i)/47C12
	 */
	public static double summation(double t, double n, double i, double r) {
		double sum = 0;
		for (int x=(int)i; x<r+1; x++) {
			sum += combinations(r,x) * combinations(t-r,n-x);
		}
		return sum/combinations(t,n);
	}
	
	/**
	 * calculates the odds of completing a flush in the back row given the opponent's board
	 * on the first or second round of betting
	 * this should work when largest suit is anything, even just one, will test
	 * not completely correct
	 */
	public static double flushInBack(HashMap<Suit,Integer> largestSuit, Board oppBoard) {
		Suit suit = (Suit) largestSuit.keySet().toArray()[0];
		int i = largestSuit.get(suit);
		int r = 13 - i;
		ArrayList<Card> oppCard = oppBoard.getCardsOfBoard();
		// find the int of suits in opponets board then call summation
		// subtract the remaining number of that suit if they exist on the opponents board
		int t;
		if (oppCard.isEmpty()) {
			t = 47;
		}
		else {
			t = 42;
			for (int x=0; x<oppCard.size(); x++) {
				if (oppCard.get(x).getSuit() == suit) {
					r -= 1;
				}
			}
		}
		return summation(t,12,i,r);
	}
	
	/**
	 * calculates the odds of completing a straight in the back given the arrangement
	 * of cards put in the back and the opponent's board
	 * cards is the test of cards that was put in the back 
	 * remaining is the player's cards not put in back
	 * doesn't consider low straights
	 *
	public static double straightInBack(ArrayList<Card> cards, ArrayList<Card> remaining, 
		Board oppBoard) {
		//TODO
		int length = cards.size();
		int t;
		ArrayList<Card> oppCards = oppBoard.getCardsOfBoard();
		t = (oppCards.isEmpty() ? 47 : 42);
		int outs;
		// check this
		Hand hand1 = new Hand(cards);
		cards = hand1.sortByRank();
		Hand hand = new Hand(cards);
		remaining.addAll(oppCards);
		if (length == 5) {
			return (hand.straightCheck() ? 1 : 0);
		}
		else if (length == 4) {
			if (hand.isGutshot(length)) {
				outs = 4;
				// find the value of the Card needed for the straight
				Value v;
				if (cards.get(3).twoAbove(cards.get(2))) {
					v = Value.setValue(cards.get(3).getValue().getValue() - 1);
				}
				else if (cards.get(2).twoAbove(cards.get(1))) {
					v = Value.setValue(cards.get(2).getValue().getValue() - 1);
				}
				else {
					v = Value.setValue(cards.get(1).getValue().getValue() - 1);
				}
				// if an out is contained in the other visible cards, remove it
				for (int i=0; i<remaining.size(); i++) {
					if (remaining.get(i).getValue() == v) {
						outs --;
					}
				}
				return 1 - ((combinations(t-outs,12))/(combinations(t,12)));
			}
			else if (hand.isOpenEnded(length)) {
				outs = 8;
				Value v1 = Value.setValue(cards.get(3).getValue().getValue() + 1);
				Value v2 = Value.setValue(cards.get(0).getValue().getValue() - 1);
				for (int i=0; i<remaining.size(); i++) {
					if (remaining.get(i).getValue() == v1 || remaining.get(i).getValue() == v2) {
						outs --;
					}
				}
				return 1 - ((combinations(t-outs,12))/(combinations(t,12)));
			}
			else {
				return 0;
			}
		}
		else if (length == 3) {
			if (hand.isOpenEnded(length)) {
				outs = 8;
				Value v1 = Value.setValue(cards.get(3).getValue().getValue() + 1);
				Value v2 = Value.setValue(cards.get(0).getValue().getValue() - 1);
				for (int i=0; i<remaining.size(); i++) {
					if (remaining.get(i).getValue() == v1 || remaining.get(i).getValue() == v2) {
						outs --;
					}
				}
				return 1 - ((combinations(t-outs,12))/(combinations(t,12)));
			}
			else if (hand.isGutshot(length)) {
				
			}
			else if (hand.isDoubleGutshot(length)) {
				
			}
			else {
				
			}
		}
		else if (length == 2) {
			if (hand.isOpenEnded(length)) {
				
			}
			else if (hand.isGutshot(length)) {
				
			}
			else if (hand.isDoubleGutshot(length)) {
				
			}
			else if (hand.isTripleGutshot()) {
				
			}
			else {
				
			}
		}
		else { // length == 1
			
		}
	}
	*/
	
	/**
	 * calculates the odds of completing a three of a kind in the back given the arrangement
	 * of cards put in the back and the opponent's board
	 */
	public static double tripleInBack(Board oppBoard) {
		//TODO
		return 0;
	}
	
	/**
	 * calculates the odds of completing a two pair in the back given the arrangement
	 * of cards put in the back and the opponent's board
	 */
	public static double twoPairInBack(Board oppBoard) {
		//TODO
		return 0;
	}
	
	/**
	 * calculates the odds of completing a full house in the back given the arrangement
	 * of cards put in the back and the opponent's board
	 */
	public static double fullHouseInBack(Board oppBoard) {
		//TODO
		return 0;
	}
	
	/**
	 * calculates the odds of completing a four of a kind in the back given the arrangement
	 * of cards put in the back and the opponent's board
	 */
	public static double quadsInBack(Board oppBoard) {
		//TODO
		return 0;
	}
	
	/**
	 * calculates the expected points that the cards will earn when placed in the back
	 * will call helper functions to calculate this
	 * calculateBestAction will call this to decide which cards generate the most points
	 * when put in the back
	 * this function is called on a SELECTION of cards of the five, NOT all five 
	 * (except for the one case if you check if all 5 are called).
	 * This is because the probabilities will get messed up if we call
	 * each "inBack" function on the cards that yield the highest probability for
	 * its specific hand.  So how do we determine which cards to put here?:
	 * - I think now we should test ALL arrangements
	 * - cards that make up the largest Suit
	 * - pairs
	 * - two pairs
	 * - pair + 1 vs just pair, stuff like that
	 * - straight draws vs. flush draws
	 * - etc.
	 * cards is the cards specific to the back, 
	 * other cards is the cards put elsewhere on the person's board
	 *
	public static double expectedPointsInBack(ArrayList<Card> cards, ArrayList<Card> otherCards,
		Board oppBoard) {
		//TODO
		// this should go elsewhere, perhaps
		Hand hand = new Hand(cards);
		HashMap<Suit,Integer> largestSuits = hand.largestSuit();
		// this is expectedPoints if hit,
		double twoPairOdds = twoPairInBack(oppBoard);
		double tripsOdds = tripleInBack(oppBoard);
		//double straightOdds = straightInBack(cards, otherCards, oppBoard);
		double flushOdds = flushInBack(largestSuits,oppBoard);
		double fullHouseOdds = fullHouseInBack(oppBoard);
		double quadsOdds = quadsInBack(oppBoard);
		// this is going to be way too high, need the odds of a two pair, and not a flush, and
		// flush, but not anything else, etc.
		return straightOdds * STRAIGHT_IN_BACK + flushOdds * FLUSH_IN_BACK + 
		fullHouseOdds * FULL_HOUSE_IN_BACK + quadsOdds * QUADS_IN_BACK + 
		// assume if one of the above hands is not hit, a foul occurred, 
		// which is worth -6 points?
		(1 - (twoPairOdds + tripsOdds + straightOdds + flushOdds + fullHouseOdds
		+ quadsOdds)) * FOUL;
	}
	*/
}
