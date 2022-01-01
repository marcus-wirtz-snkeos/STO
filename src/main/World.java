package main;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public class World {
	
	static Random random =  new Random();

	static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	public static Dimension dim = new Dimension(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());

	public static final int worldSize = 2;
	public static int worldX = worldSize * dim.width;
	public static int worldY = worldSize * dim.height;;
	public static float dayLength = (float) 500;
	
	public static int nLakes = 10;
	public static int nBerries = 15, nStones = 10, nWoods = 10;
	public static int nWolves = 3, nRabbits = 10, nFishes = 20;
	public static int nTrees = 400, nPlants = 100, nRocks = 20, nLilies = 100, nReeds = 100;
	public static float pPine1, pPine2, pFir1, pFir2, pTree, pDeath, pPlant1, pPlant2, pPlant3, pPlant4, pRock1, pRock2, pLily1, pLily2, pLily3;
	
	public static int berryBonus;
	public static int rWood, rStone;
	public static int seedForests, seedRocks, seedPlants, seedLilies;
	public static float berryRespawn = (float) 0.001, woodSpawn = (float) 0.001, stoneSpawn = (float) 0.001;
	public static float	rabbitSpawn = (float) 0.001, fishSpawn = (float) 0.001;

	public static int nCraft = 4;
	public static boolean[] craftStats = new boolean[nCraft];
	
	public static ArrayList<Point2D.Float> wolves = new ArrayList<Point2D.Float>();
	public static ArrayList<Point2D.Float> wolveSpeed = new ArrayList<Point2D.Float>();
	public static ArrayList<Point2D.Float> rabbits = new ArrayList<Point2D.Float>();
	public static ArrayList<Point2D.Float> rabbitVel = new ArrayList<Point2D.Float>();
	public static ArrayList<Boolean> rabbitStats = new ArrayList<Boolean>();
	public static ArrayList<Point2D.Float> fishes = new ArrayList<Point2D.Float>();
	public static ArrayList<Point2D.Float> fishVel = new ArrayList<Point2D.Float>();
	public static ArrayList<Boolean> fishStats = new ArrayList<Boolean>();
	
	public static ArrayList<Point2D.Float> lakes = new ArrayList<Point2D.Float>();
	public static ArrayList<Integer> radiusLakes = new ArrayList<Integer>();
	
	public static ArrayList<Point2D.Float> berries = new ArrayList<Point2D.Float>();
	public static ArrayList<Boolean> berryStats = new ArrayList<Boolean>();
	public static ArrayList<Point2D.Float> woods = new ArrayList<Point2D.Float>();
	public static ArrayList<Boolean> woodStats = new ArrayList<Boolean>();
	public static ArrayList<Point2D.Float> stones = new ArrayList<Point2D.Float>();

	public static ArrayList<Point2D.Float> trees = new ArrayList<Point2D.Float>();
	public static ArrayList<Integer> treeType = new ArrayList<Integer>();
	public static ArrayList<Point2D.Float> rocks = new ArrayList<Point2D.Float>();
	public static ArrayList<Integer> rockType = new ArrayList<Integer>();
	public static ArrayList<Boolean> treeDeath = new ArrayList<Boolean>();
	public static ArrayList<Point2D.Float> plants = new ArrayList<Point2D.Float>();
	public static ArrayList<Integer> plantType = new ArrayList<Integer>();
	public static ArrayList<Point2D.Float> lilies = new ArrayList<Point2D.Float>();
	public static ArrayList<Integer> lilyType = new ArrayList<Integer>();
	public static ArrayList<Point2D.Float> reeds = new ArrayList<Point2D.Float>();
	
	public static ArrayList<Point2D.Float> craftables = new ArrayList<Point2D.Float>();
	public static ArrayList<Integer> craftableType = new ArrayList<Integer>();
	public static ArrayList<Integer> craftableScore = new ArrayList<Integer>();
	
	public static void initWorld() {

		// Clear previous craftables
		craftables.clear();
		craftableType.clear();
		craftableScore.clear();
		
		// Set lakes
		int minLake = 30, maxLake = 400;
		lakes.clear();
		for (int i = 0; i < nLakes; i++) {
			lakes.add(new Point2D.Float(random.nextInt(worldX), random.nextInt(worldY)));
			radiusLakes.add(random.nextInt((maxLake - minLake) + 1) + minLake);
		}
		System.out.println("Lakes Set!");

		
		// Set trees
		seedForests = 20;
		pPine1 = (float) 0.3;
		pPine2 = (float) 0.2;
		pFir1 = (float) 0.2;
		pFir2 = (float) 0.2;
		pTree = (float) 0.1;
		pDeath = (float) 0.1;
		
		trees.clear();
		for (int i = 0; i < nTrees; i++) {
			while (true) {
				int startX = random.nextInt(worldX);
				int startY = random.nextInt(worldY);
				if (checkLake(startX, startY, -5) < 0) {
					if (i < seedForests) {
						addTree(startX, startY);
						break;
					}
					else {
						if ((checkTree(startX, startY, 100) >= 0 && checkTree(startX, startY, 30) < 0) || random.nextFloat() < 0.01){
							addTree(startX, startY);
							break;
						}
					}
				}
			}
		}
		trees.sort(Game.FloatbyY);
		System.out.println("Trees Set!");
		
		// Set plants
		seedPlants = 10;
		pPlant1 = (float) 0.6;
		pPlant2 = (float) 0.35;
		pPlant3 = (float) 0.04;
		pPlant4 = (float) 0.01;
		
		plants.clear();
		for (int i = 0; i < nPlants; i++) {
			while (true) {
				int startX = random.nextInt(worldX);
				int startY = random.nextInt(worldY);
				if (checkLake(startX, startY, -5) < 0) {
					if (i < seedPlants) {
						addPlant(startX, startY);
						break;
					}
					else {
						if ((checkPlant(startX, startY, 100) >= 0 && checkTree(startX, startY, 30) < 0) || random.nextFloat() < 0.01){
							addPlant(startX, startY);
							break;
						}
					}
				}
			}
		}
		plants.sort(Game.FloatbyY);
		System.out.println("Plants Set!");
		
		// Set berries
		berryBonus = 25;
		berries.clear();
		for (int i = 0; i < nBerries; i++) {
			while (true) {
				int startX = random.nextInt(worldX);
				int startY = random.nextInt(worldY);
				if (checkLake(startX, startY, -5) < 0) {
					berries.add(new Point2D.Float(startX, startY));
					berryStats.add(true);
					break;
				}
			}
		}
		berries.sort(Game.FloatbyY);
		System.out.println("Berries Set!");
		
		// Set wood
		rWood = 200;
		
		woods.clear();
		for (int i = 0; i < nWoods; i++) {
			while (true) {
				int startX = random.nextInt(worldX);
				int startY = random.nextInt(worldY);
				if (checkLake(startX, startY, -5) < 0 && checkTree(startX, startY, rWood) >= 0) {
					woods.add(new Point2D.Float(startX, startY));
					if (random.nextFloat() < 0.5)
						woodStats.add(true);
					else
						woodStats.add(false);
					break;
				}
			}
		}
		woods.sort(Game.FloatbyY);
		System.out.println("Wood Set!");
		
		// Set stones
		rStone = 40;		
		stones.clear();
		for (int i = 0; i < nStones; i++) {
			while (true) {
				int startX = random.nextInt(worldX);
				int startY = random.nextInt(worldY);
				if (checkLake(startX, startY, -5) < 0 && (checkRock(startX, startY, 300, false) >= 0 || random.nextFloat() < 0.05)) {
					stones.add(new Point2D.Float(startX, startY));
					break;
				}
			}
		}		
		stones.sort(Game.FloatbyY);
		System.out.println("Stones Set!");
		
		// Set rocks
		seedRocks = 4;
		pRock1 = (float) 0.7;
		pRock2 = (float) 0.3;
		
		rocks.clear();
		for (int i = 0; i < nRocks; i++) {
			while (true) {
				int startX = random.nextInt(worldX);
				int startY = random.nextInt(worldY);
				if (checkLake(startX, startY, -5) < 0 && checkTree(startX, startY, 60) < 0 && checkPlant(startX, startY, 60) < 0) {
					if (i < seedRocks) {
						addRock(startX, startY);
						break;
					}
					if ((checkRock(startX, startY, 200, false) >= 0 && checkRock(startX, startY, 40, false) < 0) || random.nextFloat() < 0.01){
						addRock(startX, startY);
						break;
					}
				}
			}
		}
		rocks.sort(Game.FloatbyY);
		System.out.println("Rocks Set!");

		// Set wolve positions
		wolves.clear();
		for (int i = 0; i < nWolves; i++) {
			while (true) {
				float startX = worldX * random.nextFloat();
				float startY = worldY * random.nextFloat();
				if (checkLake(startX, startY, -5) < 0) {
					wolves.add(new Point2D.Float(startX, startY));
					wolveSpeed.add(new Point2D.Float(random.nextFloat()-(float)0.5, random.nextFloat()-(float)0.5));
					break;
				}
			}
		}
		System.out.println("Wolves Set!");
		
		// Set rabbits		
		rabbits.clear();
		for (int i = 0; i < nRabbits; i++) {
			while (true) {
				float startX = worldX * random.nextFloat();
				float startY = worldY * random.nextFloat();
				if (checkLake(startX, startY, -5) < 0) {
					rabbits.add(new Point2D.Float(startX, startY));
					rabbitVel.add(new Point2D.Float(2*(random.nextFloat()-(float)0.5), 2*(random.nextFloat()-(float)0.5)));
					rabbitStats.add(true);
					break;
				}
			}
		}
		System.out.println("Rabbits Set!");
		
		// Set fishes
		fishes.clear();
		for (int i = 0; i < nFishes; i++) {
			while (true) {
				float startX = worldX * random.nextFloat();
				float startY = worldY * random.nextFloat();
				if (checkLake(startX, startY, 20) >= 0 ) {
					fishes.add(new Point2D.Float(startX, startY));
					fishVel.add(new Point2D.Float(2*(random.nextFloat()-(float)0.5), 2*(random.nextFloat()-(float)0.5)));
					fishStats.add(true);
					break;
				}
			}
		}
		System.out.println("Fishes set!");
		
		// Set lilies
		seedLilies = 20;
		pLily1 = (float) 0.4;
		pLily2 = (float) 0.4;
		pLily3 = (float) 0.2;
		
		lilies.clear();
		for (int i = 0; i < nLilies; i++) {
			while (true) {
				int startX = random.nextInt(worldX);
				int startY = random.nextInt(worldY);
				if (checkLake(startX, startY, 20) >= 0) {
					if (i < seedLilies) {
						addLily(startX, startY);
						break;
					}
					if (checkLily(startX, startY, 40) >= 0 || random.nextFloat() < 0.01){
						addLily(startX, startY);
						break;
					}
				}
			}
		}
		lilies.sort(Game.FloatbyY);
		System.out.println("Lilies Set!");
		
		// Set reeds
		reeds.clear();
		for (int i = 0; i < nReeds; i++) {
			while (true) {
				int startX = random.nextInt(worldX);
				int startY = random.nextInt(worldY);
				if (checkLake(startX, startY, 20) >= 0 && checkLake(startX, startY, 80) < 0) {
					reeds.add(new Point2D.Float(startX, startY));
					break;
				}
			}
		}
		reeds.sort(Game.FloatbyY);
		System.out.println("Reeds Set!");
	}
	
	public static void updateItems() {
		// update fire burning down
		if (Game.tick % 30 == 0 && random.nextFloat() < 0.5) { 
			for (int i = 0; i < World.craftables.size(); i++) {
				if (craftableType.get(i) == 1) {
					craftableScore.set(i, Math.max(craftableScore.get(i) - 1, 0));
				}
			}
		}
	}
	
	public static void spawnItems() {
		
		// just spawn items every 100 ticks
		if (Game.tick % 100 != 0) { return; }
		
		// grow berries in bushes
		for (int i = 0; i < nBerries; i++) {
			if (berryStats.get(i) == false && random.nextFloat() < berryRespawn)
				berryStats.set(i, true);
		}
		
		// spawn wood in world
		if (random.nextFloat() < woodSpawn) {
			nWoods += 1;
			while (true) {
				int startX = random.nextInt(worldX);
				int startY = random.nextInt(worldY);
				if (checkLake(startX, startY, -5) < 0 && checkTree(startX, startY, rWood) >= 0) {
					woods.add(new Point2D.Float(startX, startY));
					if (random.nextFloat() < 0.5)
						woodStats.add(true);
					else
						woodStats.add(false);
					break;
				}
			}
			woods.sort(Game.FloatbyY);
		}
		
		// spawn stone in world
		if (random.nextFloat() < stoneSpawn) {
			nStones += 1;
			while (true) {
				int startX = random.nextInt(worldX);
				int startY = random.nextInt(worldY);
				if (checkLake(startX, startY, -5) < 0 && checkRock(startX, startY, 300, false) >= 0 && checkRock(startX, startY, 30, false) < 0) {
					stones.add(new Point2D.Float(startX, startY));
					break;
				}
			}
			stones.sort(Game.FloatbyY);
		}
		
		// spawn new rabbits
		if (random.nextFloat() < rabbitSpawn) {
			for (int i = 0; i < rabbits.size(); i++) {
				if (rabbitStats.get(i) == false) {
					rabbitStats.set(i, true);
					while (true) {
						float startX = random.nextInt(worldX);
						float startY = random.nextInt(worldY);
						if (checkLake(startX, startY, -5) < 0 && checkRock(startX, startY, 40, true) < 0) {
							rabbits.set(i, new Point2D.Float(startX, startY));
							break;
						}
					}
					break;
				}
			}
		}
		
		// spawn new fishes
		if (random.nextFloat() < fishSpawn) {
			for (int i = 0; i < fishes.size(); i++) {
				if (fishStats.get(i) == false) {
					fishStats.set(i, true);
					while (true) {
						float startX = random.nextInt(worldX);
						float startY = random.nextInt(worldY);
						if (checkLake(startX, startY, 20) >= 0) {
							fishes.set(i, new Point2D.Float(startX, startY));
							break;
						}
					}
					break;
				}
			}
		}
	}
	
	public static void addCraftable(int dis) {

		if (Game.player.getDirection() == "Up")
			craftables.add(new Point2D.Float((int) Game.player.getX(), (int) Game.player.getY() - dis));
		else if (Game.player.getDirection() == "Down")
			craftables.add(new Point2D.Float((int) Game.player.getX(), (int) Game.player.getY() + dis));
		else if (Game.player.getDirection() == "Left")
			craftables.add(new Point2D.Float((int) Game.player.getX() - dis, (int) Game.player.getY()));
		else
			craftables.add(new Point2D.Float((int) Game.player.getX() + dis, (int) Game.player.getY()));
	}

	public static void removeCraftable(int idx) {
		craftables.remove(idx);
		craftableType.remove(idx);
		craftableScore.remove(idx);
	}
	
	public static int checkItem(float x, float y, float r, ArrayList<Point2D.Float> array) {
		for (int i = 0; i < array.size(); i++) {
			float dis_x = array.get(i).x - x;
			float dis_y = array.get(i).y - y;
			if (Math.sqrt(dis_x * dis_x + dis_y * dis_y) < r) {
				return i;
			}
		}
		return -1;
	}
	
	public static int checkWolve(float x, float y) { return checkItem(x, y, Wildlife.wolveRadius, wolves); }
	
	public static int checkBerry(float x, float y) { return checkItem(x, y, 20, berries); }
	
	public static int checkWood(float x, float y) { return checkItem(x, y, 20, woods); }

	public static int checkStone(float x, float y) { return checkItem(x, y, 20, stones); }
	
	public static int checkTree(float x, float y, int r) { return checkItem(x, y, r, trees); }

	public static int checkPlant(float x, float y, int r) { return checkItem(x, y, r, plants); }
	
	public static int checkLily(float x, float y, int r) { return checkItem(x, y, r, lilies); }
	
	public static int checkLake(float x, float y, float r) {
		for (int i = 0; i < nLakes; i++) {
			float dis_x = lakes.get(i).x - x;
			float dis_y = lakes.get(i).y - y;		
			if (Math.sqrt(dis_x * dis_x + 4 * dis_y * dis_y) < radiusLakes.get(i) - r) {
				return i;
			}
		}
		return -1;
	}
	
	public static int checkRock(float x, float y, int r, boolean collide) {
		float dis_x = 0, dis_y = 0;
		for (int i = 0; i < rocks.size(); i++) {
			if (collide == true) {
				dis_x = rocks.get(i).x - x;
				dis_y = 5 * (rocks.get(i).y - 5 - y);
			}
			else {
				dis_x = rocks.get(i).x - x;
				dis_y = rocks.get(i).y - y;
			}
			if (Math.sqrt(dis_x * dis_x + dis_y * dis_y) < r) {
				return i;
			}
		}
		return -1;
	}

	public static int checkCraftable(float x, float y) {
		for (int i = 0; i < craftables.size(); i++) {
			float dis_x = craftables.get(i).x - x;
			float dis_y = craftables.get(i).y - y;
			float r = 1;
			if (craftableType.get(i) == 1)
				r = 25;
			if (craftableType.get(i) == 2)
				r = 20;
			if (craftableType.get(i) == 4)
				r = 45;
			if (Math.sqrt(dis_x * dis_x + 20 * dis_y * dis_y) < r)
				return i;
		}
		return -1;
	}

	public static void addTree(int x, int y) {
		
		trees.add(new Point2D.Float(x, y));
		float pCheck = random.nextFloat();
		if (pCheck < pPine1)
			treeType.add(0);
		else if (pCheck < pPine1 + pPine2)
			treeType.add(1);
		else if (pCheck < pPine1 + pPine2 + pFir1)
			treeType.add(2);
		else if (pCheck < pPine1 + pPine2 + pFir1 + pFir2)
			treeType.add(3);
		else
			treeType.add(4);
		if (random.nextFloat() < pDeath)
			treeDeath.add(true);
		else
			treeDeath.add(false);
	}
	
	public static void addPlant(int x, int y) {
		
		plants.add(new Point2D.Float(x, y));
		float pCheck = random.nextFloat();
		if (pCheck < pPlant1)
			plantType.add(1);
		else if (pCheck < pPlant1 + pPlant2)
			plantType.add(2);
		else if (pCheck < pPlant1 + pPlant2 + pPlant3)
			plantType.add(3);
		else
			plantType.add(4);
	}
	
	public static void addLily(int x, int y) {
		
		lilies.add(new Point2D.Float(x, y));
		float pCheck = random.nextFloat();
		if (pCheck < pLily1)
			lilyType.add(1);
		else if (pCheck < pLily1 + pLily2)
			lilyType.add(2);
		else
			lilyType.add(3);
	}
	
	public static void addRock(int x, int y) {
		
		rocks.add(new Point2D.Float(x, y));
		float pCheck = random.nextFloat();
		if (pCheck < pRock1)
			rockType.add(1);
		else
			rockType.add(2);
	}
}
