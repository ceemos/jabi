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
import jabi.ui.MainWindow;
import jabi.util.I18N;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Deletes the selected entry from Jabi's model.
 */
@SuppressWarnings("serial")
public class DeleteAction extends AbstractAction {

	public DeleteAction() {
		ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/jabi/res/note_delete.png")));
		putValue(Action.SMALL_ICON, icon);
		putValue(Action.NAME, I18N.instance.getMessage("DeleteAction.Label"));
		setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(
				MainWindow.instance, I18N.instance
						.getMessage("DeleteAction.Confirm"), I18N.instance
						.getMessage("DeleteAction.Label"),
				JOptionPane.YES_NO_OPTION)) {
			for (IEntry entry : MainWindow.instance.getSelectedEntries()) {
				Model.instance.removeEntry(entry);
			}
		}
	}

}
