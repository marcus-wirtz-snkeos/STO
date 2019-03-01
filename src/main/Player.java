package main;

import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.Random;

public class Player {

	Game game = Game.game;
	private Point2D.Float position;
	private int condition, tired, hungry, thirsty;
	private String direction;
	
	static Random random =  new Random();
	
	public Player() {

		// Set initial stats
		condition = 100;
		tired = 100;
		hungry = 100;
		thirsty = 100;
	}
	
	public void initPosition() {
		// Set player start
		direction = "Down";
		int startX = 0;
		int startY = 0;
		while (true) {
			startX = random.nextInt(Game.worldX / 3) + Game.worldX / 3;
			startY = random.nextInt(Game.worldY / 3) + Game.worldY / 3;
			if ((World.whichWolve(startX, startY) < 0 && World.checkLake(startX, startY, -5) == false))
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
	
	public void fuelFire() {
		if (Game.hidden == false && Game.cook == false && Game.craft == false && Game.harvest == false) {
			for (int i = 0; i < World.craftables.size(); i++) {
				if (World.craftableType.get(i) == 1) {
					float disx = World.craftables.get(i).x - Game.player.getX();
					float disy = World.craftables.get(i).y - Game.player.getY();
					if (Math.sqrt(disx * disx + disy * disy) < 50) {
						Game.woodCollected -= 1;
						World.craftableScore.set(i, Math.min(100, World.craftableScore.get(i) + 20));
					}
				}
			}
		}
	}
}
