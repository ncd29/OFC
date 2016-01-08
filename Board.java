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
	
	//empty constructor
	public Board() {
		board = new HashMap<Row,Hand>();
		board.put(Row.BOTTOM, new Hand(new ArrayList<Card>(),Row.BOTTOM));
		board.put(Row.MIDDLE, new Hand(new ArrayList<Card>(),Row.MIDDLE));
		board.put(Row.TOP, new Hand(new ArrayList<Card>(),Row.TOP));
		points = 0;
		expectedPoints = 0.0;
	}
	
	public Board(HashMap<Row,Hand> rows, int expectedPoints) {
		board = rows;
		this.expectedPoints = expectedPoints;
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
	
	private void setExpectedPoints(double d) {
		expectedPoints = d;
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
		Stack<Board> boards = allArrangements(dealt,this);
		Board best = boards.pop();
		expectedPoints = simulate(best,opp,new Deck(),100,100); // try with 100 runs first
		best.setExpectedPoints(expectedPoints);
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
	 * return all the possible board arrangements given the amount of cards to place 
	 * in each row, helper for allArrangements
	 * current is the current list of board arrangments, for recursion
	 * max permutations is the maximum number of arrangements given the cards in 
	 * the front middle and back
	 */
	public static ArrayList<Board> permutations(ArrayList<Card> cards, int front, int middle, int back,
			ArrayList<Board> current, int maxPermutations) {
		//System.out.println("number of cards = " + cards.size());
		//System.out.println("in permutations " + maxPermutations);
		//System.out.println(front + " " + middle + " " + back);
		HashMap<Row,Hand> rows = new HashMap<Row,Hand>();
		if (front == 0) {
			rows.put(Row.TOP, null);
		}
		if (middle == 0) {
			rows.put(Row.MIDDLE, null);
		}
		if (back == 0) {
			rows.put(Row.BOTTOM, null);
		}
		ArrayList<Card> frontCards = new ArrayList<Card>();
		for (int i=0; i<front; i++) {
			frontCards.add(cards.get(i));
		}
		rows.put(Row.TOP, new Hand(frontCards,Row.TOP));
		ArrayList<Card> middleCards = new ArrayList<Card>();
		for (int i=front; i<middle+front; i++) {
			middleCards.add(cards.get(i));
		}
		rows.put(Row.MIDDLE, new Hand(middleCards,Row.MIDDLE));
		ArrayList<Card> backCards = new ArrayList<Card>();
		for (int i=front+middle; i<back+front+middle; i++) {
			backCards.add(cards.get(i));
		}
		rows.put(Row.BOTTOM, new Hand(backCards,Row.BOTTOM));
		Board board = new Board(rows,0);
		boolean contained = false;
		System.out.println(current.size());
		for (int i=0; i<current.size(); i++) {
			if (board.equals(current.get(i))) {
				contained = true;
			}
		}
		if (!contained) {
			current.add(board);
		}
		if (current.size() < maxPermutations) {
			// shuffle cards, kind of a hack, and keep shuffling until you get all combos
			//System.out.println("looping");
			Deck deck = new Deck(cards);
			deck.shuffle();
			//System.out.println("shuffled");
			ArrayList<Card> shuffled = deck.getDeck();
			return permutations(shuffled,front,middle,back,current,maxPermutations);
		}
		else {
			//System.out.println("current");
			return current;
		}
	}
	
	/**
	 * returns a Stack containing all the possible board arrangements that can be made
	 * by adding the cards to the current Board
	 */
	public static Stack<Board> allArrangements(ArrayList<Card> cards, Board board) {
		Stack<Board> boards = new Stack<Board>();
		int length = cards.size();
		int front;
		int middle;
		int back;
		ArrayList<Board> boardList = new ArrayList<Board>();
		System.out.println("length = " + length);
		if (length == 5) {
			// 1 permutation
			front = 0; middle = 0; back = 5;
			boardList.addAll(permutations(cards,front,middle,back,new ArrayList<Board>(),1));
			front = 0; middle = 5; back = 0;
			boardList.addAll(permutations(cards,front,middle,back,new ArrayList<Board>(),1));
			// 5 permutations
			front = 0; middle = 1; back = 4;
			boardList.addAll(permutations(cards,front,middle,back,new ArrayList<Board>(),5));
			front = 0; middle = 4; back = 1;
			boardList.addAll(permutations(cards,front,middle,back,new ArrayList<Board>(),5));
			front = 1; middle = 4; back = 0;
			boardList.addAll(permutations(cards,front,middle,back,new ArrayList<Board>(),5));
			front = 1; middle = 0; back = 4;
			boardList.addAll(permutations(cards,front,middle,back,new ArrayList<Board>(),5));
			// 10 permutations
			front = 3; middle = 2; back = 0;
			boardList.addAll(permutations(cards,front,middle,back,new ArrayList<Board>(),10));
			front = 3; middle = 0; back = 2;
			boardList.addAll(permutations(cards,front,middle,back,new ArrayList<Board>(),10));
			front = 2; middle = 0; back = 3;
			boardList.addAll(permutations(cards,front,middle,back,new ArrayList<Board>(),10));
			front = 2; middle = 3; back = 0;
			boardList.addAll(permutations(cards,front,middle,back,new ArrayList<Board>(),10));
			front = 0; middle = 3; back = 2;
			boardList.addAll(permutations(cards,front,middle,back,new ArrayList<Board>(),10));
			front = 0; middle = 2; back = 3;
			boardList.addAll(permutations(cards,front,middle,back,new ArrayList<Board>(),10));
			// 20 permutations
			front = 3; middle = 1; back = 1;
			boardList.addAll(permutations(cards,front,middle,back,new ArrayList<Board>(),20));
			front = 1; middle = 3; back = 1;
			boardList.addAll(permutations(cards,front,middle,back,new ArrayList<Board>(),20));
			front = 1; middle = 1; back = 3;
			boardList.addAll(permutations(cards,front,middle,back,new ArrayList<Board>(),20));
			// 30 permutations
			front = 2; middle = 2; back = 1;
			boardList.addAll(permutations(cards,front,middle,back,new ArrayList<Board>(),30));
			front = 2; middle = 1; back = 2;
			boardList.addAll(permutations(cards,front,middle,back,new ArrayList<Board>(),30));
			front = 1; middle = 2; back = 2;
			boardList.addAll(permutations(cards,front,middle,back,new ArrayList<Board>(),30));
		}
		else { // length = 3
			//TODO
			System.out.println("wrong");
			return new Stack<Board>();
		}
		for (int i=0; i<boardList.size(); i++) {
			boards.push(boardList.get(i));
		}
		return boards;
	}
	
	/**
	 * runs the simulation on a board, runs is the number of simulations to be done
	 */
	private static double simulate(Board board, Board oppBoard, Deck deck, int runs, int counter) {
		//TODO
		if (counter == 0) {
			return board.getExpectedPoints()/(double)runs;
		}
		// remove the cards already on the board from the deck
		ArrayList<Card> cardsToRemove = new ArrayList<Card>();
		ArrayList<Card> deckOfCards = deck.getDeck();
		cardsToRemove.addAll(board.getCardsOfBoard());
		cardsToRemove.addAll(oppBoard.getCardsOfBoard());
		for (int i=0; i<cardsToRemove.size(); i++) {
			deckOfCards.remove(cardsToRemove.get(i));
		}
		// remove 3 cards for each player and add them to the board, for the number of times
		// for the current state
		
		// calculate the points this board generated, add it to the total
		// decrement counter
		return 0;
	}
	
	/**
	 * check if two boards are the same
	 */
	public boolean equals(Board b) {
		HashMap<Row,Hand> rows = this.board;
		HashMap<Row,Hand> rows2 = b.getBoard();
		ArrayList<Card> top = rows.get(Row.TOP).getCards();
		ArrayList<Card> top2 = rows2.get(Row.TOP).getCards();
		if (top.size() != top2.size()) {
			return false;
		}
		for (int i=0; i<top.size(); i++) {
			if (!top.get(i).equals(top.get(i))) {
				return false;
			}
		}
		ArrayList<Card> middle = rows.get(Row.MIDDLE).getCards();
		ArrayList<Card> middle2 = rows2.get(Row.MIDDLE).getCards();
		if (middle.size() != middle2.size()) {
			return false;
		}
		for (int i=0; i<middle.size(); i++) {
			if (!middle.get(i).equals(middle2.get(i))) {
				return false;
			}
		}
		ArrayList<Card> bottom = rows.get(Row.BOTTOM).getCards();
		ArrayList<Card> bottom2 = rows2.get(Row.BOTTOM).getCards();
		if (bottom.size() != bottom2.size()) {
			return false;
		}
		for (int i=0; i<bottom.size(); i++) {
			if (!bottom.get(i).equals(bottom2.get(i))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * returns the board as a string, printing every card in every row
	 */
	public String toString() {
		HashMap<Row,Hand> rows = this.board;
		String s = "Top Cards: ";
		ArrayList<Card> top = rows.get(Row.TOP).getCards();
		for (int i=0; i<top.size(); i++) {
			s = s + "\n" + top.get(i).toString();
		}
		s = s  + " Middle Cards: ";
		ArrayList<Card> middle = rows.get(Row.MIDDLE).getCards();
		for (int i=0; i<middle.size(); i++) {
			s = s + "\n" + middle.get(i).toString();
		}
		s = s + " Bottom Cards: ";
		ArrayList<Card> bottom = rows.get(Row.BOTTOM).getCards();
		for (int i=0; i<bottom.size(); i++) {
			s = s + "\n" + bottom.get(i).toString();
		}
		return s;
	}
}
