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
package jabi.persistence;

import jabi.model.IEntry;

import java.io.File;

/**
 * Defines the interface that any persistence provider that can
 * be used in Jabi must provide.
 */
public interface IPersistenceProvider {
	
	/**
	 * Stores the given entries in the given file.
	 * 
	 * @param entries to store
	 * @param destinationFile to write the entries to
	 * @throws PersistenceProviderException if storing fails
	 */
	void store(IEntry[] entries, File destinationFile) throws PersistenceProviderException;
	
	/**
	 * Loads bibliopgrahic entries from the given file.
	 * 
	 * @param sourceFile to load the entries from
	 * @return Entries load from the file
	 * @throws PersistenceProviderException if loading fails
	 */
	IEntry[] load(File sourceFile) throws PersistenceProviderException;
	
}
