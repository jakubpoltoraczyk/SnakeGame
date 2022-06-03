/** Class which represents a frog object */
public class Frog {
	private int xCoordinate = 0;
	private int yCoordinate = 0;
	private AppleGenerator appleGenerator;
	private ObstacleGenerator obstacleGenerator;
	private UserSnake userSnake;
	
	/**
	 * Create default instance of frog class
	 * @param appleGeneratorInstance Instance of apple generator from the board
	 * @param userSnakeInstance Instance of user snake from the board
	 * @param obstacleGeneratorInstance Instance of obstacle generator from the board
	 */
	Frog(AppleGenerator appleGeneratorInstance, UserSnake userSnakeInstance, ObstacleGenerator obstacleGeneratorInstance) {
		appleGenerator = appleGeneratorInstance;
		obstacleGenerator = obstacleGeneratorInstance;
		userSnake = userSnakeInstance;
		xCoordinate = SharedData.random.nextInt(
				(int)SharedData.SCREEN_WIDTH / SharedData.UNIT_SIZE) * SharedData.UNIT_SIZE;
		yCoordinate = SharedData.random.nextInt(
				(int)SharedData.SCREEN_HEIGHT / SharedData.UNIT_SIZE) * SharedData.UNIT_SIZE;
	}
	
	/** Clear frog object and initialize it by empty data */
	public void clear() {
		xCoordinate = SharedData.random.nextInt(
				(int)SharedData.SCREEN_WIDTH / SharedData.UNIT_SIZE) * SharedData.UNIT_SIZE;
		yCoordinate = SharedData.random.nextInt(
				(int)SharedData.SCREEN_HEIGHT / SharedData.UNIT_SIZE) * SharedData.UNIT_SIZE;
	}
	
	/**
	 * Provide coordinate X of frog object
	 * @return Coordinate X of frog object
	 */
	public int getXCoordinate() {
		return xCoordinate;
	}
	
	/**
	 * Provide coordinate Y of frog object
	 * @return Coordinate Y of frog object
	 */
	public int getYCoordinate() {
		return yCoordinate;
	}
	
	/**
	 * Check possible collisions related to frog object and new one specified by given coordinates
	 * @param objectXCoordinate Coordinate X of object to check
	 * @param objectYCoordinate Coordinate Y of object to check
	 * @return True if there's a collision, otherwise false
	 */
	public boolean checkCollisions(int objectXCoordinate, int objectYCoordinate) {
		if(xCoordinate == objectXCoordinate && yCoordinate == objectYCoordinate) {
			return true;
		}
		return false;
	}
	
	/** Move a frog object to new position */
	public void move() {
		int newXCoordinate = xCoordinate;
		int newYCoordinate = yCoordinate;
		do {
			int randomValue = SharedData.random.nextInt(4);
			switch(randomValue) {
			case 0:
				newXCoordinate = xCoordinate + SharedData.UNIT_SIZE;
				break;
			case 1:
				newXCoordinate = xCoordinate - SharedData.UNIT_SIZE;
				break;
			case 2:
				newYCoordinate = yCoordinate + SharedData.UNIT_SIZE;
				break;
			case 3:
				newYCoordinate = yCoordinate - SharedData.UNIT_SIZE;
				break;
			}
		}while(!canMove(newXCoordinate, newYCoordinate));
		xCoordinate = newXCoordinate;
		yCoordinate = newYCoordinate;
	}
	
	/**
	 * Check if moving frog to selected position is available
	 * @param newCoordinateX Coordinate X of new position
	 * @param newCoordinateY Coordinate Y of new position
	 * @return True if move is proper, otherwise false
	 */
	private boolean canMove(int newCoordinateX, int newCoordinateY) {		
		if(newCoordinateX <= 0 || newCoordinateX >= SharedData.SCREEN_WIDTH || 
				newCoordinateY <= 0 || newCoordinateY >= SharedData.SCREEN_HEIGHT) {
			return false;
		}
		
		if(appleGenerator.checkAppleCollision(newCoordinateX, newCoordinateY, false)) {
			return false;
		}
		
		if(obstacleGenerator.checkCollision(newCoordinateX, newCoordinateY)) {
			return false;
		}
		
		if(newCoordinateX == userSnake.getSnakeCoordinatesX()[0] && 
				newCoordinateY == userSnake.getSnakeCoordinatesY()[0]) {
			return false;
		}
		
		return true;
	}
}
