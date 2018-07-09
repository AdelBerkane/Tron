package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import controller.Game;

/**
 * TRON PROJECT
 * 
 * @author lborruto
 * @version 1.0
 */

/**
 * 
 * Our Main class with JFrame
 * 
 */
public class Main extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {

		// Creates a new thread so our interface can process itself
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new Main();
				frame.setVisible(true);
			}
		});
	}

	/**
	 * 
	 * Constructor for our GUI
	 * 
	 */
	Main() {
		// Launch the game by calling Game and so initializeGame();
		add(new Game());
		// Window not resizable
		setResizable(false);
		pack();
		// Title of our Window
		setTitle("TRON");
		setLocationRelativeTo(null);
		// Close the Java process when the Frame is closed (very important)
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}