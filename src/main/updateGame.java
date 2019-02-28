package main;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.Random;

public class updateGame {

	static Random random = new Random();
	static Game game = Game.game;
	
	public static void stats() {
		
		int tick = Game.tick;
		

		if (World.checkLake(Game.player.getX() - (Game.playerMovement + 1), Game.player.getY(), 0) == true || World.checkLake(Game.player.getX() + (Game.playerMovement + 1), Game.player.getY(), 0) == true || 
				World.checkLake(Game.player.getX(), Game.player.getY() + (Game.playerMovement + 1), 0) == true || World.checkLake(Game.player.getX(), Game.player.getY() - (Game.playerMovement + 1), 0) == true) {
			Game.player.addThirsty(100);
		}
		
		if (tick % 30 == 0) {
			float pval = random.nextFloat();
			if (pval < 0.4){
				if (Game.player.getHungry() > 0)
					Game.player.addHungry(-1);
				else
					Game.player.addCondition(-1);
			}
			else {
				if (Game.player.getThirsty() > 0)
					Game.player.addThirsty(-1);
				else
					Game.player.addCondition(-1);
			}
		}
		
		if (tick % 100 == 0 && Game.player.getTired() > 50 && Game.player.getHungry() > 50 && Game.player.getThirsty() > 50 && Game.player.getCondition() < 100)
			if (Game.hidden == false)
				Game.player.addCondition(1);
			else
				Game.player.addCondition(2);

		if (tick % 5 == 0 && Game.keys[KeyEvent.VK_SHIFT] && Game.player.getTired() > 0)
			Game.player.addTired(-1);
		
		if (tick % 50 == 0 && Game.player.getTired() < 100)
			Game.player.addTired(1);
			
		if (tick % 100 == 0)
			Game.score += 1;
		
		if (Game.player.getCondition() <= 0)
			Game.over = true;
		
	}
	
	public static void move() {
		if (Game.cook == true || Game.craft == true || Game.harvest == true || Game.hidden == true || Game.keys[KeyEvent.VK_SPACE] == true)
			return;

		float movement = Game.playerMovement;
		movement -= (0.03 * Game.stoneCollected + 0.01 * Game.woodCollected + 0.001 * Game.leaveCollected + 
				0.002 * Game.lianaCollected + 0.001 * Game.berryCollected);

		if (Game.keys[KeyEvent.VK_SHIFT] && Game.player.getTired() > 0) 
			movement *= 1.5;

		movement = Math.max(movement, 0);
		
		if((Game.keys[KeyEvent.VK_W] && Game.keys[KeyEvent.VK_A]) || (Game.keys[KeyEvent.VK_W] && Game.keys[KeyEvent.VK_D]) || 
				(Game.keys[KeyEvent.VK_A] && Game.keys[KeyEvent.VK_S]) || (Game.keys[KeyEvent.VK_S] && Game.keys[KeyEvent.VK_D]) ||
				(Game.keys[KeyEvent.VK_W] && Game.keys[KeyEvent.VK_D]) || (Game.keys[KeyEvent.VK_W] && Game.keys[KeyEvent.VK_D]) ||
				(Game.keys[KeyEvent.VK_UP] && Game.keys[KeyEvent.VK_LEFT]) || (Game.keys[KeyEvent.VK_UP] && Game.keys[KeyEvent.VK_RIGHT]) || 
				(Game.keys[KeyEvent.VK_LEFT] && Game.keys[KeyEvent.VK_DOWN]) || (Game.keys[KeyEvent.VK_DOWN] && Game.keys[KeyEvent.VK_RIGHT]))
				movement /= Math.sqrt(2);
		
	    if((Game.keys[KeyEvent.VK_W] || Game.keys[KeyEvent.VK_UP]) && Game.player.getY() > 0 && World.checkLake(Game.player.getX(), Game.player.getY() - movement, 0) == false
	    	&& World.checkCraftable(Game.player.getX(), Game.player.getY() - movement) == false && World.checkRock(Game.player.getX(), Game.player.getY() - movement, 50, true) == false){
	    	Game.player.setDirection("Up");
	    	Game.player.addY(-movement);
	    }

	    if((Game.keys[KeyEvent.VK_S] || Game.keys[KeyEvent.VK_DOWN]) && Game.player.getY() < Game.worldY && World.checkLake(Game.player.getX(), Game.player.getY() + movement, 0) == false
	    	&& World.checkCraftable(Game.player.getX(), Game.player.getY() + movement) == false && World.checkRock(Game.player.getX(), Game.player.getY() + movement, 50, true) == false){
	    	Game.player.setDirection("Down");
	    	Game.player.addY(movement);
	    }

	    if((Game.keys[KeyEvent.VK_A] || Game.keys[KeyEvent.VK_LEFT]) && Game.player.getX() > 0 && World.checkLake(Game.player.getX() - movement, Game.player.getY(), 0) == false
	    	&& World.checkCraftable(Game.player.getX() - movement, Game.player.getY()) == false && World.checkRock(Game.player.getX() - movement, Game.player.getY(), 50, true) == false){
	    	Game.player.setDirection("Left");
	    	Game.player.addX(-movement);
	    }

	    if((Game.keys[KeyEvent.VK_D] || Game.keys[KeyEvent.VK_RIGHT]) && Game.player.getX() < Game.worldX && World.checkLake(Game.player.getX() + movement, Game.player.getY(), 0) == false
	    	&& World.checkCraftable(Game.player.getX() + movement, Game.player.getY()) == false && World.checkRock(Game.player.getX() + movement, Game.player.getY(), 50, true) == false){
	    	Game.player.setDirection("Right");
	    	Game.player.addX(movement);
	    }
	}
	
	public static void cooking() {

		if (Game.keys[KeyEvent.VK_SPACE] && Game.cook == false && Game.craft == false && Game.harvest == false && Game.hidden == false) {
			for (int i = 0; i < World.craftables.size(); i++) {
				float disx = World.craftables.get(i).x - Game.player.getX();
				float disy = World.craftables.get(i).y - Game.player.getY();
				if (World.craftableType.get(i) == 1 && World.craftableStat.get(i) == true && Math.sqrt(disx * disx + disy * disy) < 50
						&& (Game.rawMeatCollected > 0 || Game.fishCollected > 0)) {
					Game.cook = true;
					Game.craftableAction = i;
					if (Game.rawMeatCollected > 0)
						Game.rawMeatCollected -= 1;
					else if (Game.fishCollected > 0)
						Game.fishCollected -= 1;
				}
			}
		}
		else if (Game.cook == true) {

			if (Game.cookTime > 0) {
				// still cooking
				Game.cookTime -= 1;
				return;
			}

			Game.cook = false;
			Game.cookTime = 300;
			Game.meatCollected += 1;
			if (random.nextFloat() < 0.2)
				World.craftableStat.set(Game.craftableAction, false);
		}
	}
	
	public static void harvesting() {

		if (Game.keys[KeyEvent.VK_SPACE] && Game.cook == false && Game.craft == false && Game.harvest == false && Game.hidden == false) {
			for (int i = 0; i < World.craftables.size(); i++) {
				float disx = World.craftables.get(i).x - Game.player.getX();
				float disy = World.craftables.get(i).y - Game.player.getY();
				if ((World.craftableType.get(i) == 2 || World.craftableType.get(i) == 3) && 
						World.craftableStat.get(i) == false && Math.sqrt(disx * disx + disy * disy) < 50) {
					Game.harvest = true;
					Game.craftableAction = i;
				}
			}
		}
		else if (Game.harvest == true) {
			
			if (Game.harvestTime > 0) {
				// still harvesting
				Game.harvestTime -= 1;
				return;
			}

			Game.harvest = false;
			Game.harvestTime = 200;

			if (World.craftableType.get(Game.craftableAction) == 2) 
				Game.rawMeatCollected += 1;
			else if (World.craftableType.get(Game.craftableAction) == 3)
				Game.fishCollected += 1;

			if (random.nextFloat() < 0.2) {
				World.craftables.remove(Game.craftableAction);
				World.craftableType.remove(Game.craftableAction);
				World.craftableStat.remove(Game.craftableAction);
				return;
			}
			World.craftableStat.set(Game.craftableAction, true);
		}
	}
	
	public static void searching() {
		
		if (Game.keys[KeyEvent.VK_SPACE] && Game.cook == false && Game.craft == false && Game.harvest == false && Game.hidden == false) {
			float pWood = 0, pLiana = 0;
			for (int i = 0; i < World.trees.size(); i++) {
				float dis_x = World.trees.get(i).x - Game.player.getX();
				float dis_y = World.trees.get(i).y - Game.player.getY();		
				if (Math.sqrt(dis_x * dis_x + dis_y * dis_y) < 50) {
					if (World.treeDeath.get(i) == true) {
						pWood += 0.003;
						pLiana += 0.001;
					}
					else {
						pWood += 0.001;
						pLiana += 0.002;
					}
				}
			}
			
			if (random.nextFloat() < pWood)
				Game.woodCollected += 1;
			if (random.nextFloat() < pLiana)
				Game.lianaCollected += 1;
				
			float pStones = 0;
			for (int i = 0; i < World.rocks.size(); i++) {
				float dis_x = World.rocks.get(i).x - Game.player.getX();
				float dis_y = World.rocks.get(i).y - Game.player.getY();		
				if (Math.sqrt(dis_x * dis_x + dis_y * dis_y) < 50)
					pStones += 0.003;
			}
			if (random.nextFloat() < pStones)
				Game.stoneCollected += 1;
				
			float pLeave = 0;
			for (int i = 0; i < World.plants.size(); i++) {
				float dis_x = World.plants.get(i).x - Game.player.getX();
				float dis_y = World.plants.get(i).y - Game.player.getY();		
				if (Math.sqrt(dis_x * dis_x + dis_y * dis_y) < 50)
					pLeave += 0.004;
			}
			if (random.nextFloat() < pLeave)
				Game.leaveCollected += 1;
		}
	}
	
	public static void crafting() {
		
		if (Game.craft == true) {
			if (Game.craftTime > 0) {
				Game.craftTime -= 1;
				return;
			}
			Game.craft = false;
			Game.craftTime = 400;
		}
	
		
		if (Game.keys[KeyEvent.VK_1] && Game.woodCollected >= 5 && Game.stoneCollected >= 8) {
			Game.craft = true;
			
			Game.woodCollected -= 5;
			Game.stoneCollected -= 8;
			World.craftableType.add(1);
			World.craftableStat.add(true);
			addCraftable(30);
		}
		
		if (Game.keys[KeyEvent.VK_2] && Game.woodCollected >= 3 && Game.lianaCollected >= 1) {
			Game.craft = true;
			
			Game.woodCollected -= 3;
			Game.lianaCollected -= 1;
			World.craftableType.add(2);
			World.craftableStat.add(true);
			addCraftable(30);
		}
		
		if (Game.keys[KeyEvent.VK_3] && Game.woodCollected >= 3 && Game.lianaCollected >= 8 ) {

			if (World.checkLake(Game.player.getX(), Game.player.getY(), -20)) {
				Game.craft = true;
				
				Game.woodCollected -= 3;
				Game.lianaCollected -= 8;
				World.craftableType.add(3);
				World.craftableStat.add(true);
				addCraftable(40);
			}
		}
		
		if (Game.keys[KeyEvent.VK_4] && Game.woodCollected >= 8 && Game.lianaCollected >= 4 && Game.leaveCollected >= 10) {
				Game.craft = true;
				
				Game.woodCollected -= 8;
				Game.lianaCollected -= 4;
				Game.leaveCollected -= 10;
				World.craftableType.add(4);
				World.craftableStat.add(true);
				addCraftable(50);
		}
	}
	
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
					World.woods.add(new Point(startX, startY));
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
					World.stones.add(new Point(startX, startY));
					looping = false;
				}
			}
			World.stones.sort(Game.byY);
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
	
	public static void collectItems() {
		int berryIndex = World.checkBerry(Game.player.getX(), Game.player.getY());
		if (berryIndex >= 0 && World.berryStats.get(berryIndex) == true) {
			World.berryStats.set(berryIndex, false);
			Game.berryCollected += 3;
		}
		
		int woodIndex = World.checkWood(Game.player.getX(), Game.player.getY());
		if (woodIndex >= 0) {
			World.nWoods -= 1;
			World.woods.remove(woodIndex);
			World.woodStats.remove(woodIndex);
			Game.woodCollected += 1;
		}
		
		int stoneIndex = World.checkStone(Game.player.getX(), Game.player.getY());
		if (stoneIndex >= 0) {
			World.nStones -= 1;
			World.stones.remove(stoneIndex);
			Game.stoneCollected += 1;
		}
	}

	public static void hideShelter() {
		if (Game.keys[KeyEvent.VK_SPACE] && Game.cook == false && Game.craft == false && Game.harvest == false) {
			if (Game.hidden == true) {
				Game.hidden = false;
				return;
			}
			for (int i = 0; i < World.craftables.size(); i++) {
				if (World.craftableType.get(i) == 4) {
					float disx = World.craftables.get(i).x - Game.player.getX();
					float disy = World.craftables.get(i).y - Game.player.getY();
					if (Math.sqrt(disx * disx + disy * disy) < 50) {
						Game.hidden = true;
					}
				}
			}
		}
	}
	
	public static void addCraftable(int dis) {

		if (Game.player.getDirection() == "Up")
			World.craftables.add(new Point((int) Game.player.getX(), (int) Game.player.getY() - dis));
		else if (Game.player.getDirection() == "Down")
			World.craftables.add(new Point((int) Game.player.getX(), (int) Game.player.getY() + dis));
		else if (Game.player.getDirection() == "Left")
			World.craftables.add(new Point((int) Game.player.getX() - dis, (int) Game.player.getY()));
		else
			World.craftables.add(new Point((int) Game.player.getX() + dis, (int) Game.player.getY()));
	}
}
