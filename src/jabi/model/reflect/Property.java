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

/**
 * Describes a property of a bibliographic entry and
 * associates, if available, a human readable name with it.
 * If the property has no human readable name, the property's
 * name is used for the human readable name. 
 */
public class Property {

	// Name of this property
	private final String name;
	
	// Human readable name of this property
	private String displayName;

	public Property(String name, String displayName) {
		this.name = name;
		this.setDisplayName(displayName);
	}

	public Property(String name) {
		this(name, name);
	}

	/**
	 * @return The name of this property
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the human readable name for this  property.
	 * 
	 * @param displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return The human readable name of this property.
	 */
	public String getDisplayName() {
		return displayName;
	}

}
