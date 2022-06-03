/** Class which represents runnable object for frog class */
public class FrogRunnable implements Runnable {
	private Frog frog;
	private int FROG_TIMEOUT = 250;
	
	/**
	 * Create a default instance of runnable object for frog class
	 * @param frogInstance Instance of frog object from the board
	 */
	FrogRunnable(Frog frogInstance) {
		frog = frogInstance;
	}
	
	/** Start runnable object */
	public void run() {
		while(true) {
			try {
				Thread.sleep(FROG_TIMEOUT);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(SharedData.isRunning && !SharedData.isPaused) {
				frog.move();
			}
		}
	}
}
