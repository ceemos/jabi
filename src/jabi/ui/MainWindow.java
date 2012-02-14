/*
 * Copyright 2010 Ivan Bogicevic, Markus Knauß, Daniel Kulesz, Holger Röder, Matthias Wetzel
 * 
 * This file is part of Jabi.
 * 
 * Jabi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Jabi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Jabi.  If not, see <http://www.gnu.org/licenses/>.
 */
package jabi.ui;

import jabi.extensions.AbstractExtension;
import jabi.extensions.ExtensionManager;
import jabi.model.IEntry;
import jabi.model.Model;
import jabi.ui.actions.AboutAction;
import jabi.ui.actions.ActionRegistry;
import jabi.ui.actions.AddAction;
import jabi.ui.actions.CreateSampleEntriesAction;
import jabi.ui.actions.DeleteAction;
import jabi.ui.actions.EditAction;
import jabi.ui.actions.ExitAction;
import jabi.ui.actions.ExportAction;
import jabi.ui.actions.LoadAction;
import jabi.ui.actions.SaveAction;
import jabi.ui.actions.SaveAsAction;
import jabi.ui.model.EntryTableModel;
import jabi.util.I18N;
import jabi.util.SwingUtil;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

/**
 * Implements and provides the main window of Jabi's user interface. 
 */
@SuppressWarnings("serial")
public class MainWindow extends JFrame implements Observer {

	/**
	 * Singleton attribute to access this window.
	 */
	public static final MainWindow instance = new MainWindow();

	// Displays all the bibliographic entries
	private JTable entriesTable;
	
	// Holds all the entries that are to be displayed
	private EntryTableModel model;
	
	// Displays the preview of the selected entry
	private JEditorPane previewEditorPane;
	
	// Displays informational messages in the status bar.
	private JLabel statusBarMessageLabel;

	/**
	 * Initializes this window and builds the UI.
	 */
	public MainWindow() {
		setJMenuBar(createMainMenuBar());

		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(createToolBar(), BorderLayout.NORTH);
		contentPane.add(createCenterPane(), BorderLayout.CENTER);
		contentPane.add(createStatusBar(), BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setTitle(I18N.instance.getMessage("MainWindow.Title")); //$NON-NLS-1$

		SwingUtil.centerFrame(this);		

		// Register window as Model observer to show 'dirty status' in window
		// title
		Model.instance.addObserver(this);
	}

	/*
	 * Create the menu bar
	 */
	private JMenuBar createMainMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(createFileMenu());
		menuBar.add(createEditMenu());
		menuBar.add(createExtensionsMenu());
		menuBar.add(createHelpMenu());
		return menuBar;
	}

	/*
	 * Create the extensions menu.
	 */
	private JMenu createExtensionsMenu() {
		JMenu extensionsMenu = new JMenu(I18N.instance.getMessage("MainWindow.ExtensionLabel"));
		/*
		 * The extensions menu's entries are retrieved from the
		 * extension manager.
		 */
		for (AbstractExtension extension : ExtensionManager.instance.getExtensions()) {
			JMenuItem menuItem = new JMenuItem(extension);
			extensionsMenu.add(menuItem);
		}
		return extensionsMenu;
	}

	/*
	 * Create the help menu
	 */
	private JMenu createHelpMenu() {
		JMenu helpMenu = new JMenu(I18N.instance.getMessage("MainWindow.HelpLabel"));
		helpMenu.add(ActionRegistry.instance.getAction(AboutAction.class));
		helpMenu.add(new JSeparator());
		helpMenu.add(ActionRegistry.instance
				.getAction(CreateSampleEntriesAction.class));
		return helpMenu;
	}

	/*
	 * Create the edit menu
	 */
	private JMenu createEditMenu() {
		JMenu editMenu = new JMenu(I18N.instance.getMessage("MainWindow.EditLabel"));
		editMenu.add(ActionRegistry.instance.getAction(AddAction.class));
		editMenu.add(ActionRegistry.instance.getAction(EditAction.class));
		editMenu.add(ActionRegistry.instance.getAction(DeleteAction.class));
		return editMenu;
	}

	/*
	 * Create the file menu
	 */
	private JMenu createFileMenu() {
		JMenu fileMenu = new JMenu(I18N.instance.getMessage("MainWindow.FileLabel"));
		fileMenu.add(ActionRegistry.instance.getAction(LoadAction.class));
		fileMenu.add(ActionRegistry.instance.getAction(SaveAction.class));
		fileMenu.add(ActionRegistry.instance.getAction(SaveAsAction.class));
		fileMenu.add(new JSeparator());
		fileMenu.add(ActionRegistry.instance.getAction(ExportAction.class));
		fileMenu.add(new JSeparator());
		fileMenu.add(ActionRegistry.instance.getAction(ExitAction.class));
		return fileMenu;
	}

	/*
	 * Create the tool bar
	 */
	private JToolBar createToolBar() {
		JToolBar toolBar = new JToolBar(JToolBar.HORIZONTAL);
		toolBar.setFloatable(false);
		toolBar.setMargin(new Insets(5, 5, 5, 5));
		toolBar.setRollover(true);

		JButton addButton = new JButton(ActionRegistry.instance
				.getAction(AddAction.class));
		addButton.setMargin(new Insets(5, 5, 5, 5));
		toolBar.add(addButton);

		JButton editButton = new JButton(ActionRegistry.instance
				.getAction(EditAction.class));
		editButton.setMargin(new Insets(5, 5, 5, 5));
		toolBar.add(editButton);

		JButton deleteButton = new JButton(ActionRegistry.instance
				.getAction(DeleteAction.class));
		deleteButton.setMargin(new Insets(5, 5, 5, 5));
		toolBar.add(deleteButton);

		return toolBar;
	}

	/*
	 * Create the pane that holds the table and the preview window
	 */
	private Component createCenterPane() {
		JSplitPane centerPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		centerPane.setDividerSize(2);
		// We have to use absolute values here; as the pane's size is still
		// unknown, proportional values (e.g. 0.75) cannot be used to correctly
		// calculate the divider location
		centerPane.setDividerLocation(400);
		centerPane.add(createTablePane());
		centerPane.add(createPreviewPane());

		ActionListener action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ;-)
				showMaxPayne();
			}
		};
		KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_M,
				ActionEvent.CTRL_MASK);
		centerPane.registerKeyboardAction(action, keystroke,
				JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

		return centerPane;
	}

	/*
	 * Creates the table in which the managed entries are listed
	 */
	private Component createTablePane() {
		JScrollPane tablePane = new JScrollPane();
		tablePane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		tablePane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		entriesTable = new JTable();

		/* Add selection listener to listen for selection changes in table */
		entriesTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						/*
						 * Enable/disable actions depending on the number of
						 * selected entries; update preview display
						 */
						showSelectionStatus();
						int selectionCount = entriesTable.getSelectedRowCount();
						switch (selectionCount) {
						case 0:
							/* No entry selected */
							previewEditorPane.setText(""); //$NON-NLS-1$
							ActionRegistry.instance.getAction(EditAction.class)
									.setEnabled(false);
							ActionRegistry.instance.getAction(
									DeleteAction.class).setEnabled(false);
							break;
						case 1:
							/* 1 (and only 1) entry selected */
							previewEditorPane.setText(getSelectedEntries()[0]
									.getPreview());
							ActionRegistry.instance.getAction(EditAction.class)
									.setEnabled(true);
							ActionRegistry.instance.getAction(
									DeleteAction.class).setEnabled(true);
							break;
						default:
							/* 2 or more entries selected */
							previewEditorPane.setText(""); //$NON-NLS-1$
							ActionRegistry.instance.getAction(EditAction.class)
									.setEnabled(false);
							ActionRegistry.instance.getAction(
									DeleteAction.class).setEnabled(true);

						}
					}

				});

		/* Add double-click mouse listener */
		entriesTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/* Double-click opens edit window */
				if (e.getClickCount() == 2) {
					Action editAction = ActionRegistry.instance
							.getAction(EditAction.class);
					if (editAction.isEnabled()) {
						editAction.actionPerformed(null);
					}
				}
			}
		});
		entriesTable.setModel(getModel());

		/*
		 * The table row sorter is used to sort entries that are
		 * displayed when the user clicks on a columns title.
		 */
		TableRowSorter<EntryTableModel> sorter = new TableRowSorter<EntryTableModel>();
		entriesTable.setRowSorter(sorter);
		sorter.setModel(getModel());

		entriesTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		entriesTable.getColumnModel().getColumn(0).setPreferredWidth(100);
		entriesTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		entriesTable.getColumnModel().getColumn(2).setPreferredWidth(500);

		tablePane.setViewportView(entriesTable);
		return tablePane;
	}

	/**
	 * @return The model that is display in the table on the UI
	 */
	public EntryTableModel getModel() {
		if (model == null) {
			model = new EntryTableModel(Model.instance);
		}
		return model;
	}

	/**
	 * @return The entries that are selected by the user in the table.
	 */
	public IEntry[] getSelectedEntries() {
		IEntry[] selectedEntries = new IEntry[entriesTable
				.getSelectedRowCount()];
		int index = 0;
		for (int selectedRow : entriesTable.getSelectedRows()) {
			selectedEntries[index++] = model.getEntry(entriesTable
					.convertRowIndexToModel(selectedRow));
		}
		return selectedEntries;
	}

	/*
	 * ;-)
	 */
	private void showMaxPayne() {
		final JDialog maxPayneDialog = new JDialog(this);
		maxPayneDialog.setModal(true);
		maxPayneDialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				maxPayneDialog.setVisible(false);
				super.windowClosing(e);
			}
		});
		Container contentPane = maxPayneDialog.getContentPane();
		contentPane.setLayout(new BorderLayout());
		ImageIcon mpIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/jabi/res/mp.gif")));
		JLabel maxPayneLabel = new JLabel(mpIcon);
		contentPane.add(maxPayneLabel);
		maxPayneDialog.pack();
		SwingUtil.centerDialog(this, maxPayneDialog);
		maxPayneDialog.setVisible(true);
	}

	/*
	 * Creates the preview for a selected entry
	 */
	private Component createPreviewPane() {
		JScrollPane previewPane = new JScrollPane();
		previewPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		previewPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		previewEditorPane = new JEditorPane();

		previewEditorPane.setContentType("text/html");
		previewEditorPane.setEditable(false);
		previewEditorPane.setMargin(new Insets(5, 5, 5, 5));

		previewPane.setViewportView(previewEditorPane);
		return previewPane;
	}

	/**
	 * Create status bar component with status message display
	 * 
	 * @return Status bar component
	 */
	private Component createStatusBar() {
		JPanel statusBar = new JPanel(new BorderLayout());

		/* Create nested panel to hold status message (JLabel) */
		JPanel statusBarMessagePanel = new JPanel(new FlowLayout(
				FlowLayout.LEFT));
		statusBarMessagePanel.setBorder(BorderFactory.createEtchedBorder());

		statusBarMessageLabel = new JLabel();
		statusBarMessageLabel
				.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));

		statusBarMessagePanel.add(statusBarMessageLabel);

		statusBar.add(statusBarMessagePanel, BorderLayout.CENTER);

		/* Calculate appropriate height for status bar */
		FontMetrics fm = getFontMetrics(statusBarMessageLabel.getFont());
		statusBar.setPreferredSize(new Dimension(10, fm.getHeight() * 2));

		/* Initialize status message */
		showSelectionStatus();

		return statusBar;
	}

	/**
	 * Set status bar message
	 * 
	 * @param message to show in the status bar
	 */
	public void setStatusBarMessage(String message) {
		statusBarMessageLabel.setText(message);
	}

	/**
	 * Show selection status (i.e. number of selected entries) in the status bar
	 */
	private void showSelectionStatus() {
		String message = "Status";
		/* Create some useful information to show in the status bar */

		/* Set status message */
		setStatusBarMessage(message);
	}

	/**
	 * This method is called whenever the application's
	 * model ({@link Model}) is updated.
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (Model.instance.getCurrentFile() != null) {
			String title = I18N.instance.getMessage("MainWindow.TitelWithModel", Model.instance.getCurrentFile().getName());
			if (Model.instance.isDirty())
				title += "*";
			setTitle(title);
		}
	}

}