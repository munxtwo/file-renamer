package ui;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private static MainFrame frame = new MainFrame();

	private MainFrame() {
		super("File Renamer");
		createUI();
	}

	public static MainFrame getMainFrame() {
		return frame;
	}

	public void createUI() {
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Panel panel = Panel.getPanel();
		Menu menu = Menu.getMenu();
		add(panel);
		setJMenuBar(menu);
	}

}
