package main;

import java.awt.geom.Point2D;
import java.util.Random;

public class Wildlife {
	
	static Random random = new Random();
	
	// wolve setting
	public static float wolveSpeed = (float) 5;
	public static float chase = (float) 0.8;
	public static float wolveChange = (float) 0.2;
	public static float wolveRadius = 500;
	public static float wolveAggression = (float) 0.5;
	public static float wolveDriftRadius = 20 * wolveRadius;
	
	// rabbit setting
	public static float rabbitSpeed = (float) 5;
	public static float rabbitChange = (float) 0.1;
	public static float rabbitFlee = (float) 0.5;
	public static float rabbitRadius = 200;
	
	// fish setting
	public static float fishSpeed = (float) 0.5;
	public static float fishChange = (float) 0.1;
	public static float fishFlee = (float) 0.8;
	public static float fishRadius = 100;

	public static void wolveBehaviour() {
		
		for (int i = 0; i < World.nWolves; i++) {
			float wolveX = World.wolves.get(i).x, wolveY = World.wolves.get(i).y;
			float velX = World.wolveSpeed.get(i).x, velY = World.wolveSpeed.get(i).y;
			if (Game.tick % 100 == 0) {
				if (random.nextFloat() < wolveChange) {
					velX = (float) (wolveSpeed / 2 * (random.nextFloat() - 0.5)); 
					velY = (float) (wolveSpeed / 2 * (random.nextFloat() - 0.5));
				}
				else if (random.nextFloat() < wolveAggression) {
					float xDir = Game.player.getX() - wolveX;
					float yDir = Game.player.getY() - wolveY;
					float dirLen = (float) Math.sqrt(xDir * xDir + yDir * yDir);
					if (dirLen <= 10 * wolveRadius) {
						velX += wolveSpeed / 10 * xDir / dirLen;
						velY += wolveSpeed / 10 * yDir / dirLen;
					}
				}
				else {
					float randX = 2*random.nextFloat()-1;
					float randY = 2*random.nextFloat()-1;
					velX += wolveSpeed / 10 * randX;
					velY += wolveSpeed / 10 * randY;
				}
			}
			
			if (wolveX + velX < 0 || wolveX + velX > World.worldX || wolveY + velY > World.worldY || wolveY + velY < 0 
					|| World.checkLake(wolveX + 2*velX, wolveY + 2*velY, 0) >= 0 
					|| World.checkRock(wolveX + 2*velX, wolveY + 2*velY, 50, true) >= 0
					|| World.checkCraftable(wolveX + 2*velX, wolveY + 2*velY) >= 0) {
				float alpha = (float) (0.9 * Math.PI * (random.nextFloat() - 0.5));
				velX = (float) (-velX * Math.cos(alpha) + velY * Math.sin(alpha));
				velY = (float) (-velX * Math.sin(alpha) - velY * Math.cos(alpha));
			}
			float momSpeed = (float) Math.sqrt(velX * velX + velY * velY);
			if (momSpeed > wolveSpeed) {
				velX *= wolveSpeed / momSpeed;
				velY *= wolveSpeed / momSpeed;;
			}
			
			// Check for wolve chasing
			float xDir = Game.player.getX() - wolveX;
			float yDir = Game.player.getY() - wolveY;
			if (Game.player.isHidden() == false) {
				if (wolveChase(i, 1, wolveX, wolveY, Game.player.getX(), Game.player.getY(), wolveRadius) == true) {
					float dirLen = (float) Math.sqrt(xDir * xDir + yDir * yDir);
					float intensity = random.nextFloat();
					velX += intensity * chase * xDir / dirLen;
					velY += intensity * chase * yDir / dirLen;
				}
			}
			
			// Check for fire fleeing
			for (int j = 0; j < World.craftables.size(); j++) {
				if (World.craftableType.get(j) == 1 && World.craftableScore.get(j) >= 1) {
				xDir = World.craftables.get(j).x - wolveX;
				yDir = World.craftables.get(j).y - wolveY;
				if (Math.sqrt(xDir * xDir + yDir * yDir) < 300) {
					float dirLen = (float) Math.sqrt(xDir * xDir + yDir * yDir);
					float intensity = 3 * random.nextFloat();
					velX -= intensity * xDir / dirLen;
					velY -= intensity * yDir / dirLen;
				}
				}
			}

			// Check for wolve death
			if (wolveAttack(i, wolveX, wolveY, Game.player.getX(), Game.player.getY(), 25) == true && Game.player.isHidden() == false)
				Game.over = true;
			
			World.wolveSpeed.set(i, new Point2D.Float(velX, velY));
			World.wolves.set(i, new Point2D.Float(wolveX + velX, wolveY + velY));
		}
	}

	public static void rabbitBehaviour() {
		
		for (int i = 0; i < World.rabbits.size(); i++) {
			if (World.rabbitStats.get(i) == false)
				continue;
			float rabbitX = World.rabbits.get(i).x, rabbitY = World.rabbits.get(i).y;
			float velX = World.rabbitVel.get(i).x, velY = World.rabbitVel.get(i).y;
			
			for (int j = 0; j < World.craftables.size(); j++) {
				if (World.craftableType.get(j) == 2 && World.craftableScore.get(j) >= 1) {
					float xdir = rabbitX - World.craftables.get(j).x;
					float ydir = rabbitY - World.craftables.get(j).y;
					if (Math.sqrt(xdir * xdir + ydir * ydir) < 20) {
						World.rabbitStats.set(i, false);
						World.craftableScore.set(j, 0);
					}
				}
			}
			
			if (Game.tick % 50 == 0) {
				if (random.nextFloat() < rabbitChange) {
					velX = (float) (rabbitSpeed / 2 * (random.nextFloat() - 0.5)); 
					velY = (float) (rabbitSpeed / 2 * (random.nextFloat() - 0.5));
					}
				else {
					float randX = 2*random.nextFloat()-1;
					float randY = 2*random.nextFloat()-1;
					velX += 0.5 * randX;
					velY += 0.5 * randY;
				}
			}
			
			float momSpeed = (float) Math.sqrt(velX * velX + velY * velY);
			if (momSpeed > rabbitSpeed) {
				velX *= rabbitSpeed / momSpeed;
				velY *= rabbitSpeed / momSpeed;;
			}
			
			// Check for rabbit fleeing
			float xDir = Game.player.getX() - rabbitX;
			float yDir = Game.player.getY() - rabbitY;
			float dirLen = (float) Math.sqrt(xDir * xDir + yDir * yDir);
			
			if (dirLen <= rabbitRadius) {
				float p = (float) (1 - dirLen / rabbitRadius);
				if (random.nextFloat() < p) {
					velX -= random.nextFloat() * rabbitFlee * xDir / dirLen;
					velY -= random.nextFloat() * rabbitFlee * yDir / dirLen;
				}
				
			}

			for (int j = 0; j < World.nWolves; j++) {

				xDir = World.wolves.get(j).x - rabbitX;
				yDir = World.wolves.get(j).y - rabbitY;
				dirLen = (float) Math.sqrt(xDir * xDir + yDir * yDir);
				if (dirLen <= rabbitRadius) {
					float p = (float) (1 - dirLen / rabbitRadius);
					if (random.nextFloat() < p){
						float intensity = random.nextFloat();
						velX -= intensity * rabbitFlee * xDir / dirLen;
						velY -= intensity * rabbitFlee * yDir / dirLen;
					}
				}
			}
			
			if (rabbitX + velX < 0 || rabbitX + velX > World.worldX || rabbitY + velY > World.worldY || rabbitY + velY < 0 
					|| World.checkLake(rabbitX + 2*velX, rabbitY + 2*velY, 0) >= 0 
					|| World.checkRock(rabbitX + 2*velX, rabbitY + 2*velY, 50, true) >= 0) {
				float alpha = (float) (0.9 * Math.PI * (random.nextFloat() - 0.5));
				velX = (float) (-velX * Math.cos(alpha) + velY * Math.sin(alpha));
				velY = (float) (-velX * Math.sin(alpha) - velY * Math.cos(alpha));
			}
			
			World.rabbitVel.set(i, new Point2D.Float(velX, velY));
			World.rabbits.set(i, new Point2D.Float(rabbitX + velX, rabbitY + velY));
		}
	}

	public static void fishBehaviour() {
		
		for (int i = 0; i < World.fishes.size(); i++) {
			if (World.fishStats.get(i) == false)
				continue;
			float fishX = World.fishes.get(i).x, fishY = World.fishes.get(i).y;
			float velX = World.fishVel.get(i).x, velY = World.fishVel.get(i).y;
			
			for (int j = 0; j < World.craftables.size(); j++) {
				if (World.craftableType.get(j) == 3 && World.craftableScore.get(j) >= 1) {
					float xdir = fishX - World.craftables.get(j).x;
					float ydir = fishY - World.craftables.get(j).y;
					if (Math.sqrt(xdir * xdir + ydir * ydir) < 20) {
						World.fishStats.set(i, false);
						World.craftableScore.set(j, 0);
					}
				}
			}
			
			if (Game.tick % 30 == 0) {
				if (random.nextFloat() < fishChange) {
					velX = 2*random.nextFloat()-1; 
					velY = 2*random.nextFloat()-1;
					}
				else {
					float randX = 2*random.nextFloat()-1;
					float randY = 2*random.nextFloat()-1;
					velX += 0.1 * randX;
					velY += 0.1 * randY;
				}
			}

			float momSpeed = (float) Math.sqrt(velX * velX + velY * velY);
			if (momSpeed > fishSpeed) {
				velX *= fishSpeed / momSpeed;
				velY *= fishSpeed / momSpeed;;
			}
			
			// Check for fish fleeing
			float xDir = Game.player.getX() - fishX;
			float yDir = Game.player.getY() - fishY;
			float dirLen = (float) Math.sqrt(xDir * xDir + yDir * yDir);
			
			if (dirLen <= fishRadius) {
				float p = (float) (1 - dirLen / fishRadius);
				if (random.nextFloat() < p) {
					float intensity = random.nextFloat();
					velX -= intensity * fishFlee * xDir / dirLen;
					velY -= intensity * fishFlee * yDir / dirLen;
				}
				
			}

			if (World.checkLake(fishX + 2*velX, fishY + 2*velY, 20) < 0) {
				float alpha = (float) (0.9 * Math.PI * (random.nextFloat() - 0.5));
				velX = (float) (-velX * Math.cos(alpha) + velY * Math.sin(alpha));
				velY = (float) (-velX * Math.sin(alpha) - velY * Math.cos(alpha));
			}
			
			World.fishVel.set(i, new Point2D.Float(velX, velY));
			World.fishes.set(i, new Point2D.Float(fishX + velX, fishY + velY));
		}
	}
	
	public static boolean wolveAttack(int index, float wolveX, float wolveY, float playerX, float playerY, float wolveRadius) {
		
		float dis_x = wolveX - playerX;
		float dis_y = wolveY - playerY;
		if (Math.sqrt(dis_x * dis_x + 4 * dis_y * dis_y) <= wolveRadius) {
				return true;
		}
		return false;
	}
	
	public static boolean wolveChase(int index, int pow, float wolveX, float wolveY, float playerX, float playerY, float wolveRadius) {
		
		float dis_x = wolveX - playerX;
		float dis_y = wolveY - playerY;
		if (Math.sqrt(dis_x * dis_x + dis_y * dis_y) <= wolveRadius) {
			float p = (float) (1 - Math.sqrt(dis_x * dis_x + dis_y * dis_y) / wolveRadius);
			if (random.nextFloat() < Math.pow(p, pow))
				return true;
		}
		return false;
	}
	
}
