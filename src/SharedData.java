import java.util.Random;

import javax.swing.Timer;

/** Class which contains shared data for various application objects */
public final class SharedData {
	/** Default width of application screen */
	public static final int SCREEN_WIDTH = 800;
	
	/** Default height of application screen */
	public static final int SCREEN_HEIGHT = 800;
	
	/** Value of unit size from application */
	public static final int UNIT_SIZE = 25;
	
	/** Number of available game units in application */
	public static final int GAME_UNITS = (SCREEN_WIDTH
			* SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
	
	/** Value of application refresh delay */
	public static final int REFRESH_DELAY = 200;
	
	/** Maximum number of record available in ranking view */
	public static final int MAX_RECORDS = 10;
	
	/** Contains information if game has been win */
	public static boolean isWin = false;
	
	/** Contains information if game is currently restarted */
	public static boolean isRestarted = true;
	
	/** Contains information if game is currently running */
	public static boolean isRunning = false;
	
	/** Contains information if game is currently paused */
	public static boolean isPaused = false;
	
	/** Contains information if ranking view is currently displayed */
	public static boolean isRanking = false;
	
	/** Object to generate random value */
	public static Random random = new Random();
	
	/** Object to make timeout refreshes */
	public static Timer refreshTimer;
}