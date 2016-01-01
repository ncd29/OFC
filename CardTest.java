import static org.junit.Assert.*;

import org.junit.Test;


public class CardTest {

	@Test
	public void test() {
		Card c1 = new Card(Suit.hearts,Value.Ace);
		Card c2 = new Card(Suit.hearts,Value.Ace);
		Card c3 = new Card(Suit.spades,Value.King);
		Card c4 = new Card(Suit.spades,Value.Ten);
		assertTrue(c1.sameSuit(c2));
		assertTrue(c1.sameValue(c2));
		assertTrue(!c2.sameSuit(c3));
		assertTrue(!c2.sameValue(c3));
		assertTrue(c2.oneAbove(c3));
		assertTrue(c3.oneBelow(c2));
		assertEquals("Ace_of_hearts",c2.toString());
		assertEquals("10_of_spades",c4.toString());
	}

}
