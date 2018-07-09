package controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

import model.Player;
import model.Playerhuman;
import model.Score;
import view.Map;

@SuppressWarnings("serial")
public class TronMap extends Map {
	
	Score highs = new Score("HighScores.txt");
	List<Integer> highScores = new ArrayList<Integer>();
	
	public TronMap(JLabel sco1, int p) {
		super(sco1, null, p);
	}
	
	protected void tick() {
		for (Player k: getPlayers()) {
			if (k != null) {
				k.setBounds(getWidth(), getHeight());
				k.move();
			}
		}
		for (Player k1: getPlayers()) {
			for (Player k2: getPlayers()) {
				k1.crash(k1.intersects(k2));
			}
		}
		if (!Player.getAlive()) {
			getTimer().stop();
			setRun(false);
			addScore();
			setScore();
		} else {
			setRun(true);
			setScore();
		}
		repaint();
	}
	
	public void setScore() {
		getScore1().setText("   Score: " + setI(getI() + 1) + 
				"   Boost: " + getPlayer().getBoostsLeft());
		getScore1().repaint();
	}	
	
	public void reset() {
		int[] start1 = getRandomStart();
		setPlayer(new Playerhuman(start1[0], start1[1], 
				start1[2], start1[3], Color.CYAN));
		getPlayers()[0] = getPlayer();
		setI(0);
		getTimer().start();
		requestFocusInWindow();
	}
	
	public void addScore() {
		try {
			highs.addHighScore(getI() + 1);
			highScores = highs.getHighScores();
		} catch (IOException e) {
		}
	}
	
	public JPanel getHighs() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6, 2));
		panel.setBackground(Color.BLACK);
		
		JLabel j0 = new JLabel("    Survival Mode High Scores: ");
		j0.setForeground(Color.WHITE);
		j0.setBackground(Color.BLACK);
		panel.add(j0);
		
		JLabel j100 = new JLabel("");
		j100.setBackground(Color.BLACK);
		panel.add(j100);
		
		int left = 1;
		int right = 6;
		for (int i = 0; i < 5; i++) {
			JLabel j1 = new JLabel("      " + left + ".) " + highScores.get(left - 1).toString());
			j1.setForeground(Color.WHITE);
			j1.setBackground(Color.BLACK);
			panel.add(j1);
			JLabel j6 = new JLabel("      " + right + ".) " + highScores.get(right - 1).toString());
			j6.setForeground(Color.WHITE);
			j6.setBackground(Color.BLACK);
			panel.add(j6);
			left++;
			right++;
		}
		return panel;
	}
	
	@Override
	public void paintComponent(Graphics g) {
	   super.paintComponent(g);
	   if (!getPlayer().getAlive()) {
		   try{
			   BufferedImage picture = ImageIO.read(new File("over.png"));
			   g.drawImage(
					   picture, getMAPWIDTH() / 2 - 230, getMAPHEIGHT() / 2 - 110, null);
		   } catch (IOException e) {
		   }
	   }
	}

}
