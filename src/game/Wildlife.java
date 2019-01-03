package game;

import java.awt.geom.Point2D;
import java.util.Random;

public class Wildlife {
	
	static Random random = new Random();
	static STO sto = STO.sto;
	
	public static void wolveBehaviour() {
		
		for (int i = 0; i < sto.nWolves; i++) {
			float wolveX = sto.wolves.get(i).x, wolveY = sto.wolves.get(i).y;
			float velX = sto.wolveSpeed.get(i).x, velY = sto.wolveSpeed.get(i).y;
			if (sto.tick % 100 == 0) {
				if (random.nextFloat() < sto.changeDirection) {
					velX = 2*random.nextFloat()-1; 
					velY = 2*random.nextFloat()-1;
				}
				else if (random.nextFloat() < sto.wolveAggression) {
					float xDir = sto.player.x - wolveX;
					float yDir = sto.player.y - wolveY;
					float dirLen = (float) Math.sqrt(xDir * xDir + yDir * yDir);
					if (dirLen <= 10 * sto.wolveRadius) {
						velX += sto.changeSpeed * xDir / dirLen;
						velY += sto.changeSpeed * yDir / dirLen;
					}
				}
				else {
					float randX = 2*random.nextFloat()-1;
					float randY = 2*random.nextFloat()-1;
					velX += sto.changeSpeed * randX;
					velY += sto.changeSpeed * randY;
				}
			}
			
			if (wolveX + velX < 0 || wolveX + velX > sto.worldX || wolveY + velY > sto.worldY || wolveY + velY < 0 
					|| sto.checkLake(wolveX + 2*velX, wolveY + 2*velY, 0) == true || sto.checkRock(wolveX + 2*velX, wolveY + 2*velY, 50, true) == true) {
				velX = -velX;
				velY = -velY;
			}
			float momSpeed = (float) Math.sqrt(velX * velX + velY * velY);
			if (momSpeed > sto.maxSpeed) {
				velX *= sto.maxSpeed / momSpeed;
				velY *= sto.maxSpeed / momSpeed;;
			}
			
			// Check for wolve chasing
			float xDir = sto.player.x - wolveX;
			float yDir = sto.player.y - wolveY;
			if (sto.hidden == false) {
				if (wolveChase(i, 1, wolveX, wolveY, sto.player.x, sto.player.y, sto.wolveRadius) == true) {
					float dirLen = (float) Math.sqrt(xDir * xDir + yDir * yDir);
					float intensity = random.nextFloat();
					velX += intensity * sto.chase * xDir / dirLen;
					velY += intensity * sto.chase * yDir / dirLen;
				}
			}
			
			// Check for fire fleeing
			for (int j = 0; j < sto.craftables.size(); j++) {
				if (sto.craftableType.get(j) == 1 && sto.craftableStat.get(j) == true) {
				xDir = sto.craftables.get(j).x - wolveX;
				yDir = sto.craftables.get(j).y - wolveY;
				if (Math.sqrt(xDir * xDir + yDir * yDir) < 200) {
					float dirLen = (float) Math.sqrt(xDir * xDir + yDir * yDir);
					float intensity = random.nextFloat();
					velX -= intensity * xDir / dirLen;
					velY -= intensity * yDir / dirLen;
				}
				}
			}

			// Check for wolve death
			if (wolveAttack(i, wolveX, wolveY, sto.player.x, sto.player.y, 25) == true && sto.hidden == false)
				sto.over = true;
			
			sto.wolveSpeed.set(i, new Point2D.Float(velX, velY));
			sto.wolves.set(i, new Point2D.Float(wolveX + velX, wolveY + velY));
		}
	}

	public static void rabbitBehaviour() {
		
		for (int i = 0; i < sto.rabbits.size(); i++) {
			if (sto.rabbitStats.get(i) == false)
				continue;
			float rabbitX = sto.rabbits.get(i).x, rabbitY = sto.rabbits.get(i).y;
			float velX = sto.rabbitVel.get(i).x, velY = sto.rabbitVel.get(i).y;
			
			for (int j = 0; j < sto.craftables.size(); j++) {
				if (sto.craftableType.get(j) == 2 && sto.craftableStat.get(j) == true) {
					float xdir = rabbitX - sto.craftables.get(j).x;
					float ydir = rabbitY - sto.craftables.get(j).y;
					if (Math.sqrt(xdir * xdir + ydir * ydir) < 20) {
						sto.rabbitStats.set(i, false);
						sto.craftableStat.set(j, false);
					}
				}
			}
			
			if (sto.tick % 50 == 0) {
				if (random.nextFloat() < sto.rabbitChange) {
					velX = 2*random.nextFloat()-1; 
					velY = 2*random.nextFloat()-1;
					}
				else {
					float randX = 2*random.nextFloat()-1;
					float randY = 2*random.nextFloat()-1;
					velX += 0.5 * randX;
					velY += 0.5 * randY;
				}
			}
			
			float momSpeed = (float) Math.sqrt(velX * velX + velY * velY);
			if (momSpeed > sto.rabbitSpeed) {
				velX *= sto.rabbitSpeed / momSpeed;
				velY *= sto.rabbitSpeed / momSpeed;;
			}
			
			// Check for rabbit fleeing
			boolean flee = false;
			float xDir = sto.player.x - rabbitX;
			float yDir = sto.player.y - rabbitY;
			float dirLen = (float) Math.sqrt(xDir * xDir + yDir * yDir);
			
			if (dirLen <= sto.rabbitRadius) {
				float p = (float) (1 - dirLen / sto.rabbitRadius);
				if (random.nextFloat() < p) {
					float intensity = random.nextFloat();
					velX -= intensity * sto.rabbitFlee * xDir / dirLen;
					velY -= intensity * sto.rabbitFlee * yDir / dirLen;
					flee = true;
				}
				
			}

			for (int j = 0; j < sto.nWolves; j++) {

				xDir = sto.wolves.get(j).x - rabbitX;
				yDir = sto.wolves.get(j).y - rabbitY;
				dirLen = (float) Math.sqrt(xDir * xDir + yDir * yDir);
				if (dirLen <= sto.rabbitRadius) {
					float p = (float) (1 - dirLen / sto.rabbitRadius);
					if (random.nextFloat() < p){
						float intensity = random.nextFloat();
						velX -= intensity * sto.rabbitFlee * xDir / dirLen;
						velY -= intensity * sto.rabbitFlee * yDir / dirLen;
						flee = true;
					}
				}
			}
			
			if (rabbitX + velX < 0 || rabbitX + velX > sto.worldX || rabbitY + velY > sto.worldY || rabbitY + velY < 0 
					|| sto.checkLake(rabbitX + 2*velX, rabbitY + 2*velY, 0) == true || sto.checkRock(rabbitX + 2*velX, rabbitY + 2*velY, 50, true) == true) {
				if (flee == false) {
					velX = -velX;
					velY = -velY;
				}
				else 
					velX = - velX;
			}
			
			sto.rabbitVel.set(i, new Point2D.Float(velX, velY));
			sto.rabbits.set(i, new Point2D.Float(rabbitX + velX, rabbitY + velY));
		}
	}

	public static void fishBehaviour() {
		
		for (int i = 0; i < sto.fishes.size(); i++) {
			if (sto.fishStats.get(i) == false)
				continue;
			float fishX = sto.fishes.get(i).x, fishY = sto.fishes.get(i).y;
			float velX = sto.fishVel.get(i).x, velY = sto.fishVel.get(i).y;
			
			for (int j = 0; j < sto.craftables.size(); j++) {
				if (sto.craftableType.get(j) == 3 && sto.craftableStat.get(j) == true) {
					float xdir = fishX - sto.craftables.get(j).x;
					float ydir = fishY - sto.craftables.get(j).y;
					if (Math.sqrt(xdir * xdir + ydir * ydir) < 20) {
						sto.fishStats.set(i, false);
						sto.craftableStat.set(j, false);
					}
				}
			}
			
			if (sto.tick % 30 == 0) {
				if (random.nextFloat() < sto.fishChange) {
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
			if (momSpeed > sto.fishSpeed) {
				velX *= sto.fishSpeed / momSpeed;
				velY *= sto.fishSpeed / momSpeed;;
			}
			
			// Check for fish fleeing
			float xDir = sto.player.x - fishX;
			float yDir = sto.player.y - fishY;
			float dirLen = (float) Math.sqrt(xDir * xDir + yDir * yDir);
			
			if (dirLen <= sto.fishRadius) {
				float p = (float) (1 - dirLen / sto.fishRadius);
				if (random.nextFloat() < p) {
					float intensity = random.nextFloat();
					velX -= intensity * sto.fishFlee * xDir / dirLen;
					velY -= intensity * sto.fishFlee * yDir / dirLen;
				}
				
			}

			if (sto.checkLake(fishX + 2*velX, fishY + 2*velY, 20) == false) {
					velX = -velX;
					velY = -velY;
			}
			
			sto.fishVel.set(i, new Point2D.Float(velX, velY));
			sto.fishes.set(i, new Point2D.Float(fishX + velX, fishY + velY));
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
