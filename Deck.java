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
		for (int i=0; i<4; i++) {
			Value v = Value.values()[i];
			for (int j=0; j<13; j++) {
				Card c = new Card(Suit.values()[j],v);
				cards.add(c);
			}
		}
		deck = cards;
	}
	
	public ArrayList<Card> getDeck(){
		return deck;
	}
	/**
	 * shuffles the deck randomly 
	 */
	public void shuffle(Deck d) {
		//TODO
	}
	
	/**
	 * returns the top n cards from the deck d
	 * removes the cards from the deck d
	 * n >= 1 and if n > the total number of cards in d, throws exception
	 */
	public ArrayList<Card> removeTopCards(Deck d, int n) {
		//TODO
		return new ArrayList<Card>();
	}
}
