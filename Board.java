import java.util.ArrayList;
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
	private HashMap<Row,Double> expectedPoints;
	
	public Board() {
		board = new HashMap<Row,Hand>();
		board.put(Row.BOTTOM, new Hand(new ArrayList<Card>(),Row.BOTTOM));
		board.put(Row.MIDDLE, new Hand(new ArrayList<Card>(),Row.MIDDLE));
		board.put(Row.TOP, new Hand(new ArrayList<Card>(),Row.TOP));
		points = 0;
		expectedPoints = new HashMap<Row,Double>();
		expectedPoints.put(Row.BOTTOM, 0.0);
		expectedPoints.put(Row.MIDDLE, 0.0);
		expectedPoints.put(Row.TOP, 0.0);
	}
	
	public HashMap<Row,Hand> getBoard() {
		return board;
	}
	
	public int getPoints() {
		return points;
	}
	
	public HashMap<Row,Double> getExpectedPoints() {
		return expectedPoints;
	}
	
	/**
	 * returns the best possible new board using the given cards
	 * this will call other helper functions to calculate the best move
	 */
	public Board calculateBestAction(ArrayList<Card> dealt) {
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
}
