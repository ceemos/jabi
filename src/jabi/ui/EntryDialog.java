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

import jabi.model.IEntry;
import jabi.model.ValidationEntry;
import jabi.model.ValidationResult;
import jabi.model.reflect.EntryType;
import jabi.model.reflect.EntryTypeManager;
import jabi.model.reflect.Property;
import jabi.util.I18N;
import jabi.util.SwingUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 * Provides a dialog for the UI that is used to enter and modify values of the
 * properties of a bibliographic entry type. The dialog fits itself to the entry
 * type added or edited. This is done by using the reflective services of the
 * data model.
 */
@SuppressWarnings("serial")
public class EntryDialog extends JDialog {

	/*
	 * Holds the fields that are used to enter or modify the values of
	 * properties
	 */
	private Map<String, JTextField> fields = new HashMap<String, JTextField>();

	/**
	 * Singleton access method for this dialog.
	 */
	public static final EntryDialog instance = new EntryDialog();

	/**
	 * Buttons that can be pressed by the user of this dialog.
	 */
	public static enum ButtonPressed {
		OK, CANCEL, NONE,
	}

	/**
	 * Modes in which this dialog can operate.
	 */
	public static enum DialogMode {
		ADD, EDIT,
	}

	/**
	 * Shows this dialog to add a new entry
	 * 
	 * @param entry
	 *            To be added.
	 * @return The button that was pressed when the dialog was closed.
	 */
	public static ButtonPressed showAddDialog(IEntry entry) {
		EntryType entryType = EntryTypeManager.instance.getEntryType(entry);
		EntryDialog.instance.setMode(DialogMode.ADD);
		EntryDialog.instance.setEntry(entryType, entry);
		EntryDialog.instance.setVisible(true);
		return EntryDialog.instance.getButtonPressed();
	}

	/**
	 * Shows this dialog to edit the values of the properties of this entry.
	 * 
	 * @param entry
	 *            To edit the properties of.
	 * @return The button that was pressed when the dialog was closed.
	 */
	public static ButtonPressed showEditDialog(IEntry entry) {
		EntryType entryType = EntryTypeManager.instance.getEntryType(entry);
		EntryDialog.instance.setMode(DialogMode.EDIT);
		EntryDialog.instance.setEntry(entryType, entry);
		EntryDialog.instance.setVisible(true);
		return EntryDialog.instance.getButtonPressed();
	}

	/*
	 * The entries type this dialog operates on
	 */
	private EntryType entryType;

	/*
	 * The entry that is actually added or edited
	 */
	private IEntry entry;

	/*
	 * The mode in which this dialog operates
	 */
	private DialogMode mode;

	/*
	 * The button that was pressed when the dialog was closed
	 */
	private ButtonPressed buttonPressed;

	public EntryDialog() {
		super(MainWindow.instance);
		setModal(true);

		/*
		 * Executed if the window is closed using the window controls rather
		 * than the dialog's buttons.
		 */
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				EntryDialog.instance.setVisible(false);
				EntryDialog.instance.buttonPressed = ButtonPressed.NONE;
				super.windowClosing(e);
			}
		});
	}

	/**
	 * Sets the entry and its meta data that is added or edited by this dialog.
	 * 
	 * @param entryType
	 *            Meta data about this entry
	 * @param entry
	 *            that is added or modified.
	 */
	private void setEntry(EntryType entryType, IEntry entry) {
		this.entryType = entryType;
		this.entry = entry;
	}

	/**
	 * @return The added or edited entry.
	 */
	public IEntry getEntry() {
		return entry;
	}

	/**
	 * @return The button that was pressed when the dialog was closed.
	 */
	public ButtonPressed getButtonPressed() {
		return buttonPressed;
	}

	/**
	 * Sets the mode in which this dialog operates {@link DialogMode}.
	 * 
	 * @param mode
	 *            to set
	 */
	public void setMode(DialogMode mode) {
		this.mode = mode;
	}

	/**
	 * Builds the UI of this dialog.
	 */
	private void buildUI() {
		fields.clear();
		Container contentPane = getContentPane();
		contentPane.removeAll();
		contentPane.setLayout(new BorderLayout(0, 5));

		setTitle(mode == DialogMode.EDIT ? I18N.instance.getMessage(
				"EntryDialog.EditTitle", new Object[] { entryType.getTypeName()
						.getDisplayName() }) : I18N.instance.getMessage(
				"EntryDialog.AddTitle", new Object[] { entryType.getTypeName()
						.getDisplayName() }));

		GradientLabel title = new GradientLabel();
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
		title.setText(entryType.getTypeName().getDisplayName());
		title.setBackground(UIManager.getColor("nimbusBase"));
		title.setForeground(Color.WHITE);
		title.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		contentPane.add(title, BorderLayout.NORTH);

		JPanel fieldsPanel = new JPanel();
		fieldsPanel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = new Insets(0, 5, 5, 5);
		gc.anchor = GridBagConstraints.BASELINE_LEADING;
		gc.weighty = 1.0;

		/*
		 * The entry fields and their labels are built using the meta data that
		 * describes the entry that is actually added or edited.
		 */
		JLabel label = null;
		JTextField field = null;
		for (Property property : entryType.getProperties()) {
			label = new JLabel(property.getDisplayName(), SwingConstants.RIGHT);
			gc.fill = GridBagConstraints.HORIZONTAL;
			gc.gridwidth = 1;
			gc.weightx = 0.0;
			fieldsPanel.add(label, gc);

			field = new JTextField(30);
			field.setName(property.getName());
			fields.put(field.getName(), field);

			if (mode == DialogMode.EDIT) {
				field.setText(entry.getValue(field.getName()));
				field.setSelectionStart(0);
				field.setSelectionEnd(field.getText().length());
			}

			gc.fill = GridBagConstraints.HORIZONTAL;
			gc.gridwidth = GridBagConstraints.REMAINDER;
			gc.weightx = 1.0;
			fieldsPanel.add(field, gc);
		}

		contentPane.add(fieldsPanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JPanel innerButtonPanel = new JPanel();
		innerButtonPanel.setLayout(new GridLayout(1, 0));

		JButton okButton = new JButton(mode == DialogMode.EDIT ? I18N.instance
				.getMessage("EntryDialog.ApplyLabel") : I18N.instance
				.getMessage("EntryDialog.AddLabel"));
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EntryDialog.instance.buttonPressed = ButtonPressed.OK;

				Map<String, String> values = new HashMap<String, String>();
				for (String fieldName : fields.keySet()) {
					values.put(fieldName, fields.get(fieldName).getText()
							.trim());
				}
				EntryDialog.instance.getEntry().setValues(values);

				// after filling in values from the fields, entry-values are
				// checked if valid
				ValidationResult result = EntryDialog.instance.getEntry()
						.validate();
				if (!result.isValid()) {
					StringBuilder errorString = new StringBuilder();
					for (ValidationEntry vEntry : result.getEntries()) {
						errorString.append(vEntry.getPropertyMessage()).append(
								'\n');
					}
					JOptionPane.showConfirmDialog(EntryDialog.this, errorString
							.toString(), I18N.instance
							.getMessage("EntryDialog.ErrorTitle"),
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE);
				} else {
					EntryDialog.instance.setVisible(false);
				}
			}
		});
		innerButtonPanel.add(okButton);

		JButton cancelButton = new JButton(I18N.instance
				.getMessage("EntryDialog.CancelLabel"));
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EntryDialog.instance.setVisible(false);
				EntryDialog.instance.buttonPressed = ButtonPressed.CANCEL;
			}
		});
		innerButtonPanel.add(cancelButton);

		buttonPanel.add(innerButtonPanel);

		contentPane.add(buttonPanel, BorderLayout.SOUTH);

		pack();
	}

	@Override
	public void setVisible(boolean isVisible) {
		if (isVisible) {
			buildUI();
			SwingUtil.centerDialog(MainWindow.instance, this);
		}
		super.setVisible(isVisible);
	}

}
