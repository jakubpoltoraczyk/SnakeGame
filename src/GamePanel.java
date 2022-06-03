import java.awt.*;
import java.awt.event.*;
import java.io.Console;
import java.util.Collections;
import java.util.Vector;

import javax.swing.*;

import javax.swing.JPanel;

/** Class which represents main game panel of application */
public class GamePanel extends JPanel implements ActionListener {
	private AppleGenerator appleGenerator;
	private ObstacleGenerator obstacleGenerator;
	private UserSnake userSnake;
	private EnemySnake enemySnake;
	private Frog frog;
	private int userSnakeScore;
	private int enemySnakeScore;
	private JToggleButton restartButton;
	private JToggleButton pauseButton;
	private JToggleButton rankingButton;
	private FileHelper fileHelper;
	private Vector<Integer> rankingResults;
	
	/** Create default instance of main game panel */
	public GamePanel() {			
		appleGenerator = new AppleGenerator();
		obstacleGenerator = new ObstacleGenerator();
		userSnake = new UserSnake();
		frog = new Frog(appleGenerator, userSnake, obstacleGenerator);
		enemySnake = new EnemySnake(appleGenerator, obstacleGenerator, userSnake, frog);
		
		registerButtons();
		fileHelper = new FileHelper();
		rankingResults = fileHelper.readFile("src/rankingResults.txt");
		
		AppleGeneratorRunnable appleGeneratorRunnable = new AppleGeneratorRunnable(appleGenerator);
		Thread appleThread = new Thread(appleGeneratorRunnable);
		appleThread.start();
		
		FrogRunnable frogRunnable = new FrogRunnable(frog);
		Thread frogThread = new Thread(frogRunnable);
		frogThread.start();
		
		EnemySnakeRunnable enemySnakeRunnable = new EnemySnakeRunnable(enemySnake);
		Thread enemySnakeThread = new Thread(enemySnakeRunnable);
		enemySnakeThread.start();
		
		this.setPreferredSize(new Dimension(SharedData.SCREEN_WIDTH, SharedData.SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new CustomKeyAdapter(userSnake, appleGenerator));
		
		SharedData.isRunning = true;
		SharedData.refreshTimer = new Timer(SharedData.REFRESH_DELAY, this);
		SharedData.refreshTimer.start();
		
		obstacleGenerator.createObstacle();
	}
	
	/** Manage game panel redrawing process */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(SharedData.isRanking) {
			displayRanking(g);
			return;
		}
		
		if(SharedData.isWin) {
			displayGameWin(g);
			return;
		}
		
		if(SharedData.isRunning) {
			g.setColor(Color.red);
			for(int i = 0; i < appleGenerator.getNumberOfApples(); ++i) {
				g.fillOval(appleGenerator.getApplesCoordinatesX().get(i), appleGenerator.getApplesCoordinatesY().get(i),
						SharedData.UNIT_SIZE, SharedData.UNIT_SIZE);
			}
			
			g.setColor(Color.white);
			for(int i = 0; i < obstacleGenerator.getObstacleLength(); ++i) {
				g.fillRect(obstacleGenerator.getObstacleCoordinatesX().get(i), obstacleGenerator.getObstacleCoordinatesY().get(i),
						SharedData.UNIT_SIZE, SharedData.UNIT_SIZE);
			}
			
			g.setColor(Color.green);
			g.fillRect(frog.getXCoordinate(), frog.getYCoordinate(), SharedData.UNIT_SIZE, SharedData.UNIT_SIZE);
			
			for(int i = 0; i < userSnake.getSnakeLength(); ++i) {
				g.setColor(i == 0 ? userSnake.getHeadColor() : userSnake.getTailColor());
				g.fillRect(userSnake.getSnakeCoordinatesX()[i], userSnake.getSnakeCoordinatesY()[i], 
						SharedData.UNIT_SIZE, SharedData.UNIT_SIZE);
			}
			
			for(int i = 0; i < enemySnake.getSnakeLength(); ++i) {
				g.setColor(i == 0 ? enemySnake.getHeadColor() : enemySnake.getTailColor());
				g.fillRect(enemySnake.getSnakeCoordinatesX()[i], enemySnake.getSnakeCoordinatesY()[i], 
						SharedData.UNIT_SIZE, SharedData.UNIT_SIZE);
			}	
			
			g.setFont(new Font("Serif", Font.BOLD, 30));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.setColor(userSnake.getHeadColor());
			g.drawString("Score: " + userSnakeScore, 
					((SharedData.SCREEN_WIDTH - metrics.stringWidth("Score: " + userSnakeScore)) / 2) - 100, g.getFont().getSize());
			g.setColor(enemySnake.getHeadColor());
			g.drawString("Score: " + enemySnakeScore, 
					((SharedData.SCREEN_WIDTH - metrics.stringWidth("Score: " + userSnakeScore)) / 2) + 100, g.getFont().getSize());
		} else {
			displayGameOver(g);
		}
	}
	
	/**
	 * Display game win message
	 * @param g Object which is used to paint view elements
	 */
	public void displayGameWin(Graphics g) {
		saveResults();
		g.setColor(Color.red);
		g.setFont(new Font("Serif", Font.BOLD, 60));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Win", (SharedData.SCREEN_WIDTH - metrics1.stringWidth("Win")) / 2, SharedData.SCREEN_HEIGHT / 2);
		g.setColor(Color.red);
		g.setFont(new Font("Serif", Font.BOLD, 30));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.setColor(userSnake.getHeadColor());
		g.drawString("Score: " + userSnakeScore, 
				((SharedData.SCREEN_WIDTH - metrics.stringWidth("Score: " + userSnakeScore)) / 2) - 100, g.getFont().getSize());
		g.setColor(enemySnake.getHeadColor());
		g.drawString("Score: " + enemySnakeScore, 
				((SharedData.SCREEN_WIDTH - metrics.stringWidth("Score: " + userSnakeScore)) / 2) + 100, g.getFont().getSize());
	}
	
	/**
	 * Display game over view
	 * @param g Object which is used to paint view elements
	 */
	public void displayGameOver(Graphics g) {
		saveResults();
		g.setColor(Color.red);
		g.setFont(new Font("Serif", Font.BOLD, 60));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SharedData.SCREEN_WIDTH - metrics1.stringWidth("Game Over")) / 2, SharedData.SCREEN_HEIGHT / 2);
		g.setColor(Color.red);
		g.setFont(new Font("Serif", Font.BOLD, 30));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.setColor(userSnake.getHeadColor());
		g.drawString("Score: " + userSnakeScore, 
				((SharedData.SCREEN_WIDTH - metrics.stringWidth("Score: " + userSnakeScore)) / 2) - 100, g.getFont().getSize());
		g.setColor(enemySnake.getHeadColor());
		g.drawString("Score: " + enemySnakeScore, 
				((SharedData.SCREEN_WIDTH - metrics.stringWidth("Score: " + userSnakeScore)) / 2) + 100, g.getFont().getSize());
	}
	
	/**
	 * Display ranking view
	 * @param g Object which is used to paint view elements
	 */
	public void displayRanking(Graphics g) {
		g.setColor(Color.yellow);
		g.setFont(new Font("Serif", Font.BOLD, 50));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Ranking", (SharedData.SCREEN_WIDTH - metrics1.stringWidth("Ranking")) / 2, SharedData.SCREEN_HEIGHT / 4);
		g.setFont(new Font("Serif", Font.BOLD, 30));
		int maxSize = SharedData.MAX_RECORDS;
		if(maxSize > rankingResults.size()) {
			maxSize = rankingResults.size();
		}
		for(int i = 0; i < maxSize; ++i) {
			String dataToSet = String.valueOf(i + 1) + "). " + String.valueOf(rankingResults.get(maxSize - i - 1)) + " points";
			g.drawString(dataToSet, (SharedData.SCREEN_WIDTH - metrics1.stringWidth("Ranking")) / 2, 
					SharedData.SCREEN_HEIGHT / 4 + i * 30 + 60);
		}
	}

	/** Manage actions available on the board */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(SharedData.isRunning && !SharedData.isPaused) {
			userSnake.move();
			userSnake.checkCollisions();
			if(appleGenerator.checkAppleCollision(userSnake.getSnakeCoordinatesX()[0], userSnake.getSnakeCoordinatesY()[0], true)) {
				++userSnakeScore;
				userSnake.setSnakeLegth(userSnake.getSnakeLength() + 1);
			}
			if(appleGenerator.checkAppleCollision(enemySnake.getSnakeCoordinatesX()[0], enemySnake.getSnakeCoordinatesY()[0], true)) {
				++enemySnakeScore;
				enemySnake.setSnakeLegth(enemySnake.getSnakeLength() + 1);
			}
			if(obstacleGenerator.checkCollision(userSnake.getSnakeCoordinatesX()[0], userSnake.getSnakeCoordinatesY()[0])) {
				SharedData.isRunning = false;
				return;
			}
			if(frog.checkCollisions(userSnake.getSnakeCoordinatesX()[0], userSnake.getSnakeCoordinatesY()[0])) {
				SharedData.isRunning = false;
				return;
			}
			for(int i = 0; i < enemySnake.getSnakeLength(); ++i) {
				if(userSnake.getSnakeCoordinatesX()[0] == enemySnake.getSnakeCoordinatesX()[i] &&
						userSnake.getSnakeCoordinatesY()[0] == enemySnake.getSnakeCoordinatesY()[i]) {
					SharedData.isRunning = false;
					return;
				}
			}
		}
		repaint();
	}
	
	/** Register each available application button */
	private void registerButtons() {
		super.setLayout(null);
		registerRestartButton();
		registerPauseButton();
		registerRankingButton();
	}
	
	/** Register restart button in the application */
	private void registerRestartButton() {
		restartButton = new JToggleButton("Restart");
		restartButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	JToggleButton thisButton =  (JToggleButton) e.getSource();
		    	customizeAfterClick(thisButton);
		    	userSnake.clear();
		    	enemySnake.clear();
		    	frog.clear();
		    	appleGenerator.clear();
		    	obstacleGenerator.clear();
		    	userSnakeScore = 0;
		    	enemySnakeScore = 0;
		    	SharedData.isRunning = true;
		    	SharedData.isRestarted = true;
		    	SharedData.isWin = false;
		    	repaint();
		    }
		});
		Dimension restartButtonSize = restartButton.getPreferredSize();
		restartButton.setBounds(250, 750, restartButtonSize.width, restartButtonSize.height);
		this.add(restartButton);
	}
	
	/** Register pause button in the application */
	private void registerPauseButton() {
		pauseButton = new JToggleButton("Pause");
		pauseButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	JToggleButton thisButton =  (JToggleButton) e.getSource();
		    	customizeAfterClick(thisButton);
		    	if(SharedData.isPaused) {
		    		thisButton.setText("Pause");
		    		SharedData.isPaused = false;
		    	} else {
		    		thisButton.setText("Start");
		    		SharedData.isPaused = true;
		    	}
		    }
		});
	
		
		Dimension pauseButtonSize = pauseButton.getPreferredSize();
		pauseButton.setBounds(350, 750, pauseButtonSize.width, pauseButtonSize.height);
		this.add(pauseButton);
	}
	
	/** Register ranking button in the application */
	private void registerRankingButton() {
		rankingButton = new JToggleButton("Ranking");
		rankingButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	JToggleButton thisButton =  (JToggleButton) e.getSource();
		    	customizeAfterClick(thisButton);
		    	if(SharedData.isRanking) {
		    		thisButton.setText("Ranking");
		    		SharedData.isRanking = false;
		    		SharedData.isPaused = false;
		    	} else {
		    		thisButton.setText("Back");
		    		SharedData.isRanking = true;
		    		SharedData.isPaused = true;
		    	}
		    	rankingResults = fileHelper.readFile("src/rankingResults.txt");
		    }
		});
	
		
		Dimension rankingButtonSize = rankingButton.getPreferredSize();
		rankingButton.setBounds(445, 750, rankingButtonSize.width, rankingButtonSize.height);
		this.add(rankingButton);
	}
	
	/**
	 * Customize button's view after click action
	 * @param button Button which has been just clicked
	 */
	private void customizeAfterClick(JToggleButton button) {
		this.requestFocus();
		button.setSelected(false);
	}
	
	/** Save results from just played game */
	private void saveResults() {
		if(!SharedData.isRestarted) {
			return;
		}
		SharedData.isRestarted = false;
		rankingResults.add(userSnakeScore);
		Collections.sort(rankingResults);
		if(rankingResults.size() > SharedData.MAX_RECORDS) {
			rankingResults.remove(0);
		}
		fileHelper.writeFile("src/rankingResults.txt", rankingResults);
	}
}
