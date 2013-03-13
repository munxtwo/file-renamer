package ui;

import ui.Actions.*;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import logic.Renamer;

@SuppressWarnings("serial")
public class Panel extends JPanel {

	public static Panel panel = new Panel();

	public JList beforeList;
	public JList afterList;
	public JFileChooser fileChooser;
	public JTextField beforeTextField;
	public JTextField afterTextField;
	public JTextField filePathTextField;
	public JLabel beforeLabel;
	public JLabel afterLabel;
	public JLabel renameBeforeLabel;
	public JLabel renameAfterLabel;
	public JButton browseDirButton;
	public JButton selectAllButton;
	public JButton deselectAllButton;
	public JButton previewButton;
	public JButton confirmButton;
	public JButton clearButton;
	public JCheckBox hideExtCheckBox;
	public ArrayList<String[]> data = new ArrayList<String[]>();
	public ArrayList<String[]> filteredData = new ArrayList<String[]>();
	public ArrayList<String> emptyList = new ArrayList<String>();
	public File chosenDir;
	public boolean extHidden = true;
	public Properties properties = new Properties();

	private Panel() {
		setLayout(new GridBagLayout());
		// List
		createBeforeList();
		createAfterList();
		// FileChooser
		createFileChooser();
		// TextField
		createBeforeTextField();
		createAfterTextField();
		createFilePathTextField();
		// Label
		createBeforeLabel();
		createAfterLabel();
		createRenameBeforeLabel();
		createRenameAfterLabel();
		// Button
		createBrowseDirButton();
		// createSelectAllButton();
		// createDeselectAllButton();
		createPreviewButton();
		createConfirmButton();
		createClearButton();
		// CheckBox
		createHideExtCheckBox();
	}

	// List
	public void createBeforeList() {
		beforeList = new JList();
		JScrollPane scrollPaneBefore = new JScrollPane(beforeList);
		scrollPaneBefore
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneBefore
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		beforeList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					beforeTextField.setText(beforeList.getSelectedValue()
							.toString());
				}
			}
		});

		add(scrollPaneBefore,
				new GBC(0, 5, 3, 1)
						.setAnchor(GridBagConstraints.LAST_LINE_START)
						.setFill(GridBagConstraints.BOTH).setWeight(0.8, 0.5)
						.setInsets(0, 5, 5, 5));
	}

	public void createAfterList() {
		afterList = new JList();
		JScrollPane scrollPaneAfter = new JScrollPane(afterList);
		scrollPaneAfter
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneAfter
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrollPaneAfter,
				new GBC(3, 5, 3, 1).setAnchor(GridBagConstraints.LAST_LINE_END)
						.setFill(GridBagConstraints.BOTH).setWeight(0.2, 0.5)
						.setInsets(0, 5, 5, 5));
	}

	// FileChooser
	public void createFileChooser() {
		fileChooser = new JFileChooser(AppProperties.loadProperties(properties));
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	}

	// TextField
	public void createFilePathTextField() {
		filePathTextField = new JTextField(200);
		filePathTextField.setEditable(false);
		add(filePathTextField,
				new GBC(1, 0, 5, 1).setWeight(0, 0)
						.setFill(GridBagConstraints.HORIZONTAL).setInsets(5)
						.setIpad(5, 5));
	}

	public void createBeforeTextField() {
		beforeTextField = new JTextField(100);
		beforeTextField.setEditable(false);
		beforeTextField.getDocument().addDocumentListener(
				new DocumentListener() {

					@Override
					public void changedUpdate(DocumentEvent arg0) {
						filterFiles();
						afterList.setListData(emptyList.toArray());
					}

					@Override
					public void insertUpdate(DocumentEvent arg0) {
						filterFiles();
						afterList.setListData(emptyList.toArray());
					}

					@Override
					public void removeUpdate(DocumentEvent arg0) {
						filterFiles();
						afterList.setListData(emptyList.toArray());
					}

				});
		add(beforeTextField,
				new GBC(1, 1, 5, 1).setFill(GridBagConstraints.HORIZONTAL)
						.setWeight(0, 0).setInsets(5).setIpad(5, 5));
	}

	public void createAfterTextField() {
		afterTextField = new JTextField(100);
		afterTextField.setEditable(false);
		afterTextField.getDocument().addDocumentListener(
				new DocumentListener() {

					@Override
					public void changedUpdate(DocumentEvent arg0) {
						afterList.setListData(emptyList.toArray());
					}

					@Override
					public void insertUpdate(DocumentEvent arg0) {
						afterList.setListData(emptyList.toArray());
					}

					@Override
					public void removeUpdate(DocumentEvent arg0) {
						afterList.setListData(emptyList.toArray());
					}

				});

		add(afterTextField,
				new GBC(1, 2, 5, 1).setFill(GridBagConstraints.HORIZONTAL)
						.setWeight(0, 0).setInsets(5).setIpad(5, 5));
	}

	// Label
	public void createBeforeLabel() {
		beforeLabel = new JLabel("Before");
		beforeLabel.setBackground(Color.LIGHT_GRAY);
		beforeLabel.setOpaque(true);
		beforeLabel.setHorizontalAlignment(JLabel.CENTER);
		beforeLabel.setBorder(BorderFactory.createEtchedBorder());
		add(beforeLabel,
				new GBC(0, 4, 3, 1).setWeight(0, 0)
						.setFill(GridBagConstraints.HORIZONTAL)
						.setInsets(5, 5, 0, 5).setIpad(5, 5));
	}

	public void createAfterLabel() {
		afterLabel = new JLabel("After");
		afterLabel.setBackground(Color.LIGHT_GRAY);
		afterLabel.setOpaque(true);
		afterLabel.setHorizontalAlignment(JLabel.CENTER);
		afterLabel.setBorder(BorderFactory.createEtchedBorder());
		add(afterLabel,
				new GBC(3, 4, 3, 1).setWeight(0, 0)
						.setFill(GridBagConstraints.HORIZONTAL)
						.setInsets(5, 5, 0, 5).setIpad(5, 5));
	}

	public void createRenameBeforeLabel() {
		renameBeforeLabel = new JLabel("Rename From: ");
		add(renameBeforeLabel, new GBC(0, 1, 1, 1).setInsets(5).setWeight(0, 0)
				.setAnchor(GridBagConstraints.LINE_END).setIpad(5, 5));
	}

	public void createRenameAfterLabel() {
		renameAfterLabel = new JLabel("Rename To: ");
		add(renameAfterLabel, new GBC(0, 2, 1, 1).setInsets(5).setWeight(0, 0)
				.setAnchor(GridBagConstraints.LINE_END).setIpad(5, 5));
	}

	// Button
	public void createBrowseDirButton() {
		browseDirButton = new JButton("Browse dir: ");
		browseDirButton.setMnemonic('b');
		BrowseDirListener browseDirListener = new Actions().new BrowseDirListener();
		browseDirButton.addActionListener(browseDirListener);
		add(browseDirButton,
				new GBC(0, 0, 1, 1).setInsets(5)
						.setAnchor(GridBagConstraints.LINE_START)
						.setWeight(0, 0));
	}

	public void createSelectAllButton() {
		selectAllButton = new JButton("Select All");
		selectAllButton.setMnemonic('s');
		selectAllButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!data.isEmpty()) {
					beforeList.setSelectionInterval(0, data.size() - 1);
				}
			}

		});
		add(selectAllButton,
				new GBC(0, 3, 1, 1).setInsets(5)
						.setAnchor(GridBagConstraints.LINE_END).setWeight(0, 0)
						.setFill(GridBagConstraints.HORIZONTAL));
	}

	public void createDeselectAllButton() {
		deselectAllButton = new JButton("De-select All");
		deselectAllButton.setMnemonic('d');
		deselectAllButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!beforeList.isSelectionEmpty()) {
					beforeList.clearSelection();
				}
			}

		});
		add(deselectAllButton,
				new GBC(1, 3, 1, 1).setInsets(5)
						.setAnchor(GridBagConstraints.LINE_START)
						.setWeight(0.5, 0));
	}

	public void createPreviewButton() {
		previewButton = new JButton("Preview");
		previewButton.setMnemonic('p');
		previewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				filterFiles();
				previewFiles();
			}

		});
		add(previewButton,
				new GBC(4, 6, 1, 1).setInsets(5)
						.setAnchor(GridBagConstraints.LINE_END)
						.setWeight(0.5, 0));
	}

	public void createConfirmButton() {
		confirmButton = new JButton("Confirm");
		confirmButton.setMnemonic('c');
		confirmButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!data.isEmpty()) {
					int option = JOptionPane.showConfirmDialog(null,
							"Are you sure you want to rename these files?",
							"Confirm rename?", JOptionPane.YES_NO_OPTION);
					if (option == 0) {
						renameFiles();
						listFiles();
						clearFields();
					}
				}
			}

		});
		add(confirmButton,
				new GBC(5, 6, 1, 1).setInsets(5)
						.setAnchor(GridBagConstraints.LINE_END).setWeight(0, 0)
						.setFill(GridBagConstraints.HORIZONTAL));
	}

	public void createClearButton() {
		clearButton = new JButton("Clear Fields");
		clearButton.setMnemonic('l');
		clearButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clearFields();
			}

		});
		add(clearButton, new GBC(5, 3, 1, 1).setInsets(5).setWeight(0, 0)
				.setAnchor(GridBagConstraints.LINE_END));
	}

	public void createHideExtCheckBox() {
		hideExtCheckBox = new JCheckBox("Hide Extension");
		hideExtCheckBox.setSelected(extHidden);
		hideExtCheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (hideExtCheckBox.getSelectedObjects() == null) {
					extHidden = false;
				} else {
					extHidden = true;
				}
				clearFields();
				listFiles();
			}

		});
		add(hideExtCheckBox,
				new GBC(4, 3, 1, 1).setInsets(5).setAnchor(
						GridBagConstraints.LINE_END));
	}

	// Getters and setters
	public static Panel getPanel() {
		return panel;
	}

	public static void setPanel(Panel panel) {
		Panel.panel = panel;
	}

	public ArrayList<String[]> getData() {
		return data;
	}

	public void setData(ArrayList<String[]> data) {
		this.data = data;
	}

	public JTextField getBeforeTextField() {
		return beforeTextField;
	}

	public void setBeforeTextField(JTextField beforeTextField) {
		this.beforeTextField = beforeTextField;
	}

	public JList getBeforeList() {
		return beforeList;
	}

	public void setBeforeList(JList beforeList) {
		this.beforeList = beforeList;
	}

	public JTextField getAfterTextField() {
		return afterTextField;
	}

	public void setAfterTextField(JTextField afterTextField) {
		this.afterTextField = afterTextField;
	}

	public JTextField getFilePathTextField() {
		return filePathTextField;
	}

	public void setFilePathTextField(JTextField filePathTextField) {
		this.filePathTextField = filePathTextField;
	}

	public JList getAfterList() {
		return afterList;
	}

	public void setAfterList(JList afterList) {
		this.afterList = afterList;
	}

	public JFileChooser getFileChooser() {
		return fileChooser;
	}

	public void setFileChooser(JFileChooser fileChooser) {
		this.fileChooser = fileChooser;
	}

	public JLabel getBeforeLabel() {
		return beforeLabel;
	}

	public void setBeforeLabel(JLabel beforeLabel) {
		this.beforeLabel = beforeLabel;
	}

	public JLabel getAfterLabel() {
		return afterLabel;
	}

	public void setAfterLabel(JLabel afterLabel) {
		this.afterLabel = afterLabel;
	}

	public File getChosenDir() {
		return chosenDir;
	}

	public void setChosenDir(File chosenDir) {
		this.chosenDir = chosenDir;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public boolean isExtHidden() {
		return extHidden;
	}

	public void setExtHidden(boolean extHidden) {
		this.extHidden = extHidden;
	}

	// Functions
	public void listFiles() {
		if (chosenDir == null) {
			return;
		}
		data.clear();
		data = Renamer.getFileList(chosenDir);
		setBeforeList(retrieveListOfFilesFromData(data));
	}

	public void filterFiles() {
		if (data.isEmpty()) {
			return;
		}
		filteredData.clear();
		filteredData = Renamer.getFilteredList(beforeTextField.getText(), data,
				isExtHidden());
		setBeforeList(retrieveListOfFilesFromData(filteredData));
	}

	public void previewFiles() {
		if (data.isEmpty()) {
			return;
		}
		ArrayList<String[]> previewFileList = new ArrayList<String[]>();
		previewFileList = Renamer.getRenamedList(beforeTextField.getText(),
				afterTextField.getText(), filteredData, isExtHidden());
		setAfterList(retrieveListOfFilesFromData(previewFileList));
	}

	public void renameFiles() {
		ArrayList<String[]> renamedFileList = new ArrayList<String[]>();
		renamedFileList = Renamer.getRenamedList(beforeTextField.getText(),
				afterTextField.getText(), filteredData, isExtHidden());
		int status = Renamer.renameFiles(filePathTextField.getText(),
				filteredData, renamedFileList);
		if (status == -1) {
			dialogMsg("Could not rename files!", "Error!",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void setBeforeList(ArrayList<String> list) {
		beforeList.setListData(list.toArray());
	}

	public void setAfterList(ArrayList<String> list) {
		afterList.setListData(list.toArray());
	}

	public ArrayList<String> retrieveListOfFilesFromData(
			ArrayList<String[]> dataList) {
		ArrayList<String> list = new ArrayList<String>();
		if (!dataList.isEmpty()) {
			for (int i = 0; i < dataList.size(); i++) {
				if (dataList.get(i)[1].equals("")) {
					dialogMsg("File name cannot be blank!", "Error!",
							JOptionPane.ERROR_MESSAGE);
					return emptyList;
				}
				if (!extHidden) {
					list.add(dataList.get(i)[0]);
				} else {
					list.add(dataList.get(i)[1]);
				}
			}
		}
		return list;
	}

	public void clearFields() {
		beforeTextField.setText("");
		afterTextField.setText("");
		afterList.setListData(emptyList.toArray());
	}

	public void dialogMsg(String msg, String title, int msgType) {
		JOptionPane.showMessageDialog(panel, msg, title, msgType);
	}

}
