/** Class which represents runnable object for apple generator */
public class EnemySnakeRunnable implements Runnable {
	private EnemySnake enemySnake;
	private int MOVE_TIMEOUT = SharedData.REFRESH_DELAY;
	
	/**
	 * Create default instance of runnable apple generator object
	 * @param appleGeneratorInstance Apple generator instance, which will be used in runnable object
	 */
	EnemySnakeRunnable(EnemySnake enemySnakeInstance) {
		enemySnake = enemySnakeInstance;
	}
	
	/** Start runnable object */
	public void run() {
		while(true) {
			try {
				Thread.sleep(MOVE_TIMEOUT);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(SharedData.isRunning && !SharedData.isPaused) {
				enemySnake.move();
			}
		}
	}
}
