/** Class which represents runnable object for apple generator */
public class AppleGeneratorRunnable implements Runnable {
	private AppleGenerator appleGenerator;
	private int GENERATOR_TIMEOUT = 2000;
	
	/**
	 * Create default instance of runnable apple generator object
	 * @param appleGeneratorInstance Apple generator instance, which will be used in runnable object
	 */
	AppleGeneratorRunnable(AppleGenerator appleGeneratorInstance) {
		appleGenerator = appleGeneratorInstance;
	}
	
	/** Start runnable object */
	public void run() {
		while(true) {
			try {
				Thread.sleep(GENERATOR_TIMEOUT);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(SharedData.isRunning && !SharedData.isPaused) {
				appleGenerator.createApple();
			}
		}
	}
}
