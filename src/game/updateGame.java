package game;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.Random;

public class updateGame {

	static Random random = new Random();
	static STO sto = STO.sto;
	
	public static void stats() {
		
		int tick = sto.tick;
		

		if (sto.checkLake(sto.player.x - (sto.playerMovement + 1), sto.player.y, 0) == true || sto.checkLake(sto.player.x + (sto.playerMovement + 1), sto.player.y, 0) == true || 
				sto.checkLake(sto.player.x, sto.player.y + (sto.playerMovement + 1), 0) == true || sto.checkLake(sto.player.x, sto.player.y - (sto.playerMovement + 1), 0) == true) {
			sto.thirsty = 100;
		}
		
		if (tick % 30 == 0) {
			float pval = random.nextFloat();
			if (pval < 0.4){
				if (sto.hungry > 0)
					sto.hungry-=1;
				else
					sto.condition -= 1;
			}
			else {
				if (sto.thirsty > 0)
					sto.thirsty-=1;
				else
					sto.condition -= 1;
			}
		}
		
		if (tick % 100 == 0 && sto.tired > 0 && sto.hungry > 0 && sto.thirsty > 0 && sto.condition < 100)
			if (sto.hidden == false)
				sto.condition += 1;
			else
				sto.condition += 2;

		if (tick % 5 == 0 && sto.run == true) {
			if (sto.tired > 0)
				sto.tired -= 1;
		}
		
		if (tick % 50 == 0 && sto.tired < 100)
			sto.tired += 1;
			
		if (tick % 100 == 0)
			sto.score += 1;
		
		if (sto.condition <= 0)
			sto.over = true;
		
	}
	
	public static void move() {
		if (sto.hidden == false && sto.search == false && sto.craft == false && sto.cook == false && sto.harvest == false) {
		float movement = sto.playerMovement;
		movement -= (0.03 * sto.stoneCollected + 0.01 * sto.woodCollected + 0.001 * sto.leaveCollected + 
				0.002 * sto.lianaCollected + 0.001 * sto.berryCollected);
		sto.run = false;
		if (sto.keys[KeyEvent.VK_SHIFT] && sto.tired > 0) {
			sto.run = true;
			movement *= 1.5;
		}
		movement = Math.max(movement, 0);
		
		if((sto.keys[KeyEvent.VK_W] && sto.keys[KeyEvent.VK_A]) || (sto.keys[KeyEvent.VK_W] && sto.keys[KeyEvent.VK_D]) || 
				(sto.keys[KeyEvent.VK_A] && sto.keys[KeyEvent.VK_S]) || (sto.keys[KeyEvent.VK_S] && sto.keys[KeyEvent.VK_D]) ||
				(sto.keys[KeyEvent.VK_W] && sto.keys[KeyEvent.VK_D]) || (sto.keys[KeyEvent.VK_W] && sto.keys[KeyEvent.VK_D]) ||
				(sto.keys[KeyEvent.VK_UP] && sto.keys[KeyEvent.VK_LEFT]) || (sto.keys[KeyEvent.VK_UP] && sto.keys[KeyEvent.VK_RIGHT]) || 
				(sto.keys[KeyEvent.VK_LEFT] && sto.keys[KeyEvent.VK_DOWN]) || (sto.keys[KeyEvent.VK_DOWN] && sto.keys[KeyEvent.VK_RIGHT]))
				movement /= Math.sqrt(2);
		
	    if((sto.keys[KeyEvent.VK_W] || sto.keys[KeyEvent.VK_UP]) && sto.player.y > 0 && sto.checkLake(sto.player.x, sto.player.y - movement, 0) == false
	    		&& sto.checkRock(sto.player.x, sto.player.y - movement, 50, true) == false){
	    	sto.direction = "Up";
	    	sto.player.y -= movement;
	    }

	    if((sto.keys[KeyEvent.VK_S] || sto.keys[KeyEvent.VK_DOWN]) && sto.player.y < sto.worldY && sto.checkLake(sto.player.x, sto.player.y + movement, 0) == false
	    		&& sto.checkRock(sto.player.x, sto.player.y + movement, 50, true) == false){
	    	sto.direction = "Down";
	    	sto.player.y += movement;
	    }

	    if((sto.keys[KeyEvent.VK_A] || sto.keys[KeyEvent.VK_LEFT]) && sto.player.x > 0 && sto.checkLake(sto.player.x - movement, sto.player.y, 0) == false
	    		&& sto.checkRock(sto.player.x - movement, sto.player.y, 50, true) == false){
	    	sto.direction = "Left";
	    	sto.player.x -= movement;
	    }

	    if((sto.keys[KeyEvent.VK_D] || sto.keys[KeyEvent.VK_RIGHT]) && sto.player.x < sto.worldX && sto.checkLake(sto.player.x + movement, sto.player.y, 0) == false
	    		&& sto.checkRock(sto.player.x + movement, sto.player.y, 50, true) == false){
	    	sto.direction = "Right";
	    	sto.player.x += movement;
	    }
		}
	}
	
	public static void cooking() {
		
		if (sto.cook == false) {
			if (sto.keys[KeyEvent.VK_SPACE] && sto.hidden == false && sto.craft == false && sto.search == false && sto.harvest == false) {
				for (int i = 0; i < sto.craftables.size(); i++) {
					float disx = sto.craftables.get(i).x - sto.player.x;
					float disy = sto.craftables.get(i).y - sto.player.y;
					if (sto.craftableType.get(i) == 1 && sto.craftableStat.get(i) == true && Math.sqrt(disx * disx + disy * disy) < 50
							&& (sto.rawMeatCollected > 0 || sto.fishCollected > 0)) {
						sto.cook = true;
						sto.action = i;
					}
				}
			}
		}
		else {
			if (sto.cookTime == 0) {
				sto.cook = false;
				sto.cookTime = 300;
				if (sto.rawMeatCollected > 0)
					sto.rawMeatCollected -= 1;
				else
					sto.fishCollected -= 1;
				sto.meatCollected += 1;
				if (random.nextFloat() < 0.2)
					sto.craftableStat.set(sto.action, false);
			}
			else
				sto.cookTime -= 1;
		}
		
	}	
	
	public static void harvesting() {
		
		if (sto.harvest == false) {
			if (sto.keys[KeyEvent.VK_SPACE] && sto.cook == false && sto.search == false && sto.craft == false && sto.hidden == false) {
				for (int i = 0; i < sto.craftables.size(); i++) {
					float disx = sto.craftables.get(i).x - sto.player.x;
					float disy = sto.craftables.get(i).y - sto.player.y;
					if ((sto.craftableType.get(i) == 2 || sto.craftableType.get(i) == 3) && 
							sto.craftableStat.get(i) == false && Math.sqrt(disx * disx + disy * disy) < 50) {
						sto.harvest = true;
						sto.action = i;
					}
				}
			}
		}
		else {
			if (sto.harvestTime == 0) {
				sto.harvest = false;
				sto.harvestTime = 200;
	
				if (sto.craftableType.get(sto.action) == 2) 
					sto.rawMeatCollected += 1;
				else if (sto.craftableType.get(sto.action) == 3)
					sto.fishCollected += 1;

				if (random.nextFloat() < 0.2) {
					sto.craftables.remove(sto.action);
					sto.craftableType.remove(sto.action);
					sto.craftableStat.remove(sto.action);
				}
				else
					sto.craftableStat.set(sto.action, true);
			}
			else
				sto.harvestTime -= 1;
		}
		
	}
	
	public static void searching() {
		
		if (sto.search == false) {
			if (sto.space && sto.hidden == false && sto.harvest == false && sto.cook == false && sto.craft == false) {
				sto.search = true;
				sto.space = false;
			}
		}
		else {
			if (sto.searchTime == 0) {
				sto.search = false;
				sto.searchTime = 150;
			}
			else
				sto.searchTime -= 1;
		
			float pWood = 0, pLiana = 0;
			for (int i = 0; i < sto.trees.size(); i++) {
				float dis_x = sto.trees.get(i).x - sto.player.x;
				float dis_y = sto.trees.get(i).y - sto.player.y;		
				if (Math.sqrt(dis_x * dis_x + dis_y * dis_y) < 50) {
					if (sto.treeDeath.get(i) == true) {
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
				sto.woodCollected += 1;
			if (random.nextFloat() < pLiana)
				sto.lianaCollected += 1;
				
			float pStones = 0;
			for (int i = 0; i < sto.rocks.size(); i++) {
				float dis_x = sto.rocks.get(i).x - sto.player.x;
				float dis_y = sto.rocks.get(i).y - sto.player.y;		
				if (Math.sqrt(dis_x * dis_x + dis_y * dis_y) < 50)
					pStones += 0.003;
			}
			if (random.nextFloat() < pStones)
				sto.stoneCollected += 1;
				
			float pLeave = 0;
			for (int i = 0; i < sto.plants.size(); i++) {
				float dis_x = sto.plants.get(i).x - sto.player.x;
				float dis_y = sto.plants.get(i).y - sto.player.y;		
				if (Math.sqrt(dis_x * dis_x + dis_y * dis_y) < 50)
					pLeave += 0.004;
			}
			if (random.nextFloat() < pLeave)
				sto.leaveCollected += 1;
		}
	}
	
	public static void crafting() {
		
		if (sto.craft == true) {
			if (sto.craftTime == 0) {
				sto.craft = false;
				sto.craftTime = 400;
			}
			else
				sto.craftTime -= 1;
		}
		
		if (sto.craft == false && sto.keys[KeyEvent.VK_1] && sto.woodCollected >= 5 && sto.stoneCollected >= 1) {
			sto.craft = true;
			
			sto.woodCollected -= 5;
			sto.stoneCollected -= 8;
			sto.craftableType.add(1);
			sto.craftableStat.add(true);
			addCraftable(20);
		}
		
		if (sto.craft == false && sto.keys[KeyEvent.VK_2] && sto.woodCollected >= 3 && sto.lianaCollected >= 1) {
			sto.craft = true;
			
			sto.woodCollected -= 3;
			sto.lianaCollected -= 1;
			sto.craftableType.add(2);
			sto.craftableStat.add(true);
			addCraftable(20);
		}
		
		if (sto.craft == false && sto.keys[KeyEvent.VK_3] && sto.woodCollected >= 3 && sto.lianaCollected >= 8 ) {

			if (sto.checkLake(sto.player.x, sto.player.y, -20)) {
				sto.craft = true;
				
				sto.woodCollected -= 3;
				sto.lianaCollected -= 8;
				sto.craftableType.add(3);
				sto.craftableStat.add(true);
				addCraftable(50);
			}
		}
		
		if (sto.craft == false && sto.keys[KeyEvent.VK_4] && sto.woodCollected >= 8 && sto.lianaCollected >= 4 && sto.leaveCollected >= 10) {
				sto.craft = true;
				
				sto.woodCollected -= 8;
				sto.lianaCollected -= 4;
				sto.leaveCollected -= 10;
				sto.craftableType.add(4);
				sto.craftableStat.add(true);
				addCraftable(50);
		}
	}
	
	public static void spawnItems() {
		
		if (sto.tick % 100 == 0) {
		for (int i = 0; i < sto.nBerries; i++) {
			if (sto.berryStats.get(i) == false && random.nextFloat() < sto.berryRespawn)
				sto.berryStats.set(i, true);
		}
		
		if (random.nextFloat() < sto.woodSpawn) {
			sto.nWoods += 1;
			boolean looping = true;
			while (looping == true) {
				int startX = random.nextInt(sto.worldX);
				int startY = random.nextInt(sto.worldY);
				if (sto.checkLake(startX, startY, -5) == false && sto.checkTree(startX, startY, sto.rWood) == true) {
					sto.woods.add(new Point(startX, startY));
					if (random.nextFloat() < 0.5)
						sto.woodStats.add(true);
					else
						sto.woodStats.add(false);
					looping = false;
				}
			}
		}
		
		
		if (random.nextFloat() < sto.stoneSpawn) {
			sto.nStones += 1;
			boolean looping = true;
			while (looping == true) {
				int startX = random.nextInt(sto.worldX);
				int startY = random.nextInt(sto.worldY);
				if (sto.checkLake(startX, startY, -5) == false && sto.checkRock(startX, startY, 300, false) && sto.checkRock(startX, startY, 30, false) == false) {
					sto.stones.add(new Point(startX, startY));
					looping = false;
				}
			}
			sto.stones.sort(sto.byY);
		}
		if (sto.tick % 100 == 0 && random.nextFloat() < sto.rabbitSpawn) {
			for (int i = 0; i < sto.rabbits.size(); i++) {
				if (sto.rabbitStats.get(i) == false) {
					sto.rabbitStats.set(i, true);
					while (true) {
						float startX = random.nextInt(sto.worldX);
						float startY = random.nextInt(sto.worldY);
						if (sto.checkLake(startX, startY, -5) == false && sto.checkRock(startX, startY, 40, true) == false) {
							sto.rabbits.set(i, new Point2D.Float(startX, startY));
							break;
						}
					}
					break;
				}
			}
		}
		
		if (sto.tick % 100 == 0 && random.nextFloat() < sto.fishSpawn) {
			for (int i = 0; i < sto.fishes.size(); i++) {
				if (sto.fishStats.get(i) == false) {
					sto.fishStats.set(i, true);
					while (true) {
						float startX = random.nextInt(sto.worldX);
						float startY = random.nextInt(sto.worldY);
						if (sto.checkLake(startX, startY, 20) == true) {
							sto.fishes.set(i, new Point2D.Float(startX, startY));
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
		int berryIndex = sto.checkBerry(sto.player.x, sto.player.y);
		if (berryIndex >= 0 && sto.berryStats.get(berryIndex) == true) {
			sto.berryStats.set(berryIndex, false);
			sto.berryCollected += 3;
		}
		
		int woodIndex = sto.checkWood(sto.player.x, sto.player.y);
		if (woodIndex >= 0) {
			sto.nWoods -= 1;
			sto.woods.remove(woodIndex);
			sto.woodStats.remove(woodIndex);
			sto.woodCollected += 1;
		}
		
		int stoneIndex = sto.checkStone(sto.player.x, sto.player.y);
		if (stoneIndex >= 0) {
			sto.nStones -= 1;
			sto.stones.remove(stoneIndex);
			sto.stoneCollected += 1;
		}
	}

	public static void hideShelter() {
		if (sto.space == true && sto.harvest == false && sto.cook == false && sto.craft == false) {
			if (sto.hidden == true) {
				sto.hidden = false;
				sto.space = false;
			}
			else {
				for (int i = 0; i < sto.craftables.size(); i++) {
					if (sto.craftableType.get(i) == 4) {
						float disx = sto.craftables.get(i).x - sto.player.x;
						float disy = sto.craftables.get(i).y - sto.player.y;
						if (Math.sqrt(disx * disx + disy * disy) < 50) {
							sto.hidden = true;
							sto.space = false;
						}
					}
				}
			}
		}
	}
	
	public static void addCraftable(int dis) {

		if (sto.direction == "Up")
			sto.craftables.add(new Point((int) sto.player.x, (int) sto.player.y - dis));
		else if (sto.direction == "Down")
			sto.craftables.add(new Point((int) sto.player.x, (int) sto.player.y + dis));
		else if (sto.direction == "Left")
			sto.craftables.add(new Point((int) sto.player.x - dis, (int) sto.player.y));
		else
			sto.craftables.add(new Point((int) sto.player.x + dis, (int) sto.player.y));
	}
}
