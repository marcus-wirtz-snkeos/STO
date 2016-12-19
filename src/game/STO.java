package game;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;	// Modul um Fenster zu erzeugen
import javax.swing.Timer;

public class STO implements ActionListener, KeyListener {
	
	public JFrame jframe;
	public RenderPanel renderPanel;
	
	public Timer timer = new Timer(100, this);
	public Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int worldSize = 4;
	public int worldX, worldY;
	public int tick = 0, score = 0, playerMovement = 2, berryBonus, chase;
	public float changeSpeed, maxSpeed, changeDirection, wolveRadius, wolveAggression, wolveDriftRadius;
	public int condition, hungry, thirsty;
	public int nLakes, nWolves, nBerries;
	public float berryRespawn;
	public boolean over = false, run = false;
	
	public ArrayList<Point> wolves = new ArrayList<Point>();
	public ArrayList<Point2D.Float> wolveSpeed = new ArrayList<Point2D.Float>();
	public ArrayList<Point> lakes = new ArrayList<Point>();
	public ArrayList<Integer> radiusLakes = new ArrayList<Integer>();
	public ArrayList<Point> berries = new ArrayList<Point>();
	public ArrayList<Boolean> berryStats = new ArrayList<Boolean>();
	public Point player = new Point(0, 0);
	public String direction = new String();
	public Random random;
	
	public static STO sto;
	
	public STO(){
		jframe = new JFrame("Survive the outdoors");
		jframe.setVisible(true);
		jframe.setSize(dim.width, dim.height);
		System.out.println(dim.width);
		System.out.println(dim.height);
		//jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, 
		//		dim.height / 2 - jframe.getHeight() / 2);
		jframe.add(renderPanel = new RenderPanel());
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.addKeyListener(this);
		startGame();
	}
	
	public void startGame() {
		
		random = new Random();
				
		// Set initial stats
		condition = 100;
		hungry = 100;
		thirsty = 100;
		
		// Set environment
		worldX = worldSize * dim.width;
		worldY = worldSize * dim.height;
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
		
		// Set wolve behaviour
		nWolves = 50;
		changeSpeed = (float) 0.1;
		maxSpeed = 5;
		changeDirection = (float) 0.03;
		wolveRadius = 200; 
		wolveDriftRadius = 20 * wolveRadius; 
		wolveAggression = (float) 0.2;
		chase = 5;
		for (int i = 0; i < nWolves; i++) {
			boolean looping = true;
			while (looping == true) {
				int startX = random.nextInt(worldX);
				int startY = random.nextInt(worldY);
				if (checkLake(startX, startY) == false) {
					wolves.add(new Point(startX, startY));
					wolveSpeed.add(new Point2D.Float(2*random.nextFloat()-1, 2*random.nextFloat()-1));
					looping = false;
				}
			}
		}
		
		// Set player start
		direction = "Down";
		for (int i = 0; true; i++) {
			int startX = random.nextInt(worldX / 3) + worldX / 3;
			int startY = random.nextInt(worldY / 3) + worldY / 3;
			player = new Point(startX, startY);
			if ((whichWolve(startX, startY) < 0 && checkLake(startX, startY) == false))
				break;
		}
		timer.start();	
	}
	
	public void actionPerformed(ActionEvent arg0) {
		
		tick++;
		if (tick % 1 == 0 && over != true) {
			// Wolve settings
			for (int i = 0; i < sto.nWolves; i++) {
				int wolveX = wolves.get(i).x, wolveY = wolves.get(i).y;
				float velX = wolveSpeed.get(i).x, velY = wolveSpeed.get(i).y;
				if (tick % 3 == 0) {
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
				float momSpeed = (float) Math.sqrt(velX * velX + velY * velY);
				if (momSpeed > maxSpeed) {
					velX /= maxSpeed;
					velY /= maxSpeed;
				}
				if (wolveX < 0 || wolveX > worldX || wolveY > worldY || wolveY < 0 
						|| checkLake((int) (wolveX + velX), (int) (wolveY + velY)) == true) {
					velX = -velX;
					velY = -velY;
				}
				wolves.set(i, new Point((int) (wolveX + velX), (int) (wolveY + velY)));
				wolveSpeed.set(i, new Point2D.Float(velX, velY));
			}
		}
		if (tick % 3 == 0 && over != true) {
			if (random.nextFloat() < 0.4)
				if (hungry > 0)
					hungry-=1;
				else
					condition -= 1;
			else
				if (thirsty > 0)
					thirsty-=1;
				else
					condition -= 1;
		}
		
		if (tick % 30 == 0 && hungry > 0 && thirsty > 0 && condition < 100)
			condition += 1;
		
		// Check for wolve chasing
		if (wolveAttack(player.x, player.y, 1) == true) {
			int index = whichWolve(player.x, player.y);
			float xDir = player.x - wolves.get(index).x;
			float yDir = player.y - wolves.get(index).y;
			float dirLen = (float) Math.sqrt(xDir * xDir + yDir * yDir);
			float intensity = random.nextFloat();
			float velX = wolveSpeed.get(index).x + intensity * chase * xDir / dirLen;
			float velY = wolveSpeed.get(index).y + intensity * chase * yDir / dirLen;
			wolveSpeed.set(index, new Point2D.Float(velX, velY));
		}
		
		// Check for death by wolve attack or condition zero 
		if (wolveAttack(player.x, player.y, 10) == true || condition <= 0)
			over = true;
		
		if (checkLake(player.x - (playerMovement + 1), player.y) == true || checkLake(player.x + (playerMovement + 1), player.y) == true || 
				checkLake(player.x, player.y + (playerMovement + 1)) == true || checkLake(player.x, player.y - (playerMovement + 1)) == true) {
			thirsty = 100;
		}
		
		if (tick % 20 == 0) {
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
		
		if (tick % 5 == 0 && run == true && over == false)
			condition -= 1;
		
		if (tick % 50 == 0 && over == false)
			score += 1;
		
		renderPanel.repaint();
	}
	
	public boolean wolveAttack(int x, int y, int pow) {
		for (int i = 0; i < nWolves; i++) {
			float dis_x = wolves.get(i).x - x;
			float dis_y = wolves.get(i).y - y;
			if (Math.sqrt(dis_x * dis_x + dis_y * dis_y) <= wolveRadius) {
				float p = (float) (1 - Math.sqrt(dis_x * dis_x + dis_y * dis_y) / wolveRadius);
				if (random.nextFloat() < Math.pow(p, pow))
					return true;
			}
		}
		return false;
	}
	
	public int whichWolve(int x, int y) {
		for (int i = 0; i < nWolves; i++) {
			float dis_x = wolves.get(i).x - x;
			float dis_y = wolves.get(i).y - y;
			if (Math.sqrt(dis_x * dis_x + dis_y * dis_y) <= wolveRadius) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean checkLake(int x, int y) {
		for (int i = 0; i < nLakes; i++) {
			float dis_x = lakes.get(i).x - x;
			float dis_y = lakes.get(i).y - y;		
			if (dis_x * dis_x + 4 * dis_y * dis_y < radiusLakes.get(i) * radiusLakes.get(i)) {
				return true;
			}
		}
		return false;
	}
	
	public int checkBerry(int x, int y) {
		for (int i = 0; i < nBerries; i++) {
			float dis_x = berries.get(i).x - x;
			float dis_y = berries.get(i).y - y;		
			if (Math.sqrt(dis_x * dis_x + dis_y * dis_y) < 15) {
				return i;
			}
		}
		return -1;
	}
	
	public static void main(String[] args){
		sto = new STO();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int i = e.getKeyCode();
		if (i == KeyEvent.VK_A && player.x >= 0 && checkLake(player.x - playerMovement, player.y) == false && over==false) {
			player.setLocation(player.x - playerMovement, player.y);
			direction = "Left";
		}
		else if (i == KeyEvent.VK_W && player.y >= 0 && checkLake(player.x, player.y - playerMovement) == false && over==false) {
			player.setLocation(player.x, player.y - playerMovement);
			direction = "Up";
		}
		else if (i == KeyEvent.VK_S && player.y <= worldY - playerMovement && checkLake(player.x, player.y + playerMovement) == false && over==false) {
			player.setLocation(player.x, player.y + playerMovement);
			direction = "Down";
		}
		else if (i == KeyEvent.VK_D && player.x <= worldX - playerMovement && checkLake(player.x + playerMovement, player.y) == false && over==false) {
			player.setLocation(player.x + playerMovement, player.y);
			direction = "Right";
		}
		if (i == KeyEvent.VK_SHIFT) {
			if (run == true) 
				playerMovement -= 1;
			else
				playerMovement += 1;
			run = !run;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
