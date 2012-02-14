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

import jabi.model.Model;
import jabi.ui.MainWindow;
import jabi.util.I18N;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

/**
 * Exits Jabi and shows a confirmation dialog if there are unsaved changes.
 */
@SuppressWarnings("serial")
public class ExitAction extends AbstractAction {

	public ExitAction() {
		putValue(Action.NAME, I18N.instance.getMessage("ExitAction.Label"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (Model.instance.isDirty()) {
			int selection = JOptionPane.showConfirmDialog(MainWindow.instance,
					I18N.instance.getMessage("ExitAction.Confirm"),
					I18N.instance.getMessage("ExitAction.ConfirmTitle"),
					JOptionPane.YES_NO_OPTION);
			if (selection == JOptionPane.NO_OPTION) {
				return;
			}
		}
		MainWindow.instance.setVisible(false);
		MainWindow.instance.dispose();
		System.exit(0);
	}

}
