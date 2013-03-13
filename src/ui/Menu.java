/**
 * 
 */
package ui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import ui.Actions.*;

/**
 * @author ktay
 * 
 */
@SuppressWarnings("serial")
public class Menu extends JMenuBar {

	private static Menu menu = new Menu();
	public static Panel panel = Panel.getPanel();

	private Menu() {
		createFileMenu();
		createEditMenu();
		createHelpMenu();
	}

	public void createFileMenu() {

		JMenu fileMenu = new JMenu("File");
		add(fileMenu);

		JMenuItem browseDir = new JMenuItem("Browse Dir ...");
		fileMenu.add(browseDir);
		BrowseDirListener browseDirListener = new Actions().new BrowseDirListener();
		browseDir.addActionListener(browseDirListener);

		fileMenu.addSeparator();
		JMenuItem exit = new JMenuItem("Exit");
		fileMenu.add(exit);

		ExitListener exitListener = new Actions().new ExitListener();
		exit.addActionListener(exitListener);
	}

	public void createEditMenu() {
		JMenu editMenu = new JMenu("Edit");
		add(editMenu);

		JMenuItem changeDefaultDir = new JMenuItem("Change Default Dir");
		editMenu.add(changeDefaultDir);

		ChangeDefaultDirListener changeDefaultDirListener = new Actions().new ChangeDefaultDirListener();
		changeDefaultDir.addActionListener(changeDefaultDirListener);
	}

	public void createHelpMenu() {
		JMenu helpMenu = new JMenu("Help");
		add(helpMenu);

		JMenuItem about = new JMenuItem("About FileRenamer");
		helpMenu.add(about);
		
		AboutListener aboutListener = new Actions().new AboutListener();
		about.addActionListener(aboutListener);
	}

	/*
	 * Getters and setters
	 */
	public static Menu getMenu() {
		return menu;
	}

	public static void setMenu(Menu menu) {
		Menu.menu = menu;
	}
}
