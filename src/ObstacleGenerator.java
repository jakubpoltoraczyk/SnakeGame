import java.util.Vector;

/** Class which represents generator for obstacle object on the board */
public class ObstacleGenerator {
	private Vector<Integer> obstacleCoordinatesX;
	private Vector<Integer> obstacleCoordinatesY;
	private int obstacleLength;
	
	/** Create a default instance of obstacle generator class */
	public ObstacleGenerator() {
		obstacleCoordinatesX = new Vector<Integer>();
		obstacleCoordinatesY = new Vector<Integer>();
		obstacleCoordinatesX.setSize(SharedData.GAME_UNITS);
		obstacleCoordinatesY.setSize(SharedData.GAME_UNITS);
	}
	
	/** Clear obstacle generator data and initialize it with empty one */
	public void clear() {
		obstacleCoordinatesX = new Vector<Integer>();
		obstacleCoordinatesY = new Vector<Integer>();
		obstacleCoordinatesX.setSize(SharedData.GAME_UNITS);
		obstacleCoordinatesY.setSize(SharedData.GAME_UNITS);
		createObstacle();
	}
	
	/**
	 * Provide coordinates X of obstacle objects on the board
	 * @return Collection of obstacle coordinatesX
	 */
	public Vector<Integer> getObstacleCoordinatesX() {
		return obstacleCoordinatesX;
	}
	
	/**
	 * Provide coordinates Y of obstacle objects on the board
	 * @return Collection of obstacle coordinates Y
	 */
	public Vector<Integer> getObstacleCoordinatesY() {
		return obstacleCoordinatesY;
	}
	
	/**
	 * Provide total obstacle objects length
	 * @return Total length of obstacle objects 
	 */
	public int getObstacleLength() {
		return obstacleLength;
	}
	
	/** Create obstacle objects on the board */
	public void createObstacle() {
		int initialXCoordinate = SharedData.random.nextInt(SharedData.SCREEN_WIDTH / 
				SharedData.UNIT_SIZE) * SharedData.UNIT_SIZE;
		int initialYCoordinate = SharedData.random.nextInt(SharedData.SCREEN_HEIGHT / 
				SharedData.UNIT_SIZE - 20) * SharedData.UNIT_SIZE + 10 * SharedData.UNIT_SIZE;
		int length = SharedData.random.nextInt(10) + 10;
		for(int i = 0; i < length; ++i) {
			obstacleCoordinatesX.set(i, initialXCoordinate + i * SharedData.UNIT_SIZE);
			obstacleCoordinatesY.set(i, initialYCoordinate);
		}
		obstacleLength = length;
		
		initialXCoordinate = SharedData.random.nextInt(SharedData.SCREEN_WIDTH / 
				SharedData.UNIT_SIZE) * SharedData.UNIT_SIZE;
		initialYCoordinate = SharedData.random.nextInt(SharedData.SCREEN_HEIGHT / 
				SharedData.UNIT_SIZE - 20) * SharedData.UNIT_SIZE + 10 * SharedData.UNIT_SIZE;
		length = SharedData.random.nextInt(5) + 5;
		for(int i = obstacleLength; i < obstacleLength + length; ++i) {
			obstacleCoordinatesX.set(i, initialXCoordinate + i * SharedData.UNIT_SIZE);
			obstacleCoordinatesY.set(i, initialYCoordinate);
		}
		obstacleLength += length;
	}
	
	/**
	 * Check if object specified by given coordinates has a collision with obstacle
	 * @param xCoordinate Coordinate X of object to check
	 * @param yCoordinate Coordinate Y of object to check
	 * @return True if there's a collision, otherwise false
	 */
	public boolean checkCollision(int xCoordinate, int yCoordinate) {
		for(int i = 0; i < obstacleLength; ++i) {
			if(obstacleCoordinatesX.get(i) == xCoordinate && obstacleCoordinatesY.get(i) == yCoordinate) {
				return true;
			}
		}
		return false;
	}
}
