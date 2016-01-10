import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;


public class HandTest {

	@Test
	public void test() {
		Card c1 = new Card(Suit.spades,Value.Ace);
		Card c2 = new Card(Suit.spades,Value.Queen);
		Card c3 = new Card(Suit.spades,Value.King);
		Card c4 = new Card(Suit.spades,Value.Ten);
		Card c5 = new Card(Suit.spades,Value.Jack);
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(c1);
		cards.add(c2);
		cards.add(c3);
		cards.add(c4);
		cards.add(c5);
		Hand hand = new Hand(cards);
		hand.sortByRank();
		for (int i=0; i<hand.getCards().size(); i++) {
			System.out.println(hand.getCards().get(i).toString());
		}
		assertTrue(hand.royalFlushCheck());
	}

}
