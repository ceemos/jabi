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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Meta class describing the properties of a bibliographic entry. 
 */
public class EntryType {
	// Class of the bibliographic entry
	private Class<? extends IEntry> typeClass;
	
	// Name of the bibliographic entry
	private Property typeName;
	
	// List of properties of this bibliographic entries
	private List<Property> properties;

	/**
	 * Initializes this with the properties of the given class.
	 * 
	 * @param clazz Bibliographic entry to be described by this
	 */
	public EntryType(Class<? extends IEntry> clazz) {
		this.typeClass = clazz;
		this.typeName = new Property(clazz.getSimpleName());
		this.properties = new ArrayList<Property>();
		reflectProperties();
		loadPropertyTranslations();
	}

	/**
	 * Loads the properties of the bibliographic entry that is
	 * described by this.
	 */
	private void reflectProperties() {
		Method[] methods = typeClass.getMethods();

		// The following two loops evaluate all public
		// getters and setters of the given entry. Each getter
		// that has a setter with one parameter with the same
		// type as the return type is recognized to be
		// an attribute.
		Map<String, Class<?>> getters = new HashMap<String, Class<?>>();
		for (Method method : methods) {
			String name = method.getName();
			Class<?> returnType = method.getReturnType();
			int modifiers = method.getModifiers();
			if (name.startsWith("get") && Modifier.isPublic(modifiers)) {
				String attribute = name.substring(3);
				getters.put(attribute, returnType);
			}
		}
		for (Method method : methods) {
			String name = method.getName();
			Class<?>[] paramTypes = method.getParameterTypes();
			int modifiers = method.getModifiers();
			if (name.startsWith("set") && Modifier.isPublic(modifiers)
					&& (paramTypes.length == 1)) {
				String attribute = name.substring(3);
				if (getters.containsKey(attribute)
						&& getters.get(attribute) == paramTypes[0]) {
					properties.add(new Property(attribute));
				}
			}
		}
	}

	/**
	 * Loads the human readable translations for the properties
	 * of the bibliographic entry that are described by this.
	 */
	private void loadPropertyTranslations() {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle(typeClass
					.getName());
			for (Property p : properties) {
				if (bundle.containsKey(p.getName())) {
					p.setDisplayName(bundle.getString(p.getName()));
				}
			}
			if (bundle.containsKey(typeClass.getName())) {
				typeName.setDisplayName(bundle.getString(typeClass.getName()));
			}
		} catch (MissingResourceException e) {
			// There are no translations for this entry.
			// Nothing is to be done.
		}
	}

	/**
	 * @return The properties of the bibliographic entry that
	 * is described by this.
	 */
	public Property[] getProperties() {
		return properties.toArray(new Property[properties.size()]);
	}

	/**
	 * Returns meta data for a specific property.
	 * 
	 * @param propertyName Name of the property to get descriptive
	 * meta data for.
	 * @return Meta data about the property if it exists. If the
	 * property does not exist <code>null</code> is returned.
	 */
	public Property getProperty(String propertyName) {
		Iterator<Property> propertyIterator = properties.iterator();
		while (propertyIterator.hasNext()) {
			Property property = (Property) propertyIterator.next();
			if (propertyName.equalsIgnoreCase(property.getName())) {
				return property;
			}
		}
		return null;
	}

	/**
	 * @return The name of the type described by this
	 */
	public Property getTypeName() {
		return typeName;
	}

	/**
	 * @return The class of the type that is described by this.
	 */
	public Class<? extends IEntry> getTypeClass() {
		return typeClass;
	}

	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		return getTypeName().getDisplayName();
	}

}
