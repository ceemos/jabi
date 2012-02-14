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

import java.util.HashMap;
import java.util.Map;

/**
 * This class manages the export filters that are available for
 * use in the application. To make an own custom export filter
 * available to be used in the application it must be registered
 * with this class by using the {@see ExportService#register(IExportFilter)}
 * method.
 */
public class ExportService {

	/**
	 * Singleton variable
	 */
	public static final ExportService instance = new ExportService();

	private ExportService() {
	}

	/* Holds all registered export filters.
	 */
	private Map<String, IExportFilter> filters = new HashMap<String, IExportFilter>();

	/**
	 * Registers an export filter. After registering it can be used
	 * in the Jabi application through the UI.
	 * 
	 * @param filter to register to be useable
	 */
	public void register(IExportFilter filter) {
		if (filter != null)
			filters.put(filter.getFormat(), filter);
	}

	/**
	 * @return The formats to which the registered export filters
	 * can export to.
	 */
	public String[] getFormats() {
		return filters.keySet().toArray(new String[filters.keySet().size()]);
	}

	public IExportFilter getFilter(String type) {
		return filters.get(type);
	}

}
