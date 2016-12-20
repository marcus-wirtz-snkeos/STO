package game;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.JFrame;	// Modul um Fenster zu erzeugen
import javax.swing.Timer;

public class STO implements ActionListener, KeyListener {
	
	boolean[] keys = new boolean[222];
	
	public JFrame jframe;
	public RenderPanel renderPanel;
	public Timer timer = new Timer(1, this);
	public Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int worldSize = 4;
	public float dayLength;
	public int worldX, worldY;
	public int tick = 0, score, berryBonus, rWood, seedForests;
	public float changeSpeed, chase, maxSpeed, changeDirection, wolveRadius, wolveAggression, wolveDriftRadius;
	public int condition, tired, hungry, thirsty;
	public int woodCollected;
	public int nLakes, nWolves, nBerries, nWoods, nTrees;
	public float pPine1, pPine2, pFir, pTree, pDeath;
	public float berryRespawn;
	public float pTired, playerMovement;
	public boolean over, run;
	
	public ArrayList<Point2D.Float> wolves = new ArrayList<Point2D.Float>();
	public ArrayList<Point2D.Float> wolveSpeed = new ArrayList<Point2D.Float>();
	public ArrayList<Point> lakes = new ArrayList<Point>();
	public ArrayList<Integer> radiusLakes = new ArrayList<Integer>();
	public ArrayList<Point> berries = new ArrayList<Point>();
	public ArrayList<Boolean> berryStats = new ArrayList<Boolean>();
	public ArrayList<Point> woods = new ArrayList<Point>();
	public ArrayList<Boolean> woodStats = new ArrayList<Boolean>();
	public ArrayList<Point> trees = new ArrayList<Point>();
	public ArrayList<Integer> treeType = new ArrayList<Integer>();
	public ArrayList<Boolean> treeDeath = new ArrayList<Boolean>();
	public Point2D.Float player = new Point2D.Float(0, 0);
	public String direction = new String();
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
		
		random = new Random();
		over = false;
		run = false;
		
		// Set initial stats
		score = 0;
		
		condition = 100;
		tired = 100;
		hungry = 100;
		thirsty = 100;
		
		pTired = (float) 0.001;
		playerMovement = (float) 1.2;
		
		// Set environment
		worldX = worldSize * dim.width;
		worldY = worldSize * dim.height;
		dayLength = 5000;
		
		nLakes = 50;
		int minLake = 30, maxLake = 400;
		for (int i = 0; i < nLakes; i++) {
			lakes.add(new Point(random.nextInt(worldX), random.nextInt(worldY)));
			radiusLakes.add(random.nextInt((maxLake - minLake) + 1) + minLake);
		}
		
		// Set berries
		nBerries = 100;
		berryBonus = 25;
		berryRespawn = (float) 0.001;
		for (int i = 0; i < nBerries; i++) {
			boolean looping = true;
			while (looping == true) {
				int startX = random.nextInt(worldX);
				int startY = random.nextInt(worldY);
				if (checkLake(startX, startY) == false) {
					berries.add(new Point(startX, startY));
					berryStats.add(true);
					looping = false;
				}
			}
		}
		
		// Set trees
		nTrees = 3000;
		seedForests = 20;
		pPine1 = (float) 0.3;
		pPine2 = (float) 0.1;
		pFir = (float) 0.4;
		pTree = (float) 0.2;
		pDeath = (float) 0.1;
		
		for (int i = 0; i < nTrees; i++) {
			boolean looping = true;
			while (looping == true) {
				int startX = random.nextInt(worldX);
				int startY = random.nextInt(worldY);
				if (checkLake(startX, startY) == false) {
					if (i < seedForests) {
						addTree(startX, startY);
						looping = false;
					}
					else {
						if ((checkTree(startX, startY, 100) == true && checkTree(startX, startY, 30) == false) || random.nextFloat() < 0.01){
							addTree(startX, startY);
							looping = false;
						}
					}
				}
			}
		}
		
		// Set wood
		nWoods = 10;
		rWood = 30;
		for (int i = 0; i < nWoods; i++) {
			boolean looping = true;
			while (looping == true) {
				int startX = random.nextInt(worldX);
				int startY = random.nextInt(worldY);
				if (checkLake(startX, startY) == false && checkTree(startX, startY, rWood) == true) {
					woods.add(new Point(startX, startY));
					if (random.nextFloat() < 0.5)
						woodStats.add(true);
					else
						woodStats.add(false);
					looping = false;
				}
			}
		}
		
		// Set wolve behaviour
		nWolves = 50;
		changeSpeed = (float) 0.02;
		maxSpeed = (float) 0.5;
		changeDirection = (float) 0.02;
		wolveRadius = 300; 
		wolveDriftRadius = 20 * wolveRadius; 
		wolveAggression = (float) 0.3;
		chase = (float) 0.5;
		for (int i = 0; i < nWolves; i++) {
			boolean looping = true;
			while (looping == true) {
				float startX = worldX * random.nextFloat();
				float startY = worldY * random.nextFloat();
				if (checkLake(startX, startY) == false) {
					wolves.add(new Point2D.Float(startX, startY));
					wolveSpeed.add(new Point2D.Float(random.nextFloat()-(float)0.5, random.nextFloat()-(float)0.5));
					looping = false;
				}
			}
		}
		
		// Set player start
		direction = "Down";
		while (true) {
			int startX = random.nextInt(worldX / 3) + worldX / 3;
			int startY = random.nextInt(worldY / 3) + worldY / 3;
			player = new Point2D.Float(startX, startY);
			if ((whichWolve(startX, startY) < 0 && checkLake(startX, startY) == false))
				break;
		}
		timer.start();	
	}
	
	public void actionPerformed(ActionEvent arg0) {
		
		tick++;
		if (over == true)
			timer.stop();
		
		// Wolve settings
		for (int i = 0; i < sto.nWolves; i++) {
			float wolveX = wolves.get(i).x, wolveY = wolves.get(i).y;
			float velX = wolveSpeed.get(i).x, velY = wolveSpeed.get(i).y;
			if (tick % 100 == 0) {
				if (random.nextFloat() < changeDirection) {
					velX = 2*random.nextFloat()-1; 
					velY = 2*random.nextFloat()-1;
				}
				else if (random.nextFloat() < wolveAggression) {
					float xDir = player.x - wolveX;
					float yDir = player.y - wolveY;
					float dirLen = (float) Math.sqrt(xDir * xDir + yDir * yDir);
					if (dirLen <= 10 * wolveRadius) {
						velX += changeSpeed * xDir / dirLen;
						velY += changeSpeed * yDir / dirLen;
					}
				}
				else {
					float randX = 2*random.nextFloat()-1;
					float randY = 2*random.nextFloat()-1;
					velX += changeSpeed * randX;
					velY += changeSpeed * randY;
				}
			}
			if (wolveX < 0 || wolveX > worldX || wolveY > worldY || wolveY < 0 
					|| checkLake(wolveX + velX, wolveY + velY) == true) {
				velX = -velX;
				velY = -velY;
			}
			float momSpeed = (float) Math.sqrt(velX * velX + velY * velY);
			if (momSpeed > maxSpeed) {
				velX *= maxSpeed / momSpeed;
				velY *= maxSpeed / momSpeed;;
			}
			
			// Check for wolve chasing
			float xDir = player.x - wolveX;
			float yDir = player.y - wolveY;
			if (wolveAttack(i, 1) == true) {
				float dirLen = (float) Math.sqrt(xDir * xDir + yDir * yDir);
				float intensity = random.nextFloat();
				velX = wolveSpeed.get(i).x + intensity * chase * xDir / dirLen;
				velY = wolveSpeed.get(i).y + intensity * chase * yDir / dirLen;
			}
			
			// Check for wolve death
			if (wolveAttack(i, 20) == true)
				over = true;
			
			wolveSpeed.set(i, new Point2D.Float(velX, velY));
			wolves.set(i, new Point2D.Float(wolveX + velX, wolveY + velY));
		}


		if (tick % 10 == 0) {
			float pval = random.nextFloat();
			if (pval < 0.35){
				if (hungry > 0)
					hungry-=1;
				else
					condition -= 1;
			}
			else if (pval < 0.95) {
				if (thirsty > 0)
					thirsty-=1;
				else
					condition -= 1;
			}
			else {
				if (tired > 0)
					tired-=1;
				else
					condition -= 1;
			}
		}
		
		if (tick % 100 == 0 && tired > 0 && hungry > 0 && thirsty > 0 && condition < 100)
			condition += 1;
		
		if (checkLake(player.x - (playerMovement + 1), player.y) == true || checkLake(player.x + (playerMovement + 1), player.y) == true || 
				checkLake(player.x, player.y + (playerMovement + 1)) == true || checkLake(player.x, player.y - (playerMovement + 1)) == true) {
			thirsty = 100;
		}
		
		if (tick % 500 == 0) {
			for (int i = 0; i < nBerries; i++) {
				if (berryStats.get(i) == false && random.nextFloat() < berryRespawn)
					berryStats.set(i, true);
			}
		}
		
		int berryIndex = checkBerry(player.x, player.y);
		if (berryIndex >= 0 && berryStats.get(berryIndex) == true) {
			if (hungry < 100) {
				hungry = Math.min(100, hungry + berryBonus);
				berryStats.set(berryIndex, false);
			}
		}
		
		if (tick % 20 == 0 && run == true) {
			if (tired > 0)
				tired -= 1;
			else
				run = false;
		}
			
		if (tick % 100 == 0)
			score += 1;
		
		if (condition <= 0)
			over = true;
		
		updateMovement();
		
		renderPanel.repaint();
	}
	
	public boolean wolveAttack(int index, int pow) {
		
		float dis_x = wolves.get(index).x - player.x;
		float dis_y = wolves.get(index).y - player.y;
		if (Math.sqrt(dis_x * dis_x + dis_y * dis_y) <= wolveRadius) {
			float p = (float) (1 - Math.sqrt(dis_x * dis_x + dis_y * dis_y) / wolveRadius);
			if (random.nextFloat() < Math.pow(p, pow))
				return true;
		}
		return false;
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
	
	public boolean checkLake(float x, float y) {
		for (int i = 0; i < nLakes; i++) {
			float dis_x = lakes.get(i).x - x;
			float dis_y = lakes.get(i).y - y;		
			if (Math.sqrt(dis_x * dis_x + 4 * dis_y * dis_y) < radiusLakes.get(i)) {
				return true;
			}
		}
		return false;
	}
	
	public int checkBerry(float x, float y) {
		for (int i = 0; i < nBerries; i++) {
			float dis_x = berries.get(i).x - x;
			float dis_y = berries.get(i).y - y;		
			if (Math.sqrt(dis_x * dis_x + dis_y * dis_y) < 15) {
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
	
	public void addTree(int x, int y) {
		
		trees.add(new Point(x, y));
		float pCheck = random.nextFloat();
		if (pCheck < pPine1)
			treeType.add(0);
		else if (pCheck < pPine1 + pPine2)
			treeType.add(1);
		else if (pCheck < pPine1 + pPine2 + pFir)
			treeType.add(2);
		else
			treeType.add(3);
		if (random.nextFloat() < pDeath)
			treeDeath.add(true);
		else
			treeDeath.add(false);
	}
	
	public void getTired() {
		
		if (random.nextFloat() < pTired && tired > 0)
			tired -= 1;
	}
	
	public static void main(String[] args){
		sto = new STO();
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 16)
			run = true;
		if (over && e.getKeyCode() == 82)
			startGame();
	    keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 16)
			run = false;
	    keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {
	}

	public void updateMovement() {
		
		float movement = playerMovement;
		if (keys[KeyEvent.VK_SHIFT])
			movement += 0.5;
		
		if((keys[KeyEvent.VK_W] && keys[KeyEvent.VK_A]) || (keys[KeyEvent.VK_W] && keys[KeyEvent.VK_D]) || 
				(keys[KeyEvent.VK_A] && keys[KeyEvent.VK_S]) || (keys[KeyEvent.VK_S] && keys[KeyEvent.VK_D]) ||
				(keys[KeyEvent.VK_W] && keys[KeyEvent.VK_D]) || (keys[KeyEvent.VK_W] && keys[KeyEvent.VK_D]) ||
				(keys[KeyEvent.VK_UP] && keys[KeyEvent.VK_LEFT]) || (keys[KeyEvent.VK_UP] && keys[KeyEvent.VK_RIGHT]) || 
				(keys[KeyEvent.VK_LEFT] && keys[KeyEvent.VK_DOWN]) || (keys[KeyEvent.VK_DOWN] && keys[KeyEvent.VK_RIGHT]))
				movement /= Math.sqrt(2);
		
	    if((keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]) && player.y > 0 && checkLake(player.x, player.y - movement) == false){
	    	direction = "Up";
	        player.y -= movement;
	    }

	    if((keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]) && player.y < worldY && checkLake(player.x, player.y + movement) == false){
	    	direction = "Down";
	    	player.y += movement;
	    }

	    if((keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]) && player.x > 0 && checkLake(player.x - movement, player.y) == false){
	    	direction = "Left";
	    	player.x -= movement;
	    }

	    if((keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]) && player.x < worldX && checkLake(player.x + movement, player.y) == false){
	    	direction = "Right";
	    	player.x += movement;
	    }
	}
}
