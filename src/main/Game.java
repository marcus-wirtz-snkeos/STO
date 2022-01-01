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

	public static final int worldSize = 3;
	public static int worldX = worldSize * dim.width;
	public static int worldY = worldSize * dim.height;;
	public static float dayLength = (float) 500;

	public static int tick = 0;
	public static int score;
	public static float pTired = (float) 0.001;
	public static float playerMovement = (float) 1;
	public static boolean over;
	public static boolean paused;
	
	int nCraft = 4;
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
		paused = false;
		score = 0;

		// Initialize world and players
		World.initWorld();
		player.initPosition();
		timer.start();	
	}
	
	public void actionPerformed(ActionEvent arg0) {

		tick++;
		if (!paused) {

			if (over == true)
				timer.stop();
					
			// Update game
			updateGame.update();
			/*
			updateGame.spawnItems();
			
			// Update player
			player.collectItems();
			player.stats();
			player.cooking();
			player.harvesting();
			player.crafting();
			player.hideShelter();
			player.searching();
			*/
			player.move();
		}
		// Draw world
		renderPanel.repaint();
	}
	
	public static void main(String[] args){
		game = new Game();
	}
	
	public void keyPressed(KeyEvent e) {
		// eating berries
		/*
		if (e.getKeyCode() == 69) { player.eatBerry(); }
		if (e.getKeyCode() == 82) { player.eatMeat(); }
		if (e.getKeyCode() == 70) { player.fuelFire(); }
		*/
		if (e.getKeyCode() == 80) { pauseGame(); }
		// if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { player.abortAction(); }
		
	    keys[e.getKeyCode()] = true;
		if (over == true && keys[KeyEvent.VK_R])
			startGame();	
	}

	public void keyReleased(KeyEvent e) {
	    keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {
	}
	
	private void pauseGame() { paused = !paused; }
	
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
