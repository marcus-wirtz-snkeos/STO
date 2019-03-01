package main;

import java.awt.geom.Point2D;
import java.util.Random;

public class updateGame {

	static Random random = new Random();
	static Game game = Game.game;
	
	public static void spawnItems() {
		
		if (Game.tick % 100 == 0) {
		for (int i = 0; i < World.nBerries; i++) {
			if (World.berryStats.get(i) == false && random.nextFloat() < World.berryRespawn)
				World.berryStats.set(i, true);
		}
		
		if (random.nextFloat() < World.woodSpawn) {
			World.nWoods += 1;
			boolean looping = true;
			while (looping == true) {
				int startX = random.nextInt(Game.worldX);
				int startY = random.nextInt(Game.worldY);
				if (World.checkLake(startX, startY, -5) == false && World.checkTree(startX, startY, World.rWood) == true) {
					World.woods.add(new Point2D.Float(startX, startY));
					if (random.nextFloat() < 0.5)
						World.woodStats.add(true);
					else
						World.woodStats.add(false);
					looping = false;
				}
			}
		}
		
		
		if (random.nextFloat() < World.stoneSpawn) {
			World.nStones += 1;
			boolean looping = true;
			while (looping == true) {
				int startX = random.nextInt(Game.worldX);
				int startY = random.nextInt(Game.worldY);
				if (World.checkLake(startX, startY, -5) == false && World.checkRock(startX, startY, 300, false) && World.checkRock(startX, startY, 30, false) == false) {
					World.stones.add(new Point2D.Float(startX, startY));
					looping = false;
				}
			}
			World.stones.sort(Game.FloatbyY);
		}
		if (Game.tick % 100 == 0 && random.nextFloat() < World.rabbitSpawn) {
			for (int i = 0; i < World.rabbits.size(); i++) {
				if (World.rabbitStats.get(i) == false) {
					World.rabbitStats.set(i, true);
					while (true) {
						float startX = random.nextInt(Game.worldX);
						float startY = random.nextInt(Game.worldY);
						if (World.checkLake(startX, startY, -5) == false && World.checkRock(startX, startY, 40, true) == false) {
							World.rabbits.set(i, new Point2D.Float(startX, startY));
							break;
						}
					}
					break;
				}
			}
		}
		
		if (Game.tick % 100 == 0 && random.nextFloat() < World.fishSpawn) {
			for (int i = 0; i < World.fishes.size(); i++) {
				if (World.fishStats.get(i) == false) {
					World.fishStats.set(i, true);
					while (true) {
						float startX = random.nextInt(Game.worldX);
						float startY = random.nextInt(Game.worldY);
						if (World.checkLake(startX, startY, 20) == true) {
							World.fishes.set(i, new Point2D.Float(startX, startY));
							break;
						}
					}
					break;
				}
			}
		}
		
		}
	}
	
	public static void addCraftable(int dis) {

		if (Game.player.getDirection() == "Up")
			World.craftables.add(new Point2D.Float((int) Game.player.getX(), (int) Game.player.getY() - dis));
		else if (Game.player.getDirection() == "Down")
			World.craftables.add(new Point2D.Float((int) Game.player.getX(), (int) Game.player.getY() + dis));
		else if (Game.player.getDirection() == "Left")
			World.craftables.add(new Point2D.Float((int) Game.player.getX() - dis, (int) Game.player.getY()));
		else
			World.craftables.add(new Point2D.Float((int) Game.player.getX() + dis, (int) Game.player.getY()));
	}
}
