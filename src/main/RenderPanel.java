package main;

import java.awt.Color;
import java.awt.Font;
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
	public BufferedImage wolve_left;
	public BufferedImage wolve_right;
	public BufferedImage rabbit_left;
	public BufferedImage rabbit_right;
	public BufferedImage fish_left;
	public BufferedImage fish_right;
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
	public BufferedImage cooked_meat;
	public BufferedImage raw_meat;
	public BufferedImage fish;
	public BufferedImage wood1;
	public BufferedImage wood2;
	public BufferedImage plant1;
	public BufferedImage plant2;
	public BufferedImage plant3;
	public BufferedImage plant4;
	
	public BufferedImage reed;
	public BufferedImage lily1;
	public BufferedImage lily2;
	public BufferedImage lily3;
	
	
	public BufferedImage stone;
	public BufferedImage rock1;
	public BufferedImage rock2;
	public BufferedImage leafe;
	public BufferedImage liana;

	public BufferedImage snare;
	public BufferedImage snare_shot;
	public BufferedImage fish_trap;
	public BufferedImage fish_trap_shot;
	public BufferedImage fire1;
	public BufferedImage fire2;
	public BufferedImage fire3;
	public BufferedImage fire4;
	public BufferedImage fire5;
	public BufferedImage fire6;
	public BufferedImage shelter;
	public BufferedImage shelter_hidden;
	
	public BufferedImage button1;
	public BufferedImage button2;
	public BufferedImage button3;
	public BufferedImage button4;
	public BufferedImage button1_low;
	public BufferedImage button2_low;
	public BufferedImage button3_low;
	public BufferedImage button4_low;
	
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
			g.fillOval(World.lakes.get(i).x - radius - cornerX, World.lakes.get(i).y - radius / 2 - cornerY, 2 * radius, radius);
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
			
			wolve_left = ImageIO.read(new File(imagePath + "wolve_left.gif"));
			wolve_right = ImageIO.read(new File(imagePath + "wolve_right.gif"));
			rabbit_left = ImageIO.read(new File(imagePath + "rabbit_left.gif"));
			rabbit_right = ImageIO.read(new File(imagePath + "rabbit_right.gif"));
			fish_left = ImageIO.read(new File(imagePath + "fish_left.png"));
			fish_right = ImageIO.read(new File(imagePath + "fish_right.png"));
			
			stone = ImageIO.read(new File(imagePath + "stone.gif"));
			rock1 = ImageIO.read(new File(imagePath + "rock1.gif"));
			rock2 = ImageIO.read(new File(imagePath + "rock2.gif"));
			
			wood = ImageIO.read(new File(imagePath + "wood.gif"));
			stone = ImageIO.read(new File(imagePath + "stone2.gif"));
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
			shelter = ImageIO.read(new File(imagePath + "shelter.png"));
			shelter_hidden = ImageIO.read(new File(imagePath + "shelter_hidden.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		int treeInd = 0, plantInd = 0, stoneInd = 0, rockInd = 0, berryInd = 0, lilyInd = 0, reedInd = 0;
		for (int pixY = Math.max(0, cornerY - 100); pixY < Math.min(Game.worldY, cornerY + Game.dim.height + 100); pixY++) {
			
			// Draw trees
			if (treeInd == 0) {
				while (World.trees.get(treeInd).y < pixY)
					treeInd++;
			}
			if (treeInd < World.trees.size()) {
				while (World.trees.get(treeInd).y == pixY) {
					int x = World.trees.get(treeInd).x;
					if (x < cornerX -100 && x > cornerX + Game.dim.width + 100)
						continue;
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
			for (int woodInd = 0; woodInd < World.woods.size(); woodInd++) {
				int x = World.woods.get(woodInd).x;
				if (World.woods.get(woodInd).y == pixY) {
					if (World.woodStats.get(woodInd) == true)
						g.drawImage(wood1, x - cornerX - wood1.getWidth() / 2, pixY - cornerY - wood1.getHeight(), this);
					else
						g.drawImage(wood2, x - cornerX - wood2.getWidth() / 2, pixY - cornerY - wood2.getWidth() / 2, this);
				}
			}
			
			// Draw plants
			if (plantInd == 0) {
				while (World.plants.get(plantInd).y < pixY)
					plantInd++;
			}
			if (plantInd < World.plants.size()) {
				while (World.plants.get(plantInd).y == pixY) {
					int x = World.plants.get(plantInd).x;
					if (x < cornerX -100 && x > cornerX + Game.dim.width + 100)
						continue;
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
					int x = World.stones.get(stoneInd).x;
					if (x < cornerX -100 && x > cornerX + Game.dim.width + 100)
						continue;
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
					int x = World.rocks.get(rockInd).x;
					if (x < cornerX -100 && x > cornerX + Game.dim.width + 100)
						continue;
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
					int x = World.berries.get(berryInd).x;
					if (x < cornerX -100 && x > cornerX + Game.dim.width + 100)
						continue;
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
					int x = World.lilies.get(lilyInd).x;
					if (x < cornerX -100 && x > cornerX + Game.dim.width + 100)
						continue;
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
					int x = World.reeds.get(reedInd).x;
					if (x < cornerX -100 && x > cornerX + Game.dim.width + 100)
						continue;
					reedInd++;
					g.drawImage(reed, x - cornerX - reed.getWidth() / 2 + 20, pixY - cornerY - reed.getHeight() + 20, this);
					if (reedInd == World.reeds.size())
						break;
				}
			}
			
			// Draw craftables
			for (int craftInd = 0; craftInd < World.craftables.size(); craftInd++) {
				if (World.craftables.get(craftInd).y == pixY) {
					int x = World.craftables.get(craftInd).x;
					if (World.craftableType.get(craftInd) == 1) {
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
					}
					if (World.craftableType.get(craftInd) == 2) {
						if (World.craftableStat.get(craftInd) == true)
							g.drawImage(snare, x - cornerX - snare.getWidth() / 2, pixY - cornerY - snare.getHeight(), this);
						else
							g.drawImage(snare_shot, x - cornerX - snare.getWidth() / 2, pixY - cornerY - snare.getHeight(), this);
					}
					if (World.craftableType.get(craftInd) == 3)
						if (World.craftableStat.get(craftInd) == true)
							g.drawImage(fish_trap, x - cornerX - fish_trap.getWidth() / 2, pixY - cornerY - fish_trap.getHeight(), this);
						else
							g.drawImage(fish_trap_shot, x - cornerX - fish_trap_shot.getWidth() / 2, pixY - cornerY - fish_trap_shot.getHeight(), this);
					if (World.craftableType.get(craftInd) == 4) {
						if (Game.hidden == false)
							g.drawImage(shelter, x - cornerX - shelter.getWidth() / 2, pixY - cornerY - shelter.getHeight(), this);
						else
							g.drawImage(shelter_hidden, x - cornerX - shelter.getWidth() / 2, pixY - cornerY - shelter.getHeight(), this);
					}
				}
			}
			
			// Draw figure
			if ((int) Game.player.getY() == pixY) {
				try {
					figure = ImageIO.read(new File(imagePath + "woman_" + Game.player.getDirection() + ".gif"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (Game.hidden == false)
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
		g.setFont(new Font("TimesRoman", Font.PLAIN, 22));
		
		String health = "Condition: " + Game.player.getCondition();
		g.setColor(Color.WHITE);
		g.drawString(health, Game.dim.width - 199, Game.dim.height - 169);
		if (Game.player.getCondition() < 10)
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLACK);
		g.drawString(health, Game.dim.width - 200, Game.dim.height - 170);
		
		String tired = "Tired: " + Game.player.getTired();
		g.setColor(Color.WHITE);
		g.drawString(tired, Game.dim.width - 199, Game.dim.height - 139);
		if (Game.player.getTired() == 0)
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLACK);
		g.drawString(tired, Game.dim.width - 200, Game.dim.height - 140);	

		String hungry = "Hungry: " + Game.player.getHungry();
		g.setColor(Color.WHITE);
		g.drawString(hungry, Game.dim.width - 199, Game.dim.height - 109);
		if (Game.player.getHungry() == 0)
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLACK);
		g.drawString(hungry, Game.dim.width - 200, Game.dim.height - 110);

		String thirsty = "Thirsty: " + Game.player.getThirsty();
		g.setColor(Color.WHITE);
		g.drawString(thirsty, Game.dim.width - 199, Game.dim.height - 79);
		if (Game.player.getThirsty() == 0)
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLACK);
		g.drawString(thirsty, Game.dim.width - 200, Game.dim.height - 80);	
		
		// Draw Score
		String score = "Score: " + Game.score;
		g.setColor(Color.WHITE);
		g.drawString(score, 11, 21);
		g.setColor(Color.BLACK);
		g.drawString(score, 10, 20);
		
		// Draw inventory
		String woods = Game.woodCollected + " x ";
		g.drawString(woods, 15, Game.dim.height - 200);
		String stones = Game.stoneCollected + " x ";
		g.drawString(stones, 15, Game.dim.height - 160);
		String leaves = Game.leaveCollected + " x ";
		g.drawString(leaves, 15, Game.dim.height - 120);
		String lianas = Game.lianaCollected + " x ";
		g.drawString(lianas, 15, Game.dim.height - 80);
		String berries = Game.berryCollected + " x           (e)";
		g.drawString(berries, 130, Game.dim.height - 200);
		String meat = Game.meatCollected + " x           (r)";
		g.drawString(meat, 130, Game.dim.height - 160);
		String rawmeat = Game.rawMeatCollected + " x ";
		g.drawString(rawmeat, 130, Game.dim.height - 120);
		String fishes = Game.fishCollected + " x ";
		g.drawString(fishes, 130, Game.dim.height - 80);
		
		g.drawImage(wood, 60, Game.dim.height - 224, this);
		g.drawImage(stone, 65, Game.dim.height - 182, this);
		g.drawImage(leafe, 65, Game.dim.height - 142, this);
		g.drawImage(liana, 63, Game.dim.height - 96, this);
		g.drawImage(berry, 184, Game.dim.height - 224, this);
		g.drawImage(cooked_meat, 180, Game.dim.height - 182, this);
		g.drawImage(raw_meat, 180, Game.dim.height - 142, this);
		g.drawImage(fish, 180, Game.dim.height - 96, this);
		
		// Draw craftable system
		if (Game.woodCollected >= 5 && Game.stoneCollected >= 8)
			g.drawImage(button1, Game.dim.width / 2 - 200, 20, this);
		else
			g.drawImage(button1_low, Game.dim.width / 2 - 200, 20, this);
		if (Game.woodCollected >= 3 && Game.lianaCollected >= 1)
			g.drawImage(button2, Game.dim.width / 2 - 100, 20, this);
		else
			g.drawImage(button2_low, Game.dim.width / 2 - 100, 20, this);
		if (Game.woodCollected >= 3 && Game.lianaCollected >= 8)
			g.drawImage(button3, Game.dim.width / 2, 20, this);
		else
			g.drawImage(button3_low, Game.dim.width / 2, 20, this);
		if (Game.woodCollected >= 8 && Game.lianaCollected >= 4 && Game.leaveCollected >= 10)
			g.drawImage(button4, Game.dim.width / 2 + 100, 20, this);
		else
			g.drawImage(button4_low, Game.dim.width / 2 + 100, 20, this);
		
		// Show options
		if (Game.cook == false && Game.hidden == false && Game.craft == false && Game.harvest == false) {
			for (int i = 0; i < World.craftables.size(); i++) {
				float disx = World.craftables.get(i).x - Game.player.getX();
				float disy = World.craftables.get(i).y - Game.player.getY();
				if (World.craftableType.get(i) == 1 && World.craftableStat.get(i) == true && Math.sqrt(disx * disx + disy * disy) < 50
						&& (Game.rawMeatCollected > 0 || Game.fishCollected > 0)) {
					String cook = "Press [Space] for cooking";
					g.setColor(Color.BLACK);
					g.drawString(cook, Game.dim.width / 2 - 150, Game.dim.height - 140);
				}
				else if ((World.craftableType.get(i) == 2 || World.craftableType.get(i) == 3) && World.craftableStat.get(i) == false && Math.sqrt(disx * disx + disy * disy) < 50) {
					String cook = "Press [Space] for harvesting";
					g.setColor(Color.BLACK);
					g.drawString(cook, Game.dim.width / 2 - 150, Game.dim.height - 140);
				}
				else if (World.craftableType.get(i) == 4 && Math.sqrt(disx * disx + disy * disy) < 50) {
					String cook = "Press [Space] for hiding";
					g.setColor(Color.BLACK);
					g.drawString(cook, Game.dim.width / 2 - 150, Game.dim.height - 140);
				}
			}
		}


		if (Game.harvest == false && Game.cook == false && Game.craft == false) {
			for (int i = 0; i < World.craftables.size(); i++) {
				float disx = World.craftables.get(i).x - Game.player.getX();
				float disy = World.craftables.get(i).y - Game.player.getY();
				if ((World.craftableType.get(i) == 2 || World.craftableType.get(i) == 3) && 
						World.craftableStat.get(i) == false && Math.sqrt(disx * disx + disy * disy) < 50) {
					String harvest = "";
					if (World.craftableType.get(i) == 2)
						harvest = "Press [Space] for harvesting";
					else if (World.craftableType.get(i) == 2)
						harvest = "Press [Space] for collecting";
					g.setColor(Color.BLACK);
					g.drawString(harvest, Game.dim.width / 2 - 150, Game.dim.height - 140);
				}
			}
		}

		if (Game.keys[KeyEvent.VK_SPACE] && Game.cook == false && Game.craft == false && Game.harvest == false && Game.hidden == false) {
			String search = "Searching...";
			g.setColor(Color.WHITE);
			g.drawString(search, Game.dim.width / 2 - 50, Game.dim.height - 100);
			g.fillRect(Game.dim.width / 2 - 100, Game.dim.height - 90, (int) (5 * Game.tick % 200), 10);
		}

		if (Game.hidden == true) {
			String hidden = "Hidden in shelter!";
			g.setColor(Color.WHITE);
			g.drawString(hidden, Game.dim.width / 2 - 80, Game.dim.height - 100);
		}

		if (Game.craft == true) {
			String craft = "Crafting...";
			g.setColor(Color.WHITE);
			g.drawString(craft, Game.dim.width / 2 - 70, Game.dim.height - 100);
			for (int i = 0; i < 400-Game.craftTime; i++)
				g.fillRect(Game.dim.width / 2 - 200 + i, Game.dim.height - 90, 1, 10);
		}
		
		if (Game.cook == true) {
			String cook = "Cooking...";
			g.setColor(Color.WHITE);
			g.drawString(cook, Game.dim.width / 2 - 70, Game.dim.height - 100);
			for (int i = 0; i < 300-Game.cookTime; i++)
				g.fillRect(Game.dim.width / 2 - 200 + (int) ((float) 400 / 300) *i, Game.dim.height - 90, (int) ((float) 400 / 300), 10);
		}
		
		if (Game.harvest == true) {
			String harvest = "";
			if (World.craftableType.get(Game.craftableAction) == 2)
				harvest = "Harvesting rabbit...";
			else
				harvest = "Collecting fish...";
			g.setColor(Color.WHITE);
			g.drawString(harvest, Game.dim.width / 2 - 120, Game.dim.height - 100);
			for (int i = 0; i < 200-Game.harvestTime; i++)
				g.fillRect(Game.dim.width / 2 - 200 + 2*i, Game.dim.height - 90, 2, 10);
		}
		
		if (Game.keys[KeyEvent.VK_SHIFT] && Game.player.getTired() > 0) {
			String run = "Running...";
			g.setColor(Color.RED);
			g.drawString(run, Game.dim.width / 2 - 70, Game.dim.height - 100);
		}
		
		if (Game.over == true) {
			String gameover = "GAME OVER!";
			g.setColor(Color.RED);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
			g.drawString(gameover, Game.dim.width / 2 - 8 * gameover.length(), Game.dim.height / 2 - 13);		
			g.setColor(Color.BLACK);
			g.drawString(gameover, Game.dim.width / 2 - 8 * gameover.length() + 1, Game.dim.height / 2 - 13 + 1);
			
			String restart = "Press 'R' for restart.";
			g.setFont(new Font("TimesRoman", Font.PLAIN, 22));
			g.drawString(restart, Game.dim.width / 2 - 82, Game.dim.height / 2 + 10);
		}
	}
}