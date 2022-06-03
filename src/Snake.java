import java.awt.Color;

/** Abstract class which represents snake object */
public abstract class Snake {
	protected int snakeCoordinatesX[];
	protected int snakeCoordinatesY[];
	protected int snakeLength = 6;
	protected char snakeDirection = 'R';
	protected Color headColor;
	protected Color tailColor;
	
	/** Create default instance of snake class */
	public Snake() {
		snakeCoordinatesX = new int[SharedData.GAME_UNITS];
		snakeCoordinatesY = new int[SharedData.GAME_UNITS];
		headColor = new Color(0, 204, 204);
		tailColor = new Color(153, 255, 255);
	}
	
	/** Clear snake data and initialize object with empty ones */
	public void clear() {
		snakeCoordinatesX = new int[SharedData.GAME_UNITS];
		snakeCoordinatesY = new int[SharedData.GAME_UNITS];
		snakeLength = 6;
		snakeDirection = 'R';
	}
	
	/**
	 * Provide snake coordinates X values
	 * @return Collection of snake coordinates X
	 */
	public int[] getSnakeCoordinatesX() {
		return snakeCoordinatesX;
	}
	
	/**
	 * Provide snake coordinates Y values
	 * @return Collection of snake coordinates Y
	 */
	public int[] getSnakeCoordinatesY() {
		return snakeCoordinatesY;
	}
	
	/**
	 * Provide snake total length
	 * @return Total length of snake object
	 */
	public int getSnakeLength() {
		return snakeLength;
	}
	
	/**
	 * Provide current snake direction
	 * @return Current direction of snake object
	 */
	public char getSnakeDirection() {
		return snakeDirection;
	}
	
	/**
	 * Provide snake head color
	 * @return Color of snake head object
	 */
	public Color getHeadColor() {
		return headColor;
	}
	
	/**
	 * Provide snake tail color
	 * @return Color of snake tail object
	 */
	public Color getTailColor() {
		return tailColor;
	}
	
	/**
	 * Set new snake object length
	 * @param newSnakeLength Value of new snake object length
	 */
	public void setSnakeLegth(int newSnakeLength) {
		snakeLength = newSnakeLength;
	}
	
	/**
	 * Set new snake object direction
	 * @param newSnakeDirection Value of new snake object direction
	 */
	public void setSnakeDirection(char newSnakeDirection) {
		snakeDirection = newSnakeDirection;
	}
	
	/** Move snake object in regard to currently set parameters */
	public abstract void move();
}
