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

import jabi.export.ExportService;
import jabi.export.IExportFilter;
import jabi.model.IEntry;
import jabi.model.Model;
import jabi.ui.MainWindow;
import jabi.util.I18N;
import jabi.util.UIUtil;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Shows the export selector and calls the selected export filter to write the
 * entries to another format.
 */
@SuppressWarnings("serial")
public class ExportAction extends AbstractAction implements Observer {

	public ExportAction() {
		ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/jabi/res/table_go.png")));
		putValue(Action.SMALL_ICON, icon);
		putValue(Action.NAME, I18N.instance.getMessage("ExportAction.Label"));
		setEnabled(Model.instance.getEntries().size() > 0);
		Model.instance.addObserver(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Select from the available export formats
		Object[] exportFormats = ExportService.instance.getFormats();
		if (exportFormats.length >= 1) {
			Object exportFormat = JOptionPane.showInputDialog(
					MainWindow.instance, I18N.instance
							.getMessage("ExportAction.SelectFilter"),
					I18N.instance.getMessage("ExportAction.SelectFilterTitle"),
					JOptionPane.INFORMATION_MESSAGE, null, exportFormats,
					exportFormats[0]);
			IExportFilter exportFilter = ExportService.instance
					.getFilter((String) exportFormat);
			if (exportFilter != null) {
				// Show the file chooser to select the file to write to
				File file = UIUtil.chooseFile(MainWindow.instance, true);
				if (file != null) {
					// Write the bibliographic entries to the selected
					// file
					try {
						IEntry[] entries = (IEntry[]) Model.instance
								.getEntries().toArray(
										new IEntry[Model.instance.getEntries()
												.size()]);
						exportFilter.export(entries, file);
						String message = I18N.instance.getMessage(
								"ExportAction.StatusMessage", entries.length,
								file.getName());
						MainWindow.instance.setStatusBarMessage(message);
					} catch (Exception exc) {
						JOptionPane.showMessageDialog(MainWindow.instance,
								I18N.instance.getMessage("ExportAction.Error"),
								I18N.instance
										.getMessage("ExportAction.ErrorTitle"),
								JOptionPane.ERROR_MESSAGE);
						exc.printStackTrace();
					}
				}
			} else {
				JOptionPane.showMessageDialog(MainWindow.instance,
						I18N.instance.getMessage("ExportAction.FilterError"),
						I18N.instance
								.getMessage("ExportAction.FilterErrorTitle"),
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(MainWindow.instance, I18N.instance
					.getMessage("ExportAction.NoFilter"), I18N.instance
					.getMessage("ExportAction.NoFilterTitle"),
					JOptionPane.ERROR_MESSAGE);
		}

	}

	@Override
	public void update(Observable o, Object arg) {
		setEnabled(Model.instance.getEntries().size() > 0);
	}

}
