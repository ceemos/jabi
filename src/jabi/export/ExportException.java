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
package jabi.export;

/**
 * This exception is thrown whenever exporting bibliographic
 * entries to a file fails.
 */
@SuppressWarnings("serial")
public class ExportException extends Exception {

	/**
	 * @see Exception
	 */
	public ExportException() {
	}

	/**
	 * @see Exception
	 */
	public ExportException(String message) {
		super(message);
	}

	/**
	 * @see Exception
	 */
	public ExportException(Throwable cause) {
		super(cause);
	}

	/**
	 * @see Exception
	 */
	public ExportException(String message, Throwable cause) {
		super(message, cause);
	}

}
