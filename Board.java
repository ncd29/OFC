import java.util.ArrayList;
import java.util.Stack;
import java.util.HashMap;
import java.util.Random;

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
	
	//empty constructor creates an empty board
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
		points = 0;
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
	 * returns the number of cards in the top row
	 */
	private int getTopSize() {
		HashMap<Row,Hand> rows = this.board;
		return rows.get(Row.TOP).getCards().size();
	}
	
	/**
	 * returns the number of cards in the middle row
	 */
	private int getMiddleSize() {
		HashMap<Row,Hand> rows = this.board;
		return rows.get(Row.MIDDLE).getCards().size();
	}
	
	/**
	 * returns the number of cards in the top row
	 */
	private int getBottomSize() {
		HashMap<Row,Hand> rows = this.board;
		return rows.get(Row.BOTTOM).getCards().size();
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
		//TODO - I think this is done
		double expectedPoints = 0.0;
		Stack<Board> boards = allArrangements(dealt,this);
		Board best = boards.pop();
		expectedPoints = simulate(best,opp,new Deck(),100,100); // try with 100 runs first, but need more
		best.setExpectedPoints(expectedPoints);
		while (!boards.isEmpty()) {
			double points = simulate(boards.peek(),opp,new Deck(),100,100);
			if (points > expectedPoints) {
				best = boards.pop(); 
				expectedPoints = points;
			}
			else {
				boards.pop();
			}
		}
		return best;
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
		//System.out.println(current.size());
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
		//System.out.println("length = " + length);
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
			//System.out.println("wrong");
			return new Stack<Board>();
		}
		for (int i=0; i<boardList.size(); i++) {
			boards.push(boardList.get(i));
		}
		return boards;
	}
	
	/**
	 * cards to place will be two of three, n is a random number between 0 and
	 */
	private void placeCardsOnBoard(ArrayList<Card> cards, int n) {
		// n = 0 : 1,2 top
		if (this.getTopSize() < 2 && n == 0) {
			ArrayList<Card> cardsOnBoard = this.board.get(Row.TOP).getCards();
			cardsOnBoard.add(cards.get(0));
			cardsOnBoard.add(cards.get(1));
			this.board.put(Row.TOP, new Hand(cardsOnBoard,Row.TOP));
		}
		// n = 1 : 1,3 top
		else if (this.getTopSize() < 2 && n == 1) {
			ArrayList<Card> cardsOnBoard = this.board.get(Row.TOP).getCards();
			cardsOnBoard.add(cards.get(0));
			cardsOnBoard.add(cards.get(2));
			this.board.put(Row.TOP, new Hand(cardsOnBoard,Row.TOP));
		}
		// n = 2 : 2,3 top
		else if (this.getTopSize() < 2 && n == 2) {
			ArrayList<Card> cardsOnBoard = this.board.get(Row.TOP).getCards();
			cardsOnBoard.add(cards.get(1));
			cardsOnBoard.add(cards.get(2));
			this.board.put(Row.TOP, new Hand(cardsOnBoard,Row.TOP));
		}
		// n = 3 : 1 top, 2 middle
		else if (this.getTopSize() < 3 && this.getMiddleSize() < 5 && n == 3) {
			ArrayList<Card> cardsOnTop = this.board.get(Row.TOP).getCards();
			cardsOnTop.add(cards.get(0));
			ArrayList<Card> cardsInMiddle = this.board.get(Row.MIDDLE).getCards();
			cardsInMiddle.add(cards.get(1));
			this.board.put(Row.TOP, new Hand(cardsOnTop,Row.TOP));
			this.board.put(Row.MIDDLE, new Hand(cardsInMiddle,Row.MIDDLE));
		}
		// n = 4 : 2 top, 1 middle
		else if (this.getTopSize() < 3 && this.getMiddleSize() < 5 && n == 4) {
			ArrayList<Card> cardsOnTop = this.board.get(Row.TOP).getCards();
			cardsOnTop.add(cards.get(1));
			ArrayList<Card> cardsInMiddle = this.board.get(Row.MIDDLE).getCards();
			cardsInMiddle.add(cards.get(0));
			this.board.put(Row.TOP, new Hand(cardsOnTop,Row.TOP));
			this.board.put(Row.MIDDLE, new Hand(cardsInMiddle,Row.MIDDLE));
		}
		// n = 5 : 1 top, 3 middle
		else if (this.getTopSize() < 3 && this.getMiddleSize() < 5 && n == 5) {
			ArrayList<Card> cardsOnTop = this.board.get(Row.TOP).getCards();
			cardsOnTop.add(cards.get(0));
			ArrayList<Card> cardsInMiddle = this.board.get(Row.MIDDLE).getCards();
			cardsInMiddle.add(cards.get(2));
			this.board.put(Row.TOP, new Hand(cardsOnTop,Row.TOP));
			this.board.put(Row.MIDDLE, new Hand(cardsInMiddle,Row.MIDDLE));
		}
		// n = 6 : 3 top, 1 middle
		else if (this.getTopSize() < 3 && this.getMiddleSize() < 5 && n == 6) {
			ArrayList<Card> cardsOnTop = this.board.get(Row.TOP).getCards();
			cardsOnTop.add(cards.get(2));
			ArrayList<Card> cardsInMiddle = this.board.get(Row.MIDDLE).getCards();
			cardsInMiddle.add(cards.get(0));
			this.board.put(Row.TOP, new Hand(cardsOnTop,Row.TOP));
			this.board.put(Row.MIDDLE, new Hand(cardsInMiddle,Row.MIDDLE));
		}
		// n = 7 : 2 top, 3 middle
		else if (this.getTopSize() < 3 && this.getMiddleSize() < 5 && n == 7) {
			ArrayList<Card> cardsOnTop = this.board.get(Row.TOP).getCards();
			cardsOnTop.add(cards.get(1));
			ArrayList<Card> cardsInMiddle = this.board.get(Row.MIDDLE).getCards();
			cardsInMiddle.add(cards.get(2));
			this.board.put(Row.TOP, new Hand(cardsOnTop,Row.TOP));
			this.board.put(Row.MIDDLE, new Hand(cardsInMiddle,Row.MIDDLE));
		}
		// n = 8 : 3 top, 2 middle
		else if (this.getTopSize() < 3 && this.getMiddleSize() < 5 && n == 8) {
			ArrayList<Card> cardsOnTop = this.board.get(Row.TOP).getCards();
			cardsOnTop.add(cards.get(2));
			ArrayList<Card> cardsInMiddle = this.board.get(Row.MIDDLE).getCards();
			cardsInMiddle.add(cards.get(1));
			this.board.put(Row.TOP, new Hand(cardsOnTop,Row.TOP));
			this.board.put(Row.MIDDLE, new Hand(cardsInMiddle,Row.MIDDLE));
		}
		// n = 9 : 1 top, 2 bottom
		else if (this.getTopSize() < 3 && this.getBottomSize() < 5 && n == 9) {
			ArrayList<Card> cardsOnTop = this.board.get(Row.TOP).getCards();
			cardsOnTop.add(cards.get(0));
			ArrayList<Card> cardsOnBottom = this.board.get(Row.BOTTOM).getCards();
			cardsOnBottom.add(cards.get(1));
			this.board.put(Row.TOP, new Hand(cardsOnTop,Row.TOP));
			this.board.put(Row.BOTTOM, new Hand(cardsOnBottom,Row.BOTTOM));
		}
		// n = 10 : 2 top, 1 bottom
		else if (this.getTopSize() < 3 && this.getBottomSize() < 5 && n == 10) {
			ArrayList<Card> cardsOnTop = this.board.get(Row.TOP).getCards();
			cardsOnTop.add(cards.get(1));
			ArrayList<Card> cardsOnBottom = this.board.get(Row.BOTTOM).getCards();
			cardsOnBottom.add(cards.get(0));
			this.board.put(Row.TOP, new Hand(cardsOnTop,Row.TOP));
			this.board.put(Row.BOTTOM, new Hand(cardsOnBottom,Row.BOTTOM));
		}
		// n = 11 : 1 top, 3 bottom
		else if (this.getTopSize() < 3 && this.getBottomSize() < 5 && n == 11) {
			ArrayList<Card> cardsOnTop = this.board.get(Row.TOP).getCards();
			cardsOnTop.add(cards.get(0));
			ArrayList<Card> cardsOnBottom = this.board.get(Row.BOTTOM).getCards();
			cardsOnBottom.add(cards.get(2));
			this.board.put(Row.TOP, new Hand(cardsOnTop,Row.TOP));
			this.board.put(Row.BOTTOM, new Hand(cardsOnBottom,Row.BOTTOM));
		}
		// n = 12 : 3 top, 1 bottom
		else if (this.getTopSize() < 3 && this.getBottomSize() < 5 && n == 12) {
			ArrayList<Card> cardsOnTop = this.board.get(Row.TOP).getCards();
			cardsOnTop.add(cards.get(2));
			ArrayList<Card> cardsOnBottom = this.board.get(Row.BOTTOM).getCards();
			cardsOnBottom.add(cards.get(0));
			this.board.put(Row.TOP, new Hand(cardsOnTop,Row.TOP));
			this.board.put(Row.BOTTOM, new Hand(cardsOnBottom,Row.BOTTOM));
		}
		// n = 13 : 2 top, 3 bottom
		else if (this.getTopSize() < 3 && this.getBottomSize() < 5 && n == 13) {
			ArrayList<Card> cardsOnTop = this.board.get(Row.TOP).getCards();
			cardsOnTop.add(cards.get(1));
			ArrayList<Card> cardsOnBottom = this.board.get(Row.BOTTOM).getCards();
			cardsOnBottom.add(cards.get(2));
			this.board.put(Row.TOP, new Hand(cardsOnTop,Row.TOP));
			this.board.put(Row.BOTTOM, new Hand(cardsOnBottom,Row.BOTTOM));
		}
		// n = 14 : 3 top, 2 bottom
		else if (this.getTopSize() < 3 && this.getBottomSize() < 5 && n == 14) {
			ArrayList<Card> cardsOnTop = this.board.get(Row.TOP).getCards();
			cardsOnTop.add(cards.get(2));
			ArrayList<Card> cardsOnBottom = this.board.get(Row.BOTTOM).getCards();
			cardsOnBottom.add(cards.get(1));
			this.board.put(Row.TOP, new Hand(cardsOnTop,Row.TOP));
			this.board.put(Row.BOTTOM, new Hand(cardsOnBottom,Row.BOTTOM));
		}
		// n = 15 : 1,2 middle
		else if (this.getMiddleSize() < 4 && n == 15) {
			ArrayList<Card> cardsInMiddle = this.board.get(Row.MIDDLE).getCards();
			cardsInMiddle.add(cards.get(0));
			cardsInMiddle.add(cards.get(1));
			this.board.put(Row.MIDDLE, new Hand(cardsInMiddle,Row.MIDDLE));
		}
		// n = 16 : 1,3 middle
		else if (this.getMiddleSize() < 4 && n == 16) {
			ArrayList<Card> cardsInMiddle = this.board.get(Row.MIDDLE).getCards();
			cardsInMiddle.add(cards.get(0));
			cardsInMiddle.add(cards.get(2));
			this.board.put(Row.MIDDLE, new Hand(cardsInMiddle,Row.MIDDLE));
		}
		// n = 17 : 2,3 middle
		else if (this.getMiddleSize() < 4 && n == 17) {
			ArrayList<Card> cardsInMiddle = this.board.get(Row.MIDDLE).getCards();
			cardsInMiddle.add(cards.get(1));
			cardsInMiddle.add(cards.get(2));
			this.board.put(Row.MIDDLE, new Hand(cardsInMiddle,Row.MIDDLE));
		}
		// n = 18 : 1 middle, 2 bottom
		else if (this.getMiddleSize() < 5 && this.getBottomSize() < 5 && n == 18) {
			ArrayList<Card> cardsInMiddle = this.board.get(Row.MIDDLE).getCards();
			cardsInMiddle.add(cards.get(0));
			ArrayList<Card> cardsOnBottom = this.board.get(Row.BOTTOM).getCards();
			cardsOnBottom.add(cards.get(1));
			this.board.put(Row.MIDDLE, new Hand(cardsInMiddle,Row.MIDDLE));
			this.board.put(Row.BOTTOM, new Hand(cardsOnBottom,Row.BOTTOM));
		}
		// n = 19 : 2 middle, 1 bottom
		else if (this.getMiddleSize() < 5 && this.getBottomSize() < 5 && n == 19) {
			ArrayList<Card> cardsInMiddle = this.board.get(Row.MIDDLE).getCards();
			cardsInMiddle.add(cards.get(1));
			ArrayList<Card> cardsOnBottom = this.board.get(Row.BOTTOM).getCards();
			cardsOnBottom.add(cards.get(0));
			this.board.put(Row.MIDDLE, new Hand(cardsInMiddle,Row.MIDDLE));
			this.board.put(Row.BOTTOM, new Hand(cardsOnBottom,Row.BOTTOM));
		}
		// n = 20 : 1 middle, 3 bottom
		else if (this.getMiddleSize() < 5 && this.getBottomSize() < 5 && n == 20) {
			ArrayList<Card> cardsInMiddle = this.board.get(Row.MIDDLE).getCards();
			cardsInMiddle.add(cards.get(0));
			ArrayList<Card> cardsOnBottom = this.board.get(Row.BOTTOM).getCards();
			cardsOnBottom.add(cards.get(2));
			this.board.put(Row.MIDDLE, new Hand(cardsInMiddle,Row.MIDDLE));
			this.board.put(Row.BOTTOM, new Hand(cardsOnBottom,Row.BOTTOM));
		}
		// n = 21 : 3 middle, 1 bottom
		else if (this.getMiddleSize() < 5 && this.getBottomSize() < 5 && n == 21) {
			ArrayList<Card> cardsInMiddle = this.board.get(Row.MIDDLE).getCards();
			cardsInMiddle.add(cards.get(2));
			ArrayList<Card> cardsOnBottom = this.board.get(Row.BOTTOM).getCards();
			cardsOnBottom.add(cards.get(0));
			this.board.put(Row.MIDDLE, new Hand(cardsInMiddle,Row.MIDDLE));
			this.board.put(Row.BOTTOM, new Hand(cardsOnBottom,Row.BOTTOM));
		}
		// n = 22 : 2 middle, 3 bottom
		else if (this.getMiddleSize() < 5 && this.getBottomSize() < 5 && n == 22) {
			ArrayList<Card> cardsInMiddle = this.board.get(Row.MIDDLE).getCards();
			cardsInMiddle.add(cards.get(1));
			ArrayList<Card> cardsOnBottom = this.board.get(Row.BOTTOM).getCards();
			cardsOnBottom.add(cards.get(2));
			this.board.put(Row.MIDDLE, new Hand(cardsInMiddle,Row.MIDDLE));
			this.board.put(Row.BOTTOM, new Hand(cardsOnBottom,Row.BOTTOM));
		}
		// n = 23 : 3 middle, 2 bottom
		else if (this.getMiddleSize() < 5 && this.getBottomSize() < 5 && n == 23) {
			ArrayList<Card> cardsInMiddle = this.board.get(Row.MIDDLE).getCards();
			cardsInMiddle.add(cards.get(2));
			ArrayList<Card> cardsOnBottom = this.board.get(Row.BOTTOM).getCards();
			cardsOnBottom.add(cards.get(1));
			this.board.put(Row.MIDDLE, new Hand(cardsInMiddle,Row.MIDDLE));
			this.board.put(Row.BOTTOM, new Hand(cardsOnBottom,Row.BOTTOM));
		}
		// n = 24 : 1,2 bottom
		else if (this.getBottomSize() < 4 && n == 24) {
			ArrayList<Card> cardsOnBoard = this.board.get(Row.BOTTOM).getCards();
			cardsOnBoard.add(cards.get(0));
			cardsOnBoard.add(cards.get(1));
			this.board.put(Row.BOTTOM, new Hand(cardsOnBoard,Row.BOTTOM));
		}
		// n = 25 : 1,3 bottom
		else if (this.getBottomSize() < 4 && n == 25) {
			ArrayList<Card> cardsOnBoard = this.board.get(Row.BOTTOM).getCards();
			cardsOnBoard.add(cards.get(0));
			cardsOnBoard.add(cards.get(2));
			this.board.put(Row.BOTTOM, new Hand(cardsOnBoard,Row.BOTTOM));
		}
		// n = 26 : 2,3 bottom
		else if (this.getBottomSize() < 4 && n == 26) {
			ArrayList<Card> cardsOnBoard = this.board.get(Row.BOTTOM).getCards();
			cardsOnBoard.add(cards.get(1));
			cardsOnBoard.add(cards.get(2));
			this.board.put(Row.BOTTOM, new Hand(cardsOnBoard,Row.BOTTOM));
		}
		// if arrangement was not possible try a new number
		else {
			int n2 = new Random().nextInt(27);
			System.out.println(n2);
			this.placeCardsOnBoard(cards,n2);
		}
	} 
	
	/**
	 * returns the amount of points a completed board is worth
	 */
	private double calculatePoints() {
		HashMap<Row,Hand> rows = this.board;
		Hand top = rows.get(Row.TOP);
		Hand middle = rows.get(Row.MIDDLE);
		Hand bottom = rows.get(Row.BOTTOM);
		double topRank = top.determineBestHandThree();
		double topPoints = topRank;
		double middleRank = middle.determineBestHandFive();
		double middlePoints = 0;
		double bottomRank = bottom.determineBestHandFive();
		double bottomPoints = 0;
		if (topRank > middleRank
			|| topRank > bottomRank
			|| middleRank > bottomRank) {
				return 0;
		}
		else {
			if (middleRank < 12) { middlePoints = 0; }
			if (middleRank >= 12 && middleRank < 25) { middlePoints = 2; }
			if (middleRank == 25) { middlePoints = 4; }
			if (middleRank == 26) { middlePoints = 8; }
			if (middleRank == 27) { middlePoints = 12; }
			if (middleRank == 28) { middlePoints = 20; }
			if (middleRank == 29) { middlePoints = 30; }
			if (middleRank == 30) { middlePoints = 50; }
			if (bottomRank < 25) { bottomPoints = 0; }
			if (bottomRank == 25) { bottomPoints = 2; }
			if (bottomRank == 26) { bottomPoints = 4; }
			if (bottomRank == 27) { bottomPoints = 6; }
			if (bottomRank == 28) { bottomPoints = 10; }
			if (bottomRank == 29) { bottomPoints = 15; }
			if (bottomRank == 30) { bottomPoints = 25; }
		}
		return topPoints + middlePoints + bottomPoints;
	}
	/**
	 * runs the simulation on a board, runs is the number of simulations to be done
	 */
	private static double simulate(Board board, Board oppBoard, Deck deck, int runs, int counter) {
		//TODO
		if (counter == 0) {
			return board.getExpectedPoints()/(double)runs;
		}
		// make copies of the boards
		Board copy = new Board();
		copy = board;
		Board oppCopy = new Board();
		oppCopy = oppBoard;
		// remove the cards already on the board from the deck
		ArrayList<Card> cardsToRemove = new ArrayList<Card>();
		ArrayList<Card> deckOfCards = deck.getDeck();
		cardsToRemove.addAll(board.getCardsOfBoard());
		cardsToRemove.addAll(oppBoard.getCardsOfBoard());
		for (int i=0; i<cardsToRemove.size(); i++) {
			deckOfCards.remove(cardsToRemove.get(i));
		}
		Deck newDeck = new Deck(deckOfCards);
		// remove 3 cards for each player and add them to the board randomly, 
		// 4 times for a complete board 
		Random random = new Random();
		int n = random.nextInt(27);
		board.placeCardsOnBoard(newDeck.removeTopCards(3), n);
		oppBoard.placeCardsOnBoard(newDeck.removeTopCards(3),n);
		n = random.nextInt(27);
		board.placeCardsOnBoard(newDeck.removeTopCards(3), n);
		oppBoard.placeCardsOnBoard(newDeck.removeTopCards(3),n);
		n = random.nextInt(27);
		board.placeCardsOnBoard(newDeck.removeTopCards(3), n);
		oppBoard.placeCardsOnBoard(newDeck.removeTopCards(3),n);
		n = random.nextInt(27);
		board.placeCardsOnBoard(newDeck.removeTopCards(3), n);
		oppBoard.placeCardsOnBoard(newDeck.removeTopCards(3),n);
		// calculate the points this board generated, add it to the total
		double points = board.calculatePoints();
		copy.setExpectedPoints(points + board.getExpectedPoints());
		// decrement counter
		counter --;
		return simulate(copy,oppCopy,new Deck(),runs,counter);
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
