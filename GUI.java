
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.*;
import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.awt.image.BufferedImage;

/**
 * Main class for the GUI
 * @author nathanieldavenport
 *
 * Card images from:
 * http://opengameart.org/content/playing-cards-vector-png - may have to check if these are ok to use 
 * if I end up doing more with this
 * Based off of a GUI (has been modified a lot) from CS2110 at Cornell
 * 
 * 
 */

public class GUI extends JFrame {
	
	private static int WIDTH = 1200;
	private static int HEIGHT = 800;
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	private Gamestate gamestate; // the gamestate probably not be needed?
	private String[] pngFileNames; // array of image file names (change this comment, 
	//put it up, and get rid of printing strings below)

	class PngFilter implements FilenameFilter {
		public boolean accept(File dir, String s) {
			return s.endsWith(".png");
		}
	}
	 
	public GUI() {
		// TODO Auto-generated constructor stub
		// Find all pngs that are in the PNG cards directory
		// need to rework this code a little
		File imageDir = new File("images/PNG-cards-1.3");
		pngFileNames = imageDir.list(new PngFilter());

		if (pngFileNames.length == 0) {
			throw new RuntimeException("No image files in images directory.");
		}
		
		System.out.println("Png File Names:");
		for (String f : pngFileNames) {
		  	//System.out.println(f);
		}
		setVisible(true);
		layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		setLayout(layout);
		setSize(WIDTH,HEIGHT);
		setTitle("Pineapple Calculator");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public GridBagLayout getLayout() {
		return layout;
	}
	
	public GridBagConstraints getConstraints() {
		return constraints;
	}
	
	public void addComponent(Component component,
		int gridx, int gridy, int gridwidth, int gridheight, int anchor, int fill) {
			GridBagConstraints c = new GridBagConstraints(gridx, gridy,
			gridwidth, gridheight, 1.0, 1.0, anchor, fill, new Insets(0,0,0,0), 0, 0);
			this.add(component, c);
	}
	
	/**
	 * runs the program
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Gamestate();
			}
		});
	}

}
