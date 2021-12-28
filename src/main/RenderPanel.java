package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		
		g.setColor(new Color(0, 204, 0, 150));
		g.fillRect(0, 0, Game.dim.width, Game.dim.height);
		
		int cornerX = (int) Math.min(Math.max(Game.player.getX() - Game.dim.width / 2, 0), Game.worldX - Game.dim.width);
		int cornerY = (int) Math.min(Math.max(Game.player.getY() - Game.dim.height / 2, 0), Game.worldY - Game.dim.height);
		
		// Draw lakes
		g.setColor(Color.BLUE);
		for (int i = 0; i < World.nLakes; i++) {
			int radius = World.radiusLakes.get(i);
			g.fillOval((int) World.lakes.get(i).x - radius - cornerX, (int) World.lakes.get(i).y - radius / 2 - cornerY, 2 * radius, radius);
		}
		
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
			
			wolve_left = ImageIO.read(new File(imagePath + "marshall_izquierda.gif"));
			wolve_right = ImageIO.read(new File(imagePath + "marshall_derecha.gif"));
			rabbit_left = ImageIO.read(new File(imagePath + "hueso.gif"));
			rabbit_right = ImageIO.read(new File(imagePath + "hueso.gif"));
			fish_left = ImageIO.read(new File(imagePath + "fish_left.png"));
			fish_right = ImageIO.read(new File(imagePath + "fish_right.png"));
			
			stone = ImageIO.read(new File(imagePath + "stone.gif"));
			rock1 = ImageIO.read(new File(imagePath + "rock1.gif"));
			rock2 = ImageIO.read(new File(imagePath + "rock2.gif"));
			
			wood = ImageIO.read(new File(imagePath + "wood.gif"));
			stone = ImageIO.read(new File(imagePath + "stone2.gif"));
			leafe = ImageIO.read(new File(imagePath + "leafe.gif"));
			liana = ImageIO.read(new File(imagePath + "liana.gif"));
			
			berry = ImageIO.read(new File(imagePath + "hueso.gif"));
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
		
		int treeInd = 0, plantInd = 0, stoneInd = 0, rockInd = 0, woodInd = 0, berryInd = 0, lilyInd = 0, reedInd = 0;
		for (int pixY = Math.max(0, cornerY - 100); pixY < Math.min(Game.worldY, cornerY + Game.dim.height + 200); pixY++) {

			// Draw trees
			if (treeInd == 0) {
				while (World.trees.get(treeInd).y < pixY)
					treeInd++;
			}
			if (treeInd < World.trees.size()) {
				while (World.trees.get(treeInd).y == pixY) {
					int x = (int) World.trees.get(treeInd).x;
					if (x < cornerX - 100 || x > cornerX + Game.dim.width + 100) {
						treeInd++;
						if (treeInd == World.trees.size())
							break;
						continue;
					}
					if (World.treeType.get(treeInd) == 0) {
						if (World.treeDeath.get(treeInd) == true)
							g.drawImage(Pine1_Death, x - cornerX - Pine1_Death.getWidth() / 2, pixY - cornerY - Pine1_Death.getHeight(), this);
						else
							g.drawImage(Pine1, x - cornerX - Pine1.getWidth() / 2, pixY - cornerY - Pine1.getHeight(), this);
					}
					if (World.treeType.get(treeInd) == 1) {
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
					if (treeInd == World.trees.size())
						break;
				}
			}
			
			// Draw woods
			if (woodInd == 0) {
				while (World.woods.get(woodInd).y < pixY)
					woodInd++;
			}
			if (woodInd < World.woods.size()) {
				while (World.woods.get(woodInd).y == pixY) {
					int x = (int) World.woods.get(woodInd).x;
					if (x < cornerX - 100 || x > cornerX + Game.dim.width + 100) {
						woodInd++;
						if (woodInd == World.woods.size())
							break;
						continue;
					}
					if (World.woodStats.get(woodInd) == true)
						g.drawImage(wood1, x - cornerX - wood1.getWidth() / 2, pixY - cornerY - wood1.getHeight(), this);
					else
						g.drawImage(wood2, x - cornerX - wood2.getWidth() / 2, pixY - cornerY - wood2.getWidth() / 2, this);
					woodInd++;
					if (woodInd == World.woods.size())
						break;
				}
			}
			
			// Draw plants
			if (plantInd == 0) {
				while (World.plants.get(plantInd).y < pixY)
					plantInd++;
			}
			if (plantInd < World.plants.size()) {
				while (World.plants.get(plantInd).y == pixY) {
					int x = (int) World.plants.get(plantInd).x;
					if (x < cornerX -100 || x > cornerX + Game.dim.width + 100) {
						plantInd++;
						if (plantInd == World.plants.size())
							break;
						continue;
					}
					if (World.plantType.get(plantInd) == 1)
						g.drawImage(plant1, x - cornerX - plant1.getWidth() / 2, pixY - cornerY - plant1.getHeight(), this);
					if (World.plantType.get(plantInd) == 2)
						g.drawImage(plant2, x - cornerX - plant2.getWidth() / 2, pixY - cornerY - plant2.getHeight(), this);
					if (World.plantType.get(plantInd) == 3)
						g.drawImage(plant3, x - cornerX - plant3.getWidth() / 2, pixY - cornerY - plant3.getHeight(), this);
					if (World.plantType.get(plantInd) == 4)
						g.drawImage(plant4, x - cornerX - plant4.getWidth() / 2, pixY - cornerY - plant4.getHeight(), this);
					plantInd++;
					if (plantInd == World.plants.size())
						break;
				}
			}
			
			// Draw stones
			if (stoneInd == 0) {
				while (World.stones.get(stoneInd).y < pixY)
					stoneInd++;
			}
			if (stoneInd < World.stones.size()) {
				while (World.stones.get(stoneInd).y == pixY) {
					int x = (int) World.stones.get(stoneInd).x;
					if (x < cornerX - 100 || x > cornerX + Game.dim.width + 100) {
						stoneInd++;
						if (stoneInd == World.stones.size())
							break;
						continue;
					}
					g.drawImage(stone, x - cornerX - stone.getWidth() / 2, pixY - cornerY - stone.getHeight(), this);
					stoneInd++;
					if (stoneInd == World.stones.size())
						break;
				}
			}
			
			// Draw rocks
			if (rockInd == 0) {
				while (World.rocks.get(rockInd).y < pixY) {
					rockInd++;
				}
			}
			if (rockInd < World.rocks.size()) {			
				while (World.rocks.get(rockInd).y == pixY) {
					int x = (int) World.rocks.get(rockInd).x;
					if (x < cornerX - 100 || x > cornerX + Game.dim.width + 100) {
						rockInd++;
						if (rockInd == World.rocks.size())
							break;
						continue;
					}
					if (World.rockType.get(rockInd) == 1)
						g.drawImage(rock1, x - cornerX - rock1.getWidth() / 2, pixY - cornerY - rock1.getHeight(), this);
					else
						g.drawImage(rock2, x - cornerX - rock2.getWidth() / 2, pixY - cornerY - rock2.getHeight(), this);
					rockInd++;
					if (rockInd == World.rocks.size())
						break;
				}
			}

			// Draw berries
			if (berryInd == 0) {
				while (World.berries.get(berryInd).y < pixY)
					berryInd++;
			}
			if  (berryInd < World.berries.size()) {
				while (World.berries.get(berryInd).y == pixY) {
					int x = (int) World.berries.get(berryInd).x;
					if (x < cornerX - 100 || x > cornerX + Game.dim.width + 100) {
						berryInd++;
						if (berryInd == World.berries.size())
							break;
						continue;
					}
					if (World.berryStats.get(berryInd) == true)
						g.drawImage(berryFull, x - cornerX - berryFull.getWidth() / 2, pixY - cornerY - berryFull.getHeight(), this);
					else
						g.drawImage(berryEmpty, x - cornerX - berryEmpty.getWidth() / 2, pixY - cornerY - berryEmpty.getHeight(), this);
					berryInd++;
					if (berryInd == World.berries.size())
						break;
				}
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
			
			// Draw lilies
			if (lilyInd == 0) {
				while (World.lilies.get(lilyInd).y < pixY)
					lilyInd++;
			}
			if (lilyInd < World.lilies.size()) {
				while (World.lilies.get(lilyInd).y == pixY) {
					int x = (int) World.lilies.get(lilyInd).x;
					if (x < cornerX - 100 || x > cornerX + Game.dim.width + 100) {
						lilyInd++;
						if (lilyInd == World.lilies.size())
							break;
						continue;
					}
					if (World.lilyType.get(lilyInd) == 1)
						g.drawImage(lily1, x - cornerX - lily1.getWidth() / 2, pixY - cornerY - lily1.getHeight(), this);
					if (World.lilyType.get(lilyInd) == 2)
						g.drawImage(lily2, x - cornerX - lily2.getWidth() / 2, pixY - cornerY - lily2.getHeight(), this);
					if (World.lilyType.get(lilyInd) == 3)
						g.drawImage(lily3, x - cornerX - lily3.getWidth() / 2, pixY - cornerY - lily3.getHeight(), this);
					lilyInd++;
					if (lilyInd == World.lilies.size())
						break;
				}
			}

			// Draw reeds
			if (reedInd == 0) {
				while (World.reeds.get(reedInd).y < pixY)
					reedInd++;
			}
			if (reedInd < World.reeds.size()) {
				while (World.reeds.get(reedInd).y == pixY) {
					int x = (int) World.reeds.get(reedInd).x;
					if (x < cornerX - 100 || x > cornerX + Game.dim.width + 100) {
						reedInd++;
						if (reedInd == World.reeds.size())
							break;
						continue;
					}
					g.drawImage(reed, x - cornerX - reed.getWidth() / 2 + 20, pixY - cornerY - reed.getHeight() + 20, this);
					reedInd++;
					if (reedInd == World.reeds.size())
						break;
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
								g.drawImage(fire2, x - cornerX - fire1.getWidth() / 2, pixY - cornerY - fire1.getHeight(), this);
							else if (k == 3)
								g.drawImage(fire3, x - cornerX - fire1.getWidth() / 2, pixY - cornerY - fire1.getHeight(), this);
							else if (k == 4)
								g.drawImage(fire4, x - cornerX - fire1.getWidth() / 2, pixY - cornerY - fire1.getHeight(), this);
							else if (k == 5)
								g.drawImage(fire5, x - cornerX - fire1.getWidth() / 2, pixY - cornerY - fire1.getHeight(), this);
							else if (k == 6)
								g.drawImage(fire6, x - cornerX - fire1.getWidth() / 2, pixY - cornerY - fire1.getHeight(), this);
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
							g.drawImage(fish_trap, x - cornerX - fish_trap.getWidth() / 2, pixY - cornerY - fish_trap.getHeight(), this);
						else
							g.drawImage(fish_trap_shot, x - cornerX - fish_trap_shot.getWidth() / 2, pixY - cornerY - fish_trap_shot.getHeight(), this);
					if (World.craftableType.get(craftInd) == 4) {
						if (Game.player.isHidden() == false)
							g.drawImage(shelter, x - cornerX - shelter.getWidth() / 2, pixY - cornerY - shelter.getHeight(), this);
						else
							g.drawImage(shelter_hidden, x - cornerX - shelter.getWidth() / 2, pixY - cornerY - shelter.getHeight(), this);
					}
				}
			}
			
			// Draw figure
			if ((int) Game.player.getY() == pixY) {
				try {
					if (Game.keys[KeyEvent.VK_A] || Game.keys[KeyEvent.VK_LEFT])
						figure = ImageIO.read(new File(imagePath + "everest_izquierda.gif"));
					else
						figure = ImageIO.read(new File(imagePath + "everest_derecha.gif"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (Game.player.isHidden() == false)
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
		Game.player.drawStats(g);
		// Draw inventory
		Game.player.drawInventory(g);
		g.drawImage(wood, 60, Game.dim.height - 224, this);
		g.drawImage(stone, 65, Game.dim.height - 182, this);
		g.drawImage(leafe, 65, Game.dim.height - 142, this);
		g.drawImage(liana, 63, Game.dim.height - 96, this);
		g.drawImage(berry, 184, Game.dim.height - 224, this);
		g.drawImage(cooked_meat, 180, Game.dim.height - 182, this);
		g.drawImage(raw_meat, 180, Game.dim.height - 142, this);
		g.drawImage(fish, 180, Game.dim.height - 96, this);

		// Draw craftable system
		if (Game.player.woodCollected >= 5 && Game.player.stoneCollected >= 8)
			g.drawImage(button1, Game.dim.width / 2 - 200, 20, this);
		else
			g.drawImage(button1_low, Game.dim.width / 2 - 200, 20, this);
		if (Game.player.woodCollected >= 3 && Game.player.lianaCollected >= 1)
			g.drawImage(button2, Game.dim.width / 2 - 100, 20, this);
		else
			g.drawImage(button2_low, Game.dim.width / 2 - 100, 20, this);
		if (Game.player.woodCollected >= 3 && Game.player.lianaCollected >= 8)
			g.drawImage(button3, Game.dim.width / 2, 20, this);
		else
			g.drawImage(button3_low, Game.dim.width / 2, 20, this);
		if (Game.player.woodCollected >= 8 && Game.player.lianaCollected >= 4 && Game.player.leaveCollected >= 10)
			g.drawImage(button4, Game.dim.width / 2 + 100, 20, this);
		else
			g.drawImage(button4_low, Game.dim.width / 2 + 100, 20, this);
		
		// Show options
		Game.player.showOptions(g);
	}
}
