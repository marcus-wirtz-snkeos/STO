package main;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public class World {
	
	static Random random =  new Random();

	public static int nWolves, nRabbits, nFishes;
	public static int nBerries, nLakes, nRocks, nWoods, nStones, nTrees, nPlants, nLilies, nReeds;
	public static float pPine1, pPine2, pFir1, pFir2, pTree, pDeath, pPlant1, pPlant2, pPlant3, pPlant4, pRock1, pRock2, pLily1, pLily2, pLily3;
	
	public static int berryBonus;
	public static int rWood, rStone;
	public static int seedForests, seedRocks, seedPlants, seedLilies;
	public static float berryRespawn, woodSpawn, stoneSpawn, rabbitSpawn, fishSpawn;


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
	public static ArrayList<Point2D.Float> stones = new ArrayList<Point2D.Float>();
	public static ArrayList<Boolean> woodStats = new ArrayList<Boolean>();
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
		// Set craftables
		/*
		craftables.clear();
		craftableType.clear();
		craftableScore.clear();
		
		nLakes = 20;
		int minLake = 30, maxLake = 400;
		lakes.clear();
		for (int i = 0; i < nLakes; i++) {
			lakes.add(new Point2D.Float(random.nextInt(Game.worldX), random.nextInt(Game.worldY)));
			radiusLakes.add(random.nextInt((maxLake - minLake) + 1) + minLake);
		}
		System.out.println("Lakes Set!");
		
		// Set berries
		nBerries = 30;
		berryBonus = 25;
		berryRespawn = (float) 0.0001;
		berries.clear();
		for (int i = 0; i < nBerries; i++) {
			boolean looping = true;
			while (looping == true) {
				int startX = random.nextInt(Game.worldX);
				int startY = random.nextInt(Game.worldY);
				if (checkLake(startX, startY, -5) == false) {
					berries.add(new Point2D.Float(startX, startY));
					berryStats.add(true);
					looping = false;
				}
			}
		}
		berries.sort(Game.FloatbyY);
		
		*/
		// Set trees
		nTrees = 40;
		seedForests = 20;
		pPine1 = (float) 0.3;
		pPine2 = (float) 0.2;
		pFir1 = (float) 0.2;
		pFir2 = (float) 0.2;
		pTree = (float) 0.1;
		pDeath = (float) 0.1;
		
		trees.clear();
		System.out.println("Berries Set!");
		for (int i = 0; i < nTrees; i++) {
			int startX = random.nextInt(Game.worldX);
			int startY = random.nextInt(Game.worldY);
			addTree(startX, startY);
		}
		trees.sort(Game.FloatbyY);
		System.out.println("Trees Set!");
	}
		/*
		
		// Set plants
		nPlants = 200;
		seedPlants = 10;
		pPlant1 = (float) 0.6;
		pPlant2 = (float) 0.35;
		pPlant3 = (float) 0.04;
		pPlant4 = (float) 0.01;
		
		plants.clear();
		for (int i = 0; i < nPlants; i++) {
			boolean looping = true;
			while (looping == true) {
				int startX = random.nextInt(Game.worldX);
				int startY = random.nextInt(Game.worldY);
				if (checkLake(startX, startY, -5) == false) {
					if (i < seedPlants) {
						addPlant(startX, startY);
						looping = false;
					}
					else {
						if ((checkPlant(startX, startY, 100) == true && checkTree(startX, startY, 30) == false) || random.nextFloat() < 0.01){
							addPlant(startX, startY);
							looping = false;
						}
					}
				}
			}
		}
		plants.sort(Game.FloatbyY);
		System.out.println("Plants Set!");
		
		// Set wood
		nWoods = 20;
		rWood = 40;
		woodSpawn = (float) 0.005;
		
		woods.clear();
		for (int i = 0; i < nWoods; i++) {
			boolean looping = true;
			while (looping == true) {
				int startX = random.nextInt(Game.worldX);
				int startY = random.nextInt(Game.worldY);
				if (checkLake(startX, startY, -5) == false && checkTree(startX, startY, rWood) == true) {
					woods.add(new Point2D.Float(startX, startY));
					if (random.nextFloat() < 0.5)
						woodStats.add(true);
					else
						woodStats.add(false);
					looping = false;
				}
			}
		}
		System.out.println("Wood Set!");
		
		// Set rocks
		nRocks = 40;
		seedRocks = 4;
		pRock1 = (float) 0.7;
		pRock2 = (float) 0.3;
		
		rocks.clear();
		for (int i = 0; i < nRocks; i++) {
			boolean looping = true;
			while (looping == true) {
				int startX = random.nextInt(Game.worldX);
				int startY = random.nextInt(Game.worldY);
				if (checkLake(startX, startY, -5) == false && checkTree(startX, startY, 60) == false && checkPlant(startX, startY, 60) == false) {
					if (i < seedRocks) {
						addRock(startX, startY);
						looping = false;
					}
					else {
						if ((checkRock(startX, startY, 200, false) == true && checkRock(startX, startY, 40, false) == false) || random.nextFloat() < 0.01){
							addRock(startX, startY);
							looping = false;
						}
					}
				}
			}
		}
		rocks.sort(Game.FloatbyY);
		System.out.println("Wocks Set!");

		// Set stones
		nStones = 20;
		rStone = 40;
		stoneSpawn = (float) 0.001;
		
		stones.clear();
		for (int i = 0; i < nStones; i++) {
			boolean looping = true;
			while (looping == true) {
				int startX = random.nextInt(Game.worldX);
				int startY = random.nextInt(Game.worldY);
				if (checkLake(startX, startY, -5) == false && (checkRock(startX, startY, 300, false) || random.nextFloat() < 0.05)) {
					stones.add(new Point2D.Float(startX, startY));
					looping = false;
				}
			}
		}		
		stones.sort(Game.FloatbyY);
		System.out.println("Stones Set!");

		// Set wolve behaviour

		nWolves = 5;
		wolves.clear();
		for (int i = 0; i < nWolves; i++) {
			while (true) {
				float startX = Game.worldX * random.nextFloat();
				float startY = Game.worldY * random.nextFloat();
				if (checkLake(startX, startY, -5) == false) {
					wolves.add(new Point2D.Float(startX, startY));
					wolveSpeed.add(new Point2D.Float(random.nextFloat()-(float)0.5, random.nextFloat()-(float)0.5));
					break;
				}
			}
		}
		System.out.println("Wolves Set!");
		
		// Set rabbits
		nRabbits = 20;
		rabbitSpawn = (float) 0.001;
		
		rabbits.clear();
		for (int i = 0; i < nRabbits; i++) {
			while (true) {
				float startX = Game.worldX * random.nextFloat();
				float startY = Game.worldY * random.nextFloat();
				if (checkLake(startX, startY, -5) == false) {
					rabbits.add(new Point2D.Float(startX, startY));
					rabbitVel.add(new Point2D.Float(2*(random.nextFloat()-(float)0.5), 2*(random.nextFloat()-(float)0.5)));
					rabbitStats.add(true);
					break;
				}
			}
		}
		System.out.println("Rabbits Set!");
		
		// Set fishes
		nFishes = 50;
		fishSpawn = (float) 0.001;
		
		fishes.clear();
		for (int i = 0; i < nFishes; i++) {
			while (true) {
				float startX = Game.worldX * random.nextFloat();
				float startY = Game.worldY * random.nextFloat();
				if (checkLake(startX, startY, 20) == true) {
					fishes.add(new Point2D.Float(startX, startY));
					fishVel.add(new Point2D.Float(2*(random.nextFloat()-(float)0.5), 2*(random.nextFloat()-(float)0.5)));
					fishStats.add(true);
					break;
				}
			}
		}
		System.out.println("Fishes set!");

		
		// Set lilies
		nLilies = 200;
		seedLilies = 20;
		pLily1 = (float) 0.4;
		pLily2 = (float) 0.4;
		pLily3 = (float) 0.2;
		
		lilies.clear();
		for (int i = 0; i < nLilies; i++) {
			while (true) {
				int startX = random.nextInt(Game.worldX);
				int startY = random.nextInt(Game.worldY);
				if (checkLake(startX, startY, 20) == true) {
					if (i < seedLilies) {
						addLily(startX, startY);
						break;
					}
					else {
						if (checkLily(startX, startY, 40) == true || random.nextFloat() < 0.01){
							addLily(startX, startY);
							break;
						}
					}
				}
			}
		}
		lilies.sort(Game.FloatbyY);
		System.out.println("Lilies Set!");
		
		// Set reeds
		nReeds = 200;
		reeds.clear();
		for (int i = 0; i < nReeds; i++) {
			while (true) {
				int startX = random.nextInt(Game.worldX);
				int startY = random.nextInt(Game.worldY);
				if (checkLake(startX, startY, 20) == true && checkLake(startX, startY, 80) == false) {
					reeds.add(new Point2D.Float(startX, startY));
					break;
				}
			}
		}
		reeds.sort(Game.FloatbyY);
		System.out.println("Reeds Set!");
	}
	
	/*
	public static int whichWolve(float x, float y) {
		for (int i = 0; i < nWolves; i++) {
			float dis_x = wolves.get(i).x - x;
			float dis_y = wolves.get(i).y - y;
			if (Math.sqrt(dis_x * dis_x + dis_y * dis_y) <= Wildlife.wolveRadius) {
				return i;
			}
		}
		return -1;
	}
	
	public static boolean checkLake(float x, float y, float r) {
		for (int i = 0; i < nLakes; i++) {
			float dis_x = lakes.get(i).x - x;
			float dis_y = lakes.get(i).y - y;		
			if (Math.sqrt(dis_x * dis_x + 4 * dis_y * dis_y) < radiusLakes.get(i) - r) {
				return true;
			}
		}
		return false;
	}
	
	public static int checkBerry(float x, float y) {
		for (int i = 0; i < nBerries; i++) {
			float dis_x = berries.get(i).x - x;
			float dis_y = berries.get(i).y - y;		
			if (Math.sqrt(dis_x * dis_x + dis_y * dis_y) < 20) {
				return i;
			}
		}
		return -1;
	}
	
	public static int checkWood(float x, float y) {
		for (int i = 0; i < nWoods; i++) {
			float dis_x = woods.get(i).x - x;
			float dis_y = woods.get(i).y - y;		
			if (Math.sqrt(dis_x * dis_x + dis_y * dis_y) < 20) {
				return i;
			}
		}
		return -1;
	}
	
	public static int checkStone(float x, float y) {
		for (int i = 0; i < nStones; i++) {
			float dis_x = stones.get(i).x - x;
			float dis_y = stones.get(i).y - y;		
			if (Math.sqrt(dis_x * dis_x + dis_y * dis_y) < 20) {
				return i;
			}
		}
		return -1;
	}
	
	public static boolean checkTree(float x, float y, int r) {
		for (int i = 0; i < trees.size(); i++) {
			float dis_x = trees.get(i).x - x;
			float dis_y = trees.get(i).y - y;		
			if (Math.sqrt(dis_x * dis_x + dis_y * dis_y) < r) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean checkPlant(float x, float y, int r) {
		for (int i = 0; i < plants.size(); i++) {
			float dis_x = plants.get(i).x - x;
			float dis_y = plants.get(i).y - y;		
			if (Math.sqrt(dis_x * dis_x + dis_y * dis_y) < r) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean checkLily(float x, float y, int r) {
		for (int i = 0; i < lilies.size(); i++) {
			float dis_x = lilies.get(i).x - x;
			float dis_y = lilies.get(i).y - y;		
			if (Math.sqrt(dis_x * dis_x + dis_y * dis_y) < r) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean checkRock(float x, float y, int r, boolean collide) {
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
				return true;
			}
		}
		return false;
	}

	public static boolean checkCraftable(float x, float y) {
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
				return true;
		}
		return false;
	}
	*/
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
	/*
	
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
	*/
}
