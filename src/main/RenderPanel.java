package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RenderPanel extends JPanel {
	
	static Random random =  new Random();
	
	public BufferedImage figure;
	public BufferedImage wolve_left, wolve_right;
	public BufferedImage rabbit_left, rabbit_right;
	public BufferedImage fish_left, fish_right;
	public BufferedImage berryFull, berryEmpty;
	public BufferedImage Pine1, Pine1_Death, Pine2, Pine2_Death, Fir1, Fir2, Fir_Death, Tree, Tree_Death;
	public BufferedImage berry, fish, cooked_meat, raw_meat;
	public BufferedImage wood1, wood2;
	public BufferedImage plant1, plant2, plant3, plant4;
	public BufferedImage reed, lily1, lily2, lily3;
	public BufferedImage stone, wood, rock1, rock2, leafe, liana;
	public BufferedImage snare, snare_shot, fish_trap, fish_trap_shot;
	public BufferedImage fire1, fire2, fire3, fire4, fire5, fire6, fire_burned;
	public BufferedImage shelter, shelter_hidden;
	public BufferedImage button1, button2, button3, button4, button1_low, button2_low, button3_low, button4_low;
	
	public String imagePath = System.getProperty("user.dir") + "/img/";
	
	private void readImages(Graphics g) {

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
			
			reed = ImageIO.read(new File(imagePath + "reed.png"));
			lily1 = ImageIO.read(new File(imagePath + "lily1.gif"));
			lily2 = ImageIO.read(new File(imagePath + "lily2.gif"));
			lily3 = ImageIO.read(new File(imagePath + "lily3.gif"));
			
			berryFull = ImageIO.read(new File(imagePath + "berry_full.gif"));
			berryEmpty = ImageIO.read(new File(imagePath + "berry_empty.gif"));
			
			wolve_left = ImageIO.read(new File(imagePath + "wolve_left.gif"));
			wolve_right = ImageIO.read(new File(imagePath + "wolve_right.gif"));
			rabbit_left = ImageIO.read(new File(imagePath + "rabbit_left.gif"));
			rabbit_right = ImageIO.read(new File(imagePath + "rabbit_right.gif"));
			fish_left = ImageIO.read(new File(imagePath + "fish_left.png"));
			fish_right = ImageIO.read(new File(imagePath + "fish_right.png"));
			
			stone = ImageIO.read(new File(imagePath + "stone.gif"));
			// stone = ImageIO.read(new File(imagePath + "stone2.gif"));
			rock1 = ImageIO.read(new File(imagePath + "rock1.gif"));
			rock2 = ImageIO.read(new File(imagePath + "rock2.gif"));
			
			wood = ImageIO.read(new File(imagePath + "wood.gif"));
			leafe = ImageIO.read(new File(imagePath + "leafe.gif"));
			liana = ImageIO.read(new File(imagePath + "liana.gif"));
			
			berry = ImageIO.read(new File(imagePath + "berry.gif"));
			cooked_meat = ImageIO.read(new File(imagePath + "cooked_meat.gif"));
			raw_meat = ImageIO.read(new File(imagePath + "raw_meat.gif"));
			fish = ImageIO.read(new File(imagePath + "fish.gif"));
	
			button1 = ImageIO.read(new File(imagePath + "button1.png"));
			button2 = ImageIO.read(new File(imagePath + "button2.png"));
			button3 = ImageIO.read(new File(imagePath + "button3.png"));
			button4 = ImageIO.read(new File(imagePath + "button4.png"));
			button1_low = ImageIO.read(new File(imagePath + "button1_low.png"));
			button2_low = ImageIO.read(new File(imagePath + "button2_low.png"));
			button3_low = ImageIO.read(new File(imagePath + "button3_low.png"));
			button4_low = ImageIO.read(new File(imagePath + "button4_low.png"));
			
			snare = ImageIO.read(new File(imagePath + "snare.gif"));
			snare_shot = ImageIO.read(new File(imagePath + "snare_shot.gif"));
			fish_trap = ImageIO.read(new File(imagePath + "fish_trap.png"));
			fish_trap_shot = ImageIO.read(new File(imagePath + "fish_trap_shot.png"));
			fire1 = ImageIO.read(new File(imagePath + "fire1.gif"));
			fire2 = ImageIO.read(new File(imagePath + "fire2.gif"));
			fire3 = ImageIO.read(new File(imagePath + "fire3.gif"));
			fire4 = ImageIO.read(new File(imagePath + "fire4.gif"));
			fire5 = ImageIO.read(new File(imagePath + "fire5.gif"));
			fire6 = ImageIO.read(new File(imagePath + "fire6.gif"));
			fire_burned = ImageIO.read(new File(imagePath + "fire_burned.gif"));
			shelter = ImageIO.read(new File(imagePath + "shelter.png"));
			shelter_hidden = ImageIO.read(new File(imagePath + "shelter_hidden.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	protected void paintComponent(Graphics g) {
		
		this.readImages(g);

		super.paintComponent(g);
		
		g.setColor(new Color(0, 204, 0, 150));
		g.fillRect(0, 0, World.dim.width, World.dim.height);
		
		int cornerX = (int) Math.min(Math.max(Game.player.getX() - World.dim.width / 2, 0), World.worldX - World.dim.width);
		int cornerY = (int) Math.min(Math.max(Game.player.getY() - World.dim.height / 2, 0), World.worldY - World.dim.height);
		
		// Draw lakes
		g.setColor(Color.BLUE);
		for (int i = 0; i < World.nLakes; i++) {
			int radius = World.radiusLakes.get(i);
			g.fillOval((int) World.lakes.get(i).x - radius - cornerX, (int) World.lakes.get(i).y - radius / 2 - cornerY, 2 * radius, radius);
		}
		
		int treeInd = 0, plantInd = 0, rockInd = 0, berryInd = 0, woodInd = 0, stoneInd = 0, lilyInd = 0, reedInd = 0;
		for (int pixY = Math.max(0, cornerY - 100); pixY < Math.min(World.worldY, cornerY + World.dim.height + 200); pixY++) {

			// Draw trees
			treeInd = this.getFirstObjectIndexInY(pixY, treeInd, World.trees);
			while (this.checkIterateOnY(pixY, treeInd, World.trees)) {
				int x = (int) World.trees.get(treeInd).x;
				if (this.checkOutOfWindow(x, cornerX)) { treeInd++; continue; }

				if (World.treeType.get(treeInd) == 0) {
					if (World.treeDeath.get(treeInd) == true)
						g.drawImage(Pine1_Death, x - cornerX - Pine1_Death.getWidth() / 2, pixY - cornerY - Pine1_Death.getHeight(), this);
					else
						g.drawImage(Pine1, x - cornerX - Pine1.getWidth() / 2, pixY - cornerY - Pine1.getHeight(), this);
				}
				else if (World.treeType.get(treeInd) == 1) {
					if (World.treeDeath.get(treeInd) == true)
						g.drawImage(Pine2_Death, x - cornerX - Pine2_Death.getWidth() / 2, pixY - cornerY - Pine2_Death.getHeight(), this);
					else
						g.drawImage(Pine2, x - cornerX - Pine2.getWidth() / 2, pixY - cornerY - Pine2.getHeight(), this);
				}
				else if (World.treeType.get(treeInd) == 2) {
					g.drawImage(Fir1, x - cornerX - Fir1.getWidth() / 2, pixY - cornerY - Fir1.getHeight(), this);
				}
				else if (World.treeType.get(treeInd) == 3) {
					g.drawImage(Fir2, x - cornerX - Fir1.getWidth() / 2, pixY - cornerY - Fir2.getHeight(), this);
				}
				else{
					if (World.treeDeath.get(treeInd) == true)
						g.drawImage(Tree_Death, x - cornerX - Tree_Death.getWidth() / 2, pixY - cornerY - Tree_Death.getHeight(), this);
					else
						g.drawImage(Tree, x - cornerX - Tree.getWidth() / 2, pixY - cornerY - Tree.getHeight(), this);
				}
				treeInd++;
			}
			
			// Draw rocks
			rockInd = this.getFirstObjectIndexInY(pixY, rockInd, World.rocks);	
			while (this.checkIterateOnY(pixY, rockInd, World.rocks)) {
				int x = (int) World.rocks.get(rockInd).x;
				if (this.checkOutOfWindow(x, cornerX)) { rockInd++; continue; }

				if (World.rockType.get(rockInd) == 1)
					g.drawImage(rock1, x - cornerX - rock1.getWidth() / 2, pixY - cornerY - rock1.getHeight(), this);
				else
					g.drawImage(rock2, x - cornerX - rock2.getWidth() / 2, pixY - cornerY - rock2.getHeight(), this);
				rockInd++;
			}

			// Draw plants
			plantInd = this.getFirstObjectIndexInY(pixY, plantInd, World.plants);
			while (this.checkIterateOnY(pixY, plantInd, World.plants)) {
				int x = (int) World.plants.get(plantInd).x;
				if (this.checkOutOfWindow(x, cornerX)) { plantInd++; continue; }

				if (World.plantType.get(plantInd) == 1)
					g.drawImage(plant1, x - cornerX - plant1.getWidth() / 2, pixY - cornerY - plant1.getHeight(), this);
				if (World.plantType.get(plantInd) == 2)
					g.drawImage(plant2, x - cornerX - plant2.getWidth() / 2, pixY - cornerY - plant2.getHeight(), this);
				if (World.plantType.get(plantInd) == 3)
					g.drawImage(plant3, x - cornerX - plant3.getWidth() / 2, pixY - cornerY - plant3.getHeight(), this);
				if (World.plantType.get(plantInd) == 4)
					g.drawImage(plant4, x - cornerX - plant4.getWidth() / 2, pixY - cornerY - plant4.getHeight(), this);
				plantInd++;
			}

			// Draw berries
			berryInd = this.getFirstObjectIndexInY(pixY, berryInd, World.berries);
			while (this.checkIterateOnY(pixY, berryInd, World.berries)) {
				int x = (int) World.berries.get(berryInd).x;
				if (this.checkOutOfWindow(x, cornerX)) { berryInd++; continue; }

				if (World.berryStats.get(berryInd) == true)
					g.drawImage(berryFull, x - cornerX - berryFull.getWidth() / 2, pixY - cornerY - berryFull.getHeight(), this);
				else
					g.drawImage(berryEmpty, x - cornerX - berryEmpty.getWidth() / 2, pixY - cornerY - berryEmpty.getHeight(), this);
				berryInd++;
			}

			// Draw woods
			woodInd = this.getFirstObjectIndexInY(pixY, woodInd, World.woods);
			while (this.checkIterateOnY(pixY, woodInd, World.woods)) {
				int x = (int) World.woods.get(woodInd).x;
				if (this.checkOutOfWindow(x, cornerX)) { woodInd++; continue; }
				
				int x_draw = x - cornerX - wood1.getWidth() / 2;
				int y_draw = pixY - cornerY - wood1.getHeight() / 2;
				if (World.woodStats.get(woodInd) == true) { g.drawImage(wood1, x_draw, y_draw, this); }
				else { g.drawImage(wood2, x_draw, y_draw, this); }
				woodInd++;
			}
			
			// Draw stones
			stoneInd = this.getFirstObjectIndexInY(pixY, stoneInd, World.stones);
			while (this.checkIterateOnY(pixY, stoneInd, World.stones)) {
				if ((int) World.stones.get(stoneInd).y != pixY) { continue; }
				int x = (int) World.stones.get(stoneInd).x;
				if (this.checkOutOfWindow(x, cornerX)) { stoneInd++; continue; }

				g.drawImage(stone, x - cornerX - stone.getWidth() / 2, pixY - cornerY - stone.getHeight(), this);
				stoneInd++;
			}
			
			// Draw lilies
			lilyInd = this.getFirstObjectIndexInY(pixY, lilyInd, World.lilies);
			while (this.checkIterateOnY(pixY, lilyInd, World.lilies)) {
				int x = (int) World.lilies.get(lilyInd).x;
				if (this.checkOutOfWindow(x, cornerX)) { lilyInd++; continue; }

				if (World.lilyType.get(lilyInd) == 1)
					g.drawImage(lily1, x - cornerX - lily1.getWidth() / 2, pixY - cornerY - lily1.getHeight(), this);
				if (World.lilyType.get(lilyInd) == 2)
					g.drawImage(lily2, x - cornerX - lily2.getWidth() / 2, pixY - cornerY - lily2.getHeight(), this);
				if (World.lilyType.get(lilyInd) == 3)
					g.drawImage(lily3, x - cornerX - lily3.getWidth() / 2, pixY - cornerY - lily3.getHeight(), this);
				lilyInd++;
			}

			// Draw reeds
			reedInd = this.getFirstObjectIndexInY(pixY, reedInd, World.reeds);
			while (this.checkIterateOnY(pixY, reedInd, World.reeds)) {
				int x = (int) World.reeds.get(reedInd).x;
				if (this.checkOutOfWindow(x, cornerX)) { reedInd++; continue; }

				g.drawImage(reed, x - cornerX - reed.getWidth() / 2 + 20, pixY - cornerY - reed.getHeight() + 20, this);
				reedInd++;
			}
			
			// Draw wolves
			for (int wolveInd = 0; wolveInd < World.nWolves; wolveInd++) {
				if ((int) World.wolves.get(wolveInd).y == pixY) {
					int wolveX = (int) World.wolves.get(wolveInd).x, wolveY = (int) World.wolves.get(wolveInd).y;
					if (World.wolveSpeed.get(wolveInd).x < 0)
						g.drawImage(wolve_left, wolveX - cornerX - wolve_left.getWidth() / 2, wolveY - cornerY - wolve_left.getHeight(), this);
					else
						g.drawImage(wolve_right, wolveX  - cornerX - wolve_right.getWidth() / 2, wolveY - cornerY - wolve_right.getHeight(), this);
				}
			}

			// Draw rabbits
			for (int rabbitInd = 0; rabbitInd < World.rabbits.size(); rabbitInd++) {
				if ((int) World.rabbits.get(rabbitInd).y == pixY) {
					int rabbitX = (int) World.rabbits.get(rabbitInd).x, rabbitY = (int) World.rabbits.get(rabbitInd).y;
					if (World.rabbitStats.get(rabbitInd) == false)
						continue;
					if (World.rabbitVel.get(rabbitInd).x < 0)
						g.drawImage(rabbit_left, rabbitX - cornerX - rabbit_left.getWidth() / 2, rabbitY - cornerY - rabbit_left.getHeight(), this);
					else
						g.drawImage(rabbit_right, rabbitX  - cornerX - rabbit_right.getWidth() / 2, rabbitY - cornerY - rabbit_right.getHeight(), this);
				}
			}
			
			// Draw fishes
			for (int fishInd = 0; fishInd < World.fishes.size(); fishInd++) {
				if ((int) World.fishes.get(fishInd).y == pixY) {
					int fishX = (int) World.fishes.get(fishInd).x, fishY = (int) World.fishes.get(fishInd).y;
					if (World.fishStats.get(fishInd) == false)
						continue;
					if (World.fishVel.get(fishInd).x < 0)
						g.drawImage(fish_left, fishX - cornerX - fish_left.getWidth() / 2, fishY - cornerY - fish_left.getHeight(), this);
					else
						g.drawImage(fish_right, fishX  - cornerX - fish_right.getWidth() / 2, fishY - cornerY - fish_right.getHeight(), this);
				}
			}
			
			// Draw craftables
			for (int craftInd = 0; craftInd < World.craftables.size(); craftInd++) {
				if (World.craftables.get(craftInd).y == pixY) {
					int x = (int) World.craftables.get(craftInd).x;
					if (World.craftableType.get(craftInd) == 1) {
						int score = World.craftableScore.get(craftInd);
						if (score >= 1) {
							int k = random.nextInt(6) + 1;
							if (k == 1)
								g.drawImage(fire1, x - cornerX - fire1.getWidth() / 2, pixY - cornerY - fire1.getHeight(), this);
							else if (k == 2)
								g.drawImage(fire2, x - cornerX - fire2.getWidth() / 2, pixY - cornerY - fire2.getHeight(), this);
							else if (k == 3)
								g.drawImage(fire3, x - cornerX - fire3.getWidth() / 2, pixY - cornerY - fire3.getHeight(), this);
							else if (k == 4)
								g.drawImage(fire4, x - cornerX - fire4.getWidth() / 2, pixY - cornerY - fire4.getHeight(), this);
							else if (k == 5)
								g.drawImage(fire5, x - cornerX - fire5.getWidth() / 2, pixY - cornerY - fire5.getHeight(), this);
							else if (k == 6)
								g.drawImage(fire6, x - cornerX - fire6.getWidth() / 2, pixY - cornerY - fire6.getHeight(), this);
							g.setColor(Color.BLACK);
							g.drawString((String) "" + score + " %", x - cornerX, pixY - cornerY - (int) (0.6 * fire1.getHeight()));
						}
						else {
							g.drawImage(fire_burned, x - cornerX - fire1.getWidth() / 2, pixY - cornerY - fire1.getHeight(), this);
						}
					}
					if (World.craftableType.get(craftInd) == 2) {
						if (World.craftableScore.get(craftInd) >= 1)
							g.drawImage(snare, x - cornerX - snare.getWidth() / 2, pixY - cornerY - snare.getHeight(), this);
						else
							g.drawImage(snare_shot, x - cornerX - snare.getWidth() / 2, pixY - cornerY - snare.getHeight(), this);
					}
					if (World.craftableType.get(craftInd) == 3)
						if (World.craftableScore.get(craftInd) >= 1)
							g.drawImage(fish_trap, x - cornerX - fish_trap.getWidth() / 2, pixY - cornerY - fish_trap.getHeight() / 2, this);
						else
							g.drawImage(fish_trap_shot, x - cornerX - fish_trap_shot.getWidth() / 2, pixY - cornerY - fish_trap_shot.getHeight() / 2, this);
					if (World.craftableType.get(craftInd) == 4) {
						if (!Game.player.isHidden())
							g.drawImage(shelter, x - cornerX - shelter.getWidth() / 2, pixY - cornerY - shelter.getHeight(), this);
						else
							g.drawImage(shelter_hidden, x - cornerX - shelter.getWidth() / 2, pixY - cornerY - shelter.getHeight(), this);
					}
				}
			}
			
			// Draw figure
			if ((int) Game.player.getY() == pixY && Game.player.isHidden() == false) {
				try {
					figure = ImageIO.read(new File(imagePath + "woman_" + Game.player.getDirection() + ".gif"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g.drawImage(figure, (int) Game.player.getX() - cornerX - figure.getWidth() / 2, (int) Game.player.getY() - cornerY - figure.getHeight() + 5, this);
			}
		}
		
		/*
		// Draw Day/Night Cycle
		int bright = (int)  (120 * (1 - Math.cos((float) Game.tick / Game.dayLength)));
		g.setColor(new Color(0, 0, 0, bright));
		g.fillRect(0, 0, Game.dim.width, Game.dim.height);
		*/
		
		// Draw stats
		Game.player.renderStatsInventoryOptions(g);
		g.drawImage(wood, 60, World.dim.height - 224, this);
		g.drawImage(stone, 65, World.dim.height - 182, this);
		g.drawImage(leafe, 65, World.dim.height - 142, this);
		g.drawImage(liana, 63, World.dim.height - 96, this);
		g.drawImage(berry, 184, World.dim.height - 224, this);
		g.drawImage(cooked_meat, 180, World.dim.height - 182, this);
		g.drawImage(raw_meat, 180, World.dim.height - 142, this);
		g.drawImage(fish, 180, World.dim.height - 96, this);

		// Draw craftable system
		if (Game.player.getWoodCollected() >= 5 && Game.player.getStoneCollected() >= 8)
			g.drawImage(button1, World.dim.width / 2 - 200, 20, this);
		else
			g.drawImage(button1_low, World.dim.width / 2 - 200, 20, this);
		
		if (Game.player.getWoodCollected() >= 3 && Game.player.getLianaCollected() >= 1)
			g.drawImage(button2, World.dim.width / 2 - 100, 20, this);
		else
			g.drawImage(button2_low, World.dim.width / 2 - 100, 20, this);

		if (Game.player.getWoodCollected() >= 3 && Game.player.getLianaCollected() >= 8)
			g.drawImage(button3, World.dim.width / 2, 20, this);
		else
			g.drawImage(button3_low, World.dim.width / 2, 20, this);

		if (Game.player.getWoodCollected() >= 8 && Game.player.getLianaCollected() >= 4 && Game.player.getLeaveCollected() >= 10)
			g.drawImage(button4, World.dim.width / 2 + 100, 20, this);
		else
			g.drawImage(button4_low, World.dim.width / 2 + 100, 20, this);
			
		// show Game over screen
		if (Game.over == true) {
			String str = "GAME OVER!";
			g.setColor(Color.RED);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
			g.drawString(str, World.dim.width / 2 - 8 * str.length(), World.dim.height / 2 - 13);		
			g.setColor(Color.BLACK);
			g.drawString(str, World.dim.width / 2 - 8 * str.length() + 1, World.dim.height / 2 - 13 + 1);
			
			str = "Press 'R' for restart.";
			g.setFont(new Font("TimesRoman", Font.PLAIN, 22));
			g.drawString(str, World.dim.width / 2 - 82, World.dim.height / 2 + 10);
		}
		
		// game paused
		if (Game.paused) {
			if (Game.tick % 10 < 5)
				g.setColor(Color.BLACK);
			else
				g.setColor(Color.WHITE);
			g.drawString((String) "Paused", World.dim.width / 2 - 40, World.dim.height / 2 + 50);
		}
	}
	
	private int getFirstObjectIndexInY(int pixYStart, int arrayInd, ArrayList<Point2D.Float> array) {
		if (arrayInd == 0) {
			while (arrayInd < array.size() && array.get(arrayInd).y < pixYStart) { arrayInd++; }
		}
		return arrayInd;
	}
	
	private boolean checkIterateOnY(int pixY, int arrayInd, ArrayList<Point2D.Float> array) {
		return (arrayInd < array.size() && array.get(arrayInd).y == pixY);
	}
	
	private boolean checkOutOfWindow(int x, int cornerX) {
		return (x < cornerX - 100 || x > cornerX + World.dim.width + 100);
	}
	
}
