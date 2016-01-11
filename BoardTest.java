import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Stack;

import org.junit.Test;


public class BoardTest {

	@Test
	public void test() {
		Card c1 = new Card(Suit.hearts,Value.Ace);
		Card c2 = new Card(Suit.clubs,Value.Ace);
		Card c3 = new Card(Suit.spades,Value.Ace);
		Card c4 = new Card(Suit.spades,Value.Nine);
		Card c5 = new Card(Suit.clubs,Value.Ten);
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(c1);
		cards.add(c2);
		cards.add(c3);
		cards.add(c4);
		cards.add(c5);
		Board board = new Board();
		Board best = board.calculateBestAction(cards, new Board());
		System.out.println(best.toString());
		//System.out.println(boardsList);
		//Stack<Board> boards = Board.allArrangements(cards);
		//System.out.println(boards.size());
		//while (!boards.isEmpty()) {
			//System.out.println(boards.pop().toString());
		//}
	}

}
