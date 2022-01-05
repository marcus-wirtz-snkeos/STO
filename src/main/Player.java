package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.Random;

public class Player {

	private Point2D.Float position;
	private static float playerMovement = (float) 4;
	private int condition, tired, hungry, thirsty;
	private int woodCollected, stoneCollected, leaveCollected, lianaCollected;
	private int berryCollected, meatCollected, rawMeatCollected, fishCollected;
	private boolean craft, cook, harvest, hidden;
	private int craftableAction;
	private int craftTime, harvestTime, cookTime;
	private String direction;
	
	private static Random random =  new Random();
	
	Player() {
		
		// Set initial stats
		this.condition = 100;
		this.tired = 100;
		this.hungry = 100;
		this.thirsty = 100;
		
		// Set inventory
		this.woodCollected = 0;
		this.stoneCollected = 0;
		this.leaveCollected = 0;
		this.lianaCollected = 0;
		this.berryCollected = 0;
		this.meatCollected = 0;
		this.rawMeatCollected = 0;
		this.fishCollected = 0;
		
		// define actions
		this.craftableAction = 1;
		
		// time to finish actions
		this.resetCooking();
		this.resetHarvesting();
		this.resetCrafting();
		this.hidden = false;
	}

	public void initPosition() {
		// Set player start
		this.direction = "Down";
		int startX = 0;
		int startY = 0;
		while (true) {
			startX = Player.random.nextInt(World.worldX / 3) + World.worldX / 3;
			startY = Player.random.nextInt(World.worldY / 3) + World.worldY / 3;
			if ((World.checkWolve(startX, startY) < 0 && 
				 World.checkLake(startX, startY, -5) < 0) && 
				 World.checkRock(startX, startY, 50, true) < 0)
				break;
		}
		this.position = new Point2D.Float((float) startX, (float) startY);
	}
	
	// public getter function to access variables from outside
	public float getX() { return this.position.x; }
	public float getY() { return this.position.y; }
	public String getDirection() { return this.direction; }
	public float getCondition() { return this.condition; }
	public float getTired() { return this.tired; }
	public float getHungry() { return this.hungry; }
	public float getThirsty() { return this.thirsty; }
	
	public int getWoodCollected() { return this.woodCollected; }
	public int getStoneCollected() { return this.stoneCollected; }
	public int getLianaCollected() { return this.lianaCollected; }
	public int getLeaveCollected() { return this.leaveCollected; }
	
	public boolean doingAction() { return (this.hidden == true || this.cook == true || this.craft == true || this.harvest == true); }
	public boolean isHidden() { return this.hidden; }
	
	// private functions which are only used within the class
	private void addCondition(int add) { this.condition = Math.max(0, Math.min(this.condition + add, 100)); }
	private void addTired(int add) { this.tired = Math.max(0, Math.min(this.tired + add, 100)); }
	private void addHungry(int add) { this.hungry = Math.max(0, Math.min(this.hungry + add, 100)); }
	private void addThirsty(int add) { this.thirsty = Math.max(0, Math.min(this.thirsty + add, 100)); }

	private void addX(float x) { this.position.x += x; }
	private void addY(float y) { this.position.y += y; }
	private void setDirection(String dir) { this.direction = dir; }
	
	private void resetHarvesting() { this.harvest = false; this.harvestTime = 200; }
	private void resetCooking() { this.cook = false; this.cookTime = 300; }
	private void resetCrafting() { this.craft = false; this.craftTime = 400; }
	
	// functions that are executed upon key hits
	public void fuelFire() {
		if (this.doingAction() == false && this.woodCollected >= 1) {
			for (int i = 0; i < World.craftables.size(); i++) {
				if (World.craftableType.get(i) == 1) {
					if (this.checkDistance(World.craftables.get(i), (float) 50)) {
						this.woodCollected -= 1;
						World.craftableScore.set(i, Math.min(100, World.craftableScore.get(i) + 20));
					}
				}
			}
		}
	}
	
	public void eatBerry() {
		if (this.doingAction() == false && this.berryCollected > 0) {
			this.berryCollected -= 1;
			this.addHungry(5);
			this.addThirsty(1);
		}
	}
	
	public void eatMeat() {
		if (this.doingAction() == false && this.meatCollected > 0) {
			this.meatCollected -= 1;
			this.addHungry(40);
			this.addThirsty(-5);
		}
	}
	
	public void abortAction() {
		
		if (this.doingAction() == false) {
			// Implement Menu here
			return;
		}
		
		if (this.craft == true) {
			int idx = World.craftables.size() - 1;
			World.removeCraftable(idx);
		}
		this.resetCooking();
		this.resetHarvesting();
		this.resetCrafting();
	}
	
	public void hideShelter() {
		if (this.cook == false && this.craft == false && this.harvest == false) {
			if (this.hidden == true) { this.hidden = false; }
			else {
				for (int i = 0; i < World.craftables.size(); i++) {
					if (World.craftableType.get(i) == 4) {
						if (this.checkDistance(World.craftables.get(i), 50))
							this.hidden = true;
					}
				}
			}
		}
	}
	
	// update function for the game
	public void update() {
		this.stats();
		this.collectItems();
		this.cooking();
		this.harvesting();
		this.crafting();
		this.searching();
		this.move();
	}
	
	private void stats() {

		// drink
		Point2D.Float dir;
		dir = this.newDirection(Player.playerMovement);
		if (World.checkLake(dir.x, dir.y, 0) >= 0) { this.addThirsty(1); }
		
		// get hungrier / thirstier
		if (Game.tick % 30 == 0) {
			if (Player.random.nextFloat() < 0.4){
				if (this.getHungry() > 0) { this.addHungry(-1); }
				else { this.addCondition(-1); }
			}
			else {
				if (this.getThirsty() > 0) { this.addThirsty(-1); }
				else { this.addCondition(-1); }
			}
		}
		
		if (Game.tick % 100 == 0 && this.getTired() > 50 && this.getHungry() > 50 && this.getThirsty() > 50)
			if (this.hidden == false) { this.addCondition(1); }
			else { this.addCondition(2); }

		if (Game.tick % 5 == 0 && Game.keys[KeyEvent.VK_SHIFT] && this.getTired() > 0)
			this.addTired(-1);
		
		if (Game.tick % 50 == 0 && this.getTired() < 100)
			this.addTired(1);

		if (this.getCondition() == 0)
			Game.over = true;
	}

	private void collectItems() {
		int berryIndex = World.checkBerry(this.getX(), this.getY());
		if (berryIndex >= 0 && World.berryStats.get(berryIndex) == true) {
			World.berryStats.set(berryIndex, false);
			this.berryCollected += 3;
		}
		
		int woodIndex = World.checkWood(this.getX(), this.getY());
		if (woodIndex >= 0) {
			World.nWoods -= 1;
			World.woods.remove(woodIndex);
			World.woodStats.remove(woodIndex);
			this.woodCollected += 1;
		}
		
		int stoneIndex = World.checkStone(this.getX(), this.getY());
		if (stoneIndex >= 0) {
			World.nStones -= 1;
			World.stones.remove(stoneIndex);
			this.stoneCollected += 1;
		}
	}
	
	private void cooking() {

		if (this.cook) {
			if (this.cookTime > 0 && World.craftableScore.get(this.craftableAction) >= 1) { this.cookTime -= 1; }	// still cooking
			else {
				this.resetCooking();
				if (World.craftableScore.get(this.craftableAction) >= 1) { this.meatCollected += 1; }
			}
		}
		
		if (Game.keys[KeyEvent.VK_SPACE] && this.doingAction() == false) {
			for (int i = 0; i < World.craftables.size(); i++) {
				if (World.craftableType.get(i) == 1 && World.craftableScore.get(i) >= 1 && (this.rawMeatCollected > 0 || this.fishCollected > 0)) {
					if (this.checkDistance(World.craftables.get(i), 50)) {
						this.cook = true;
						this.craftableAction = i;
						if (this.rawMeatCollected > 0) { this.rawMeatCollected -= 1; }
						else if (this.fishCollected > 0) { this.fishCollected -= 1; }
					}
				}
			}
		}
	}
	
	private void harvesting() {
		
		if (this.harvest) {
			if (this.harvestTime > 0) {	this.harvestTime -= 1; return; }  // still harvesting
			this.resetHarvesting();

			if (World.craftableType.get(this.craftableAction) == 2) { this.rawMeatCollected += 1; }
			else if (World.craftableType.get(this.craftableAction) == 3) { this.fishCollected += 1; }

			if (Player.random.nextFloat() < 0.2) { World.removeCraftable(this.craftableAction); }
			else {World.craftableScore.set(this.craftableAction, 100); }
		}

		if (Game.keys[KeyEvent.VK_SPACE] && !this.doingAction()) {
			for (int i = 0; i < World.craftables.size(); i++) {
				float disx = World.craftables.get(i).x - this.getX();
				float disy = World.craftables.get(i).y - this.getY();
				if ((World.craftableType.get(i) == 2 || World.craftableType.get(i) == 3) && 
						World.craftableScore.get(i) == 0 && Math.sqrt(disx * disx + disy * disy) < 50) {
					this.harvest = true;
					this.craftableAction = i;
				}
			}
		}			
	}
	
	private void crafting() {
		
		if (this.craft) {
			if (this.craftTime > 0) { this.craftTime -= 1; return; } // still crafting
			this.resetCrafting();
			World.craftableScore.set(World.craftableScore.size()-1, 100);
		}
		
		// craft campfire
		if (Game.keys[KeyEvent.VK_1] && this.woodCollected >= 5 && this.stoneCollected >= 8) {
			this.craft = true;
			this.woodCollected -= 5;
			this.stoneCollected -= 8;
			World.addCraftable(30);
			World.craftableType.add(1);
			World.craftableScore.add(0);
		}
		// craft snare
		else if (Game.keys[KeyEvent.VK_2] && this.woodCollected >= 3 && this.lianaCollected >= 1) {
			this.craft = true;
			this.woodCollected -= 3;
			this.lianaCollected -= 1;
			World.addCraftable(30);
			World.craftableType.add(2);
			World.craftableScore.add(1);
		}
		// craft fish trap
		else if (Game.keys[KeyEvent.VK_3] && this.woodCollected >= 3 && this.lianaCollected >= 8 ) {

			Point2D.Float test_dir = this.newDirection(Player.playerMovement);
			if (World.checkLake(test_dir.x, test_dir.y, 0) >= 0) {
				this.craft = true;
				this.woodCollected -= 3;
				this.lianaCollected -= 8;
				World.addCraftable(40);
				World.craftableType.add(3);
				World.craftableScore.add(1);
			}
		}
		else if (Game.keys[KeyEvent.VK_4] && this.woodCollected >= 8 && this.lianaCollected >= 4 && this.leaveCollected >= 10) {
				this.craft = true;
				
				this.woodCollected -= 8;
				this.lianaCollected -= 4;
				this.leaveCollected -= 10;
				World.addCraftable(50);
				World.craftableType.add(4);
				World.craftableScore.add(1);
		}
	}
	
	private void searching() {
		
		if (Game.keys[KeyEvent.VK_SPACE] && !this.doingAction()) {
			float pWood = 0, pLiana = 0;
			for (int i = 0; i < World.trees.size(); i++) {
				if (this.checkDistance(World.trees.get(i), 50)) {
					if (World.treeDeath.get(i) == true) { pWood += 0.003; pLiana += 0.001; }
					else { pWood += 0.001; pLiana += 0.002;	}
				}
			}
			
			if (Player.random.nextFloat() < pWood) { this.woodCollected += 1; }
			if (Player.random.nextFloat() < pLiana) { this.lianaCollected += 1; }
			
			// search stones
			float pStones = 0;
			for (int i = 0; i < World.rocks.size(); i++) {
				if (this.checkDistance(World.rocks.get(i), 50)) { pStones += 0.003; }
			}
			if (Player.random.nextFloat() < pStones) { this.stoneCollected += 1; }
			
			// search leaves
			float pLeave = 0;
			for (int i = 0; i < World.plants.size(); i++) {
				if (this.checkDistance(World.plants.get(i), 50)) { pLeave += 0.004; }
			}
			if (Player.random.nextFloat() < pLeave) { this.leaveCollected += 1; }
		}
	}
	
	private void move() {
		if (this.doingAction() || Game.keys[KeyEvent.VK_SPACE] == true)
			return;

		float movement = Player.playerMovement;
		movement -= (0.03 * this.stoneCollected + 0.01 * this.woodCollected + 0.001 * this.leaveCollected + 
				0.002 * this.lianaCollected + 0.001 * this.berryCollected);
		movement = Math.max(movement, Player.playerMovement / 10);

		if (Game.keys[KeyEvent.VK_SHIFT] && this.tired > 0) 
			movement *= 1.5;
		
		if((Game.keys[KeyEvent.VK_W] && Game.keys[KeyEvent.VK_A]) || (Game.keys[KeyEvent.VK_W] && Game.keys[KeyEvent.VK_D]) || 
				(Game.keys[KeyEvent.VK_A] && Game.keys[KeyEvent.VK_S]) || (Game.keys[KeyEvent.VK_S] && Game.keys[KeyEvent.VK_D]) ||
				(Game.keys[KeyEvent.VK_W] && Game.keys[KeyEvent.VK_D]) || (Game.keys[KeyEvent.VK_W] && Game.keys[KeyEvent.VK_D]) ||
				(Game.keys[KeyEvent.VK_UP] && Game.keys[KeyEvent.VK_LEFT]) || (Game.keys[KeyEvent.VK_UP] && Game.keys[KeyEvent.VK_RIGHT]) || 
				(Game.keys[KeyEvent.VK_LEFT] && Game.keys[KeyEvent.VK_DOWN]) || (Game.keys[KeyEvent.VK_DOWN] && Game.keys[KeyEvent.VK_RIGHT]))
				movement /= Math.sqrt(2);
		
	    if (Game.keys[KeyEvent.VK_W] || Game.keys[KeyEvent.VK_UP]){
	    	this.setDirection("Up");
	    	if (! this.directionBlocked(movement))
	    		this.addY(-movement);
	    }

	    if (Game.keys[KeyEvent.VK_S] || Game.keys[KeyEvent.VK_DOWN]){
	    	this.setDirection("Down");
	    	if (! this.directionBlocked(movement))
	    		this.addY(movement);
	    }

	    if (Game.keys[KeyEvent.VK_A] || Game.keys[KeyEvent.VK_LEFT]){
	    	this.setDirection("Left");
	    	if (! this.directionBlocked(movement))
	    		this.addX(-movement);
	    }

	    if(Game.keys[KeyEvent.VK_D] || Game.keys[KeyEvent.VK_RIGHT]){
	    	this.setDirection("Right");
	    	if (! this.directionBlocked(movement))
	    		this.addX(movement);
	    }
	}
	
	
	private Boolean checkDistance(Point2D.Float point, float distance) {
		float disx = point.x - this.getX();
		float disy = point.y - this.getY();
		return Math.sqrt(disx * disx + disy * disy) < distance;
	}
	
	
	private Point2D.Float newDirection(float movement) {
		
		Point2D.Float test_position = new Point2D.Float(this.getX(), this.getY());
		if (this.direction == "Up") { test_position.y -= movement;	}
		if (this.direction == "Down") { test_position.y += movement; }
		if (this.direction == "Left") { test_position.x -= movement; }
		if (this.direction == "Right") { test_position.x += movement; }
		return test_position;
	}
	
	
	private boolean directionBlocked(float movement) {

		Point2D.Float dir;
		dir = this.newDirection(movement);
		boolean end_world = dir.x < 5 || dir.x > World.worldX - 5 || dir.y < 5 || dir.y > World.worldY - 5;
		boolean hit_lake = World.checkLake(dir.x, dir.y, 0) >= 0;
		boolean hit_craft = World.checkCraftable(dir.x, dir.y) >= 0;
		boolean hit_rock = World.checkRock(dir.x, dir.y, 50, true) >= 0;

		return end_world || hit_lake || hit_craft || hit_rock;
	}
	
	// painting functions
	public void renderStatsInventoryOptions(Graphics g) {
		this.drawStats(g);
		this.drawInventory(g);
		this.showOptions(g);
	}
	
	private void drawStats(Graphics g) {
		g.setFont(new Font("TimesRoman", Font.PLAIN, 22));
		
		String str = "Condition: " + this.condition;
		g.setColor(Color.WHITE);
		g.drawString(str, World.dim.width - 199, World.dim.height - 169);
		if (this.condition < 10) {
			if (Game.tick % 15 < 5)
				g.setColor(Color.BLACK);
			else
				g.setColor(Color.RED);
		}
		else
			g.setColor(Color.BLACK);
		g.drawString(str, World.dim.width - 200, World.dim.height - 170);
		
		str = "Tired: " + this.tired;
		g.setColor(Color.WHITE);
		g.drawString(str, World.dim.width - 199, World.dim.height - 139);
		if (this.tired == 0) { g.setColor(Color.RED); }
		else if (this.tired < 50 && this.condition < 100) { g.setColor(Color.YELLOW); }
		else { g.setColor(Color.BLACK); }
		g.drawString(str, World.dim.width - 200, World.dim.height - 140);	

		str = "Hungry: " + this.hungry;
		g.setColor(Color.WHITE);
		g.drawString(str, World.dim.width - 199, World.dim.height - 109);
		if (this.hungry == 0) {
			if (Game.tick % 15 < 5)
				g.setColor(Color.BLACK);
			else
				g.setColor(Color.RED);
		}
		else if (this.hungry < 50 && this.condition < 100) { g.setColor(Color.YELLOW); }
		else { g.setColor(Color.BLACK); }
		g.drawString(str, World.dim.width - 200, World.dim.height - 110);

		str = "Thirsty: " + this.thirsty;
		g.setColor(Color.WHITE);
		g.drawString(str, World.dim.width - 199, World.dim.height - 79);
		if (this.thirsty == 0) {
			if (Game.tick % 15 < 5)
				g.setColor(Color.BLACK);
			else
				g.setColor(Color.RED);
		}
		else if (this.thirsty < 50 && this.condition < 100) { g.setColor(Color.YELLOW); }
		else { g.setColor(Color.BLACK); }
		g.drawString(str, World.dim.width - 200, World.dim.height - 80);	
		
		// Draw Score
		str = "Score: " + Game.score;
		g.setColor(Color.WHITE);
		g.drawString(str, 11, 21);
		g.setColor(Color.BLACK);
		g.drawString(str, 10, 20);
	}
	
	private void drawInventory(Graphics g) {
		String str = this.woodCollected + " x ";
		g.drawString(str, 15, World.dim.height - 200);
		str = this.stoneCollected + " x ";
		g.drawString(str, 15, World.dim.height - 160);
		str = this.leaveCollected + " x ";
		g.drawString(str, 15, World.dim.height - 120);
		str = this.lianaCollected + " x ";
		g.drawString(str, 15, World.dim.height - 80);
		str = this.berryCollected + " x           (e)";
		g.drawString(str, 130, World.dim.height - 200);
		str = this.meatCollected + " x           (r)";
		g.drawString(str, 130, World.dim.height - 160);
		str = this.rawMeatCollected + " x ";
		g.drawString(str, 130, World.dim.height - 120);
		str = this.fishCollected + " x ";
		g.drawString(str, 130, World.dim.height - 80);
	}
	
	private void showOptions(Graphics g) {
		if (this.doingAction() == false) {
			for (int i = 0; i < World.craftables.size(); i++) {
				if (this.checkDistance(World.craftables.get(i), 50)) {
					String str;
					g.setColor(Color.BLACK);
					if (World.craftableType.get(i) == 1 && this.woodCollected > 0) {
						str = "Press [F] for fueling the fire";
						g.drawString(str, World.dim.width / 2 - 150, World.dim.height - 160);
					}
	
					if (World.craftableType.get(i) == 1 && World.craftableScore.get(i) >= 1
							&& (this.rawMeatCollected > 0 || this.fishCollected > 0)) {
						str = "Press [Space] for cooking";
					}
					else if (World.craftableType.get(i) == 2 && World.craftableScore.get(i) < 1)
						str = "Press [Space] for harvesting";
					else if (World.craftableType.get(i) == 3 && World.craftableScore.get(i) < 1)
						str = "Press [Space] for collecting";
					else if (World.craftableType.get(i) == 4)
						str = "Press [Space] for hiding";
					else
						continue;
					g.drawString(str, World.dim.width / 2 - 150, World.dim.height - 140);
				}
			}
		}

		if (Game.keys[KeyEvent.VK_SPACE] && this.doingAction() == false) {
			String str = "Searching...";
			g.setColor(Color.WHITE);
			g.drawString(str, World.dim.width / 2 - 50, World.dim.height - 100);
			g.fillRect(World.dim.width / 2 - 100, World.dim.height - 90, (int) (5 * Game.tick % 220), 10);
		}

		if (this.hidden == true) {
			String str = "Hidden in shelter!";
			g.setColor(Color.WHITE);
			g.drawString(str, World.dim.width / 2 - 80, World.dim.height - 100);
		}

		if (this.craft == true) {
			String str = "Crafting...";
			g.setColor(Color.WHITE);
			g.drawString(str, World.dim.width / 2 - 70, World.dim.height - 100);
			for (int i = 0; i < 400 - this.craftTime; i++)
				g.fillRect(World.dim.width / 2 - 200 + i, World.dim.height - 90, 1, 10);
		}
		
		if (this.cook == true) {
			String str = "Cooking...";
			g.setColor(Color.WHITE);
			g.drawString(str, World.dim.width / 2 - 70, World.dim.height - 100);
			for (int i = 0; i < 300 - this.cookTime; i++)
				g.fillRect(World.dim.width / 2 - 200 + (int) ((float) 400 / 300) *i, World.dim.height - 90, (int) ((float) 400 / 300), 10);
		}
		
		if (this.harvest == true) {
			String str = "";
			if (World.craftableType.get(craftableAction) == 2)
				str = "Harvesting rabbit...";
			else
				str = "Collecting fish...";
			g.setColor(Color.WHITE);
			g.drawString(str, World.dim.width / 2 - 120, World.dim.height - 100);
			for (int i = 0; i < 200-harvestTime; i++)
				g.fillRect(World.dim.width / 2 - 200 + 2*i, World.dim.height - 90, 2, 10);
		}
		
		if (Game.keys[KeyEvent.VK_SHIFT] && this.tired > 0) {
			String str = "Running...";
			g.setColor(Color.RED);
			g.drawString(str, World.dim.width / 2 - 70, World.dim.height - 100);
		}
		
	}
}
