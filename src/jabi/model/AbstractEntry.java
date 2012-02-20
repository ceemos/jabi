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

import jabi.model.reflect.EntryTypeManager;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Default implementation of {@link IEntry} that provides
 * standard behavior and reflective services that are needed
 * to provide an extensible data model. 
 */
public abstract class AbstractEntry implements IEntry {

	private static final long serialVersionUID = 42L;

	// The type of an entry is the kind of the bibliographic reference, e. g.
	// book or article.
	private String type;

	// The id is the used shortening of the reference in a text, e. g. at
	// "[Jackson2001]"
	private String id;

	// The title of the bibliographic reference, e. g. "Java for Dummies"
	private String title;

	/**
	 * @param type of the reference, e. g. book or article
	 */
	public AbstractEntry(String type) {
		this.type = type;
	}

	/**
	 * @param type of the reference, e. g. book or article
	 * @param id used to reference the entry, e. g. Jackson2001
	 * @param title of the reference, e. g. "Java for Dummies"
	 */
	public AbstractEntry(String type, String id, String title) {
		this.type = type;
		this.id = id;
		this.title = title;
	}

	/**
	 * @see IEntry#setValues(Map)
	 */
	@Override
	public final void setValues(Map<String, String> values) {
		for (String propertyName : values.keySet()) {
			try {
				Method m = this.getClass().getMethod("set" + propertyName,
						String.class);
				m.invoke(this, values.get(propertyName));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see IEntry#getValue(String)
	 */
	@Override
	public final String getValue(String propertyName) {
		try {
			Method m = this.getClass().getMethod("get" + propertyName);
			return (String) m.invoke(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * This method validates the values that are set for this entry.
	 * For instance, if alternative entries are set only once and if
	 * mandatory entries are set to a value.
	 * 
	 * @return A {@link ValidationResult} object is returned that contains
	 * any validation errors that were detected.
	 */
	@Override
	public ValidationResult validate() {
		return new ValidationResult(true, null);
	}

	/**
	 * Sets the ID of this entry.
	 * @param id to set
	 */
	public final void setId(String id) {
		this.id = id;
	}

	/**
	 * Sets the title for this entry.
	 * @param title to set
	 */
	public final void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @see IEntry#getId()
	 */
	@Override
	public final String getId() {
		return id;
	}

	/**
	 * @see IEntry#getTitle()
	 */
	@Override
	public final String getTitle() {
		return title;
	}

	/**
	 * @see IEntry#getType()
	 */
	@Override
	public final String getType() {
		return type;
	}

	/**
	 * @see jabi.model.IEntry#getPreview()
	 */
	@Override
	public String getPreview() {
		return getTitle();
	}

	private Date creationDate;
	private Date changeDate;
	
	@Override
	public final Date creationDate() {
		if (creationDate == null) {
			return Calendar.getInstance().getTime();
		}
		return creationDate;
	}

	@Override
	public final Date changeDate() {
		if (changeDate == null) {
			return Calendar.getInstance().getTime();
		}
		return changeDate;
	}

	@Override
	public final void creationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public final void changeDate(Date lastChangeDate) {
		this.changeDate = lastChangeDate;
	}

        /**
         * Tests wether a Property is set.
         * @param property name of the Property
         * @param value value to test
         * @param error String to had to the User
         * @param ves List to Store the result in.
         * @return  true, if the froperty is set. If false, a ValidationEntry is added.
         */
    protected boolean validationHelper(String property, String value, String error, List<ValidationEntry> ves) {
        if (value == null || "".equals(value)) {
            ves.add(new ValidationEntry(EntryTypeManager.instance.getEntryType(this).getProperty(property).getDisplayName(), error));
            return false;
        }
        return true;
    }

    /**
     * Tests a Year given as String for validity.
     * @param ves List to Store Results in
     * @param value The Year to check
     * @return true if valid.
     */
    protected boolean validateYear(ArrayList<ValidationEntry> ves, String value) {
        try {
            // it seems there is no better way than trying if a number ist numeric.
            int year = Integer.parseInt(value);
        } catch (NumberFormatException nfe) {
            ves.add(new ValidationEntry(EntryTypeManager.instance.getEntryType(this).getProperty("year").getDisplayName(), "Jahr muss numerisch sein"));
            return false;
        }
        return true;
    }
	
}
