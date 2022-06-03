import java.awt.Color;

/** Class which represents user snake object */
public class UserSnake extends Snake {	
	/** Create default instance of user snake class */
	public UserSnake() {
		snakeCoordinatesX = new int[SharedData.GAME_UNITS];
		snakeCoordinatesY = new int[SharedData.GAME_UNITS];
		headColor = new Color(0, 204, 204);
		tailColor = new Color(153, 255, 255);
	}
	
	/** Move snake object in regard to currently set parameters */
	public void move() {
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
	
	/** Check if user snake collied with any application object */
	public void checkCollisions() {
		for(int i = snakeLength; i > 0; --i) {
			if(snakeCoordinatesX[0] == snakeCoordinatesX[i] && snakeCoordinatesY[0] == snakeCoordinatesY[i]) {
				SharedData.isRunning = false;
			}
		}
		
		if(snakeCoordinatesX[0] < 0 || snakeCoordinatesX[0] >= SharedData.SCREEN_WIDTH ||
				snakeCoordinatesY[0] < 0 || snakeCoordinatesY[0] >= SharedData.SCREEN_HEIGHT) {
			SharedData.isRunning = false;
		}
	}
}
