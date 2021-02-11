package gui.java.client;

/**
 * A simple application client that simulates a peer
 *
 * @author Daniel Bento
 * @version 1.0 (08/02/2021)
 */
public class Main {

	/**
	 * An utility class should not have public constructors
	 */
	private Main() {
	}

    /**
     * A simple interaction with the application services
     * @param args Command line parameters
     */
    public static void main(String[] args) {
		gui.java.presentation.fx.Startup.startGUI();
    }
}