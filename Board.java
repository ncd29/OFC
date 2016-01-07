import java.util.ArrayList;
import java.util.Stack;
import java.util.HashMap;

/**
 * Represents a current player's board
 * @author nathanieldavenport
 * 
 * @param board: the board of cards
 * @param points: the amount of points the board is worth, determined at the end
 * @param expectedPoints: the expected amount of points the each row of the board
 * will be worth given the current situation
 *
 */

public class Board {
	
	private HashMap<Row,Hand> board;
	private int points;
	private double expectedPoints;
	
	public Board() {
		board = new HashMap<Row,Hand>();
		board.put(Row.BOTTOM, new Hand(new ArrayList<Card>(),Row.BOTTOM));
		board.put(Row.MIDDLE, new Hand(new ArrayList<Card>(),Row.MIDDLE));
		board.put(Row.TOP, new Hand(new ArrayList<Card>(),Row.TOP));
		points = 0;
		expectedPoints = 0.0;
	}
	
	public HashMap<Row,Hand> getBoard() {
		return board;
	}
	
	public int getPoints() {
		return points;
	}
	
	public double getExpectedPoints() {
		return expectedPoints;
	}
	
	/**
	 * returns as a list all of the cards currently on the board
	 */
	public ArrayList<Card> getCardsOfBoard() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.addAll(board.get(Row.BOTTOM).getCards());
		cards.addAll(board.get(Row.MIDDLE).getCards());
		cards.addAll(board.get(Row.TOP).getCards());
		return cards;
	}
	
	/**
	 * returns the best possible new board using the given cards
	 * this will call other helper functions to calculate the best move
	 * opp is the opponents board
	 */
	public Board calculateBestAction(ArrayList<Card> dealt, Board opp) {
		//TODO
		double expectedPoints = 0.0;
		//Stack<Board> boards = allArrangments(dealt,this);
		//Board best = Stack.pop();
		//expectedPoints = Probability.someHelper(Stack.pop());
		//while (!boards.isEmpty()) {
		//	points = Probability.someHelper(Stack.peek());
		//  if (points > expectedPoints) best = boards.pop(); expectedPoints = points
		//  else boards.pop();
		//}
		// calculate the expectedPoints in the back, middle, and front from each move
		// to find the total expectedPoints from each move, will check all moves
		// will call a helper function to check each arrangement, whcih will in turn call helpers
		// for back, middle, and front, and sum the totals
		return null;
		
	}
	
	/**
	 * returns a Stack containing all the possible board arrangements that can be made
	 * by adding the cards to the current Board
	 */
	public Stack<Board> allArrangements(ArrayList<Card> cards, Board current) {
		//TODO
		return null;
	}
	
	/**
	 * check if two boards are the same
	 */
	public Board equals(Board b) {
		//TODO
		return null;
	}
	
	/**
	 * returns the board as a string, printing every card in every row
	 */
	public String toString() {
		//TODO
		return "";
	}
}
