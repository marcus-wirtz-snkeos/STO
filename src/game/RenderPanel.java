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
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		int treeInd = 0, plantInd = 0, stoneInd = 0, rockInd = 0, berryInd = 0, lilyInd = 0, reedInd = 0;
		for (int pixY = Math.max(0, cornerY - 100); pixY < Math.min(sto.worldY, cornerY + sto.dim.height + 100); pixY++) {
			
			// Draw trees
			if (treeInd == 0) {
				while (sto.trees.get(treeInd).y < pixY)
					treeInd++;
			}
			if (treeInd < sto.trees.size()) {
				while (sto.trees.get(treeInd).y == pixY) {
					int x = sto.trees.get(treeInd).x;
					if (x < cornerX -100 && x > cornerX + sto.dim.width + 100)
						continue;
					if (sto.treeType.get(treeInd) == 0) {
						if (sto.treeDeath.get(treeInd) == true)
							g.drawImage(Pine1_Death, x - cornerX - Pine1_Death.getWidth() / 2, pixY - cornerY - Pine1_Death.getHeight(), this);
						else
							g.drawImage(Pine1, x - cornerX - Pine1.getWidth() / 2, pixY - cornerY - Pine1.getHeight(), this);
					}
					if (sto.treeType.get(treeInd) == 1) {
						if (sto.treeDeath.get(treeInd) == true)
							g.drawImage(Pine2_Death, x - cornerX - Pine2_Death.getWidth() / 2, pixY - cornerY - Pine2_Death.getHeight(), this);
						else
							g.drawImage(Pine2, x - cornerX - Pine2.getWidth() / 2, pixY - cornerY - Pine2.getHeight(), this);
					}
					else if (sto.treeType.get(treeInd) == 2) {
						g.drawImage(Fir1, x - cornerX - Fir1.getWidth() / 2, pixY - cornerY - Fir1.getHeight(), this);
					}
					else if (sto.treeType.get(treeInd) == 3) {
						g.drawImage(Fir2, x - cornerX - Fir1.getWidth() / 2, pixY - cornerY - Fir2.getHeight(), this);
					}
					else{
						if (sto.treeDeath.get(treeInd) == true)
							g.drawImage(Tree_Death, x - cornerX - Tree_Death.getWidth() / 2, pixY - cornerY - Tree_Death.getHeight(), this);
						else
							g.drawImage(Tree, x - cornerX - Tree.getWidth() / 2, pixY - cornerY - Tree.getHeight(), this);
					}
					treeInd++;
					if (treeInd == sto.trees.size())
						break;
				}
			}
			
			// Draw woods
			for (int woodInd = 0; woodInd < sto.woods.size(); woodInd++) {
				int x = sto.woods.get(woodInd).x;
				if (sto.woods.get(woodInd).y == pixY) {
					if (sto.woodStats.get(woodInd) == true)
						g.drawImage(wood1, x - cornerX - wood1.getWidth() / 2, pixY - cornerY - wood1.getHeight(), this);
					else
						g.drawImage(wood2, x - cornerX - wood2.getWidth() / 2, pixY - cornerY - wood2.getWidth() / 2, this);
				}
			}
			
			// Draw plants
			if (plantInd == 0) {
				while (sto.plants.get(plantInd).y < pixY)
					plantInd++;
			}
			if (plantInd < sto.plants.size()) {
				while (sto.plants.get(plantInd).y == pixY) {
					int x = sto.plants.get(plantInd).x;
					if (x < cornerX -100 && x > cornerX + sto.dim.width + 100)
						continue;
					if (sto.plantType.get(plantInd) == 1)
						g.drawImage(plant1, x - cornerX - plant1.getWidth() / 2, pixY - cornerY - plant1.getHeight(), this);
					if (sto.plantType.get(plantInd) == 2)
						g.drawImage(plant2, x - cornerX - plant2.getWidth() / 2, pixY - cornerY - plant2.getHeight(), this);
					if (sto.plantType.get(plantInd) == 3)
						g.drawImage(plant3, x - cornerX - plant3.getWidth() / 2, pixY - cornerY - plant3.getHeight(), this);
					if (sto.plantType.get(plantInd) == 4)
						g.drawImage(plant4, x - cornerX - plant4.getWidth() / 2, pixY - cornerY - plant4.getHeight(), this);
					plantInd++;
					if (plantInd == sto.plants.size())
						break;
				}
			}
			
			// Draw stones
			if (stoneInd == 0) {
				while (sto.stones.get(stoneInd).y < pixY)
					stoneInd++;
			}
			if (stoneInd < sto.stones.size()) {
				while (sto.stones.get(stoneInd).y == pixY) {
					int x = sto.stones.get(stoneInd).x;
					if (x < cornerX -100 && x > cornerX + sto.dim.width + 100)
						continue;
					g.drawImage(stone, x - cornerX - stone.getWidth() / 2, pixY - cornerY - stone.getHeight(), this);
					stoneInd++;
					if (stoneInd == sto.stones.size())
						break;
				}
			}
			
			// Draw rocks
			if (rockInd == 0) {
				while (sto.rocks.get(rockInd).y < pixY) {
					rockInd++;
				}
			}
			if (rockInd < sto.rocks.size()) {			
				while (sto.rocks.get(rockInd).y == pixY) {
					int x = sto.rocks.get(rockInd).x;
					if (x < cornerX -100 && x > cornerX + sto.dim.width + 100)
						continue;
					if (sto.rockType.get(rockInd) == 1)
						g.drawImage(rock1, x - cornerX - rock1.getWidth() / 2, pixY - cornerY - rock1.getHeight(), this);
					else
						g.drawImage(rock2, x - cornerX - rock2.getWidth() / 2, pixY - cornerY - rock2.getHeight(), this);
					rockInd++;
					if (rockInd == sto.rocks.size())
						break;
				}
			}
			
			// Draw berries
			if (berryInd == 0) {
				while (sto.berries.get(berryInd).y < pixY)
					berryInd++;
			}
			if  (berryInd < sto.berries.size()) {
				while (sto.berries.get(berryInd).y == pixY) {
					int x = sto.berries.get(berryInd).x;
					if (x < cornerX -100 && x > cornerX + sto.dim.width + 100)
						continue;
					if (sto.berryStats.get(berryInd) == true)
						g.drawImage(berryFull, x - cornerX - berryFull.getWidth() / 2, pixY - cornerY - berryFull.getHeight(), this);
					else
						g.drawImage(berryEmpty, x - cornerX - berryEmpty.getWidth() / 2, pixY - cornerY - berryEmpty.getHeight(), this);
					berryInd++;
					if (berryInd == sto.berries.size())
						break;
				}
			}
			
			// Draw wolves
			for (int wolveInd = 0; wolveInd < sto.nWolves; wolveInd++) {
				if ((int) sto.wolves.get(wolveInd).y == pixY) {
					int wolveX = (int) sto.wolves.get(wolveInd).x, wolveY = (int) sto.wolves.get(wolveInd).y;
					if (sto.wolveSpeed.get(wolveInd).x < 0)
						g.drawImage(wolve_left, wolveX - cornerX - wolve_left.getWidth() / 2, wolveY - cornerY - wolve_left.getHeight(), this);
					else
						g.drawImage(wolve_right, wolveX  - cornerX - wolve_right.getWidth() / 2, wolveY - cornerY - wolve_right.getHeight(), this);
				}
			}

			// Draw rabbits
			for (int rabbitInd = 0; rabbitInd < sto.rabbits.size(); rabbitInd++) {
				if ((int) sto.rabbits.get(rabbitInd).y == pixY) {
					int rabbitX = (int) sto.rabbits.get(rabbitInd).x, rabbitY = (int) sto.rabbits.get(rabbitInd).y;
					if (sto.rabbitStats.get(rabbitInd) == false)
						continue;
					if (sto.rabbitVel.get(rabbitInd).x < 0)
						g.drawImage(rabbit_left, rabbitX - cornerX - rabbit_left.getWidth() / 2, rabbitY - cornerY - rabbit_left.getHeight(), this);
					else
						g.drawImage(rabbit_right, rabbitX  - cornerX - rabbit_right.getWidth() / 2, rabbitY - cornerY - rabbit_right.getHeight(), this);
				}
			}
			
			// Draw fishes
			for (int fishInd = 0; fishInd < sto.fishes.size(); fishInd++) {
				if ((int) sto.fishes.get(fishInd).y == pixY) {
					int fishX = (int) sto.fishes.get(fishInd).x, fishY = (int) sto.fishes.get(fishInd).y;
					if (sto.fishStats.get(fishInd) == false)
						continue;
					if (sto.fishVel.get(fishInd).x < 0)
						g.drawImage(fish_left, fishX - cornerX - fish_left.getWidth() / 2, fishY - cornerY - fish_left.getHeight(), this);
					else
						g.drawImage(fish_right, fishX  - cornerX - fish_right.getWidth() / 2, fishY - cornerY - fish_right.getHeight(), this);
				}
			}
			
			// Draw lilies
			if (lilyInd == 0) {
				while (sto.lilies.get(lilyInd).y < pixY)
					lilyInd++;
			}
			if (lilyInd < sto.lilies.size()) {
				while (sto.lilies.get(lilyInd).y == pixY) {
					int x = sto.lilies.get(lilyInd).x;
					if (x < cornerX -100 && x > cornerX + sto.dim.width + 100)
						continue;
					if (sto.lilyType.get(lilyInd) == 1)
						g.drawImage(lily1, x - cornerX - lily1.getWidth() / 2, pixY - cornerY - lily1.getHeight(), this);
					if (sto.lilyType.get(lilyInd) == 2)
						g.drawImage(lily2, x - cornerX - lily2.getWidth() / 2, pixY - cornerY - lily2.getHeight(), this);
					if (sto.lilyType.get(lilyInd) == 3)
						g.drawImage(lily3, x - cornerX - lily3.getWidth() / 2, pixY - cornerY - lily3.getHeight(), this);
					lilyInd++;
					if (lilyInd == sto.lilies.size())
						break;
				}
			}
			
			// Draw reeds
			if (reedInd == 0) {
				while (sto.reeds.get(reedInd).y < pixY)
					reedInd++;
			}
			if (reedInd < sto.reeds.size()) {
				while (sto.reeds.get(reedInd).y == pixY) {
					int x = sto.reeds.get(reedInd).x;
					if (x < cornerX -100 && x > cornerX + sto.dim.width + 100)
						continue;
					reedInd++;
					g.drawImage(reed, x - cornerX - reed.getWidth() / 2 + 20, pixY - cornerY - reed.getHeight() + 20, this);
					if (reedInd == sto.reeds.size())
						break;
				}
			}
			
			// Draw craftables
			for (int craftInd = 0; craftInd < sto.craftables.size(); craftInd++) {
				if (sto.craftables.get(craftInd).y == pixY) {
					int x = sto.craftables.get(craftInd).x;
					if (sto.craftableType.get(craftInd) == 1) {
						int k = sto.random.nextInt(6) + 1;
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
					if (sto.craftableType.get(craftInd) == 2) {
						if (sto.craftableStat.get(craftInd) == true)
							g.drawImage(snare, x - cornerX - snare.getWidth() / 2, pixY - cornerY - snare.getHeight(), this);
						else
							g.drawImage(snare_shot, x - cornerX - snare.getWidth() / 2, pixY - cornerY - snare.getHeight(), this);
					}
					if (sto.craftableType.get(craftInd) == 3)
						if (sto.craftableStat.get(craftInd) == true)
							g.drawImage(fish_trap, x - cornerX - fish_trap.getWidth() / 2, pixY - cornerY - fish_trap.getHeight(), this);
						else
							g.drawImage(fish_trap_shot, x - cornerX - fish_trap_shot.getWidth() / 2, pixY - cornerY - fish_trap_shot.getHeight(), this);
					if (sto.craftableType.get(craftInd) == 4)
						g.drawImage(shelter, x - cornerX - shelter.getWidth() / 2, pixY - cornerY - shelter.getHeight(), this);
				}
			}
			
			// Draw figure
			if ((int) sto.player.y == pixY) {
				try {
					figure = ImageIO.read(new File(imagePath + "woman_" + sto.direction + ".gif"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g.drawImage(figure, (int) sto.player.x - cornerX - figure.getWidth() / 2, (int) sto.player.y - cornerY - figure.getHeight() + 5, this);
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
		String woods = sto.woodCollected + " x ";
		g.drawString(woods, 15, sto.dim.height - 200);
		String stones = sto.stoneCollected + " x ";
		g.drawString(stones, 15, sto.dim.height - 160);
		String leaves = sto.leaveCollected + " x ";
		g.drawString(leaves, 15, sto.dim.height - 120);
		String lianas = sto.lianaCollected + " x ";
		g.drawString(lianas, 15, sto.dim.height - 80);
		String berries = sto.berryCollected + " x           (e)";
		g.drawString(berries, 130, sto.dim.height - 200);
		String meat = sto.meatCollected + " x           (r)";
		g.drawString(meat, 130, sto.dim.height - 160);
		String rawmeat = sto.rawMeatCollected + " x ";
		g.drawString(rawmeat, 130, sto.dim.height - 120);
		String fishes = sto.fishCollected + " x ";
		g.drawString(fishes, 130, sto.dim.height - 80);
		
		g.drawImage(wood, 60, sto.dim.height - 224, this);
		g.drawImage(stone, 65, sto.dim.height - 182, this);
		g.drawImage(leafe, 65, sto.dim.height - 142, this);
		g.drawImage(liana, 63, sto.dim.height - 96, this);
		g.drawImage(berry, 184, sto.dim.height - 224, this);
		g.drawImage(cooked_meat, 180, sto.dim.height - 182, this);
		g.drawImage(raw_meat, 180, sto.dim.height - 142, this);
		g.drawImage(fish, 180, sto.dim.height - 96, this);
		
		// Draw craftable system
		if (sto.woodCollected >= 5 && sto.stoneCollected >= 8)
			g.drawImage(button1, sto.dim.width / 2 - 200, 20, this);
		else
			g.drawImage(button1_low, sto.dim.width / 2 - 200, 20, this);
		if (sto.woodCollected >= 3 && sto.lianaCollected >= 1)
			g.drawImage(button2, sto.dim.width / 2 - 100, 20, this);
		else
			g.drawImage(button2_low, sto.dim.width / 2 - 100, 20, this);
		if (sto.woodCollected >= 3 && sto.lianaCollected >= 8)
			g.drawImage(button3, sto.dim.width / 2, 20, this);
		else
			g.drawImage(button3_low, sto.dim.width / 2, 20, this);
		if (sto.woodCollected >= 8 && sto.lianaCollected >= 4 && sto.leaveCollected >= 10)
			g.drawImage(button4, sto.dim.width / 2 + 100, 20, this);
		else
			g.drawImage(button4_low, sto.dim.width / 2 + 100, 20, this);
		
		// Show craft options
		if (sto.cook == false) {
			if (sto.craft == false && sto.search == false && sto.harvest == false) {
				for (int i = 0; i < sto.craftables.size(); i++) {
					float disx = sto.craftables.get(i).x - sto.player.x;
					float disy = sto.craftables.get(i).y - sto.player.y;
					if (sto.craftableType.get(i) == 1 && sto.craftableStat.get(i) == true && Math.sqrt(disx * disx + disy * disy) < 50
							&& (sto.rawMeatCollected > 0 || sto.fishCollected > 0)) {
						String cook = "Press [Space] for cooking";
						g.setColor(Color.BLACK);
						g.drawString(cook, sto.dim.width / 2 - 150, sto.dim.height - 140);
					}
				}
			}
		}
		
		if (sto.harvest == false) {
			if (sto.cook == false && sto.search == false && sto.craft == false) {
				for (int i = 0; i < sto.craftables.size(); i++) {
					float disx = sto.craftables.get(i).x - sto.player.x;
					float disy = sto.craftables.get(i).y - sto.player.y;
					if ((sto.craftableType.get(i) == 2 || sto.craftableType.get(i) == 3) && 
							sto.craftableStat.get(i) == false && Math.sqrt(disx * disx + disy * disy) < 50) {
						String harvest = "";
						if (sto.craftableType.get(i) == 2)
							harvest = "Press [Space] for harvesting";
						else if (sto.craftableType.get(i) == 2)
							harvest = "Press [Space] for collecting";
						g.setColor(Color.BLACK);
						g.drawString(harvest, sto.dim.width / 2 - 150, sto.dim.height - 140);
					}
				}
			}
		}

		if (sto.search == true) {
			String search = "Searching...";
			g.setColor(Color.WHITE);
			g.drawString(search, sto.dim.width / 2 - 80, sto.dim.height - 100);
			for (int i = 0; i < 150-sto.searchTime; i++)
				g.fillRect(sto.dim.width / 2 - 200 + (int) ((float) 400 / 150) *i, sto.dim.height - 90, (int) ((float) 400 / 150), 10);
		}
		
		if (sto.craft == true) {
			String craft = "Crafting...";
			g.setColor(Color.WHITE);
			g.drawString(craft, sto.dim.width / 2 - 70, sto.dim.height - 100);
			for (int i = 0; i < 400-sto.craftTime; i++)
				g.fillRect(sto.dim.width / 2 - 200 + i, sto.dim.height - 90, 1, 10);
		}
		
		if (sto.cook == true) {
			String cook = "Cooking...";
			g.setColor(Color.WHITE);
			g.drawString(cook, sto.dim.width / 2 - 70, sto.dim.height - 100);
			for (int i = 0; i < 300-sto.cookTime; i++)
				g.fillRect(sto.dim.width / 2 - 200 + (int) ((float) 400 / 300) *i, sto.dim.height - 90, (int) ((float) 400 / 300), 10);
		}
		
		if (sto.harvest == true) {
			String harvest = "";
			if (sto.craftableType.get(sto.action) == 2)
				harvest = "Harvesting rabbit...";
			else
				harvest = "Collecting fish...";
			g.setColor(Color.WHITE);
			g.drawString(harvest, sto.dim.width / 2 - 120, sto.dim.height - 100);
			for (int i = 0; i < 200-sto.harvestTime; i++)
				g.fillRect(sto.dim.width / 2 - 200 + 2*i, sto.dim.height - 90, 2, 10);
		}
		
		if (sto.run == true && sto.tired > 0) {
			String run = "Running...";
			g.setColor(Color.RED);
			g.drawString(run, sto.dim.width / 2 - 70, sto.dim.height - 100);
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
