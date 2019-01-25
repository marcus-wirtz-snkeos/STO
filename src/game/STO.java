package game;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;

public class STO implements ActionListener, KeyListener {
	
	boolean[] keys = new boolean[222];
	
	public JFrame jframe;
	public RenderPanel renderPanel;
	public Timer timer = new Timer(1, this);
	GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	public Dimension dim = new Dimension(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());

	public static final int worldSize = 4;
	public float dayLength;
	public int worldX, worldY;
	public int tick = 0, score, craftTime, harvestTime, cookTime, berryBonus, rWood, rStone;
	public int seedForests, seedRocks, seedPlants, seedLilies;
	public float changeSpeed, chase, maxSpeed, changeDirection, wolveRadius, wolveAggression, wolveDriftRadius;
	public float rabbitChange, rabbitFlee, rabbitSpeed, rabbitRadius;
	public float fishChange, fishFlee, fishSpeed, fishRadius;
	public int condition, tired, hungry, thirsty;
	public int woodCollected, stoneCollected, leaveCollected, lianaCollected, berryCollected, meatCollected, rawMeatCollected, fishCollected;
	public int nLakes, nWolves, nRabbits, nFishes, nBerries, nWoods, nRocks, nStones, nTrees, nPlants, nLilies, nReeds;
	public float pPine1, pPine2, pFir1, pFir2, pTree, pDeath, pPlant1, pPlant2, pPlant3, pPlant4, pRock1, pRock2, pLily1, pLily2, pLily3;
	public float berryRespawn, woodSpawn, stoneSpawn, rabbitSpawn, fishSpawn;
	public float pTired, playerMovement;
	public boolean over, craft, cook, harvest, hidden;
	
	
	public ArrayList<Point2D.Float> wolves = new ArrayList<Point2D.Float>();
	public ArrayList<Point2D.Float> wolveSpeed = new ArrayList<Point2D.Float>();
	public ArrayList<Point2D.Float> rabbits = new ArrayList<Point2D.Float>();
	public ArrayList<Point2D.Float> rabbitVel = new ArrayList<Point2D.Float>();
	public ArrayList<Boolean> rabbitStats = new ArrayList<Boolean>();
	public ArrayList<Point2D.Float> fishes = new ArrayList<Point2D.Float>();
	public ArrayList<Point2D.Float> fishVel = new ArrayList<Point2D.Float>();
	public ArrayList<Boolean> fishStats = new ArrayList<Boolean>();
	public ArrayList<Point> lakes = new ArrayList<Point>();
	public ArrayList<Integer> radiusLakes = new ArrayList<Integer>();
	public ArrayList<Point> berries = new ArrayList<Point>();
	public ArrayList<Boolean> berryStats = new ArrayList<Boolean>();
	public ArrayList<Point> woods = new ArrayList<Point>();
	public ArrayList<Point> stones = new ArrayList<Point>();
	public ArrayList<Boolean> woodStats = new ArrayList<Boolean>();
	public ArrayList<Point> trees = new ArrayList<Point>();
	public ArrayList<Integer> treeType = new ArrayList<Integer>();
	public ArrayList<Point> rocks = new ArrayList<Point>();
	public ArrayList<Integer> rockType = new ArrayList<Integer>();
	public ArrayList<Boolean> treeDeath = new ArrayList<Boolean>();
	public ArrayList<Point> plants = new ArrayList<Point>();
	public ArrayList<Integer> plantType = new ArrayList<Integer>();
	public ArrayList<Point> lilies = new ArrayList<Point>();
	public ArrayList<Integer> lilyType = new ArrayList<Integer>();
	public ArrayList<Point> reeds = new ArrayList<Point>();
	public ArrayList<Point> craftables = new ArrayList<Point>();
	public ArrayList<Integer> craftableType = new ArrayList<Integer>();
	public ArrayList<Boolean> craftableStat = new ArrayList<Boolean>();
	public Point2D.Float player = new Point2D.Float(0, 0);
	public String direction = new String();
	
	int nCraft = 4, craftableAction = 1;
	boolean[] craftStats = new boolean[nCraft];
	
	public Random random;
	
	public static STO sto;
	
	public STO(){
		jframe = new JFrame("Survive the outdoors");
		jframe.setSize(dim.width, dim.height);
		jframe.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		jframe.setUndecorated(true);
		jframe.setVisible(true);
		jframe.add(renderPanel = new RenderPanel());
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.addKeyListener(this);
		startGame();
	}
	
	public void startGame() {
		
		System.out.println("Game startet!");
		
		random = new Random();
		over = false;
		craft = false;
		cook = false;
		harvest = false;
		hidden = false;
		
		// Set initial stats
		score = 0;
		harvestTime = 200;
		cookTime = 300;
		craftTime = 400;
		
		condition = 100;
		tired = 100;
		hungry = 100;
		thirsty = 100;
		
		pTired = (float) 0.001;
		playerMovement = (float) 3;
		
		// Set inventory
		woodCollected = 30;
		stoneCollected = 20;
		leaveCollected = 20;
		lianaCollected = 20;
		berryCollected = 10;
		meatCollected = 0;
		rawMeatCollected = 1;
		fishCollected = 1;
		
		// Set craftables
		craftables.clear();
		craftableType.clear();
		craftableStat.clear();
		
		for (int i = 0; i < nCraft; i++)
			craftStats[i] = false;
		
		// Set environment
		worldX = worldSize * dim.width;
		worldY = worldSize * dim.height;
		dayLength = 5000;
		
		nLakes = 50;
		int minLake = 30, maxLake = 400;
		lakes.clear();
		for (int i = 0; i < nLakes; i++) {
			lakes.add(new Point(random.nextInt(worldX), random.nextInt(worldY)));
			radiusLakes.add(random.nextInt((maxLake - minLake) + 1) + minLake);
		}
		System.out.println("Lakes Set!");
		
		// Set berries
		nBerries = 60;
		berryBonus = 25;
		berryRespawn = (float) 0.0001;
		berries.clear();
		for (int i = 0; i < nBerries; i++) {
			boolean looping = true;
			while (looping == true) {
				int startX = random.nextInt(worldX);
				int startY = random.nextInt(worldY);
				if (checkLake(startX, startY, -5) == false) {
					berries.add(new Point(startX, startY));
					berryStats.add(true);
					looping = false;
				}
			}
		}
		berries.sort(byY);
		
		// Set trees
		nTrees = 2000;
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
			System.out.println("Tree" + i);
			while (true) {
				int startX = random.nextInt(worldX);
				int startY = random.nextInt(worldY);
				if (checkLake(startX, startY, -5) == false) {
					if (i < seedForests) {
						addTree(startX, startY);
						break;
					}
					else {
						if ((checkTree(startX, startY, 100) == true && checkTree(startX, startY, 30) == false) || random.nextFloat() < 0.01){
							addTree(startX, startY);
							break;
						}
					}
				}
			}
		}
		trees.sort(byY);
		System.out.println("Trees Set!");
		
		// Set plants
		nPlants = 500;
		seedPlants = 10;
		pPlant1 = (float) 0.6;
		pPlant2 = (float) 0.35;
		pPlant3 = (float) 0.04;
		pPlant4 = (float) 0.01;
		
		plants.clear();
		for (int i = 0; i < nPlants; i++) {
			boolean looping = true;
			while (looping == true) {
				int startX = random.nextInt(worldX);
				int startY = random.nextInt(worldY);
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
		plants.sort(byY);
		System.out.println("Plants Set!");
		
		// Set wood
		nWoods = 20;
		rWood = 40;
		woodSpawn = (float) 0.005;
		
		woods.clear();
		for (int i = 0; i < nWoods; i++) {
			boolean looping = true;
			while (looping == true) {
				int startX = random.nextInt(worldX);
				int startY = random.nextInt(worldY);
				if (checkLake(startX, startY, -5) == false && checkTree(startX, startY, rWood) == true) {
					woods.add(new Point(startX, startY));
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
		nRocks = 80;
		seedRocks = 5;
		pRock1 = (float) 0.7;
		pRock2 = (float) 0.3;
		
		rocks.clear();
		for (int i = 0; i < nRocks; i++) {
			boolean looping = true;
			while (looping == true) {
				int startX = random.nextInt(worldX);
				int startY = random.nextInt(worldY);
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
		rocks.sort(byY);
		System.out.println("Wocks Set!");

		// Set stones
		nStones = 15;
		rStone = 40;
		stoneSpawn = (float) 0.001;
		
		stones.clear();
		for (int i = 0; i < nStones; i++) {
			boolean looping = true;
			while (looping == true) {
				int startX = random.nextInt(worldX);
				int startY = random.nextInt(worldY);
				if (checkLake(startX, startY, -5) == false && (checkRock(startX, startY, 300, false) || random.nextFloat() < 0.05)) {
					stones.add(new Point(startX, startY));
					looping = false;
				}
			}
		}		
		stones.sort(byY);
		System.out.println("Stones Set!");

		// Set wolve behaviour
		nWolves = 10;
		changeSpeed = (float) 0.1;
		maxSpeed = (float) 3;
		changeDirection = (float) 0.05;
		wolveRadius = 500; 
		wolveDriftRadius = 20 * wolveRadius; 
		wolveAggression = (float) 0.5;
		chase = (float) 0.8;
		
		wolves.clear();
		for (int i = 0; i < nWolves; i++) {
			while (true) {
				float startX = worldX * random.nextFloat();
				float startY = worldY * random.nextFloat();
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
		rabbitSpeed = (float) 4;
		rabbitChange = (float) 0.1;
		rabbitFlee = (float) 0.5;
		rabbitRadius = 200;
		rabbitSpawn = (float) 0.001;
		
		rabbits.clear();
		for (int i = 0; i < nRabbits; i++) {
			while (true) {
				float startX = worldX * random.nextFloat();
				float startY = worldY * random.nextFloat();
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
		fishSpeed = (float) 0.5;
		fishChange = (float) 0.1;
		fishFlee = (float) 0.8;
		fishRadius = 100;
		fishSpawn = (float) 0.001;
		
		fishes.clear();
		for (int i = 0; i < nFishes; i++) {
			while (true) {
				float startX = worldX * random.nextFloat();
				float startY = worldY * random.nextFloat();
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
		nLilies = 500;
		seedLilies = 20;
		pLily1 = (float) 0.4;
		pLily2 = (float) 0.4;
		pLily3 = (float) 0.2;
		
		lilies.clear();
		for (int i = 0; i < nLilies; i++) {
			while (true) {
				int startX = random.nextInt(worldX);
				int startY = random.nextInt(worldY);
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
		lilies.sort(byY);
		System.out.println("Lilies Set!");
		
		// Set reeds
		nReeds = 500;
		reeds.clear();
		for (int i = 0; i < nReeds; i++) {
			while (true) {
				int startX = random.nextInt(worldX);
				int startY = random.nextInt(worldY);
				if (checkLake(startX, startY, 20) == true && checkLake(startX, startY, 80) == false) {
					reeds.add(new Point(startX, startY));
					break;
				}
			}
		}
		reeds.sort(byY);
		System.out.println("Reeds Set!");
		
		// Set player start
		direction = "Down";
		while (true) {
			int startX = random.nextInt(worldX / 3) + worldX / 3;
			int startY = random.nextInt(worldY / 3) + worldY / 3;
			player = new Point2D.Float(startX, startY);
			if ((whichWolve(startX, startY) < 0 && checkLake(startX, startY, -5) == false))
				break;
		}
		timer.start();	
	}
	
	public void actionPerformed(ActionEvent arg0) {
		
		tick++;
		
		if (over == true)
			timer.stop();
		
		// Wildlife settings
		Wildlife.wolveBehaviour();
		Wildlife.rabbitBehaviour();
		Wildlife.fishBehaviour();
		
		// Update game
		updateGame.spawnItems();
		updateGame.collectItems();
		updateGame.stats();
		updateGame.cooking();
		updateGame.harvesting();
		updateGame.crafting();
		updateGame.hideShelter();
		updateGame.searching();
		updateGame.move();

		// Draw world
		renderPanel.repaint();
	}
	
	public int whichWolve(float x, float y) {
		for (int i = 0; i < nWolves; i++) {
			float dis_x = wolves.get(i).x - x;
			float dis_y = wolves.get(i).y - y;
			if (Math.sqrt(dis_x * dis_x + dis_y * dis_y) <= wolveRadius) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean checkLake(float x, float y, float r) {
		for (int i = 0; i < nLakes; i++) {
			float dis_x = lakes.get(i).x - x;
			float dis_y = lakes.get(i).y - y;		
			if (Math.sqrt(dis_x * dis_x + 4 * dis_y * dis_y) < radiusLakes.get(i) - r) {
				return true;
			}
		}
		return false;
	}
	
	public int checkBerry(float x, float y) {
		for (int i = 0; i < nBerries; i++) {
			float dis_x = berries.get(i).x - x;
			float dis_y = berries.get(i).y - y;		
			if (Math.sqrt(dis_x * dis_x + dis_y * dis_y) < 20) {
				return i;
			}
		}
		return -1;
	}
	
	public int checkWood(float x, float y) {
		for (int i = 0; i < nWoods; i++) {
			float dis_x = woods.get(i).x - x;
			float dis_y = woods.get(i).y - y;		
			if (Math.sqrt(dis_x * dis_x + dis_y * dis_y) < 20) {
				return i;
			}
		}
		return -1;
	}
	
	public int checkStone(float x, float y) {
		for (int i = 0; i < nStones; i++) {
			float dis_x = stones.get(i).x - x;
			float dis_y = stones.get(i).y - y;		
			if (Math.sqrt(dis_x * dis_x + dis_y * dis_y) < 20) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean checkTree(float x, float y, int r) {
		for (int i = 0; i < trees.size(); i++) {
			float dis_x = trees.get(i).x - x;
			float dis_y = trees.get(i).y - y;		
			if (Math.sqrt(dis_x * dis_x + dis_y * dis_y) < r) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkPlant(float x, float y, int r) {
		for (int i = 0; i < plants.size(); i++) {
			float dis_x = plants.get(i).x - x;
			float dis_y = plants.get(i).y - y;		
			if (Math.sqrt(dis_x * dis_x + dis_y * dis_y) < r) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkLily(float x, float y, int r) {
		for (int i = 0; i < lilies.size(); i++) {
			float dis_x = lilies.get(i).x - x;
			float dis_y = lilies.get(i).y - y;		
			if (Math.sqrt(dis_x * dis_x + dis_y * dis_y) < r) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkRock(float x, float y, int r, boolean collide) {
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
	
	public void addTree(int x, int y) {
		
		trees.add(new Point(x, y));
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
	
	public void addPlant(int x, int y) {
		
		plants.add(new Point(x, y));
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
	
	public void addLily(int x, int y) {
		
		lilies.add(new Point(x, y));
		float pCheck = random.nextFloat();
		if (pCheck < pLily1)
			lilyType.add(1);
		else if (pCheck < pLily1 + pLily2)
			lilyType.add(2);
		else
			lilyType.add(3);
	}
	
	public void addRock(int x, int y) {
		
		rocks.add(new Point(x, y));
		float pCheck = random.nextFloat();
		if (pCheck < pRock1)
			rockType.add(1);
		else
			rockType.add(2);
	}
	
	public static void main(String[] args){
		sto = new STO();
	}
	
	public void keyPressed(KeyEvent e) {
		// eating berries
		if (e.getKeyCode() == 69 && berryCollected > 0) {
			berryCollected -= 1;
			hungry = Math.min(100, hungry + 5);
		}
		if (e.getKeyCode() == 82 && meatCollected > 0) {
			meatCollected -= 1;
			hungry = Math.min(100, hungry + 40);
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (sto.craft == true) {
				int idx = sto.craftables.size() - 1;
				sto.craftables.remove(idx);
				sto.craftableType.remove(idx);
				sto.craftableStat.remove(idx);
			}
			craft = false;
			craftTime = 400;
			cook = false;
			cookTime = 300;
			harvest = false;
			harvestTime = 200;
		}
		
	    keys[e.getKeyCode()] = true;
		if (over == true && keys[KeyEvent.VK_R])
			startGame();	
	}

	public void keyReleased(KeyEvent e) {
	    keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {
	}
	
	Comparator<Point> byY = new Comparator<Point>() {
		public int compare(Point p1, Point p2) {
			return Integer.compare(p1.y, p2.y);
		}
	};
	
	Comparator<Point2D.Float> FloatbyY = new Comparator<Point2D.Float>() {
		public int compare(Point2D.Float p1, Point2D.Float p2) {
			return Float.compare(p1.y, p2.y);
		}
	};
}
