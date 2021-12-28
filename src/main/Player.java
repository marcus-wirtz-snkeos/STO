package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.Random;

public class Player {

	Game game = Game.game;
	private Point2D.Float position;
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
		
		craft = false;
		cook = false;
		harvest = false;
		hidden = false;

		// time to finish actions
		harvestTime = 200;
		cookTime = 300;
		craftTime = 400;
	}

	public void initPosition() {
		// Set player start
		direction = "Down";
		int startX = 0;
		int startY = 0;
		while (true) {
			startX = random.nextInt(Game.worldX / 3) + Game.worldX / 3;
			startY = random.nextInt(Game.worldY / 3) + Game.worldY / 3;
			if ((World.whichWolve(startX, startY) < 0 && World.checkLake(startX, startY, -5) == false) && World.checkRock(startX, startY, 50, true) == false)
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
	
	public void addCondition(int add) { condition = Math.min(condition + add, 100); }
	public void addTired(int add) { tired = Math.min(tired + add, 100); }
	public void addHungry(int add) { hungry = Math.min(hungry + add, 100); }
	public void addThirsty(int add) { thirsty = Math.min(thirsty + add, 100); }
	
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
		}
	}
	
	public void eatMeat() {
		if (this.doingAction() == false && meatCollected > 0) {
			meatCollected -= 1;
			this.addHungry(40);
		}
	}
	
	public void abortAction() {
		
		if (this.doingAction() == false) {
			// Implement Menu here
			return;
		}
		
		if (craft == true) {
			int idx = World.craftables.size() - 1;
			World.craftables.remove(idx);
			World.craftableType.remove(idx);
			World.craftableScore.remove(idx);
		}
		craft = false;
		craftTime = 400;
		cook = false;
		cookTime = 300;
		harvest = false;
		harvestTime = 200;
	}
	
	
	public void stats() {

		if (World.checkLake(position.x - (Game.playerMovement + 1), position.y, 0) == true || World.checkLake(position.x + (Game.playerMovement + 1), position.y, 0) == true || 
				World.checkLake(position.x, position.y + (Game.playerMovement + 1), 0) == true || World.checkLake(position.x, position.y - (Game.playerMovement + 1), 0) == true) {
			this.addThirsty(100);
		}
		
		if (Game.tick % 30 == 0) {
			if (random.nextFloat() < 0.4){
				if (this.getHungry() > 0)
					this.addHungry(-1);
				else
					this.addCondition(-1);
			}
			else {
				if (this.getThirsty() > 0)
					this.addThirsty(-1);
				else
					this.addCondition(-1);
			}
		}
		
		if (Game.tick % 100 == 0 && this.getTired() > 50 && this.getHungry() > 50 && this.getThirsty() > 50)
			if (hidden == false)
				this.addCondition(1);
			else
				this.addCondition(2);

		if (Game.tick % 5 == 0 && Game.keys[KeyEvent.VK_SHIFT] && this.getTired() > 0)
			this.addTired(-1);
		
		if (Game.tick % 50 == 0 && this.getTired() < 100)
			this.addTired(1);

		if (this.getCondition() <= 0)
			Game.over = true;		
	}
	
	public void move() {
		if (cook == true || craft == true || harvest == true || hidden == true || Game.keys[KeyEvent.VK_SPACE] == true)
			return;

		float movement = Game.playerMovement;
		movement -= (0.03 * stoneCollected + 0.01 * woodCollected + 0.001 * leaveCollected + 
				0.002 * lianaCollected + 0.001 * berryCollected);

		if (Game.keys[KeyEvent.VK_SHIFT] && this.getTired() > 0) 
			movement *= 1.5;

		movement = Math.max(movement, 0);
		
		if((Game.keys[KeyEvent.VK_W] && Game.keys[KeyEvent.VK_A]) || (Game.keys[KeyEvent.VK_W] && Game.keys[KeyEvent.VK_D]) || 
				(Game.keys[KeyEvent.VK_A] && Game.keys[KeyEvent.VK_S]) || (Game.keys[KeyEvent.VK_S] && Game.keys[KeyEvent.VK_D]) ||
				(Game.keys[KeyEvent.VK_W] && Game.keys[KeyEvent.VK_D]) || (Game.keys[KeyEvent.VK_W] && Game.keys[KeyEvent.VK_D]) ||
				(Game.keys[KeyEvent.VK_UP] && Game.keys[KeyEvent.VK_LEFT]) || (Game.keys[KeyEvent.VK_UP] && Game.keys[KeyEvent.VK_RIGHT]) || 
				(Game.keys[KeyEvent.VK_LEFT] && Game.keys[KeyEvent.VK_DOWN]) || (Game.keys[KeyEvent.VK_DOWN] && Game.keys[KeyEvent.VK_RIGHT]))
				movement /= Math.sqrt(2);
		
	    if((Game.keys[KeyEvent.VK_W] || Game.keys[KeyEvent.VK_UP]) && this.getY() > 0 && World.checkLake(this.getX(), this.getY() - movement, 0) == false
	    	&& World.checkCraftable(this.getX(), this.getY() - movement) == false && World.checkRock(this.getX(), this.getY() - movement, 50, true) == false){
	    	this.setDirection("Up");
	    	this.addY(-movement);
	    }

	    if((Game.keys[KeyEvent.VK_S] || Game.keys[KeyEvent.VK_DOWN]) && this.getY() < Game.worldY && World.checkLake(this.getX(), this.getY() + movement, 0) == false
	    	&& World.checkCraftable(this.getX(), this.getY() + movement) == false && World.checkRock(this.getX(), this.getY() + movement, 50, true) == false){
	    	this.setDirection("Down");
	    	this.addY(movement);
	    }

	    if((Game.keys[KeyEvent.VK_A] || Game.keys[KeyEvent.VK_LEFT]) && this.getX() > 0 && World.checkLake(this.getX() - movement, this.getY(), 0) == false
	    	&& World.checkCraftable(this.getX() - movement, this.getY()) == false && World.checkRock(this.getX() - movement, this.getY(), 50, true) == false){
	    	this.setDirection("Left");
	    	this.addX(-movement);
	    }

	    if((Game.keys[KeyEvent.VK_D] || Game.keys[KeyEvent.VK_RIGHT]) && this.getX() < Game.worldX && World.checkLake(this.getX() + movement, this.getY(), 0) == false
	    	&& World.checkCraftable(this.getX() + movement, this.getY()) == false && World.checkRock(this.getX() + movement, this.getY(), 50, true) == false){
	    	this.setDirection("Right");
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
						if (rawMeatCollected > 0)
							rawMeatCollected -= 1;
						else if (fishCollected > 0)
							fishCollected -= 1;
					}
				}
			}
		}
		else if (cook == true) {			
			if (cookTime > 0 && World.craftableScore.get(craftableAction) >= 1) {
				// still cooking
				cookTime -= 1;
			}
			else {
				cook = false;
				cookTime = 300;
				if (World.craftableScore.get(craftableAction) >= 1)
					meatCollected += 1;	
			}
		}
	}
	
	public void harvesting() {

		if (Game.keys[KeyEvent.VK_SPACE] && cook == false && craft == false && harvest == false && hidden == false) {
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

			harvest = false;
			harvestTime = 200;

			if (World.craftableType.get(craftableAction) == 2) 
				rawMeatCollected += 1;
			else if (World.craftableType.get(craftableAction) == 3)
				fishCollected += 1;

			if (random.nextFloat() < 0.2) {
				World.craftables.remove(craftableAction);
				World.craftableType.remove(craftableAction);
				World.craftableScore.remove(craftableAction);
				return;
			}
			World.craftableScore.set(craftableAction, 100);
		}
	}
	
	public void searching() {
		
		if (Game.keys[KeyEvent.VK_SPACE] && cook == false && craft == false && harvest == false && hidden == false) {
			float pWood = 0, pLiana = 0;
			for (int i = 0; i < World.trees.size(); i++) {
				float dis_x = World.trees.get(i).x - this.getX();
				float dis_y = World.trees.get(i).y - this.getY();		
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
				woodCollected += 1;
			if (random.nextFloat() < pLiana)
				lianaCollected += 1;
				
			float pStones = 0;
			for (int i = 0; i < World.rocks.size(); i++) {
				float dis_x = World.rocks.get(i).x - this.getX();
				float dis_y = World.rocks.get(i).y - this.getY();		
				if (Math.sqrt(dis_x * dis_x + dis_y * dis_y) < 50)
					pStones += 0.003;
			}
			if (random.nextFloat() < pStones)
				stoneCollected += 1;
				
			float pLeave = 0;
			for (int i = 0; i < World.plants.size(); i++) {
				float dis_x = World.plants.get(i).x - this.getX();
				float dis_y = World.plants.get(i).y - this.getY();		
				if (Math.sqrt(dis_x * dis_x + dis_y * dis_y) < 50)
					pLeave += 0.004;
			}
			if (random.nextFloat() < pLeave)
				leaveCollected += 1;
		}
	}
	
	public void crafting() {
		
		if (craft == true) {
			if (craftTime > 0) {
				craftTime -= 1;
				return;
			}
			craft = false;
			craftTime = 400;
		}
	
		
		if (Game.keys[KeyEvent.VK_1] && woodCollected >= 5 && stoneCollected >= 8) {
			craft = true;
			
			woodCollected -= 5;
			stoneCollected -= 8;
			World.craftableType.add(1);
			World.craftableScore.add(100);
			updateGame.addCraftable(30);
		}
		
		if (Game.keys[KeyEvent.VK_2] && woodCollected >= 3 && lianaCollected >= 1) {
			craft = true;
			
			woodCollected -= 3;
			lianaCollected -= 1;
			World.craftableType.add(2);
			World.craftableScore.add(100);
			updateGame.addCraftable(30);
		}
		
		if (Game.keys[KeyEvent.VK_3] && woodCollected >= 3 && lianaCollected >= 8 ) {

			if (World.checkLake(this.getX(), this.getY(), -20)) {
				craft = true;
				
				woodCollected -= 3;
				lianaCollected -= 8;
				World.craftableType.add(3);
				World.craftableScore.add(100);
				updateGame.addCraftable(40);
			}
		}
		
		if (Game.keys[KeyEvent.VK_4] && woodCollected >= 8 && lianaCollected >= 4 && leaveCollected >= 10) {
				craft = true;
				
				woodCollected -= 8;
				lianaCollected -= 4;
				leaveCollected -= 10;
				World.craftableType.add(4);
				World.craftableScore.add(100);
				updateGame.addCraftable(50);
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
		if (Game.keys[KeyEvent.VK_SPACE] && cook == false && craft == false && harvest == false) {
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
		g.drawString(str, Game.dim.width - 199, Game.dim.height - 169);
		if (this.getCondition() < 10)
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLACK);
		g.drawString(str, Game.dim.width - 200, Game.dim.height - 170);
		
		str = "Tired: " + tired;
		g.setColor(Color.WHITE);
		g.drawString(str, Game.dim.width - 199, Game.dim.height - 139);
		if (this.getTired() == 0)
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLACK);
		g.drawString(str, Game.dim.width - 200, Game.dim.height - 140);	

		str = "Hungry: " + hungry;
		g.setColor(Color.WHITE);
		g.drawString(str, Game.dim.width - 199, Game.dim.height - 109);
		if (this.getHungry() == 0)
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLACK);
		g.drawString(str, Game.dim.width - 200, Game.dim.height - 110);

		str = "Thirsty: " + thirsty;
		g.setColor(Color.WHITE);
		g.drawString(str, Game.dim.width - 199, Game.dim.height - 79);
		if (this.getThirsty() == 0)
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLACK);
		g.drawString(str, Game.dim.width - 200, Game.dim.height - 80);	
		
		// Draw Score
		str = "Score: " + Game.score;
		g.setColor(Color.WHITE);
		g.drawString(str, 11, 21);
		g.setColor(Color.BLACK);
		g.drawString(str, 10, 20);
	}
	
	public void drawInventory(Graphics g) {
		String str = woodCollected + " x ";
		g.drawString(str, 15, Game.dim.height - 200);
		str = stoneCollected + " x ";
		g.drawString(str, 15, Game.dim.height - 160);
		str = leaveCollected + " x ";
		g.drawString(str, 15, Game.dim.height - 120);
		str = lianaCollected + " x ";
		g.drawString(str, 15, Game.dim.height - 80);
		str = berryCollected + " x           (e)";
		g.drawString(str, 130, Game.dim.height - 200);
		str = meatCollected + " x           (r)";
		g.drawString(str, 130, Game.dim.height - 160);
		str = rawMeatCollected + " x ";
		g.drawString(str, 130, Game.dim.height - 120);
		str = fishCollected + " x ";
		g.drawString(str, 130, Game.dim.height - 80);
	}
	
	public void showOptions(Graphics g) {
		if (this.doingAction() == false) {
			for (int i = 0; i < World.craftables.size(); i++) {
				if (this.checkDistance(World.craftables.get(i), 50)) {
					String str;
					g.setColor(Color.BLACK);
					if (World.craftableType.get(i) == 1) {
						str = "Press [F] for fueling the fire";
						g.drawString(str, Game.dim.width / 2 - 150, Game.dim.height - 160);
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
					g.drawString(str, Game.dim.width / 2 - 150, Game.dim.height - 140);
				}
			}
		}

		if (Game.keys[KeyEvent.VK_SPACE] && this.doingAction() == false) {
			String str = "Searching...";
			g.setColor(Color.WHITE);
			g.drawString(str, Game.dim.width / 2 - 50, Game.dim.height - 100);
			g.fillRect(Game.dim.width / 2 - 100, Game.dim.height - 90, (int) (5 * Game.tick % 200), 10);
		}

		if (hidden == true) {
			String str = "Hidden in shelter!";
			g.setColor(Color.WHITE);
			g.drawString(str, Game.dim.width / 2 - 80, Game.dim.height - 100);
		}

		if (craft == true) {
			String str = "Crafting...";
			g.setColor(Color.WHITE);
			g.drawString(str, Game.dim.width / 2 - 70, Game.dim.height - 100);
			for (int i = 0; i < 400 - craftTime; i++)
				g.fillRect(Game.dim.width / 2 - 200 + i, Game.dim.height - 90, 1, 10);
		}
		
		if (cook == true) {
			String str = "Cooking...";
			g.setColor(Color.WHITE);
			g.drawString(str, Game.dim.width / 2 - 70, Game.dim.height - 100);
			for (int i = 0; i < 300-cookTime; i++)
				g.fillRect(Game.dim.width / 2 - 200 + (int) ((float) 400 / 300) *i, Game.dim.height - 90, (int) ((float) 400 / 300), 10);
		}
		
		if (harvest == true) {
			String str = "";
			if (World.craftableType.get(craftableAction) == 2)
				str = "Harvesting rabbit...";
			else
				str = "Collecting fish...";
			g.setColor(Color.WHITE);
			g.drawString(str, Game.dim.width / 2 - 120, Game.dim.height - 100);
			for (int i = 0; i < 200-harvestTime; i++)
				g.fillRect(Game.dim.width / 2 - 200 + 2*i, Game.dim.height - 90, 2, 10);
		}
		
		if (Game.keys[KeyEvent.VK_SHIFT] && tired > 0) {
			String str = "Running...";
			g.setColor(Color.RED);
			g.drawString(str, Game.dim.width / 2 - 70, Game.dim.height - 100);
		}
		
		if (Game.over == true) {
			String str = "GAME OVER!";
			g.setColor(Color.RED);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
			g.drawString(str, Game.dim.width / 2 - 8 * str.length(), Game.dim.height / 2 - 13);		
			g.setColor(Color.BLACK);
			g.drawString(str, Game.dim.width / 2 - 8 * str.length() + 1, Game.dim.height / 2 - 13 + 1);
			
			str = "Press 'R' for restart.";
			g.setFont(new Font("TimesRoman", Font.PLAIN, 22));
			g.drawString(str, Game.dim.width / 2 - 82, Game.dim.height / 2 + 10);
		}
	}
}
