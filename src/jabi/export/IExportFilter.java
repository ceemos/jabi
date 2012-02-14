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

import java.io.File;

import jabi.model.IEntry;

/**
 * Defines the interface that must be implemented by any export
 * filter that is to be used within the Jabi application.
 */
public interface IExportFilter {

	/**
	 * @return The format to which this export filter can export
	 * entries to. For instance, a filter that writes CSV files
	 * will return "CSV".
	 */
	public String getFormat();

	/**
	 * Exports the given entries to the given file in the format
	 * that is implemented by this filter.
	 * 
	 * @param entries to export to the given file
	 * @param outFile to write the entries to
	 * @throws ExportException is thrown if the export files. For instance,
	 * if writting to the given file is not possible.
	 */
	public void export(IEntry[] entries, File outFile) throws ExportException;

}
