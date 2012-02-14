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
import jabi.util.UIUtil;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Loads bibliographic entries from a file.
 */
@SuppressWarnings("serial")
public class LoadAction extends AbstractAction {

	public LoadAction() {
		ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/jabi/res/book_open.png")));
		putValue(Action.SMALL_ICON, icon);
		putValue(Action.NAME, I18N.instance.getMessage("LoadAction.Label"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// The warning message that all existing entries are overwritten
		// is only showed if there are any entries.
		int selection = JOptionPane.YES_OPTION;
		if (Model.instance.getEntries().size() > 0) {
			selection = JOptionPane.showConfirmDialog(MainWindow.instance,
					I18N.instance.getMessage("LoadAction.Confirm"),
					I18N.instance.getMessage("LoadAction.ConfirmTitle"),
					JOptionPane.YES_NO_OPTION);
		}

		if (JOptionPane.YES_OPTION == selection) {
			File file = UIUtil.chooseFile(MainWindow.instance, false);
			String message;
			if (file != null) {
				try {
					IEntry[] loadedEntries = PersistenceService.instance
							.getProvider().load(file);
					Model.instance.setEntries(new ArrayList<IEntry>(Arrays
							.asList(loadedEntries)));
					message = I18N.instance.getMessage(
							"LoadAction.StatusMessage", loadedEntries.length,
							file.getName());
					MainWindow.instance.setStatusBarMessage(message);
					Model.instance.setCurrentFile(file);
					Model.instance.setDirty(false);
				} catch (PersistenceProviderException exc) {
					exc.printStackTrace();
					JOptionPane.showMessageDialog(MainWindow.instance,
							I18N.instance.getMessage("LoadAction.Error"),
							I18N.instance.getMessage("LoadAction.ErrorTitle"),
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}
