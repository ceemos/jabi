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

import java.util.List;

/**
 * Holds all {@link ValidationEntry} objects that are generated by the
 * {@link IEntry#validate()} method.
 */
public class ValidationResult {
	
	/**
	 * Flags if this result is valid. The result is valid if no error
	 * messages are contained but only informational messages.
	 */
	private boolean valid;
	
	/*
	 * List of all entries.
	 */
	private List<ValidationEntry> entries;
	
	public ValidationResult(boolean valid, List<ValidationEntry> entries) {
		this.valid = valid;
		this.entries = entries;
	}

	/**
	 * @return True if this validation result contains no error message
	 * otherwise false is returned.
	 */
	public boolean isValid() {
		return valid;
	}

	
	/**
	 * @return Validation messages stored for this result.
	 */
	public ValidationEntry[] getEntries() {
		return entries.toArray(new ValidationEntry[entries.size()]);
	}
	
}