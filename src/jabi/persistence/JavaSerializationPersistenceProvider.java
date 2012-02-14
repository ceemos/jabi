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
 * Implements persistence by using standard Java object serialization
 */
public class JavaSerializationPersistenceProvider implements IPersistenceProvider {

	/**
	 * @see IPersistenceProvider#store(IEntry[], File)
	 */
	@Override
	public void store(IEntry[] entries,	File destinationFile) throws PersistenceProviderException {
		// TODO: Storing the entries must be implemented here
		throw new PersistenceProviderException();
	}

	/**
	 * @see IPersistenceProvider#load(File)
	 */
	@Override
	public IEntry[] load(File sourceFile)  throws PersistenceProviderException {
		// TODO: Implement the loading of an entries file
		throw new PersistenceProviderException();
	}

}
