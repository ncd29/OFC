import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * the class that keeps track of the current state the game is in
 * @author nathanieldavenport
 *
 */

public class Gamestate {
	
	private Deck deck; // the current state of the deck of the game
	private Board[] boards; // the boards of each player
	private Player[] players; // the two (or three) players in the game
	private GUI gui; // the gui associated with the game
	private int p; // the player currently acting, 0 or 1
	
	/**
	 * initialize a new game with default settings
	 */
	public Gamestate() {
		deck = new Deck();
		boards = new Board[2];
		boards[0] = new Board();
		boards[1] = new Board();
		players = new Player[2];
		players[0] = new Player(boards[0],0,new ArrayList<Card>());
		players[1] = new Player(boards[1],0,new ArrayList<Card>());
		gui = new GUI();
		p = 0;
		deal(5,0);
	}
	
	/**
	 * deal the right amount of card's to player p
	 * n specifies the amount of cards to deal
	 */
	private void deal(int n, int p) {
		this.deck.shuffle();
		ArrayList<Card> cards = this.deck.removeTopCards(n);
		this.players[0].addCards(cards); 
		drawCards(cards,200,550,4);
	}
	
	private void drawCards(ArrayList<Card> cards, int width_offset, int height_offset, int h) {
		for (int i=0; i<cards.size(); i++) {
			try {	
				String card = cards.get(i).toString();
				System.out.println(card);
				ImageIcon icon = new ImageIcon(ImageIO.read(new File("images/PNG-cards-1.3/" + card + ".png")));
				Image img = icon.getImage();
				BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), 
				BufferedImage.TYPE_INT_ARGB);
				Graphics g = bi.createGraphics();
				//System.out.println(g);
				//System.out.println(width_offset);
				g.drawImage(img, width_offset, height_offset, 75, 150, null);
				ImageIcon newIcon = new ImageIcon(bi);
				JLabel image = new JLabel(newIcon);
				this.gui.addComponent(image,h,6,1,2,GridBagConstraints.CENTER,GridBagConstraints.BOTH);
				//width_offset += 10;
				//height_offset += 10;
				h ++;
			} catch (IOException e) {
				System.out.println("Incorrect filepath");
				e.printStackTrace();
			}
		}
	}
}
