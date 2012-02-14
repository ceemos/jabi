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

/**
 * Common String utilities
 */
public class StringUtil {

	/**
	 * Evaluates infix, in case it is null or an empty String it returns the
	 * infix surrounded by the prefix and suffix. Otherwise am empty String
	 * (without surroundings) is returned.
	 * 
	 * @param prefix
	 * @param infix
	 * @param suffix
	 * @return
	 */
	public static String surroundInfix(String prefix, String infix,
			String suffix) {
		if (infix != null && !infix.equals("")) {
			return prefix + infix + suffix;
		}
		return "";
	}

	/**
	 * Tests if the given string is null or empty
	 * 
	 * @param s
	 *            String
	 * @return true if string is either null or an empty string (i.e. a string
	 *         with length 0 or containing only whitespaces); false otherwise
	 */
	public static boolean nullOrEmpty(String s) {
		return (s == null || s.trim().equals(""));
	}
}
