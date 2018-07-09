package view;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

import model.Player;

@SuppressWarnings("serial")
public abstract class Map extends JComponent {
	
	
	private Player player;
	private Player[] players;
	Color[] colors = {Color.CYAN, Color.PINK, Color.WHITE, Color.YELLOW,
			  Color.BLUE, Color.ORANGE, Color.RED, Color.GREEN};
	
	Random rand = new Random();
	
	
	private int MAPWIDTH = 600;
	private int MAPHEIGHT = 400;
	
	int VELOCITY = 3;
	
	private int i = 0;
	private JLabel score1;
	JLabel score2;
	
	int interval = 20;
	private Timer timer;
	private boolean run = true;
	
	public Map(JLabel sco1, JLabel sco2, int p) {
		setBackground(Color.WHITE);
		if (p > 8) { p = 8;}
		this.setPlayers(new Player[p]);
		this.setScore1(sco1);
		this.score2 = sco2;
		
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setFocusable(true);
		
		setTimer(new Timer(interval, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		}));
		getTimer().start();
		
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (!getPlayer().getAlive()) {
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					getPlayer().setXVelocity(-VELOCITY);
					getPlayer().setYVelocity(0);
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					getPlayer().setXVelocity(VELOCITY);
					getPlayer().setYVelocity(0);
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					getPlayer().setYVelocity(-VELOCITY);
					getPlayer().setXVelocity(0);
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					getPlayer().setYVelocity(VELOCITY);
					getPlayer().setXVelocity(0);
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					getPlayer().jump();
				} else if (e.getKeyCode() == KeyEvent.VK_B) {
					getPlayer().startBoost();
				}
				}
			public void keyReleased(KeyEvent e) {
				
			}
		});

}
	
	public int[] getRandomStart() {
		int[] start = new int[4];
		int xnew = 50 + rand.nextInt(400);
		int ynew = 50 + rand.nextInt(400);
		int ra = rand.nextInt(2);
		int velx = 0;
		int vely = 0;
		if (ra == 0) {
			if (xnew < 250) {
				velx = VELOCITY;
			} else {
				velx = -VELOCITY;
			}
		} else {
			if (ynew < 250) {
				vely = VELOCITY;
			} else {
				vely = -VELOCITY;
			}
		}
		start[0] = xnew;
		start[1] = ynew;
		start[2] = velx;
		start[3] = vely;
		return start;
	}
	
	// returns the velocity
		public int getVelocity() {
			return VELOCITY;
		}
		
		// moves the game by one timestamp
		protected abstract void tick();
		
		// initializes all new characters and restarts the timer
		public abstract void reset();
		
		// changes the score being displayed
		public abstract void setScore();
		
		// adds scores to high scores or sets the score after a level
		public abstract void addScore();
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getMAPWIDTH(), getMAPHEIGHT());
			for (Player p: getPlayers()) {
				if (p != null) {
					p.draw(g);
				}
			}
		}
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(getMAPWIDTH(), getMAPHEIGHT());
		}

		public Player[] getPlayers() {
			return players;
		}

		public void setPlayers(Player[] players) {
			this.players = players;
		}

		public boolean isRun() {
			return run;
		}

		public void setRun(boolean run) {
			this.run = run;
		}

		public Timer getTimer() {
			return timer;
		}

		public void setTimer(Timer timer) {
			this.timer = timer;
		}

		public JLabel getScore1() {
			return score1;
		}

		public void setScore1(JLabel score1) {
			this.score1 = score1;
		}

		public int getI() {
			return i;
		}

		public int setI(int i) {
			this.i = i;
			return i;
		}

		public Player getPlayer() {
			return player;
		}

		public void setPlayer(Player player) {
			this.player = player;
		}

		public int getMAPWIDTH() {
			return MAPWIDTH;
		}

		public void setMAPWIDTH(int mAPWIDTH) {
			MAPWIDTH = mAPWIDTH;
		}

		public int getMAPHEIGHT() {
			return MAPHEIGHT;
		}

		public void setMAPHEIGHT(int mAPHEIGHT) {
			MAPHEIGHT = mAPHEIGHT;
		}
		
		
}
