package main;

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

public class Game implements ActionListener, KeyListener {
	
	static boolean[] keys = new boolean[222];
	
	public JFrame jframe;
	public RenderPanel renderPanel;
	public Timer timer = new Timer(1, this);
	static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	public static Dimension dim = new Dimension(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());

	public static final int worldSize = 4;
	public static int worldX, worldY;
	public static float dayLength;

	public static int tick = 0;
	public static int score;
	public static int craftTime;
	public static int harvestTime;
	public static int cookTime;

	public static int woodCollected, stoneCollected, leaveCollected, lianaCollected, berryCollected, meatCollected, rawMeatCollected, fishCollected;
	public static float pTired, playerMovement;
	public static boolean over, craft, cook, harvest;

	public static boolean hidden;
	
	int nCraft = 4;

	static int craftableAction = 1;
	boolean[] craftStats = new boolean[nCraft];
	
	public static Random random;
	
	public static Player player;
	
	public static Game game;
	
	public Game(){
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
		
		player = new Player();
		random = new Random();
		over = false;
		craft = false;
		cook = false;
		harvest = false;
		hidden = false;

		score = 0;
		harvestTime = 200;
		cookTime = 300;
		craftTime = 400;
		
		pTired = (float) 0.001;
		playerMovement = (float) 3;
		
		// Set inventory
		woodCollected = 0;
		stoneCollected = 0;
		leaveCollected = 0;
		lianaCollected = 0;
		berryCollected = 0;
		meatCollected = 0;
		rawMeatCollected = 0;
		fishCollected = 0;

		for (int i = 0; i < nCraft; i++)
			craftStats[i] = false;
		
		// Set environment
		worldX = worldSize * dim.width;
		worldY = worldSize * dim.height;
		dayLength = 5000;
		
		World.initWorld();
		player.initPosition();
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
	
	public static void main(String[] args){
		game = new Game();
	}
	
	public void keyPressed(KeyEvent e) {
		// eating berries
		if (e.getKeyCode() == 69 && berryCollected > 0) {
			berryCollected -= 1;
			player.addHungry(5);
		}
		if (e.getKeyCode() == 82 && meatCollected > 0) {
			meatCollected -= 1;
			player.addHungry(40);
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (craft == true) {
				int idx = World.craftables.size() - 1;
				World.craftables.remove(idx);
				World.craftableType.remove(idx);
				World.craftableStat.remove(idx);
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
	
	static Comparator<Point> byY = new Comparator<Point>() {
		public int compare(Point p1, Point p2) {
			return Integer.compare(p1.y, p2.y);
		}
	};
	
	static Comparator<Point2D.Float> FloatbyY = new Comparator<Point2D.Float>() {
		public int compare(Point2D.Float p1, Point2D.Float p2) {
			return Float.compare(p1.y, p2.y);
		}
	};
}
