import java.util.Vector;

/** Class which represents a generator for apple objects */
public class AppleGenerator {
	private Vector<Integer> applesCoordinatesX; ///< Coordinates X of apples
	private Vector<Integer> applesCoordinatesY; ///< Coordinates Y of apples
	private int numberOfApples; ///< Number of apples on the board
	
	/** Create a default instance of apple generator class */
	public AppleGenerator() {
		applesCoordinatesX = new Vector<Integer>();
		applesCoordinatesY = new Vector<Integer>();
		applesCoordinatesX.setSize(SharedData.GAME_UNITS);
		applesCoordinatesY.setSize(SharedData.GAME_UNITS);
	}
	
	/** Reset whole generator and create new empty generator data */
	public void clear() {
		applesCoordinatesX = new Vector<Integer>();
		applesCoordinatesY = new Vector<Integer>();
		applesCoordinatesX.setSize(SharedData.GAME_UNITS);
		applesCoordinatesY.setSize(SharedData.GAME_UNITS);
		numberOfApples = 0;
	}
	
	/**
	 * Provide apples coordinates X
	 * @return Collection of apples coordinates X
	 */
	public Vector<Integer> getApplesCoordinatesX() {
		return applesCoordinatesX;
	}
	
	/**
	 * Provide apples coordinates Y
	 * @return Collection of apples coordinates Y
	 */
	public Vector<Integer> getApplesCoordinatesY() {
		return applesCoordinatesY;
	}
	
	/**
	 * Provide number of apples, which are available
	 * @return Number of available apples
	 */
	public int getNumberOfApples() {
		return numberOfApples;
	}
	
	/** Create new apple object */
	public void createApple() {
		if(SharedData.isRunning) {
			applesCoordinatesX.set(numberOfApples, 
					SharedData.random.nextInt((int)SharedData.SCREEN_WIDTH / SharedData.UNIT_SIZE) * SharedData.UNIT_SIZE); 
			applesCoordinatesY.set(numberOfApples,
					SharedData.random.nextInt((int)SharedData.SCREEN_HEIGHT / SharedData.UNIT_SIZE) * SharedData.UNIT_SIZE);
			++numberOfApples;
		}	
	}
	
	/**
	 * Check if object with given coordinates collide with any apple
	 * @param x Coordinate X of object to check
	 * @param y Coordinate Y of object to check
	 * @param autoRemove It true then apple is removed, when collision will appear
	 * @return True if there's a collision, otherwise false
	 */
	public boolean checkAppleCollision(int x, int y, boolean autoRemove) {
		for(int i = 0; i < numberOfApples; ++i) {
			if(applesCoordinatesX.get(i) == x && applesCoordinatesY.get(i) == y) {
				if(autoRemove) {
					applesCoordinatesX.remove(i);
					applesCoordinatesY.remove(i);
					--numberOfApples;	
				}
				return true;
			}
		}
		return false;
	}
}
