package driver;

import java.awt.EventQueue;

import ui.MainFrame;

public class Driver {

	public static void showGUI() {
		MainFrame frame = MainFrame.getMainFrame();
		frame.setVisible(true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				showGUI();
			}
		});
	}

}
