import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
/**
 * Represents a deck of cards
 * @author nathanieldavenport
 *
 * @param deck: represents the current deck of cards
 */
public class Deck {
	
	private ArrayList<Card> deck;
	
	// creates a new deck with all 52 cards
	public Deck() {
		ArrayList<Card> cards = new ArrayList<Card>();
		for (int i=0; i<13; i++) {
			Value v = Value.values()[i];
			for (int j=0; j<4; j++) {
				Card c = new Card(Suit.values()[j],v);
				cards.add(c);
			}
		}
		deck = cards;
	}
	
	// creates an incomplete "deck" of cards
	// this is probably really bad design, but I need to shuffle
	public Deck(ArrayList<Card> cards) {
		deck = cards;
	}
	
	public ArrayList<Card> getDeck(){
		return deck;
	}
	
	/**
	 * shuffles the deck randomly
	 * Knuth shuffling algorithm from :
	 * http://algs4.cs.princeton.edu/11model/Knuth.java.html 
	 */
	public void shuffle() {
		Collections.shuffle(this.deck);
	    // not working right now
		//int n = this.deck.size();
	    //System.out.println("shuffled size = " + n);
        //for (int i = 0; i < n; i++) {
            // choose index uniformly in [i, n-1]
          //  int r = i + (int) (Math.random() * (n - i));
            //Card temp = this.deck.get(r);
            //this.deck.add(r,this.deck.get(i));
            //this.deck.remove(i);
            //this.deck.add(i,temp);
            //this.deck.remove(r);
		//}
        //System.out.println("new shuffled size = " + this.deck.size());
	}
	
	/**
	 * returns the top n cards from the deck
	 * removes the cards from the deck
	 * n >= 1 and if n > the total number of cards in the deck, throws exception
	 */
	public ArrayList<Card> removeTopCards(int n) {
		ArrayList<Card> cards = new ArrayList<Card>();
		for (int i=0; i<n; i++) {
			cards.add(this.deck.remove(i));
		}
		return cards;
	}
	
	/**
	 * print the deck as a string
	 */
	public void printString() {
		ArrayList<Card> cards = this.deck;
		for (int i=0; i<cards.size(); i++) {
			System.out.println(cards.get(i).toString());
		}
	}
}
