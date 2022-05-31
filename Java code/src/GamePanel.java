import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{
	
	private static int SCREEN_WIDTH;
	private static int SCREEN_HEIGHT;
	private static int UNIT_SIZE;
	private static int GAME_UNITS;
	private static int DELAY;
	private int x[];
	private int y[];
	private int bodyParts;
	private int applesEaten;
	private int appleX;
	private int appleY;
	private char direction = 'R';
	private boolean running = false;
	private Timer timer;
	private Random random;
	private boolean rainbow = false;
	private int win;
	private boolean grid = false;
	private int diff;
	
	private int pos = 0;
	private boolean bow[] = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false};
	
	GameFrame frame;
	
	public GamePanel(int x){
		
		diff = x;
		
		difficulty();
		
		frame = new GameFrame(10);
		
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		
		
		startGame();
	}
	
	public void difficulty() {
		
		if (diff == 0) {
			SCREEN_WIDTH = 400;
			SCREEN_HEIGHT = SCREEN_WIDTH;
			UNIT_SIZE = 50;
			GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
			DELAY = 150;
			bodyParts = 4;
			this.x = new int[GAME_UNITS];
			this.y = new int[GAME_UNITS];
			win = (SCREEN_WIDTH / UNIT_SIZE) * (SCREEN_HEIGHT / UNIT_SIZE);
			grid = true;
		} else if (diff == 1) {
			SCREEN_WIDTH = 600;
			SCREEN_HEIGHT = SCREEN_WIDTH;
			UNIT_SIZE = 50;
			GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
			DELAY = 100;
			bodyParts = 6;
			this.x = new int[GAME_UNITS];
			this.y = new int[GAME_UNITS];
			win = (SCREEN_WIDTH / UNIT_SIZE) * (SCREEN_HEIGHT / UNIT_SIZE);
		} else if (diff == 2) {
			SCREEN_WIDTH = 600;
			SCREEN_HEIGHT = SCREEN_WIDTH;
			UNIT_SIZE = 25;
			GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
			DELAY = 50;
			bodyParts = 8;
			this.x = new int[GAME_UNITS];
			this.y = new int[GAME_UNITS];
			win = (SCREEN_WIDTH / UNIT_SIZE) * (SCREEN_HEIGHT / UNIT_SIZE);
		} else {
			System.exit(1);
		}
	}
	
	public void startGame() {
		
		newApple();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		
		if (running) {
			
			
			//Grid
			if (grid) {
				for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
					g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
					g.drawLine(0, i * UNIT_SIZE,SCREEN_WIDTH , i * UNIT_SIZE);
				}
			}
			
			//apple
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			g.setColor(Color.green);
			g.fillOval(appleX + (UNIT_SIZE / 2) - (UNIT_SIZE / 8), appleY - (UNIT_SIZE / 8), UNIT_SIZE / 4, UNIT_SIZE / 2);
			
			//snake
			for(int i = 0; i < bodyParts; i++) {
			
				if (i == 0) {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				} else {
					g.setColor(new Color(45, 180, 0));
					if (rainbow) {
						g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
					}
						g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			} 
			
			g.setColor(Color.red);
			g.setFont(new Font ("Impact", Font.BOLD, 20));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: " + applesEaten, SCREEN_WIDTH - (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)), g.getFont().getSize());
			
		} else if (win == 1){
			win(g);
		} else {
			gameOver(g);
		}
	}
	
	public void win(Graphics g) {
		
		g.setColor(Color.red);
		g.setFont(new Font ("Impact", Font.BOLD, 75));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("You Won", (SCREEN_WIDTH - metrics1.stringWidth("You Won")) / 2, SCREEN_HEIGHT / 2);
		
		g.setColor(Color.red);
		g.setFont(new Font ("Impact", Font.BOLD, 40));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics2.stringWidth("Score: " + applesEaten)) / 2, (SCREEN_HEIGHT / 2) + 60);
		
		frame.ButtonVisible(applesEaten, diff);
	}
	
	public void newApple() {
		
		appleX = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
		
		for(int i = bodyParts; i > -1; i--) {
			if((x[i] == appleX) && (y[i] == appleY)) {
				newApple();
				break;
			}
		}
	}
	
	public void move() {
		
		for(int i = bodyParts; i > 0; i--) {
			
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}
		switch (direction) {
		
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		}
	}
	
	public void checkApple() {
		
		if ((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
	}
	
	public void checkCollisions() {
		
		//head touches body
		for(int i = bodyParts; i > 0; i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}
		
		//head touches left border
		if (x[0] < 0) {
			running = false;
		}
		
		//head touches right border
		if (x[0] > SCREEN_WIDTH) {
			running = false;
		}
				
		//head touches top border
		if (y[0] < 0) {
			running = false;
		}
			
		//head touches bottom border
		if (y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		
		//Win determination
		if (bodyParts >= win) {
			running = false;
			win = 1;
		}
	
		//stop timer 
		if (!running) {
			timer.stop();
		}
	}
	
	public void gameOver(Graphics g) {
		
		//Game Over text
		g.setColor(Color.red);
		g.setFont(new Font ("Impact", Font.BOLD, 75));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics1.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
		
		g.setColor(Color.red);
		g.setFont(new Font ("Impact", Font.BOLD, 40));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics2.stringWidth("Score: " + applesEaten)) / 2, (SCREEN_HEIGHT / 2) + 60);
		
		frame.ButtonVisible(applesEaten, diff);
	}
	
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if (running) {
			
			if (bow[0] && bow[1] && bow[2] && bow[3] && bow[4] && bow[5] && bow[6] && bow[7] && bow[8] && bow[9] && bow[10] && bow[11]) {
				rainbow = true;
			}
			
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	
	
	public class MyKeyAdapter extends KeyAdapter{
		
		@Override
		public void keyPressed(KeyEvent e) {
			
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (direction != 'R') {
					direction = 'L';
					break;
				}
			case KeyEvent.VK_RIGHT:
				if (direction != 'L') {
					direction = 'R';
					break;
				}
			case KeyEvent.VK_UP:
				if (direction != 'D') {
					direction = 'U';
					break;
				}
			case KeyEvent.VK_DOWN:
				if (direction != 'U') {
					direction = 'D';
					break;
				}

				
			//secret	
			case KeyEvent.VK_ENTER:
				if (pos < bow.length) {
					bow[pos] = true;
					pos++;
				}
				break;
			}
		}
	}
}
