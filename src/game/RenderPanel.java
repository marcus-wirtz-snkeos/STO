package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RenderPanel extends JPanel {
	
	public BufferedImage figure;
	public BufferedImage wolve_left;
	public BufferedImage wolve_right;
	public BufferedImage berryFull;
	public BufferedImage berryEmpty;
	public BufferedImage Pine1;
	public BufferedImage Pine1_Death;
	public BufferedImage Pine2;
	public BufferedImage Pine2_Death;
	public BufferedImage Fir;
	public BufferedImage Fir_Death;
	public BufferedImage Tree;
	public BufferedImage Tree_Death;
	public BufferedImage wood1;
	public BufferedImage wood2;
	
	public String imagePath = "/home/marcus/Documents/Java/STO/src/game/img/";

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		STO sto = STO.sto;
		g.setColor(new Color(0, 204, 0, 150));
		g.fillRect(0, 0, sto.dim.width, sto.dim.height);
		
		int cornerX = (int) Math.min(Math.max(sto.player.x - sto.dim.width / 2, 0), sto.worldX - sto.dim.width);
		int cornerY = (int) Math.min(Math.max(sto.player.y - sto.dim.height / 2, 0), sto.worldY - sto.dim.height);
		
		// Draw lakes
		g.setColor(Color.BLUE);
		for (int i = 0; i < sto.nLakes; i++) {
			int radius = sto.radiusLakes.get(i);
			g.fillOval(sto.lakes.get(i).x - radius - cornerX, sto.lakes.get(i).y - radius / 2 - cornerY, 2 * radius, radius);
		}
		
		/*
		// Draw wolve radius
		for (int i = 0; i < sto.nWolves; i++) {
			g.setColor(new Color(213, 134, 145, 60));
			g.fillOval(sto.wolves.get(i).x - (int) sto.wolveRadius - cornerX, sto.wolves.get(i).y - (int) sto.wolveRadius - cornerY, 
					 (int) (2 * sto.wolveRadius), (int) (2 * sto.wolveRadius));
		}
		*/
		
		try {
			Pine1 = ImageIO.read(new File(imagePath + "pine1.gif"));
			Pine1_Death = ImageIO.read(new File(imagePath + "pine1_death.gif"));;
			Pine2 = ImageIO.read(new File(imagePath + "pine2.gif"));;
			Pine2_Death = ImageIO.read(new File(imagePath + "pine2_death.gif"));;
			Fir = ImageIO.read(new File(imagePath + "fir.gif"));;
			Tree = ImageIO.read(new File(imagePath + "tree.gif"));;
			Tree_Death = ImageIO.read(new File(imagePath + "tree_death.gif"));;
			
			wood1 = ImageIO.read(new File(imagePath + "wood1.gif"));
			wood2 = ImageIO.read(new File(imagePath + "wood2.gif"));
			
			berryFull = ImageIO.read(new File(imagePath + "berry_full.gif"));
			berryEmpty = ImageIO.read(new File(imagePath + "berry_empty.gif"));
			
			wolve_left = ImageIO.read(new File(imagePath + "wolve_left.gif"));
			wolve_right = ImageIO.read(new File(imagePath + "wolve_right.gif"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		for (int pix = Math.max(0, cornerY - 100); pix < Math.min(sto.worldY, cornerY + sto.dim.height + 100); pix++) {
			// Draw trees
			
			for (int i = 0; i < sto.nTrees; i++) {
				if (sto.trees.get(i).y == pix){
					if (sto.treeType.get(i) == 0) {
						if (sto.treeDeath.get(i) == true)
							g.drawImage(Pine1_Death, sto.trees.get(i).x - cornerX - Pine1_Death.getWidth() / 2, sto.trees.get(i).y - cornerY - Pine1_Death.getHeight(), this);
						else
							g.drawImage(Pine1, sto.trees.get(i).x - cornerX - Pine1.getWidth() / 2, sto.trees.get(i).y - cornerY - Pine1.getHeight(), this);
					}
					if (sto.treeType.get(i) == 1) {
						if (sto.treeDeath.get(i) == true)
							g.drawImage(Pine2_Death, sto.trees.get(i).x - cornerX - Pine2_Death.getWidth() / 2, sto.trees.get(i).y - cornerY - Pine2_Death.getHeight(), this);
						else
							g.drawImage(Pine2, sto.trees.get(i).x - cornerX - Pine2.getWidth() / 2, sto.trees.get(i).y - cornerY - Pine2.getHeight(), this);
					}
					else if (sto.treeType.get(i) == 2) {
						g.drawImage(Fir, sto.trees.get(i).x - cornerX - Fir.getWidth() / 2, sto.trees.get(i).y - cornerY - Fir.getHeight(), this);
					}
					else{
						if (sto.treeDeath.get(i) == true)
							g.drawImage(Tree_Death, sto.trees.get(i).x - cornerX - Tree_Death.getWidth() / 2, sto.trees.get(i).y - cornerY - Tree_Death.getHeight(), this);
						else
							g.drawImage(Tree, sto.trees.get(i).x - cornerX - Tree.getWidth() / 2, sto.trees.get(i).y - cornerY - Tree.getHeight(), this);
					}
				}
			}
			
			// Draw woods
			for (int i = 0; i < sto.nWoods; i++) {
				if (sto.woods.get(i).y == pix){
					if (sto.woodStats.get(i) == true)
						g.drawImage(wood1, sto.woods.get(i).x - cornerX - wood1.getWidth() / 2, sto.woods.get(i).y - cornerY - wood1.getHeight(), this);
					else
						g.drawImage(wood2, sto.woods.get(i).x - cornerX - wood2.getWidth() / 2, sto.woods.get(i).y - cornerY - wood2.getWidth() / 2, this);
				}
			}
			
			// Draw berries
			for (int i = 0; i < sto.nBerries; i++) {
				if (sto.berries.get(i).y == pix){
					if (sto.berryStats.get(i) == true)
						g.drawImage(berryFull, sto.berries.get(i).x - cornerX - berryFull.getWidth() / 2, sto.berries.get(i).y - cornerY - berryFull.getHeight(), this);
					else
						g.drawImage(berryEmpty, sto.berries.get(i).x - cornerX - berryEmpty.getWidth() / 2, sto.berries.get(i).y - cornerY - berryEmpty.getHeight(), this);
				}
			}
			
			
			// Draw wolves
			for (int i = 0; i < sto.nWolves; i++) {
				int wolveX = (int) sto.wolves.get(i).x, wolveY = (int) sto.wolves.get(i).y;
				if (wolveY == pix){
					if (sto.wolveSpeed.get(i).x < 0)
						g.drawImage(wolve_left, wolveX - cornerX - wolve_left.getWidth() / 2, wolveY - cornerY - wolve_left.getHeight(), this);
					else
						g.drawImage(wolve_right, wolveX  - cornerX - wolve_right.getWidth() / 2, wolveY - cornerY - wolve_right.getHeight(), this);
				}
			}

			// Draw figure
			if ((int) sto.player.y == pix) {
				try {
					figure = ImageIO.read(new File(imagePath + "woman_" + sto.direction + ".gif"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g.drawImage(figure, (int) sto.player.x - cornerX - figure.getWidth() / 2, (int) sto.player.y - cornerY - figure.getHeight() + 10, this);
			}
		}
		
		/*
		// Draw Day/Night Cycle
		int bright = (int)  (120 * (1 - Math.cos((float) sto.tick / sto.dayLength)));
		g.setColor(new Color(0, 0, 0, bright));
		g.fillRect(0, 0, sto.dim.width, sto.dim.height);
		*/
		
		// Draw stats
		g.setFont(new Font("TimesRoman", Font.PLAIN, 22));
		
		String health = "Condition: " + sto.condition;
		g.setColor(Color.WHITE);
		g.drawString(health, sto.dim.width - 199, sto.dim.height - 169);
		if (sto.condition < 10)
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLACK);
		g.drawString(health, sto.dim.width - 200, sto.dim.height - 170);
		
		String tired = "Tired: " + sto.tired;
		g.setColor(Color.WHITE);
		g.drawString(tired, sto.dim.width - 199, sto.dim.height - 139);
		if (sto.tired == 0)
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLACK);
		g.drawString(tired, sto.dim.width - 200, sto.dim.height - 140);	

		String hungry = "Hungry: " + sto.hungry;
		g.setColor(Color.WHITE);
		g.drawString(hungry, sto.dim.width - 199, sto.dim.height - 109);
		if (sto.hungry == 0)
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLACK);
		g.drawString(hungry, sto.dim.width - 200, sto.dim.height - 110);

		String thirsty = "Thirsty: " + sto.thirsty;
		g.setColor(Color.WHITE);
		g.drawString(thirsty, sto.dim.width - 199, sto.dim.height - 79);
		if (sto.thirsty == 0)
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLACK);
		g.drawString(thirsty, sto.dim.width - 200, sto.dim.height - 80);	
		
		String score = "Score: " + sto.score;
		g.setColor(Color.WHITE);
		g.drawString(score, 11, 21);
		g.setColor(Color.BLACK);
		g.drawString(score, 10, 20);

		
		if (sto.run == true) {
			String run = "Running...";
			g.setColor(Color.RED);
			g.drawString(run, sto.dim.width / 2 - 20, sto.dim.height - 100);
		}
		
		if (sto.over == true) {
			String gameover = "GAME OVER!";
			g.setColor(Color.RED);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
			g.drawString(gameover, sto.dim.width / 2 - 8 * gameover.length(), sto.dim.height / 2 - 13);		
			g.setColor(Color.BLACK);
			g.drawString(gameover, sto.dim.width / 2 - 8 * gameover.length() + 1, sto.dim.height / 2 - 13 + 1);
			
			String restart = "Press 'R' for restart.";
			g.setFont(new Font("TimesRoman", Font.PLAIN, 22));
			g.drawString(restart, sto.dim.width / 2 - 82, sto.dim.height / 2 + 10);
		}
	}
}
