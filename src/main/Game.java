package main;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.util.Comparator;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Game implements ActionListener, KeyListener {
	
	static boolean[] keys = new boolean[222];
	
	public JFrame jframe;
	public RenderPanel renderPanel;
	public Timer timer = new Timer(1, this);

	public static int tick = 0;
	public static int score;
	public static boolean over;
	public static boolean paused;
		
	public static Random random;
	
	public static Player player;
	
	public static Game game;
	
	public Game(){
		jframe = new JFrame("Survive the outdoors");
		jframe.setSize(World.dim.width, World.dim.height);
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

		for (int i = 0; i < World.nCraft; i++)
			World.craftStats[i] = false;
		
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
			update();
		}

		// Draw world
		renderPanel.repaint();
	}
	
	public static void update() {

		// Wildlife settings
		Wildlife.wolveBehaviour();
		Wildlife.rabbitBehaviour();
		Wildlife.fishBehaviour();
		
		// update items
		World.spawnItems();
		World.updateItems();
		
		// Update player
		player.collectItems();
		player.stats();
		player.cooking();
		player.harvesting();
		player.crafting();
		player.searching();
		player.move();
		
		if (tick % 100 == 0)
			score += 1;

		if (tick == 10000)
			tick = 0;
	}
		
	public static void main(String[] args){
		game = new Game();
	}
	
	public void keyPressed(KeyEvent e) {
		// eating berries
		if (e.getKeyCode() == 69) { player.eatBerry(); }
		if (e.getKeyCode() == 82) { player.eatMeat(); }
		if (e.getKeyCode() == 70) { player.fuelFire(); }
		if (e.getKeyCode() == KeyEvent.VK_SPACE) { player.hideShelter(); }
		if (e.getKeyCode() == 80) { pauseGame(); }
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { player.abortAction(); }
		
	    keys[e.getKeyCode()] = true;
		if (over == true && keys[KeyEvent.VK_R])
			startGame();	
	}

	public void keyReleased(KeyEvent e) { keys[e.getKeyCode()] = false; }

	public void keyTyped(KeyEvent e) { }
	
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
