package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RenderPanel extends JPanel {
	
	public BufferedImage figure;
	public BufferedImage wolve_left;
	public BufferedImage wolve_right;
	public BufferedImage berryFull;
	public BufferedImage berryEmpty;
	public String imagePath = "/home/marcus/Documents/Java/TLD/src/img/";

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		STO tld = STO.sto;
		g.setColor(new Color(0, 204, 0, 150));
		g.fillRect(0, 0, tld.dim.width, tld.dim.height);
		
		int cornerX = Math.min(Math.max(tld.player.x - tld.dim.width / 2, 0), tld.worldX - tld.dim.width);
		int cornerY = Math.min(Math.max(tld.player.y - tld.dim.height / 2, 0), tld.worldY - tld.dim.height);
		
		g.setColor(Color.BLUE);
		for (int i = 0; i < tld.nLakes; i++) {
			int radius = tld.radiusLakes.get(i);
			g.fillOval(tld.lakes.get(i).x - radius - cornerX, tld.lakes.get(i).y - radius / 2 - cornerY, 2 * radius, radius);
		}
		
		for (int i = 0; i < tld.nWolves; i++) {
			g.setColor(new Color(213, 134, 145, 60));
			g.fillOval(tld.wolves.get(i).x - (int) tld.wolveRadius - cornerX, tld.wolves.get(i).y - (int) tld.wolveRadius - cornerY, 
					 (int) (2 * tld.wolveRadius), (int) (2 * tld.wolveRadius));
			g.setColor(new Color(213, 134, 145, 60));
			g.fillOval(tld.wolves.get(i).x - (int) tld.wolveRadius / 2 - cornerX, tld.wolves.get(i).y - (int) tld.wolveRadius / 2 - cornerY, 
					 (int) (tld.wolveRadius), (int) (tld.wolveRadius));
		}
		
		// Draw berries
		try {
			berryFull = ImageIO.read(new File(imagePath + "berry_full.gif"));
			berryEmpty = ImageIO.read(new File(imagePath + "berry_empty.gif"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 0; i < tld.nBerries; i++) {
			if (tld.berryStats.get(i) == true)
				g.drawImage(berryFull, tld.berries.get(i).x - cornerX - 20, tld.berries.get(i).y - cornerY - 15, this);
			else
				g.drawImage(berryEmpty, tld.berries.get(i).x - cornerX - 20, tld.berries.get(i).y - cornerY - 15, this);
		}
		
		// Draw wolves
		try {
			wolve_left = ImageIO.read(new File(imagePath + "wolve_left.gif"));
			wolve_right = ImageIO.read(new File(imagePath + "wolve_right.gif"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 0; i < tld.nWolves; i++) {
			if (tld.wolveSpeed.get(i).x < 0)
				g.drawImage(wolve_left, tld.wolves.get(i).x  - cornerX- 25, tld.wolves.get(i).y - cornerY - 25, this);
			else
				g.drawImage(wolve_right, tld.wolves.get(i).x  - cornerX- 25, tld.wolves.get(i).y - cornerY - 25, this);
		}
		
		// Draw figure
		try {
			figure = ImageIO.read(new File(imagePath + "woman_" + tld.direction + ".gif"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		g.drawImage(figure, tld.player.x - cornerX - 20, tld.player.y - cornerY - 70, this);
		
		// Draw stats
		if (tld.condition == 0)
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLACK);
		String health = "Condition: " + tld.condition;
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		g.drawString(health, tld.dim.width - 200, tld.dim.height - 170);
		
		if (tld.hungry == 0)
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLACK);
		String hungry = "Hungry: " + tld.hungry;
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		g.drawString(hungry, tld.dim.width - 200, tld.dim.height - 140);
		
		if (tld.thirsty == 0)
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLACK);
		String thirsty = "Thirsty: " + tld.thirsty;
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		g.drawString(thirsty, tld.dim.width - 200, tld.dim.height - 110);	
		
		String score = "Score: " + tld.score;
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		g.drawString(score, 10, 20);
		
		if (tld.run == true) {
			String run = "Running...";
			g.setColor(Color.RED);
			g.drawString(run, tld.dim.width / 2 - 20, tld.dim.height - 100);
		}
		
		if (tld.over == true) {
			String gameover = "GAME OVER!";
			g.setColor(Color.RED);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
			g.drawString(gameover, tld.dim.width / 2 - 8 * gameover.length(), tld.dim.height / 2 - 13);		
			g.setColor(Color.BLACK);
			g.drawString(gameover, tld.dim.width / 2 - 8 * gameover.length() + 1, tld.dim.height / 2 - 13 + 1);
			return;
		}
	}
}
