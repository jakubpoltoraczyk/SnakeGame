import javax.swing.JFrame;

/** Class which represents main game frame of application */
public class GameFrame extends JFrame {
	/** Create a default instance of main game frame */
	GameFrame() {
		this.add(new GamePanel());
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
}
