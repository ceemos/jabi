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
import jabi.ui.EntryDialog;
import jabi.ui.MainWindow;
import jabi.ui.EntryDialog.ButtonPressed;
import jabi.util.I18N;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Calendar;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

/**
 * Shows the edit dialog for the first selected entry.
 */
@SuppressWarnings("serial")
public class EditAction extends AbstractAction {

	public EditAction() {
		ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/jabi/res/note_edit.png")));
		putValue(Action.SMALL_ICON, icon);
		putValue(Action.NAME, I18N.instance.getMessage("EditAction.Label"));
		setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		IEntry entry = MainWindow.instance.getSelectedEntries()[0];
		if (entry != null) {
			ButtonPressed result = EntryDialog.showEditDialog(entry);
			if (result == ButtonPressed.OK) {
				entry.changeDate(Calendar.getInstance().getTime());
				
				Model.instance.setDirty(true);
			}
		}
	}

}
