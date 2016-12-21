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
	public BufferedImage Fir1;
	public BufferedImage Fir2;
	public BufferedImage Fir_Death;
	public BufferedImage Tree;
	public BufferedImage Tree_Death;
	public BufferedImage wood;
	public BufferedImage berry;
	public BufferedImage wood1;
	public BufferedImage wood2;
	public BufferedImage plant1;
	public BufferedImage plant2;
	public BufferedImage plant3;
	public BufferedImage plant4;
	public BufferedImage stone;
	public BufferedImage rock1;
	public BufferedImage rock2;
	public BufferedImage leafe;
	public BufferedImage liana;
	
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
			Fir1 = ImageIO.read(new File(imagePath + "fir1.gif"));;
			Fir2 = ImageIO.read(new File(imagePath + "fir1.gif"));;
			Tree = ImageIO.read(new File(imagePath + "tree.gif"));;
			Tree_Death = ImageIO.read(new File(imagePath + "tree_death.gif"));;
			
			wood1 = ImageIO.read(new File(imagePath + "wood1.gif"));
			wood2 = ImageIO.read(new File(imagePath + "wood2.gif"));
			
			plant1 = ImageIO.read(new File(imagePath + "plant1.gif"));
			plant2 = ImageIO.read(new File(imagePath + "plant2.gif"));
			plant3 = ImageIO.read(new File(imagePath + "plant3.gif"));
			plant4 = ImageIO.read(new File(imagePath + "plant4.gif"));
			
			berryFull = ImageIO.read(new File(imagePath + "berry_full.gif"));
			berryEmpty = ImageIO.read(new File(imagePath + "berry_empty.gif"));
			
			wolve_left = ImageIO.read(new File(imagePath + "wolve_left.gif"));
			wolve_right = ImageIO.read(new File(imagePath + "wolve_right.gif"));
			
			stone = ImageIO.read(new File(imagePath + "stone.gif"));
			rock1 = ImageIO.read(new File(imagePath + "rock1.gif"));
			rock2 = ImageIO.read(new File(imagePath + "rock2.gif"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		for (int pixY = Math.max(0, cornerY - 100); pixY < Math.min(sto.worldY, cornerY + sto.dim.height + 100); pixY++) {
			// Draw trees
			
			for (int i = 0; i < sto.nTrees; i++) {
				int x = sto.trees.get(i).x;
				if (sto.trees.get(i).y == pixY){
					if (sto.treeType.get(i) == 0) {
						if (sto.treeDeath.get(i) == true)
							g.drawImage(Pine1_Death, x - cornerX - Pine1_Death.getWidth() / 2, pixY - cornerY - Pine1_Death.getHeight(), this);
						else
							g.drawImage(Pine1, x - cornerX - Pine1.getWidth() / 2, pixY - cornerY - Pine1.getHeight(), this);
					}
					if (sto.treeType.get(i) == 1) {
						if (sto.treeDeath.get(i) == true)
							g.drawImage(Pine2_Death, x - cornerX - Pine2_Death.getWidth() / 2, sto.trees.get(i).y - cornerY - Pine2_Death.getHeight(), this);
						else
							g.drawImage(Pine2, x - cornerX - Pine2.getWidth() / 2, pixY - cornerY - Pine2.getHeight(), this);
					}
					else if (sto.treeType.get(i) == 2) {
						g.drawImage(Fir1, x - cornerX - Fir1.getWidth() / 2, pixY - cornerY - Fir1.getHeight(), this);
					}
					else if (sto.treeType.get(i) == 3) {
						g.drawImage(Fir2, x - cornerX - Fir1.getWidth() / 2, pixY - cornerY - Fir2.getHeight(), this);
					}
					else{
						if (sto.treeDeath.get(i) == true)
							g.drawImage(Tree_Death, x - cornerX - Tree_Death.getWidth() / 2, pixY - cornerY - Tree_Death.getHeight(), this);
						else
							g.drawImage(Tree, x - cornerX - Tree.getWidth() / 2, pixY - cornerY - Tree.getHeight(), this);
					}
				}
			}
			
			// Draw woods
			for (int i = 0; i < sto.nWoods; i++) {
				int x = sto.woods.get(i).x;
				if (sto.woods.get(i).y == pixY){
					if (sto.woodStats.get(i) == true)
						g.drawImage(wood1, x - cornerX - wood1.getWidth() / 2, pixY - cornerY - wood1.getHeight(), this);
					else
						g.drawImage(wood2, x - cornerX - wood2.getWidth() / 2, pixY - cornerY - wood2.getWidth() / 2, this);
				}
			}
			
			// Draw plants
			for (int i = 0; i < sto.nPlants; i++) {
				int x = sto.plants.get(i).x;
				if (sto.plants.get(i).y == pixY){
					if (sto.plantType.get(i) == 1)
						g.drawImage(plant1, x - cornerX - plant1.getWidth() / 2, pixY - cornerY - plant1.getHeight(), this);
					if (sto.plantType.get(i) == 2)
						g.drawImage(plant2, x - cornerX - plant2.getWidth() / 2, pixY - cornerY - plant2.getHeight(), this);
					if (sto.plantType.get(i) == 3)
						g.drawImage(plant3, x - cornerX - plant3.getWidth() / 2, pixY - cornerY - plant3.getHeight(), this);
					if (sto.plantType.get(i) == 4)
						g.drawImage(plant4, x - cornerX - plant4.getWidth() / 2, pixY - cornerY - plant4.getHeight(), this);
				}
			}
			
			// Draw stones
			for (int i = 0; i < sto.nStones; i++) {
				int x = sto.stones.get(i).x;
				if (sto.stones.get(i).y == pixY){
					g.drawImage(stone, x - cornerX - stone.getWidth() / 2, pixY - cornerY - stone.getHeight(), this);
				}
			}
			
			// Draw rocks
			for (int i = 0; i < sto.nRocks; i++) {
				int x = sto.rocks.get(i).x;
				if (sto.rocks.get(i).y == pixY){
					if (sto.rockType.get(i) == 1)
						g.drawImage(rock1, x - cornerX - rock1.getWidth() / 2, pixY - cornerY - rock1.getHeight(), this);
					else
						g.drawImage(rock2, x - cornerX - rock2.getWidth() / 2, pixY - cornerY - rock2.getWidth() / 2, this);
				}
			}
			
			// Draw berries
			for (int i = 0; i < sto.nBerries; i++) {
				int x = sto.berries.get(i).x;
				if (sto.berries.get(i).y == pixY){
					if (sto.berryStats.get(i) == true)
						g.drawImage(berryFull, x - cornerX - berryFull.getWidth() / 2, pixY - cornerY - berryFull.getHeight(), this);
					else
						g.drawImage(berryEmpty, x - cornerX - berryEmpty.getWidth() / 2, pixY - cornerY - berryEmpty.getHeight(), this);
				}
			}
			
			
			// Draw wolves
			for (int i = 0; i < sto.nWolves; i++) {
				int wolveX = (int) sto.wolves.get(i).x, wolveY = (int) sto.wolves.get(i).y;
				if (wolveY == pixY){
					if (sto.wolveSpeed.get(i).x < 0)
						g.drawImage(wolve_left, wolveX - cornerX - wolve_left.getWidth() / 2, wolveY - cornerY - wolve_left.getHeight(), this);
					else
						g.drawImage(wolve_right, wolveX  - cornerX - wolve_right.getWidth() / 2, wolveY - cornerY - wolve_right.getHeight(), this);
				}
			}

			// Draw figure
			if ((int) sto.player.y == pixY) {
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
		
		// Draw Score
		String score = "Score: " + sto.score;
		g.setColor(Color.WHITE);
		g.drawString(score, 11, 21);
		g.setColor(Color.BLACK);
		g.drawString(score, 10, 20);
		
		// Draw inventory
		String berries = sto.berryCollected + " x         (e)";
		g.drawString(berries, 15, sto.dim.height - 240);
		String woods = sto.woodCollected + " x ";
		g.drawString(woods, 15, sto.dim.height - 200);
		String stones = sto.stoneCollected + " x ";
		g.drawString(stones, 15, sto.dim.height - 160);
		String leaves = sto.leaveCollected + " x ";
		g.drawString(leaves, 15, sto.dim.height - 120);
		String lianas = sto.lianaCollected + " x ";
		g.drawString(lianas, 15, sto.dim.height - 80);
		
		try {
			berry = ImageIO.read(new File(imagePath + "berry.gif"));
			wood = ImageIO.read(new File(imagePath + "wood.gif"));
			stone = ImageIO.read(new File(imagePath + "stone2.gif"));
			leafe = ImageIO.read(new File(imagePath + "leafe.gif"));
			liana = ImageIO.read(new File(imagePath + "liana.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.drawImage(berry, 65, sto.dim.height - 263, this);
		g.drawImage(wood, 60, sto.dim.height - 224, this);
		g.drawImage(stone, 65, sto.dim.height - 182, this);
		g.drawImage(leafe, 65, sto.dim.height - 142, this);
		g.drawImage(liana, 63, sto.dim.height - 96, this);
		
		if (sto.run == true && sto.tired > 0) {
			String run = "Running...";
			g.setColor(Color.RED);
			g.drawString(run, sto.dim.width / 2 - 40, sto.dim.height - 100);
		}
		
		if (sto.search == true) {
			String run = "Searching...";
			g.setColor(Color.WHITE);
			g.drawString(run, sto.dim.width / 2 - 40, sto.dim.height - 100);
			for (int i = 0; i < 200-sto.searchTime; i++)
				g.fillRect(sto.dim.width / 2 - 200 + 2*i, sto.dim.height - 90, 2, 10);
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
