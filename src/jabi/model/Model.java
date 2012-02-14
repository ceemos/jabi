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
package jabi.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * <p>
 * The data model of this application. Holds all bibliographic
 * entries.
 * </p><p>
 * This data model is observable. Any observer that registers
 * itself with this model will be notified if values in the
 * model are modified.
 * </p> 
 * <p>
 * To learn about registering yourself as observer see
 * {@link Observable} and {@link Observer}.
 * </p>
 */
public class Model extends Observable implements Serializable {

	/* TODO: Extract an interface that provides the basic
	 *       model methods. The aspect for saving and loading
	 *       the model - currentFile attribute - should be
	 *       externalized to a decorator.
	 *       
	 *       To realize this the singleton used for this model
	 *       must be refactored to a facade or a factory.
	 */
	
	private static final long serialVersionUID = 42L;

	/**
	 * Singleton field to access this model.
	 */
	public final static Model instance = new Model();

	/*
	 * Holds all bibliographic entries.
	 */
	private List<IEntry> entries;
	
	/*
	 * Flags if this model was changed.
	 */
	private transient boolean dirty;
	
	/*
	 * Holds the file to which this model is written to if it
	 * is to be saved.
	 */
	private transient File currentFile;

	private Model() {
		entries = new ArrayList<IEntry>();
		setCurrentFile(null);
		dirty = false;
	}

	/**
	 * @return True if this model was changed otherwise false.
	 */
	public boolean isDirty() {
		return dirty;
	}
	
	/**
	 * @param dirty Sets the dirty flag to the given value.
	 * If the dirty flag is set to true and was before false
	 * all all observers of this model are notified about
	 * the model change.
	 */
	public void setDirty(boolean dirty) {
		if ((dirty) || (dirty != this.dirty)) {
			this.dirty = dirty;
			setChanged();
			notifyObservers();
		}
	}
	
	/**
	 * @return Stored bibliographic entries
	 */
	public List<IEntry> getEntries() {
		return entries;
	}

	/**
	 * @param entries to store
	 */
	public void setEntries(List<IEntry> entries) {
		this.entries = entries;
		setDirty(true);
	}

	/**
	 * @param entry to add to this model
	 */
	public void addEntry(IEntry entry) {
		entries.add(entry);
		setDirty(true);
	}

	/**
	 * @param entry to remove from this model
	 */
	public void removeEntry(IEntry entry) {
		entries.remove(entry);
		setDirty(true);
	}

	/**
	 * Sets the file to which this model is saved to.
	 * 
	 * @param currentFile File to save this model to.
	 */
	public void setCurrentFile(File currentFile) {
		this.currentFile = currentFile;
		setChanged();
		notifyObservers();
	}

	/**
	 * @return The file to which this model is saved to.
	 * If no file is set for this model <tt>null</tt> is
	 * returned.
	 */
	public File getCurrentFile() {
		return currentFile;
	}

}
