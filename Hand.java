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
			int i = j-1;
			while (i>=0 && cards.get(i).getValue().getValue() > key) {
				cards.get(i+1).setValue(cards.get(i).getValue());
				cards.get(i+1).setSuit(cards.get(i).getSuit());
				i = i - 1;
			cards.get(i+1).setValue(Value.setValue(key));			
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
			this.sortByRank();
			Value v = this.cards.get(0).getValue();
			Value v2 = this.cards.get(1).getValue();
			Suit s = this.cards.get(0).getSuit();
			Suit s2 = this.cards.get(1).getSuit();
			Suit s3 = this.cards.get(2).getSuit();
			Value v3 = this.cards.get(2).getValue();
			Value v4 = this.cards.get(3).getValue();
			Value v5 = this.cards.get(4).getValue();
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
	 * remove duplicates from the card list
	 */
	private ArrayList<Card> removeDups() {
		//TODO
		return new ArrayList<Card>();
	}
	
	/**
	 * check if a card list contains a royal flush
	 */
	private boolean royalFlushCheck() {
		//TODO
		return false;
	}
	
	/**
	 * check if a card list contains a straight flush
	 */
	private boolean straightFlushCheck() {
		//TODO
		return false;
	}
	
	/**
	 * check if a card list contains a four of a kind
	 */
	private boolean quadsCheck() {
		//TODO
		return false;
	}
	
	/**
	 * check if a card list contains a full house
	 */
	private boolean fullHouseCheck() {
		//TODO
		return false;
	}
	
	/**
	 * check if a card list contains a flush
	 */
	private boolean flushCheck() {
		//TODO
		return false;
	}
	
	/**
	 * check if a card list contains an A-5 straight
	 * helper for low Straight and straightCheck?
	 */
	private boolean lowStraightCheck() {
		//TODO
		return false;
	}
	
	/**
	 * check if a card list contains a straight
	 */
	public boolean straightCheck() {
		//TODO
		return false;
	}
	
	/**
	 * check if a card list contains a three of a kind
	 */
	private boolean tripleCheck() {
		//TODO
		return false;
	}
	
	/**
	 * check if a card list contains two pair
	 */
	private boolean twoPairCheck() {
		//TODO
		return false;
	}
	
	/**
	 * check if a card list contains a pair
	 */
	private boolean pairCheck() {
		//TODO
		return false;
	}
	
	/**
	 * helper function that compares hands whose best hand's are both high cards
	 */
	private Hand compareHighCard(Hand h){
		//TODO
		return h;
	}
	
	/**
	 * helper function that determines the rank of three of a kind in the hand
	 * if the hand does not contain three of a kind, returns null
	 */
	private Value valueOfTriple(){
		//TODO
		return null;
	}
	
	/**
	 * helper function that determines the rank of pair in the hand
	 * if the hand does not contain three of a kind, returns null
	 */
	private Value valueOfPair(){
		//TODO
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
	public Hand determineBestHand(){
		//TODO
		return null;
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
