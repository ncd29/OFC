/**
 * Represents a card of a deck of cards
 * @author nathanieldavenport
 *
 *@param suit: the suit of the card
 *@param value: the rank of the card
 */
public class Card {
	
	private Suit suit;
	private Value value;
	
	public Card(Suit s, Value v) {
		suit = s;
		value = v;
	}
	
	public Suit getSuit() {
		return suit;
	}
	
	public Value getValue() {
		return value;
	}
	
	public boolean sameSuit(Card c) {
		return this.suit == c.getSuit();
	}
	
	public boolean sameValue(Card c) {
		return this.value == c.getValue();
	}
	
	/**
	 * returns true if the card is exactly one rank greater than card C.
	 * Aces are always considered high
	 * Ex: Jack is one above Ten.
	 */
	public boolean oneAbove(Card c) {
		return this.value.getValue() - 1 == c.getValue().getValue();
	}
	
	/**
	 * returns true if the card is exactly one rank less than card C.
	 * Aces are always considered high
	 * Ex: Ten is one below Jack.
	 */
	public boolean oneBelow(Card c) {
		return this.value.getValue() == c.getValue().getValue() - 1;
	}
	
	/**
	 * returns the string representation of card
	 * Ex: "Ace of Spades"
	 */
	public String toString() {
		if (this.value.getValue() > 10) {
			return this.value + "_of_" + this.suit;
		}
		else {
			return this.value.getValue() + "_of_" + this.suit;
		}
	}
	
	//above is tested
	
	/**
	 * check if two cards are the same
	 */
	public boolean equals(Card c) {
		return this.suit == c.getSuit() && this.value == c.getValue();
	}
	
}
