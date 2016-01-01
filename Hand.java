import java.util.ArrayList;
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
	private int points;
	private Row row;
	
	public Hand(ArrayList<Card> c,Row r) {
		cards = c;
		points = 0; // may need to change this
		row = r;
	}
	
	public ArrayList<Card> getHand() {
		return cards;
	}
	
	/**
	 * sort the cards in order of their rank
	 */
	public ArrayList<Card> sortByRank() {
		//TODO
		return new ArrayList<Card>();
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
		//TODO
		return new HashMap<Suit,Integer>();
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
	 * helper for low Straight
	 */
	private boolean lowStraightCheck() {
		//TODO
		return false;
	}
	
	/**
	 * check if a card list contains a straight
	 */
	private boolean straightCheck() {
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
