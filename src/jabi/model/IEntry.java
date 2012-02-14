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

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Defines the interface that must be provided by any bibliographic entry
 * that is managed by the Jabi application. 
 */
public interface IEntry extends Serializable {
	
	/**
	 * @return The type of the bibliographic entry. For instance,
	 * book or article.
	 */
	String getType();
	
	/**
	 * @return The id of the entry that is used to reference this
	 * entry. For instance, Ga+96 for the reference to the book
	 * Gamma, E., Helm, E., ...: Design Patterns...
	 */
	String getId();
	
	/**
	 * @return The title of this entry. Each bibliographic entry
	 * has a title.
	 */
	String getTitle();
	
	/**
	 * Sets the values for the properties of this entry.
	 * 
	 * @param values The values to set for the properties. Values
	 * are provided in a map. Each entry in the map, consisting
	 * of key and value, names the property by the key and the
	 * key's value is the value to set for the property.
	 */
	void setValues(Map<String, String> values);

	/**
	 * Returns the value for the named property given as parameter.
	 * 
	 * @param propertyName Name of the property to get the value
	 * from.
	 * @return Value of the named property. If the property does
	 * not exist in this entry <tt>null</tt> is returned.
	 */
	String getValue(String propertyName);

	/**
	 * @return The preview of this entry that is displayed in the UI.
	 */
	String getPreview();
	
	/**
	 * Validates the values set for the properties of this entry.
	 * 
	 * @return {@link ValidationResult} that contains all messages
	 * that are either error or informal messages that are
	 * displayed on the UI.
	 */
	ValidationResult validate();
	
	/*
	 * Setters and getters for the date this entry was created.
	 */
	void creationDate(Date creationDate);
	Date creationDate();
	
	/*
	 * Setters and getters for the date this entry was last modified.
	 */
	void changeDate(Date lastChangeDate);
	Date changeDate();
	
}
