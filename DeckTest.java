import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;


public class DeckTest {

	@Test
	public void test() {
		Card c1 = new Card(Suit.hearts,Value.Ace);
		Card c2 = new Card(Suit.clubs,Value.Ace);
		Card c3 = new Card(Suit.spades,Value.King);
		Card c4 = new Card(Suit.spades,Value.Ten);
		Card c5 = new Card(Suit.clubs,Value.Ten);
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(c1);
		cards.add(c2);
		cards.add(c3);
		cards.add(c4);
		cards.add(c5);
		Deck deck = new Deck(cards);
		deck.printString();
		deck.shuffle();
		deck.printString();
		deck.shuffle();
		deck.printString();
		deck.shuffle();
		deck.printString();
		deck.shuffle();
		deck.printString();
		deck.shuffle();
		deck.printString();
		deck.shuffle();
		deck.printString();
		deck.shuffle();
		deck.printString();
		deck.shuffle();
		deck.printString();
		deck.shuffle();
		deck.printString();
		deck.shuffle();
	}

}
