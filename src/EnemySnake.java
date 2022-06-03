import java.awt.Color;
import java.util.Vector;

/** Class which represents enemy snake object */
public class EnemySnake extends Snake{	
	private AppleGenerator appleGenerator;
	private ObstacleGenerator obstacleGenerator;
	private UserSnake userSnake;
	private Frog frog;
	
	/**
	 * Create a default instance of enemy snake class
	 * @param appleGeneratorInstance Instance of apple generator class from the board
	 */
	public EnemySnake(AppleGenerator appleGeneratorInstance, 
			ObstacleGenerator obstacleGeneratorInstance,
			UserSnake userSnakeInstance,
			Frog frogInstance) {
		appleGenerator = appleGeneratorInstance;
		obstacleGenerator = obstacleGeneratorInstance;
		userSnake = userSnakeInstance;
		frog = frogInstance;
		snakeCoordinatesX = new int[SharedData.GAME_UNITS];
		snakeCoordinatesY = new int[SharedData.GAME_UNITS];
		headColor = new Color(168, 82, 50);
		tailColor = new Color(168, 125, 0);
		initialSetup();
	}
	
	/** Clear enemy snake data and initialize object with new ones */
	public void clear() {
		super.clear();
		initialSetup();
	}
	
	/** Move snake object in regard to currently set parameters */
	public void move() {
		snakeDirection = calculateDirection();
		for(int i = snakeLength; i > 0; --i) {
			snakeCoordinatesX[i] = snakeCoordinatesX[i - 1];
			snakeCoordinatesY[i] = snakeCoordinatesY[i - 1];
		}
		
		switch(snakeDirection) {
		case 'U':
			snakeCoordinatesY[0] = snakeCoordinatesY[0] - SharedData.UNIT_SIZE;
			break;
		case 'D':
			snakeCoordinatesY[0] = snakeCoordinatesY[0] + SharedData.UNIT_SIZE;
			break;
		case 'L':
			snakeCoordinatesX[0] = snakeCoordinatesX[0] - SharedData.UNIT_SIZE;
			break;
		case 'R':
			snakeCoordinatesX[0] = snakeCoordinatesX[0] + SharedData.UNIT_SIZE;
			break;
		}
	}
	
	/** Calculate best direction to move AI snake */
	private char calculateDirection() {
		char newDirection = snakeDirection;
		if(appleGenerator.getNumberOfApples() == 0) {
			return newDirection;
		}
	
		int snakeHeadX = snakeCoordinatesX[0];
		int snakeHeadY = snakeCoordinatesY[0];
		int distanceX = snakeHeadX - appleGenerator.getApplesCoordinatesX().get(0);
		int distanceY = snakeHeadY - appleGenerator.getApplesCoordinatesY().get(0);;
		char newDirectionX = (distanceX < 0) ? 'R' : 'L';
		char newDirectionY = (distanceY < 0) ? 'D' : 'U';
		
		if(Math.abs(distanceX) < Math.abs(distanceY)) {
			newDirection = newDirectionX;
		} else {
			newDirection = newDirectionY;
		}
		
		if(distanceX == 0) {
			newDirection = (distanceY < 0) ? 'D' : 'U';
		}
		
		if(distanceY == 0) {
			newDirection = (distanceX < 0) ? 'R' : 'L';
		}
		
		boolean moveAllowed[] = new boolean[4];
		moveAllowed[0] = true; // U
		moveAllowed[1] = true; // D
		moveAllowed[2] = true; // L
		moveAllowed[3] = true; // R
		
		for(int i = 0; i < 4; ++i) {
			snakeHeadX = snakeCoordinatesX[0];
			snakeHeadY = snakeCoordinatesY[0];
			switch(i) {
			case 0:
				snakeHeadY = snakeHeadY - SharedData.UNIT_SIZE;
				break;
			case 1:
				snakeHeadY = snakeHeadY + SharedData.UNIT_SIZE;
				break;
			case 2:
				snakeHeadX = snakeHeadX - SharedData.UNIT_SIZE;
				break;
			case 3:
				snakeHeadX = snakeHeadX + SharedData.UNIT_SIZE;
				break;
			}
			boolean result = checkCollisions(snakeHeadX, snakeHeadY);
			if(result) {
				moveAllowed[i] = false;
			}
		}

		char finalDirection = newDirection;
		char firstOption = newDirection;
		char secondOption = (newDirection == newDirectionX ? newDirectionY : newDirectionX);
		char thirdOption = (newDirectionX == 'R' ? 'L' : 'R');
		char fourthOption = (newDirectionY == 'U' ? 'D' : 'U');
		if(isMoveAllowed(firstOption, moveAllowed)) {
			finalDirection = newDirection;
		} else if(isMoveAllowed(secondOption, moveAllowed)) {
			finalDirection = secondOption;
		} else if(isMoveAllowed(thirdOption, moveAllowed)) {
			finalDirection = thirdOption;
		} else if(isMoveAllowed(fourthOption, moveAllowed)) {
			finalDirection = fourthOption;
		} else {
			SharedData.isWin = true;	
		}
		
		return finalDirection;
	}
	
	/** Prepare initial location setup of enemy snake object */
	private void initialSetup() {
		int OFFSET_VALUE = 30;
		for(int i = 0; i < snakeLength; ++i) {
			snakeCoordinatesY[i] = SharedData.UNIT_SIZE * OFFSET_VALUE;
		}
	}
	
	/**
	 * Check enemy snake collisions with other board components
	 * @param snakeHeadX Coordinate X of snake head
	 * @param snakeHeadY Coordinate Y of snake head
	 * @return True if there's collision otherwise false
	 */
	private boolean checkCollisions(int snakeHeadX, int snakeHeadY) {
		Vector<Integer> obstacleX = obstacleGenerator.getObstacleCoordinatesX();
		Vector<Integer> obstacleY = obstacleGenerator.getObstacleCoordinatesY();
		for(int i = 0; i < obstacleGenerator.getObstacleLength(); ++i) {
			if(snakeHeadX == obstacleX.get(i) && snakeHeadY == obstacleY.get(i)) {
				return true;
			}
		}
		
		for(int i = 0; i < snakeLength; ++i) {
			if(snakeHeadX == snakeCoordinatesX[i] && snakeHeadY == snakeCoordinatesY[i]) {
				return true;
			}
		}
		
		int userSnakeX[] = userSnake.getSnakeCoordinatesX();
		int userSnakeY[] = userSnake.getSnakeCoordinatesY();
		for(int i = 0; i < userSnake.getSnakeLength(); ++i) {
			if(snakeHeadX == userSnakeX[i] && snakeHeadY == userSnakeY[i]) {
				return true;
			}
		}
		
		if(snakeHeadX == frog.getXCoordinate() && snakeHeadY == frog.getYCoordinate()) {
			return true;
		}
		
		if(snakeCoordinatesX[0] < 0 || snakeCoordinatesX[0] >= SharedData.SCREEN_WIDTH ||
				snakeCoordinatesY[0] < 0 || snakeCoordinatesY[0] >= SharedData.SCREEN_HEIGHT) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Check if selected move direction is allowed
	 * @param direction Specified move direction
	 * @param moveAllowed Contains decision about possible moves
	 * @return True is selected move is allowed, otherwise false
	 */
	private boolean isMoveAllowed(char direction, boolean moveAllowed[]) {
		if(direction == 'U' && moveAllowed[0] == true) {
			return true;
		}
		if(direction == 'D' && moveAllowed[1] == true) {
			return true;
		}
		if(direction == 'L' && moveAllowed[2] == true) {
			return true;
		}
		if(direction == 'R' && moveAllowed[3] == true) {
			return true;
		}
		return false;
	}
}