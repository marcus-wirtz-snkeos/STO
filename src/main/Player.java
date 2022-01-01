package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.Random;

public class Player {

	private Point2D.Float position;
	public static float pTired = (float) 0.001;
	public static float playerMovement = (float) 4;
	private int condition, tired, hungry, thirsty;
	public int woodCollected, stoneCollected, leaveCollected, lianaCollected, berryCollected, meatCollected, rawMeatCollected, fishCollected;
	private boolean craft, cook, harvest, hidden;
	private int craftableAction;
	private int craftTime, harvestTime, cookTime;
	private String direction;
	
	static Random random =  new Random();
	
	public Player() {

		// Set initial stats
		condition = 100;
		tired = 100;
		hungry = 100;
		thirsty = 100;
		
		// Set inventory
		woodCollected = 0;
		stoneCollected = 0;
		leaveCollected = 0;
		lianaCollected = 0;
		berryCollected = 0;
		meatCollected = 0;
		rawMeatCollected = 0;
		fishCollected = 0;
		
		// define actions
		craftableAction = 1;
		
		// time to finish actions
		resetCooking();
		resetHarvesting();
		resetCrafting();
		hidden = false;
	}

	public void initPosition() {
		// Set player start
		direction = "Down";
		int startX = 0;
		int startY = 0;
		while (true) {
			startX = random.nextInt(World.worldX / 3) + World.worldX / 3;
			startY = random.nextInt(World.worldY / 3) + World.worldY / 3;
			if ((World.checkWolve(startX, startY) < 0 && 
				 World.checkLake(startX, startY, -5) < 0) && 
				 World.checkRock(startX, startY, 50, true) < 0)
				break;
		}
		position = new Point2D.Float((float) startX, (float) startY);
	}

	public float getX() { return position.x; }
	public float getY() { return position.y; }
	public void addX(float x) { position.x += x; }
	public void addY(float y) { position.y += y; }
	public float getCondition() { return condition; }
	public float getTired() { return tired; }
	public float getHungry() { return hungry; }
	public float getThirsty() { return thirsty; }
	public String getDirection() { return direction; }
	public void setDirection(String dir) { direction = dir; }
	
	public void addCondition(int add) { condition = Math.max(0, Math.min(condition + add, 100)); }
	public void addTired(int add) { tired = Math.max(0, Math.min(tired + add, 100)); }
	public void addHungry(int add) { hungry = Math.max(0, Math.min(hungry + add, 100)); }
	public void addThirsty(int add) { thirsty = Math.max(0, Math.min(thirsty + add, 100)); }

	public void resetHarvesting() { harvest = false; harvestTime = 200; }
	public void resetCooking() { cook = false; cookTime = 300; }
	public void resetCrafting() { craft = false; craftTime = 400; }
	
	public boolean doingAction() { return (hidden == true || cook == true || craft == true || harvest == true); }
	public boolean isHidden() { return hidden; }
	
	public void fuelFire() {
		if (this.doingAction() == false && woodCollected >= 1) {
			for (int i = 0; i < World.craftables.size(); i++) {
				if (World.craftableType.get(i) == 1) {
					if (this.checkDistance(World.craftables.get(i), (float) 50)) {
						woodCollected -= 1;
						World.craftableScore.set(i, Math.min(100, World.craftableScore.get(i) + 20));
					}
				}
			}
		}
	}
	
	public void eatBerry() {
		if (this.doingAction() == false && berryCollected > 0) {
			berryCollected -= 1;
			this.addHungry(5);
			this.addThirsty(1);
		}
	}
	
	public void eatMeat() {
		if (this.doingAction() == false && meatCollected > 0) {
			meatCollected -= 1;
			this.addHungry(40);
			this.addThirsty(-5);
		}
	}
	
	public void abortAction() {
		
		if (this.doingAction() == false) {
			// Implement Menu here
			return;
		}
		
		if (craft == true) {
			int idx = World.craftables.size() - 1;
			World.removeCraftable(idx);
		}
		this.resetCooking();
		this.resetHarvesting();
		this.resetCrafting();
	}
	
	
	public void stats() {

		// drink
		Point2D.Float dir;
		dir = this.newDirection(playerMovement);
		if (World.checkLake(dir.x, dir.y, 0) >= 0) { this.addThirsty(1); }
		
		// get hungrier / thirstier
		if (Game.tick % 30 == 0) {
			if (random.nextFloat() < 0.4){
				if (this.getHungry() > 0) { this.addHungry(-1); }
				else { this.addCondition(-1); }
			}
			else {
				if (this.getThirsty() > 0) { this.addThirsty(-1); }
				else { this.addCondition(-1); }
			}
		}
		
		if (Game.tick % 100 == 0 && this.getTired() > 50 && this.getHungry() > 50 && this.getThirsty() > 50)
			if (hidden == false) { this.addCondition(1); }
			else { this.addCondition(2); }

		if (Game.tick % 5 == 0 && Game.keys[KeyEvent.VK_SHIFT] && this.getTired() > 0)
			this.addTired(-1);
		
		if (Game.tick % 50 == 0 && this.getTired() < 100)
			this.addTired(1);

		if (this.getCondition() == 0)
			Game.over = true;
	}
	
	public Point2D.Float newDirection(float movement) {

		float move_to_x = this.getX();
		float move_to_y = this.getY();
		if (this.getDirection() == "Up") { move_to_y -= movement;	}
		if (this.getDirection() == "Down") { move_to_y += movement; }
		if (this.getDirection() == "Left") { move_to_x -= movement; }
		if (this.getDirection() == "Right") { move_to_x += movement; }
		return new Point2D.Float((float) move_to_x, (float) move_to_y);
	}
	
	public boolean directionBlocked(float movement) {

		Point2D.Float dir;
		dir = this.newDirection(movement);
		boolean end_world = dir.x < 5 || dir.x > World.worldX - 5 || dir.y < 5 || dir.y > World.worldY - 5;
		boolean hit_lake = World.checkLake(dir.x, dir.y, 0) >= 0;
		boolean hit_craft = World.checkCraftable(dir.x, dir.y) >= 0;
		boolean hit_rock = World.checkRock(dir.x, dir.y, 50, true) >= 0;

		return end_world || hit_lake || hit_craft || hit_rock;
	}
	
	public void move() {
		if (this.doingAction() || Game.keys[KeyEvent.VK_SPACE] == true)
			return;

		float movement = playerMovement;
		movement -= (0.03 * stoneCollected + 0.01 * woodCollected + 0.001 * leaveCollected + 
				0.002 * lianaCollected + 0.001 * berryCollected);
		movement = Math.max(movement, playerMovement / 10);

		if (Game.keys[KeyEvent.VK_SHIFT] && this.getTired() > 0) 
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
	
	public void cooking() {

		if (Game.keys[KeyEvent.VK_SPACE] && cook == false && craft == false && harvest == false && hidden == false) {
			for (int i = 0; i < World.craftables.size(); i++) {
				if (World.craftableType.get(i) == 1 && World.craftableScore.get(i) >= 1 && (rawMeatCollected > 0 || fishCollected > 0)) {
					if (this.checkDistance(World.craftables.get(i), 50)) {
						cook = true;
						craftableAction = i;
						if (rawMeatCollected > 0) {	rawMeatCollected -= 1; }
						else if (fishCollected > 0) { fishCollected -= 1; }
					}
				}
			}
		}
		else if (cook == true) {			
			if (cookTime > 0 && World.craftableScore.get(craftableAction) >= 1) { cookTime -= 1; }
			else {
				this.resetCooking();
				if (World.craftableScore.get(craftableAction) >= 1)
					meatCollected += 1;	
			}
		}
	}
	
	public void harvesting() {

		if (Game.keys[KeyEvent.VK_SPACE] && !this.doingAction()) {
			for (int i = 0; i < World.craftables.size(); i++) {
				float disx = World.craftables.get(i).x - this.getX();
				float disy = World.craftables.get(i).y - this.getY();
				if ((World.craftableType.get(i) == 2 || World.craftableType.get(i) == 3) && 
						World.craftableScore.get(i) == 0 && Math.sqrt(disx * disx + disy * disy) < 50) {
					harvest = true;
					craftableAction = i;
				}
			}
		}
		else if (harvest == true) {
			
			if (harvestTime > 0) {
				// still harvesting
				harvestTime -= 1;
				return;
			}

			this.resetHarvesting();

			if (World.craftableType.get(craftableAction) == 2) 
				rawMeatCollected += 1;
			else if (World.craftableType.get(craftableAction) == 3)
				fishCollected += 1;

			if (random.nextFloat() < 0.2) { World.removeCraftable(craftableAction); }
			else {World.craftableScore.set(craftableAction, 100); }
		}
	}
	
	public void searching() {
		
		if (Game.keys[KeyEvent.VK_SPACE] && !this.doingAction()) {
			float pWood = 0, pLiana = 0;
			for (int i = 0; i < World.trees.size(); i++) {
				if (this.checkDistance(World.trees.get(i), 50)) {
					if (World.treeDeath.get(i) == true) { pWood += 0.003; pLiana += 0.001; }
					else { pWood += 0.001; pLiana += 0.002;	}
				}
			}
			
			if (random.nextFloat() < pWood) { woodCollected += 1; }
			if (random.nextFloat() < pLiana) { lianaCollected += 1; }
			
			// search stones
			float pStones = 0;
			for (int i = 0; i < World.rocks.size(); i++) {
				if (this.checkDistance(World.rocks.get(i), 50)) { pStones += 0.003; }
			}
			if (random.nextFloat() < pStones) { stoneCollected += 1; }
			
			// search leaves
			float pLeave = 0;
			for (int i = 0; i < World.plants.size(); i++) {
				if (this.checkDistance(World.plants.get(i), 50)) { pLeave += 0.004; }
			}
			if (random.nextFloat() < pLeave) { leaveCollected += 1; }
		}
	}
	
	public void crafting() {
		
		if (craft == true) {
			if (craftTime > 0) {
				craftTime -= 1;
				return;
			}
			this.resetCrafting();
		}
	
		if (Game.keys[KeyEvent.VK_1] && woodCollected >= 5 && stoneCollected >= 8) {
			craft = true;
			woodCollected -= 5;
			stoneCollected -= 8;
			World.addCraftable(30);
			World.craftableType.add(1);
			World.craftableScore.add(100);
		}
		else if (Game.keys[KeyEvent.VK_2] && woodCollected >= 3 && lianaCollected >= 1) {
			craft = true;
			woodCollected -= 3;
			lianaCollected -= 1;
			World.addCraftable(30);
			World.craftableType.add(2);
			World.craftableScore.add(100);
		}
		else if (Game.keys[KeyEvent.VK_3] && woodCollected >= 3 && lianaCollected >= 8 ) {

			if (World.checkLake(this.getX(), this.getY(), -20) >= 0) {
				craft = true;
				woodCollected -= 3;
				lianaCollected -= 8;
				World.addCraftable(40);
				World.craftableType.add(3);
				World.craftableScore.add(100);
			}
		}
		else if (Game.keys[KeyEvent.VK_4] && woodCollected >= 8 && lianaCollected >= 4 && leaveCollected >= 10) {
				craft = true;
				
				woodCollected -= 8;
				lianaCollected -= 4;
				leaveCollected -= 10;
				World.addCraftable(50);
				World.craftableType.add(4);
				World.craftableScore.add(100);
		}
	}
	
	public void collectItems() {
		int berryIndex = World.checkBerry(Game.player.getX(), Game.player.getY());
		if (berryIndex >= 0 && World.berryStats.get(berryIndex) == true) {
			World.berryStats.set(berryIndex, false);
			berryCollected += 3;
		}
		
		int woodIndex = World.checkWood(Game.player.getX(), Game.player.getY());
		if (woodIndex >= 0) {
			World.nWoods -= 1;
			World.woods.remove(woodIndex);
			World.woodStats.remove(woodIndex);
			woodCollected += 1;
		}
		
		int stoneIndex = World.checkStone(Game.player.getX(), Game.player.getY());
		if (stoneIndex >= 0) {
			World.nStones -= 1;
			World.stones.remove(stoneIndex);
			stoneCollected += 1;
		}
	}

	public void hideShelter() {
		if (cook == false && craft == false && harvest == false) {
			if (hidden == true) {
				hidden = false;
				return;
			}
			for (int i = 0; i < World.craftables.size(); i++) {
				if (World.craftableType.get(i) == 4) {
					if (this.checkDistance(World.craftables.get(i), 50))
						hidden = true;
				}
			}
		}
	}
	
	public Boolean checkDistance(Point2D.Float point, float distance) {
		float disx = point.x - position.x;
		float disy = point.y - position.y;
		return Math.sqrt(disx * disx + disy * disy) < distance;
	}
	
	public void drawStats(Graphics g) {
		g.setFont(new Font("TimesRoman", Font.PLAIN, 22));
		
		String str = "Condition: " + condition;
		g.setColor(Color.WHITE);
		g.drawString(str, World.dim.width - 199, World.dim.height - 169);
		if (this.getCondition() < 10) {
			if (Game.tick % 15 < 5)
				g.setColor(Color.BLACK);
			else
				g.setColor(Color.RED);
		}
		else
			g.setColor(Color.BLACK);
		g.drawString(str, World.dim.width - 200, World.dim.height - 170);
		
		str = "Tired: " + tired;
		g.setColor(Color.WHITE);
		g.drawString(str, World.dim.width - 199, World.dim.height - 139);
		if (this.getTired() == 0) { g.setColor(Color.RED); }
		else if (this.getTired() < 50 && this.getCondition() < 100) { g.setColor(Color.YELLOW); }
		else { g.setColor(Color.BLACK); }
		g.drawString(str, World.dim.width - 200, World.dim.height - 140);	

		str = "Hungry: " + hungry;
		g.setColor(Color.WHITE);
		g.drawString(str, World.dim.width - 199, World.dim.height - 109);
		if (this.getHungry() == 0) {
			if (Game.tick % 15 < 5)
				g.setColor(Color.BLACK);
			else
				g.setColor(Color.RED);
		}
		else if (this.getHungry() < 50 && this.getCondition() < 100) { g.setColor(Color.YELLOW); }
		else { g.setColor(Color.BLACK); }
		g.drawString(str, World.dim.width - 200, World.dim.height - 110);

		str = "Thirsty: " + thirsty;
		g.setColor(Color.WHITE);
		g.drawString(str, World.dim.width - 199, World.dim.height - 79);
		if (this.getThirsty() == 0) {
			if (Game.tick % 15 < 5)
				g.setColor(Color.BLACK);
			else
				g.setColor(Color.RED);
		}
		else if (this.getThirsty() < 50 && this.getCondition() < 100) { g.setColor(Color.YELLOW); }
		else { g.setColor(Color.BLACK); }
		g.drawString(str, World.dim.width - 200, World.dim.height - 80);	
		
		// Draw Score
		str = "Score: " + Game.score;
		g.setColor(Color.WHITE);
		g.drawString(str, 11, 21);
		g.setColor(Color.BLACK);
		g.drawString(str, 10, 20);
	}
	
	public void drawInventory(Graphics g) {
		String str = woodCollected + " x ";
		g.drawString(str, 15, World.dim.height - 200);
		str = stoneCollected + " x ";
		g.drawString(str, 15, World.dim.height - 160);
		str = leaveCollected + " x ";
		g.drawString(str, 15, World.dim.height - 120);
		str = lianaCollected + " x ";
		g.drawString(str, 15, World.dim.height - 80);
		str = berryCollected + " x           (e)";
		g.drawString(str, 130, World.dim.height - 200);
		str = meatCollected + " x           (r)";
		g.drawString(str, 130, World.dim.height - 160);
		str = rawMeatCollected + " x ";
		g.drawString(str, 130, World.dim.height - 120);
		str = fishCollected + " x ";
		g.drawString(str, 130, World.dim.height - 80);
	}
	
	public void showOptions(Graphics g) {
		if (this.doingAction() == false) {
			for (int i = 0; i < World.craftables.size(); i++) {
				if (this.checkDistance(World.craftables.get(i), 50)) {
					String str;
					g.setColor(Color.BLACK);
					if (World.craftableType.get(i) == 1) {
						str = "Press [F] for fueling the fire";
						g.drawString(str, World.dim.width / 2 - 150, World.dim.height - 160);
					}
	
					if (World.craftableType.get(i) == 1 && World.craftableScore.get(i) >= 1
							&& (rawMeatCollected > 0 || fishCollected > 0)) {
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
			g.fillRect(World.dim.width / 2 - 100, World.dim.height - 90, (int) (5 * Game.tick % 200), 10);
		}

		if (hidden == true) {
			String str = "Hidden in shelter!";
			g.setColor(Color.WHITE);
			g.drawString(str, World.dim.width / 2 - 80, World.dim.height - 100);
		}

		if (craft == true) {
			String str = "Crafting...";
			g.setColor(Color.WHITE);
			g.drawString(str, World.dim.width / 2 - 70, World.dim.height - 100);
			for (int i = 0; i < 400 - craftTime; i++)
				g.fillRect(World.dim.width / 2 - 200 + i, World.dim.height - 90, 1, 10);
		}
		
		if (cook == true) {
			String str = "Cooking...";
			g.setColor(Color.WHITE);
			g.drawString(str, World.dim.width / 2 - 70, World.dim.height - 100);
			for (int i = 0; i < 300-cookTime; i++)
				g.fillRect(World.dim.width / 2 - 200 + (int) ((float) 400 / 300) *i, World.dim.height - 90, (int) ((float) 400 / 300), 10);
		}
		
		if (harvest == true) {
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
		
		if (Game.keys[KeyEvent.VK_SHIFT] && tired > 0) {
			String str = "Running...";
			g.setColor(Color.RED);
			g.drawString(str, World.dim.width / 2 - 70, World.dim.height - 100);
		}
		
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
	}
}
