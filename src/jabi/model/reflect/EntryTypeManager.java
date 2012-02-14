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
package jabi.model.reflect;

import jabi.model.IEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages all the meta data entries that describe the bibliographic
 * entries that are managed by Jabi. 
 */
public class EntryTypeManager {
	
	/**
	 * Singleton attribute to access this manager
	 */
	public static final EntryTypeManager instance = new EntryTypeManager();
	
	/*
	 * List of all entries managed by this
	 */
	private List<EntryType> entryTypes;

	private EntryTypeManager() {
		entryTypes = new ArrayList<EntryType>();
	}

	/**
	 * Registers a new type for a bibliographic entry that is
	 * managed by Jabi.
	 * 
	 * @param clazz to register
	 */
	public void registerEntryType(Class<? extends IEntry> clazz) {
		EntryType entryType = new EntryType(clazz);
		entryTypes.add(entryType);

	}

	/**
	 * @return All bibliographic entry types that are registered with
	 * this
	 */
	public EntryType[] getEntryTypes() {
		return entryTypes.toArray(new EntryType[entryTypes.size()]);
	}
	
	/**
	 * Return the meta data for the given entry object.
	 * 
	 * @param entryObject to get meta data for 
	 * @return Meta data for the object if its class is
	 * registered. If no meta data is available <code>null</code>
	 * is returned.
	 */
	public EntryType getEntryType(IEntry entryObject) {
		for (EntryType entryType: entryTypes) {
			if (entryType.getTypeClass().equals(entryObject.getClass()))
				return entryType;
		}
		return null;
	}
}
