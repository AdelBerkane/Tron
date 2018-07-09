package model;

import controller.Game;

/**
 * TRON PROJECT
 * 
 * @author lborruto
 * @version 1.0
 */

/**
 * 
 * Define our Players using Getters and Setters
 * 
 */
public class Players {

	// Stores the traces locations for our Lightcycles
	private final int[] x = new int[Game.getGrid()];
	private final int[] y = new int[Game.getGrid()];

	// Stores direction of our lightcycles
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;

	// Set numbers of dots
	private int size = 0;

	/**
	 * 
	 * Get the coordinate X,Y for our Players
	 * 
	 */
	public int getPlayerX(int index) {
		return x[index];
	}

	public int getPlayerY(int index) {
		return y[index];
	}

	/**
	 * 
	 * Set the coordinate X,Y for our Players
	 * 
	 */
	public void setPlayerX(int i) {
		x[0] = i;
	}

	public void setPlayerY(int i) {
		y[0] = i;
	}

	/**
	 * 
	 * Set the movement of our players
	 * 
	 */
	public boolean isMovingLeft() {
		return left;
	}

	public void setMovingLeft(boolean movingLeft) {
		this.left = movingLeft;
	}

	public boolean isMovingRight() {
		return right;
	}

	public void setMovingRight(boolean movingRight) {
		this.right = movingRight;
	}

	public boolean isMovingUp() {
		return up;
	}

	public void setMovingUp(boolean movingUp) {
		this.up = movingUp;
	}

	public boolean isMovingDown() {
		return down;
	}

	public void setMovingDown(boolean movingDown) {
		this.down = movingDown;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int j) {
		size = j;
	}

	public void move() {
		for (int i = size; i > 0; i--) {

			// Moves the trace of the lightcycles after it
			x[i] = x[(i - 1)];
			y[i] = y[(i - 1)];
		}

		// Moves to the left
		if (left) {
			x[0] -= Game.getPixel();
		}
		// To the right
		if (right) {
			x[0] += Game.getPixel();
		}
		// Down
		if (down) {
			y[0] += Game.getPixel();
		}
		// Up
		if (up) {
			y[0] -= Game.getPixel();
		}
	}
}