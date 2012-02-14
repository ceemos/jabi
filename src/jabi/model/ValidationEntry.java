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

/**
 * One entry of a {@link ValidationResult}. Each entry is a message
 * to a property of a bibliographic entry that is validated. 
 */
public class ValidationEntry {

	/**
	 * Name of the property this entry belongs to. 
	 */
	private String propertyName;
	
	/**
	 * Validation message that is to be displayed on the UI
	 * for the property.
	 */
	private String propertyMessage;
	
	public ValidationEntry (String propertyName, String propertyMessage) {
		this.propertyName = propertyName;
		this.propertyMessage = propertyMessage;
	}

	/**
	 * @return The property this entry belongs to.
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * @return The error message concerning the property
	 */
	public String getPropertyMessage() {
		return propertyMessage;
	}	
	
}
