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
package jabi.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Provides I18N support.
 */
public class I18N {

	/**
	 * Singleton attribute to access an instance of this.
	 */
	public static final I18N instance = new I18N();

	/**
	 * The base name of the file containing the translations.
	 */
	private static final String MESSAGES_BASE_NAME = "jabi.res.Messages";

	/**
	 * The resource bundle that holds the translations for the UI.
	 */
	private final ResourceBundle bundle;

	private I18N() {
		// Load the resource bundle
		bundle = ResourceBundle.getBundle(MESSAGES_BASE_NAME);
	}

	/**
	 * Returns the message that is associated with the given key. Place holders
	 * in the message are replace with the given objects.
	 * 
	 * @param key
	 *            to get the message for
	 * @param values
	 *            to set for place holders in the message.
	 * @return Message with place holders replaced with values
	 */
	public String getMessage(String key, Object... values) {
		String msg = bundle.getString(key);
		if (values.length > 0) {
			return MessageFormat.format(msg, values);
		}
		return msg;
	}

}
