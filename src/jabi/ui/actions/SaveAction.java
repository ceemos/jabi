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
import jabi.persistence.PersistenceProviderException;
import jabi.persistence.PersistenceService;
import jabi.ui.MainWindow;
import jabi.util.I18N;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Saves the entries in Jabi's model to the standard file set for the model.
 */
@SuppressWarnings("serial")
public class SaveAction extends AbstractAction implements Observer {

	public SaveAction() {
		ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/jabi/res/table_save.png")));
		putValue(Action.SMALL_ICON, icon);
		putValue(Action.NAME, I18N.instance.getMessage("SaveAction.Label"));

		setEnabled((Model.instance.isDirty())
				&& (Model.instance.getCurrentFile() != null));
		Model.instance.addObserver(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		File file = Model.instance.getCurrentFile();
		String message; // captures status bar message
		if (file != null) {
			try {
				List<IEntry> entries = Model.instance.getEntries();
				PersistenceService.instance.getProvider().store(
						entries.toArray(new IEntry[entries.size()]), file);
				message = I18N.instance.getMessage("SaveAction.StatusMessage",
						entries.size(), file.getName());
				MainWindow.instance.setStatusBarMessage(message);

				// Entries have been saved, model is no longer 'dirty'
				Model.instance.setDirty(false);
			} catch (PersistenceProviderException exc) {
				exc.printStackTrace();
				JOptionPane.showMessageDialog(MainWindow.instance,
						I18N.instance.getMessage("SaveAction.Error"),
						I18N.instance.getMessage("SaveAction.ErrorTitle"),
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		setEnabled((Model.instance.isDirty())
				&& (Model.instance.getCurrentFile() != null));
	}

}
