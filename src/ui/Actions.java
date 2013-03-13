package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Actions {

	public static MainFrame frame = MainFrame.getMainFrame();

	class BrowseDirListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Panel panel = Panel.getPanel();
			File defaultPath = new File(AppProperties.loadProperties(panel
					.getProperties()));
			panel.getFileChooser().setCurrentDirectory(defaultPath);
			int returnVal = panel.getFileChooser().showOpenDialog(frame);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				panel.clearFields();
				panel.chosenDir = panel.getFileChooser().getSelectedFile();
				panel.getFilePathTextField().setText(
						panel.chosenDir.getAbsolutePath());
				panel.listFiles();
				panel.getBeforeTextField().setEditable(true);
				panel.getAfterTextField().setEditable(true);
			}
		}

	}

	class ExitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}

	}

	class ChangeDefaultDirListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Panel panel = Panel.getPanel();
			String defaultPath = "";
			int returnVal = panel.getFileChooser().showOpenDialog(frame);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				defaultPath = panel.getFileChooser().getSelectedFile()
						.getAbsolutePath();
			}
			AppProperties.storeProperties(panel.getProperties(), defaultPath);
		}

	}

	class AboutListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Panel panel = Panel.getPanel();
			String msg = "FileRenamer\n> Allows batch renaming of files easily and quickly\n\nVersion: 1.0\n"
					+ "(c) Copyright munxtwo 2012\n";
			JOptionPane.showMessageDialog(panel, msg, "About FileRenamer", JOptionPane.INFORMATION_MESSAGE);
		}

	}
}
