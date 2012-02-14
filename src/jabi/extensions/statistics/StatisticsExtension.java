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
package jabi.extensions.statistics;

import jabi.extensions.AbstractExtension;
import jabi.extensions.ExtensionManager;
import jabi.model.Model;

import java.awt.Toolkit;

import javax.swing.Action;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class StatisticsExtension extends AbstractExtension {

	public StatisticsExtension() {
		super("Statistik");
		ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/jabi/res/chart_bar.png")));
		putValue(Action.SMALL_ICON, icon);
		putValue(Action.NAME, "Statistik");
	}
	
	@Override
	public void execute() {
		StatisticsDialog.instance.clearStatistics();
		
		// Update statistics
		StatisticsDialog.instance.addStatistic("Anzahl Einträge", Model.instance.getEntries().size());
		StatisticsDialog.instance.addStatistic("Anzahl Erweiterungen", ExtensionManager.instance.getExtensions().length);
		StatisticsDialog.instance.addStatistic("Ungespeicherte Änderungen", Model.instance.isDirty());
		StatisticsDialog.instance.addStatistic("7er Blöcke an Einträgen", Model.instance.getEntries().size() / 7.);
		// Show the dialog
		
		StatisticsDialog.instance.setVisible(true);
	}

}
