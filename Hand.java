import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * A hand represents a set of cards of any amount
 * @author nathanieldavenport
 *
 * @param cards: the cards that make up this hand
 * @param points: the points that this hand is worth in its row, 
 * points are 0 until the hand is finished
 * @param row: the row this hand is in
 */
public class Hand {
	
	private ArrayList<Card> cards;
	private int expectedPoints;
	private Row row;
	
	public Hand(ArrayList<Card> c,Row r) {
		cards = c;
		expectedPoints = 0; // may need to change this
		row = r;
	}
	
	// hand with just the cards
	public Hand(ArrayList<Card> c) {
		cards = c;
		expectedPoints = 0;
		row = null;
	}
	
	public ArrayList<Card> getCards() {
		return cards;
	}
	
	/**
	 * sort the cards in order of their rank
	 */
	public ArrayList<Card> sortByRank() {
		ArrayList<Card> cards = this.cards;
		for (int j = 1; j < cards.size(); j++) {
			int key = cards.get(j).getValue().getValue();
			Suit s = cards.get(j).getSuit();
			int i = j-1;
			while (i>=0 && cards.get(i).getValue().getValue() > key) {
				cards.get(i+1).setValue(cards.get(i).getValue());
				cards.get(i+1).setSuit(cards.get(i).getSuit());
				i = i - 1;
				cards.get(i+1).setValue(Value.setValue(key));	
				cards.get(i+1).setSuit(s);
			}
		}
		return cards;
	}
	
	/**
	 * sort the cards in order of their suit
	 */
	public ArrayList<Card> sortBySuit() {
		//TODO
		return new ArrayList<Card>();
	}
	
	/**
	 * returns as a hashmap the most common suits in a list of cards
	 * as the key and the number of times it occurs as the value
	 * multiple suits are returned if there is a tie
	 */
	public HashMap<Suit,Integer> mostCommonSuit() {
		HashMap<Suit,Integer> mostCommon = new HashMap<Suit,Integer>();
		int hearts = 0; int spades = 0; int clubs = 0; int diamonds = 0;
		for (int i=0; i<this.cards.size(); i++) {
			if (this.cards.get(i).getSuit() == Suit.clubs) {
				clubs += 1;
				if (clubs == 2) {
					mostCommon.put(Suit.clubs, 2);
				}
				if (clubs >= 3) {
					mostCommon.clear();
					mostCommon.put(Suit.clubs,clubs);
				}
			}
			else if (this.cards.get(i).getSuit() == Suit.spades) {
				spades += 1;
				if (spades == 2) {
					mostCommon.put(Suit.spades, 2);
				}
				if (spades >= 3) {
					mostCommon.clear();
					mostCommon.put(Suit.spades,spades);
				}
			}
			else if (this.cards.get(i).getSuit() == Suit.hearts) {
				hearts += 1;
				if (hearts == 2) {
					mostCommon.put(Suit.hearts, 2);
				}
				if (hearts >= 3) {
					mostCommon.clear();
					mostCommon.put(Suit.hearts,hearts);
				}
			}
			else {
				diamonds += 1;
				if (diamonds == 2) {
					mostCommon.put(Suit.diamonds, 2);
				}
				if (clubs >= 3) {
					mostCommon.clear();
					mostCommon.put(Suit.diamonds,diamonds);
				}
			}
		}
		return mostCommon;
	}
	
	/**
	 * returns the largest suit with at least two of that suit in the hand
	 * so a hand of QsJsTh4h8d will return Suit.spades because Qs > Th
	 * currently assumes input contains 5 cards?
	 */
	public HashMap<Suit,Integer> largestSuit() {
		HashMap<Suit,Integer> mostFrequentSuits = this.mostCommonSuit();
		HashMap<Suit,Integer> biggestSuit = new HashMap<Suit,Integer>();
		if (mostFrequentSuits.size() == 1) {
			biggestSuit = mostFrequentSuits;
		}
		else {
			ArrayList<Card> cards = this.sortByRank();
			Value v = cards.get(0).getValue();
			Value v2 = cards.get(1).getValue();
			Suit s = cards.get(0).getSuit();
			Suit s2 = cards.get(1).getSuit();
			Suit s3 = cards.get(2).getSuit();
			Value v3 = cards.get(2).getValue();
			Value v4 = cards.get(3).getValue();
			Value v5 = cards.get(4).getValue();
			if (mostFrequentSuits.containsKey(s)) {
				biggestSuit.put(s, 2);
				if (v == v2 && v3 == v4) {
					biggestSuit.put(s2, 2);
				}
			}
			else {
				biggestSuit.put(s2,2);
				if (v2 == v3 && v4 == v5) {
					biggestSuit.put(s3,2);
				}
			}
		}
		return biggestSuit;
	}
	
	/**
	 * remove duplicates from the card list and returns the cards sorted in order
	 */
	private ArrayList<Card> removeDups() {
		//TODO
		ArrayList<Card> cards = this.sortByRank();
		for (int i=1; i<cards.size(); i++) {
			if (cards.get(i).getValue() == cards.get(i-1).getValue()) {
				cards.remove(cards.get(i-1));
			}
		}
		return cards;
	}
	
	/** 
	 * reverses the list of cards
	 */
	private static ArrayList<Card> reverse(ArrayList<Card> cards) {
		ArrayList<Card> result = new ArrayList<Card>();
		for (int i=cards.size()-1; i>=0; i--) {
			result.add(cards.get(i));
		}
		return result;
	}
	
	/**
	 * returns the most common suit if there is only one
	 * return not specified if there is a tie for most common suit
	 */
	private Suit onlyOneSuit() {
		HashMap<Suit,Integer> mostCommon = this.mostCommonSuit();
		Object[] mostCommonArray = mostCommon.keySet().toArray();
		return (Suit) mostCommonArray[0];
	}
	
	/**
	 * check if a card list contains a royal flush
	 * assumes length is 5
	 * tested
	 */
	public boolean royalFlushCheck() {
		ArrayList<Card> cards = this.cards;
		if (cards.size() < 5) {
			return false;
		}
		else {
			Suit mostCommonSuit = this.onlyOneSuit();
			this.sortByRank();
			if (cards.get(0).getValue() == Value.Ten && cards.get(0).getSuit() == mostCommonSuit
			 && cards.get(1).getValue() == Value.Jack && cards.get(1).getSuit() == mostCommonSuit
			 &&	cards.get(2).getValue() == Value.Queen && cards.get(2).getSuit() == mostCommonSuit
			 && cards.get(3).getValue() == Value.King && cards.get(3).getSuit() == mostCommonSuit
			 && cards.get(4).getValue() == Value.Ace && cards.get(4).getSuit() == mostCommonSuit)
			{
				return true;
			}
			else {
				return false;
			}
		}
	}
	
	/**
	 * check if a card list contains a straight flush
	 * assumes length is 5
	 */
	private boolean straightFlushCheck() {
		ArrayList<Card> cards = this.cards;
		if (cards.size() < 5) {
			return false;
		}
		else {
			Suit mostCommonSuit = this.onlyOneSuit();
			this.sortByRank();
			for (int i=0; i<cards.size()-1; i++) {
				if (!(cards.get(i).oneAbove(cards.get(i+1)) 
					&& cards.get(i).getSuit() == mostCommonSuit
					&& cards.get(i+1).getSuit() == mostCommonSuit)) {
						return false;
				}
			}
			return true;
		}
	}
	
	/**
	 * check if a card list contains a four of a kind
	 * assume input has 5 cards
	 */
	private boolean quadsCheck() {
		ArrayList<Card> cards = this.cards;
		if (cards.size() < 5) {
			return false;
		}
		this.sortByRank();
		int counter = 0;
		for (int i=0; i<cards.size()-1; i++) {
			if (cards.get(i+1).getValue() == cards.get(i).getValue()) {
				counter ++;
			}
		}
		return counter == 3 && cards.get(1) == cards.get(3);
	}
	
	/**
	 * check if a card list contains a full house
	 * assume input has 5 cards and assume quads have already been checked
	 */
	private boolean fullHouseCheck() {
		ArrayList<Card> cards = this.cards;
		if (cards.size() < 5) {
			return false;
		}
		this.sortByRank();
		int counter = 0;
		for (int i=0; i<cards.size()-1; i++) {
			if (cards.get(i+1).getValue() == cards.get(i).getValue()) {
				counter ++;
			}
		}
		return counter == 3;
	}
	
	/**
	 * check if a card list contains a flush
	 * assumes input is 5 cards
	 */
	private boolean flushCheck() {
		ArrayList<Card> cards = this.cards;
		if (cards.size() < 5) {
			return false;
		}
		else {
			Suit mostCommonSuit = this.onlyOneSuit();
			for (int i=0; i<cards.size()-1; i++) {
				if (cards.get(i).getSuit() != mostCommonSuit) {
					return false;
				}
			}
			return true;
		}
	}
	
	/**
	 * check if a card list contains an A-5 straight
	 * helper for low Straight and straightCheck?
	 */
	private boolean lowStraightCheck() {
		ArrayList<Card> cards = this.removeDups();
		if (cards.size() < 5) {
			return false;
		}
		else {
			boolean result = false;
			if (cards.get(0).getValue() == Value.Ace) {
				result = true;
			}
			cards = reverse(cards);
			if (cards.get(0).getValue() != Value.Two || cards.get(1).getValue() != Value.Three
			 || cards.get(2).getValue() != Value.Four || cards.get(3).getValue() != Value.Five) {
				result = false;
			}
			return result;
		}
		
	}
	
	/**
	 * check if a card list contains a straight
	 * assumes input is 5 cards
	 */
	public boolean straightCheck() {
		ArrayList<Card> cards = this.removeDups();
		if (cards.size() < 5) {
			return false;
		}
		boolean result = false;
		System.out.println(cards.size() + "cards size");
		if (cards.get(4).oneAbove(cards.get(3)) && cards.get(3).oneAbove(cards.get(2))
			&& cards.get(2).oneAbove(cards.get(1)) && cards.get(1).oneAbove(cards.get(0))) {
			result = true;
		}
		return result || this.lowStraightCheck();
	}
	
	//helper functions for calculating probabilities with straights
	
	/**
	 * returns true if the straight draw is open ended, meaning that all the cards
	 * are one above the previous card when sorted
	 * length must be between 2 and 4
	 */
	public boolean isOpenEnded(int length) {
		ArrayList<Card> cards = this.sortByRank();
		boolean result = false;
		int i = length - 1;
		if (cards.get(i).oneAbove(cards.get(i-1))) {
			result = true;
		}
		if (length >= 3) {
			if (!cards.get(i-1).oneAbove(cards.get(i-2))) {
				result = false;
			}
		}
		if (length == 4) {
			if (!cards.get(i-2).oneAbove(cards.get(i-3))) {
				result = false;
			}
		}
		return result;
	}
	
	/**
	 * returns true if the straight draw is a gutshot, meaning that 
	 * one specific card would be required to complete the straight
	 * length must be between 2 and 4 Ex: 5679, 568, 68
	 */
	public boolean isGutshot(int length) {
		ArrayList<Card> cards = this.sortByRank();
		if (length == 4) {
			// Ex: 5789 
			return cards.get(3).oneAbove(cards.get(2)) && 
				   cards.get(2).oneAbove(cards.get(1)) && 
				   cards.get(1).twoAbove(cards.get(0)) ||
				   // Ex: 5689
				   cards.get(3).oneAbove(cards.get(2)) && 
				   cards.get(2).twoAbove(cards.get(1)) && 
				   cards.get(1).oneAbove(cards.get(0)) ||
				   // Ex: 5679
				   cards.get(3).twoAbove(cards.get(2)) && 
				   cards.get(2).oneAbove(cards.get(1)) && 
				   cards.get(1).oneAbove(cards.get(0));
		}
		else if (length == 3) {
			// Ex: 578
			return  cards.get(2).oneAbove(cards.get(1)) && 
					cards.get(1).twoAbove(cards.get(0)) ||
					// Ex: 568
					cards.get(2).twoAbove(cards.get(1)) && 
					cards.get(1).oneAbove(cards.get(0));		   
		}
		else { // length = 2
			// Ex: 68
			return cards.get(1).twoAbove(cards.get(0));
		}
	}
	
	/**
	 * returns true if the straight draw is a double gutshot, meaning that 
	 * two specific cards would be required to complete the straight
	 * length must be between 2 and 3 Ex: 579, 69
	 */
	public boolean isDoubleGutshot(int length) {
		ArrayList<Card> cards = this.sortByRank();
		if (length == 3) {
			return cards.get(2).twoAbove(cards.get(1)) &&
				   cards.get(1).twoAbove(cards.get(0)) ||
				   cards.get(2).threeAbove(cards.get(1)) &&
				   cards.get(1).oneAbove(cards.get(0)) ||
				   cards.get(2).oneAbove(cards.get(1)) &&
				   cards.get(1).threeAbove(cards.get(0));
		}
		else { // length = 2
			return cards.get(1).threeAbove(cards.get(0));
		}
	}
	
	/**
	 * returns true if the straight draw is a triple gutshot, meaning that 
	 * three specific cards would be required to complete the straight
	 * length must be 2 Ex: 59
	 */
	public boolean isTripleGutshot() {
		ArrayList<Card> cards = this.sortByRank();
		return cards.get(1).fourAbove(cards.get(0));
	}
	
	/**
	 * check if a card list contains a three of a kind
	 * assume input is 5 or 3 cards
	 */
	private boolean tripleCheck() {
		ArrayList<Card> cards = this.cards;
		this.sortByRank();
		int counter1 = 0;
		int counter2 = 0;
		Value valueOfTriple = this.valueOfTriple();
		if (cards.size() == 3) {
			for (int i=0; i<cards.size(); i++) {
				if (cards.get(i).getValue() == valueOfTriple) {
					counter1 ++;
				}
			}
		}
		else {
			for (int i=0; i<cards.size(); i++) {
				if (cards.get(i).getValue() == valueOfTriple) {
					counter2 ++;
				}
			}
		}
		return counter1 == 3 || counter2 == 3;
	}
	
	/**
	 * check if a card list contains two pair
	 * assumes that input is 5 cards and that
	 * triple  and everything else higher has already been checked
	 */
	private boolean twoPairCheck() {
		ArrayList<Card> cards = this.cards;
		this.sortByRank();
		int counter = 0;
		if (cards.size() < 5) {
			return false;
		}
		else {
			for (int i=0; i<cards.size()-1; i++) {
				if (cards.get(i+1).getValue() == cards.get(i).getValue()) {
					counter ++;
				}
			}
		}
		return counter == 2;
	}
	
	/**
	 * check if a card list contains a pair
	 * assumes that input has 3 or 5 cards
	 * and everything else has been checked
	 */
	private boolean pairCheck() {
		ArrayList<Card> cards = this.cards;
		this.sortByRank();
		int counter = 0;
		Value valueOfPair = this.valueOfPair();
		for (int i=0; i<cards.size()-1; i++) {
			if (cards.get(i).getValue() == valueOfPair) {
				counter ++;
			}
		}
		return counter > 1;
	}
	
	/**
	 * helper function that compares hands whose best hand's are both high cards
	 * and returns the better hand
	 */
	private Hand compareHighCard(Hand h){
		//TODO
		return h;
	}
	
	/**
	 * helper function that determines the rank of three of a kind in the hand
	 * if the hand does not contain three of a kind, returns null
	 * assume the input is 3 or 5 cards
	 * and quads has already been checked
	 */
	private Value valueOfTriple(){
		ArrayList<Card> cards = this.cards;
		this.sortByRank();
		int counter1 = 0;
		if (cards.size() == 3) {
			for (int i=0; i<cards.size()-1; i++) {
				if (cards.get(i+1).getValue() == cards.get(i).getValue()) {
					counter1 ++;
				}
			}
		}
		else {
			for (int i=0; i<cards.size()-1; i++) {
				if (cards.get(i+1).getValue() == cards.get(i).getValue()
				&& cards.get(i+2).getValue() == cards.get(i+1).getValue()) {
					return cards.get(i).getValue();
				}
			}
		}
		return (counter1 == 2 ? cards.get(0).getValue() : null);
	}
	
	/**
	 * helper function that determines the rank of pair in the hand
	 * if the hand does not contain a pair, return null
	 * assumes that two pair and triple have not been checked
	 */
	private Value valueOfPair(){
		ArrayList<Card> cards = this.cards;
		this.sortByRank();
		for (int i=0; i<cards.size()-1; i++) {
			if (cards.get(i+1).getValue() == cards.get(i).getValue()) {
				return cards.get(i).getValue();
			}
		}
		return null;
	}
	
	/**
	 * helper function that compares hands whose best hand's are both pairs
	 */
	private Hand comparePair(Hand h){
		//TODO
		return h;
	}
	
	/**
	 * helper function that compares hands whose best hand's are both two pairs
	 */
	private Hand compareTwoPair(Hand h){
		//TODO
		return h;
	}
	
	/**
	 * helper function that compares hands whose best hand's are both three of a kind
	 */
	private Hand compareTriple(Hand h){
		//TODO
		return h;
	}
	
	/**
	 * helper function that compares hands whose best hand's are both straights
	 */
	private Hand compareStraight(Hand h){
		//TODO
		return h;
	}
	
	/**
	 * helper function that compares hands whose best hand's are both flushes
	 */
	private Hand compareFlush(Hand h){
		//TODO
		return h;
	}
	
	/**
	 * helper function that compares hands whose best hand's are both full houses
	 */
	private Hand compareFullHouse(Hand h){
		//TODO
		return h;
	}
	
	/**
	 * helper function that compares hands whose best hand's are both four of a kind
	 */
	private Hand compareQuads(Hand h){
		//TODO
		return h;
	}
	
	/**
	 * helper function that compares hands whose best hand's are both straight flushes
	 */
	private Hand compareStraightFlush(Hand h){
		//TODO
		return h;
	}
	
	/**
	 * determines the best 3 or 5 card hand that can be made using the given cards
	 * this will be called to determine a winner, but also to determine the best
	 * placement for cards as they are dealt
	 */
	public double determineBestHandThree(){
			Value v = this.valueOfTriple();
			Value v2 = this.valueOfPair();
			if (v != null) {
				if (v == Value.Two) { return Probability.TRIP_TWOS_IN_FRONT; }
				else if (v == Value.Three) { return Probability.TRIP_THREES_IN_FRONT; }
				else if (v == Value.Four) { return Probability.TRIP_FOURS_IN_FRONT; }
				else if (v == Value.Five) {return Probability.TRIP_FIVES_IN_FRONT; }
				else if (v == Value.Six) {return Probability.TRIP_SIXES_IN_FRONT; }
				else if (v == Value.Seven) { return Probability.TRIP_SEVENS_IN_FRONT; }
				else if (v == Value.Eight) { return Probability.TRIP_EIGHTS_IN_FRONT; }
				else if (v == Value.Nine) { return Probability.TRIP_NINES_IN_FRONT; }
				else if (v == Value.Ten) { return Probability.TRIP_TENS_IN_FRONT; }
				else if (v == Value.Jack) { return Probability.TRIP_TWOS_IN_FRONT; }
				else if (v == Value.Queen) { return Probability.TRIP_TWOS_IN_FRONT; }
				else if (v == Value.King) { return Probability.TRIP_TWOS_IN_FRONT; }
				else { //v == Value.Ace 
					return Probability.TRIP_ACES_IN_FRONT;
				}
			}
			else if (v2 != null) {
				if (v2.getValue() < 2) { return 0; }
				else if (v2.getValue() == 2) { return Probability.SIXES_IN_FRONT; }
				else if (v2.getValue() == 3) { return Probability.SEVENS_IN_FRONT; }
				else if (v2.getValue() == 4) { return Probability.EIGHTS_IN_FRONT; }
				else if (v2.getValue() == 5) { return Probability.NINES_IN_FRONT; }
				else if (v2.getValue() == 6) { return Probability.TENS_IN_FRONT; }
				else if (v2.getValue() == 7) { return Probability.JACKS_IN_FRONT; }
				else if (v2.getValue() == 8) { return Probability.QUEENS_IN_FRONT; }
				else if (v2.getValue() == 9) { return Probability.KINGS_IN_FRONT; }
				else { // valye == 10
					return Probability.ACES_IN_FRONT;
				}
			}
			else {
				return 0;
			}
	}
	
	/**
	 * determine best hand for five cards
	 */
	public double determineBestHandFive() {
		if (this.royalFlushCheck()) {
			return HandRank.RoyalFlush.getValue();
		}
		else if (this.straightFlushCheck()) {
			return HandRank.StraightFlush.getValue();
		}
		else if (this.quadsCheck()) {
			return HandRank.Quads.getValue();
		}
		else if (this.fullHouseCheck()) {
			return HandRank.FullHouse.getValue();
		}
		else if (this.flushCheck()) {
			return HandRank.Flush.getValue();
		}
		else if (this.straightCheck()) {
			return HandRank.Straight.getValue();
		}
		else if (this.tripleCheck()) {
			Value v = this.valueOfTriple();
			if (v == Value.Two) { return HandRank.TripleOfTwos.getValue() + 6.5; }
			else if (v == Value.Three) { return HandRank.TripleOfThrees.getValue() + 6.5; }
			else if (v == Value.Four) { return HandRank.TripleOfFours.getValue() + 6.5 ; }
			else if (v == Value.Five) { return HandRank.TripleOfFives.getValue() + 6.5; }
			else if (v == Value.Six) { return HandRank.TripleOfSixes.getValue() + 6.5; }
			else if (v == Value.Seven) { return HandRank.TripleOfSevens.getValue() + 6.5; }
			else if (v == Value.Eight) { return HandRank.TripleOfEights.getValue() + 6.5; }
			else if (v == Value.Nine) { return HandRank.TripleOfNines.getValue() + 6.5; }
			else if (v == Value.Ten) { return HandRank.TripleOfTens.getValue() + 6.5; }
			else if (v == Value.Jack) { return HandRank.TripleOfJacks.getValue() + 6.5; }
			else if (v == Value.Queen) { return HandRank.TripleOfQueens.getValue() + 6.5; }
			else if (v == Value.King) { return HandRank.TripleOfKings.getValue() + 6.5; }
			else { return HandRank.TripleOfAces.getValue() + 6.5; }
		}
		else if (this.twoPairCheck()) {
			return HandRank.TwoPair.getValue() + 6; // 11 + 6 = 17, between AA and 222
		}
		else if (this.pairCheck()) {
			Value v = this.valueOfPair();
			if (v == Value.Two) { return HandRank.Pair.getValue(); }
			else if (v == Value.Three) { return HandRank.Pair.getValue(); }
			else if (v == Value.Four) { return HandRank.Pair.getValue(); }
			else if (v == Value.Five) { return HandRank.Pair.getValue(); }
			else if (v == Value.Six) { return HandRank.PairOfSixes.getValue() - 1; }
			else if (v == Value.Seven) { return HandRank.PairOfSevens.getValue() - 1; }
			else if (v == Value.Eight) { return HandRank.PairOfEights.getValue() - 1; }
			else if (v == Value.Nine) { return HandRank.PairOfNines.getValue() - 1; }
			else if (v == Value.Ten) { return HandRank.PairOfTens.getValue() - 1; }
			else if (v == Value.Jack) { return HandRank.PairOfJacks.getValue() - 1; }
			else if (v == Value.Queen) { return HandRank.PairOfQueens.getValue() + 6.5; }
			else if (v == Value.King) { return HandRank.PairOfKings.getValue() + 6.5; }
			else { return HandRank.TripleOfAces.getValue() + 6.5; }
		}
		else {
			return HandRank.HighCard.getValue();
		}
	}
	
	/**
	 * compares two hands and determines returns the better one
	 */
	public Hand compareHands(Hand h){
		//TODO
		return null;
	}
	
	/**
	 * returns the string representation of a hand
	 */
	public String toString(){
		//TODO
		return "";
	}
	
}
