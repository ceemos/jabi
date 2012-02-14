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
package jabi.ui.actions;

import jabi.model.IEntry;
import jabi.model.Model;
import jabi.model.reflect.EntryType;
import jabi.model.reflect.EntryTypeManager;
import jabi.ui.EntryDialog;
import jabi.ui.EntryDialog.ButtonPressed;
import jabi.util.I18N;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Calendar;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Adds a new bibliographic reference to the model of Jabi.
 */
@SuppressWarnings("serial")
public class AddAction extends AbstractAction {

	public AddAction() {
		ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/jabi/res/note_add.png")));
		putValue(Action.SMALL_ICON, icon);
		putValue(Action.NAME, I18N.instance.getMessage("AddAction.Label"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Determine first what entry type to add.
		EntryType[] options = EntryTypeManager.instance.getEntryTypes();
		EntryType selectedEntryType = (EntryType) JOptionPane.showInputDialog(
				null, I18N.instance.getMessage("AddAction.SelectType"), I18N.instance.getMessage("AddAction.Label"),
				JOptionPane.QUESTION_MESSAGE, null, options,
				JOptionPane.OK_OPTION);
		
		// Read the values for the new entry type.
		if (selectedEntryType != null) {
			try {
				IEntry entry = selectedEntryType.getTypeClass().newInstance();
				ButtonPressed result = EntryDialog.showAddDialog(entry);
				if (result == ButtonPressed.OK) {
					IEntry newEntry = EntryDialog.instance.getEntry();
					entry.creationDate(Calendar.getInstance().getTime());
					entry.changeDate(Calendar.getInstance().getTime());
					
					Model.instance.addEntry(newEntry);
				}
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		}
	}

}
