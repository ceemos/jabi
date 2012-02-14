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

import jabi.model.IEntry;
import jabi.model.reflect.EntryType;
import jabi.model.reflect.EntryTypeManager;
import jabi.model.reflect.Property;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * <p>
 * This filter implements the export to the CSV format. CSV format
 * can be imported by spreadsheets, for example. The format is as
 * follows:
 * </p><p>
 * <tt>VALUE';' VALUE';' ...</tt>
 * </p><p>
 * As the bibliographic entities have different field names, for instance,
 * author, title, and publisher, the field name is exported before the
 * value to the CSV value. The resulting format is:
 * </p><p>
 * <tt>FIELD_NAME'='VALUE';' FIELD_NAME'='VALUE';' ...</tt>
 * </p>
 */
public class CSVExportFilter implements IExportFilter {

	/**
	 * The format to which this filter exports to
	 */
	public static final String FORMAT = "CSV";

	public CSVExportFilter() {
	}

	/**
	 * Exports the given entries to the given file.
	 * @param entries to export
	 * @param outFile file to write the entries to
	 * @throws ExportException if export fails
	 * @see IExportFilter
	 */
	@Override
	public void export(IEntry[] entries, File outFile) throws ExportException {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
			StringBuilder strBldr = new StringBuilder();
			for (IEntry entry : entries) {
				strBldr.delete(0, strBldr.length());
				EntryType entryType = EntryTypeManager.instance
						.getEntryType(entry);
				strBldr.append("Type=");
				strBldr.append(entryType.getTypeName().getName());
				strBldr.append(';');
				for (Property property : entryType.getProperties()) {
					strBldr.append(property.getName());
					strBldr.append('=');
					strBldr.append(entry.getValue(property.getName()));
					strBldr.append(';');
				}
				writer.write(strBldr.toString());
				writer.newLine();
			}
			writer.close();
		} catch (Exception e) {
			throw new ExportException(e);
		}
	}

	/**
	 * @return The format's name to which this filter exports
	 * to.
	 * @see IExportFilter
	 */
	@Override
	public String getFormat() {
		return FORMAT;
	}

}
