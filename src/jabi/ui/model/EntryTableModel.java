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
package jabi.ui.model;

import jabi.model.IEntry;
import jabi.model.Model;
import jabi.util.I18N;

import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

/**
 * Provides a model for the Jabi entries table component
 */

/**
 * @author roederhr
 *
 */
@SuppressWarnings("serial")
public class EntryTableModel extends AbstractTableModel implements Observer {

	private Model model;

	public EntryTableModel(Model model) {
		this.model = model;
		model.addObserver(this);
	}

	private final String[] colTitles = new String[] {
			I18N.instance.getMessage("EntryTableModel.TypeLabel"),
			I18N.instance.getMessage("EntryTableModel.IDLabel"),
			I18N.instance.getMessage("EntryTableModel.TitleLabel") };

	/**
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column) {
		return colTitles[column];
	}

	/**
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	/**
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return colTitles.length;
	}

	/**
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return model.getEntries().size();
	}

	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		IEntry entry = model.getEntries().get(rowIndex);
		switch (columnIndex) {
		case 0:
			return entry.getType();
		case 1:
			return entry.getId();
		case 2:
			return entry.getTitle();
		}
		return null;
	}

	/**
	 * Get entry in given row
	 * 
	 * @param rowIndex row
	 * @return entry
	 */
	public IEntry getEntry(int rowIndex) {
		if ((0 <= rowIndex) && (rowIndex < model.getEntries().size())) {
			return model.getEntries().get(rowIndex);
		}
		return null;
	}

	/**
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		/* Something has changed, notify observing table components */
		fireTableDataChanged();
	}

}
