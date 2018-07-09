package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Frame implements Runnable {
	// true if the instructions page is in the frame
		private boolean instructOn = false;
		// true if the high scores are displayed in the frame
		private boolean scoresOn = false;
		
		public void run() {
			final JFrame frame = new JFrame("Tron");
			frame.setBackground(Color.BLACK);
			frame.setPreferredSize(new Dimension(600, 400));
		    frame.setLocation(400, 100);
		    frame.setResizable(false);
		    
		    final JPanel gamemenu = new JPanel();
		    gamemenu.setLayout(new GridLayout(1,3));
		    gamemenu.setBackground(Color.BLACK);
		    
		    final JLabel score = new JLabel("    Score : 0" + "     Boost : 3");
		    score.setForeground(Color.WHITE);
		    score.setBackground(Color.BLACK);
		    gamemenu.add(score);
		    
		    
		    final Map level = new Map(score, 1);
		    level.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		    
		    
		}
		
		

}
