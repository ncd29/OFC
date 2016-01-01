import java.util.HashMap;
import java.util.ArrayList;

/**
 * class for representing a player in the ofc game
 * @author nathanieldavenport
 * 
 * @param board: the current cards on the player's board
 * @param position: the current position of the player,
 * 0 for current player who's turn it is, 1 for next player, and 2 if their is a third player
 * @param hand: the current cards that were dealt to the player
 *
 */
public class Player {
	
	private Board board;
	private int position;
	private ArrayList<Card> dealt;
	
	/**
	 * constructs a new player by giving the player 5 cards
	 * they have not been placed in the hand
	 */
	public Player(Board b,int p, ArrayList<Card> d) {
		board = b;
		position = p;
		dealt = d;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public int getPosition() {
		return position;
	}
	
	public ArrayList<Card> getDealt() {
		return dealt;
	}
	
	
	public void addCards(ArrayList<Card> cards) {
		this.dealt = cards;
	}
	
	/**
	 * reconstructs the player's hand and board, and others?
	 * after the player has placed his new cards
	 * basically puts the cards in hand onto the board
	 * interacts with gui somehow
	 */
	public Player doAction() {
		//TODO
		return null;
	}
	
	/**
	 * check if the player's move was the same as the one suggested by
	 * Board.calculateBestAction, do this by comparing the player's board
	 * with the board recommended by that function
	 * will use Board's equals
	 */
	private boolean checkMove(Board b) {
		//TODO
		return false;
	}
}
