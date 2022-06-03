import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/** Class which represents custom board key adapter */
public class CustomKeyAdapter extends KeyAdapter {
	private UserSnake userSnake;
	private AppleGenerator appleGenerator;
	
	/**
	 * Create a default instance of custom boad key adapter
	 * @param userSnakeInstance Instance of user snake class from the board
	 * @param appleGeneratorInstance Instance of apple generator class from the board
	 */
	public CustomKeyAdapter(UserSnake userSnakeInstance, AppleGenerator appleGeneratorInstance) {
		userSnake = userSnakeInstance;
		appleGenerator = appleGeneratorInstance;
	}
	
	/** Called when any key has been pressed */
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			if(userSnake.getSnakeDirection() != 'R') {
				userSnake.setSnakeDirection('L');
			}
			break;
		case KeyEvent.VK_RIGHT:
			if(userSnake.getSnakeDirection() != 'L') {
				userSnake.setSnakeDirection('R');
			}
			break;
		case KeyEvent.VK_UP:
			if(userSnake.getSnakeDirection() != 'D') {
				userSnake.setSnakeDirection('U');
			}
			break;
		case KeyEvent.VK_DOWN:
			if(userSnake.getSnakeDirection() != 'U') {
				userSnake.setSnakeDirection('D');
			}
			break;
		}
	}
}
