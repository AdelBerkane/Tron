package controller;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JPanel;
import javax.swing.Timer;

import model.Players;

/**
 * TRON PROJECT
 * 
 * @author lborruto
 * @version 1.0
 */
public class Game extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	// Define the width and the height of the window
	private final static int width = 600;
	private final static int height = 400;

	// Define the pixel size of our lightcycle
	private final static int pixel = 5;

	// The total of pixels the game could possibly have
	private final static int playGrid = (width * height) / (pixel * pixel);

	// Check if the game is running or not
	private boolean isRunning = true;

	// Record the tick time to make the lightcycles moving
	private Timer timer;

	// Set the game speed (the lower the value is, higher the lightcycle speed will
	// be)
	private int speed = 30;

	// Instances of our lightcycles
	private Players player1 = new Players();
	private Players player2 = new Players();
	

	// Define the variable winner for the endscreen and the database
	private String winner;

	// Define the chronometer set to know the time of a game
	static double chrono = 0;
	static double chrono2 = 0;
	static double temps;

	/**
	 * 
	 * Launch our JPanel window and start the game loop with " initializeGame(); "
	 * 
	 */
	public Game() {
		chrono = java.lang.System.currentTimeMillis();
		addKeyListener(new Keys());
		setBackground(Color.BLACK);
		setFocusable(true);
		setPreferredSize(new Dimension(width, height));
		initializeGame();
	}

	/**
	 * 
	 * Used to paint our components to the screen
	 * 
	 * 
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	/**
	 * 
	 * Start the principal game loop
	 * 
	 * 
	 */
	public void initializeGame() {

		// Set the lightcycle trail size
		player1.setSize(999);
		player2.setSize(999);

		// Create our player 1 lightcycle
		for (int i = 0; i < player1.getSize(); i++) {
			// Set the initial position of the lightcycle
			player1.setPlayerX(width / 2);
			player1.setPlayerY(height / 2);
		}

		// Create our player 2 lightcycle
		for (int i = 0; i < player2.getSize(); i++) {
			// Set the initial position of the lightcycle
			player2.setPlayerX(100);
			player2.setPlayerY(100);
		}
		// Set the initial movement of the lightcycle 1 and 2
		player1.setMovingLeft(true);
		player2.setMovingRight(true);

		// Timer to make the game move
		timer = new Timer(speed, this);
		timer.start();
	}

	/**
	 * 
	 * Draw our lightcycles called on repaint()
	 * 
	 */
	void draw(Graphics g) {

		// Only draw if the game is running
		if (isRunning == true) {
			// Draw our first lightcycle.
			for (int i = 0; i < player1.getSize(); i++) {
				// Lightcycle color
				g.setColor(Color.RED);
				// Draw our lightcycle with initial position and Rectangular (g.fillRect)
				g.fillRect(player1.getPlayerX(i), player1.getPlayerY(i), pixel, pixel);

			}
			// Draw our second lightcycle.
			for (int i = 0; i < player2.getSize(); i++) {
				g.setColor(Color.BLUE);
				g.fillRect(player2.getPlayerX(i), player2.getPlayerY(i), pixel, pixel);

			}
			// Synchronise the graphic
			Toolkit.getDefaultToolkit().sync();
		} else {
			// End the game if players loose or win
			chrono2 = java.lang.System.currentTimeMillis();
			temps = (chrono2 - chrono) / 1000;
			// Call the database and write Winner and Time
			Database();
			// End the game with Game over Screen + winner
			endGame(g);
		}
	}

	/**
	 * 
	 * Check the collisions between our lightcycles and itself
	 * 
	 * 
	 */
	void checkCollisions() {

		for (int i = player1.getSize(); i > 0; i--) {
			// Lightcycle can't intersect with itself
			if ((player1.getPlayerX(0) == player1.getPlayerX(i) && (player1.getPlayerY(0) == player1.getPlayerY(i)))) {
				// The game end
				isRunning = false;
				// Set the winner for the specific interaction
				winner = "BLUE WON";
			}
			// Lightcycle can't intersect with player2
			if ((player1.getPlayerX(0) == player2.getPlayerX(i) && (player1.getPlayerY(0) == player2.getPlayerY(i)))) {
				isRunning = false;
				winner = "BLUE WON";
			}
		}
		// Do the same for player2
		for (int i = player2.getSize(); i > 0; i--) {
			if ((player2.getPlayerX(0) == player2.getPlayerX(i) && (player2.getPlayerY(0) == player2.getPlayerY(i)))) {
				isRunning = false;
				winner = "RED WON";
			}
			if ((player2.getPlayerX(0) == player1.getPlayerX(i) && (player2.getPlayerY(0) == player1.getPlayerY(i)))) {
				isRunning = false;
				winner = "RED WON";
			}
		}

		// If the lightcycle intersects with the board edges
		// the game continue and set the lightcycle to the other edge
		if (player1.getPlayerY(0) >= height) {
			player1.setPlayerY(0);
		}

		if (player1.getPlayerY(0) < 0) {
			player1.setPlayerY(height);
		}

		if (player1.getPlayerX(0) >= width) {
			player1.setPlayerX(0);
		}

		if (player1.getPlayerX(0) < 0) {
			player1.setPlayerX(width);
		}

		if (player2.getPlayerY(0) >= height) {
			player2.setPlayerY(0);
		}

		if (player2.getPlayerY(0) < 0) {
			player2.setPlayerY(height);
		}

		if (player2.getPlayerX(0) >= width) {
			player2.setPlayerX(0);
		}

		if (player2.getPlayerX(0) < 0) {
			player2.setPlayerX(width);
		}

		// If the game end, stop our timer
		if (!isRunning) {
			timer.stop();
		}
	}

	/**
	 * 
	 * If the game end, draw an end screen with winner + Game over
	 * 
	 * 
	 */
	void endGame(Graphics g) {
		// Create message for game over and winner
		String message = "Game over";
		String message2 = winner;

		// To implement :
		// double message3 = temps;

		// Create a new font instance
		Font font = new Font("Times New Roman", Font.BOLD, 30);
		FontMetrics metrics = getFontMetrics(font);

		// Set the color of the text and the font
		g.setColor(Color.red);
		g.setFont(font);

		// Draw the message to the board
		g.drawString(message, (width - metrics.stringWidth(message)) / 2, height / 2);
		g.drawString(message2, (width - metrics.stringWidth(message)) / 3, height / 3);

		// To implement :
		// g.drawDouble(message3, (width - metrics.stringWidth(message)) / 4, height /
		// 4);

		System.out.println("END");
	}

	/**
	 * 
	 * Run constantly as long as we're in game.
	 * 
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		if (isRunning == true) {
			checkCollisions();
			player1.move();
			player2.move();
		}
		// Repaint our screen
		repaint();
	}

	/**
	 * 
	 * Set our Keys for playing
	 * 
	 * 
	 */
	private class Keys extends KeyAdapter {

		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();

			if ((key == KeyEvent.VK_LEFT) && (!player1.isMovingRight())) {
				player1.setMovingLeft(true);
				player1.setMovingUp(false);
				player1.setMovingDown(false);
			}

			if ((key == KeyEvent.VK_RIGHT) && (!player1.isMovingLeft())) {
				player1.setMovingRight(true);
				player1.setMovingUp(false);
				player1.setMovingDown(false);
			}

			if ((key == KeyEvent.VK_UP) && (!player1.isMovingDown())) {
				player1.setMovingUp(true);
				player1.setMovingRight(false);
				player1.setMovingLeft(false);
			}

			if ((key == KeyEvent.VK_DOWN) && (!player1.isMovingUp())) {
				player1.setMovingDown(true);
				player1.setMovingRight(false);
				player1.setMovingLeft(false);
			}

			if ((key == KeyEvent.VK_Q) && (!player2.isMovingRight())) {
				player2.setMovingLeft(true);
				player2.setMovingUp(false);
				player2.setMovingDown(false);
			}

			if ((key == KeyEvent.VK_D) && (!player2.isMovingLeft())) {
				player2.setMovingRight(true);
				player2.setMovingUp(false);
				player2.setMovingDown(false);
			}

			if ((key == KeyEvent.VK_Z) && (!player2.isMovingDown())) {
				player2.setMovingUp(true);
				player2.setMovingRight(false);
				player2.setMovingLeft(false);
			}

			if ((key == KeyEvent.VK_S) && (!player2.isMovingUp())) {
				player2.setMovingDown(true);
				player2.setMovingRight(false);
				player2.setMovingLeft(false);
			}
		}
	}

	/**
	 * 
	 * Set the database communication to write the winner and the time
	 * 
	 */
	public void Database() {

		try {
			System.out.println(temps);
			System.out.println(winner);
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String URL = "jdbc:mysql://localhost:3306/tron?autoReconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			String USER = "root";
			String PASS = "adel";

			// Connection to the Database
			Connection conn = DriverManager.getConnection(URL, USER, PASS);

			// Statement to set our variables
			PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO score(player, time) VALUES(?, ?)");
			preparedStatement.setObject(1, winner, Types.CHAR);
			preparedStatement.setObject(2, temps, Types.DOUBLE);
			preparedStatement.executeUpdate();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Used to set the total of pixel we could have
	 * 
	 *
	 */
	public static int getGrid() {
		return playGrid;
	}

	/**
	 * 
	 * Used to get Pixel size of our lightcycles
	 * 
	 *
	 */
	public static int getPixel() {
		return pixel;
	}
}