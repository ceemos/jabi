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
package jabi.extensions;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages all (future) 3rd party extensions available in Jabi
 */
public class ExtensionManager {
	
	public static final ExtensionManager instance = new ExtensionManager();
	
	private List<AbstractExtension> extensions;
	
	private ExtensionManager() {
		extensions = new ArrayList<AbstractExtension>();
	}
	
	/**
	 * Registers the given extension in Jabi
	 * @param extension the extension to register
	 */
	public void register(AbstractExtension extension) {
		if (extension != null) {
			extensions.add(extension);
		}
	}
	
	/**
	 * Obtains a list of all currently available extensions
	 * @return all currently available extensions
	 */
	public AbstractExtension[] getExtensions() {
		return extensions.toArray(new AbstractExtension[extensions.size()]); 
	}

}
