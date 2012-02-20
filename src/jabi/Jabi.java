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
package jabi;

import jabi.export.CSVExportFilter;
import jabi.export.ExportService;
import jabi.extensions.ExtensionManager;
import jabi.extensions.changelog.ChangelogExtension;
import jabi.extensions.hello.HelloExtension;
import jabi.extensions.statistics.StatisticsExtension;
import jabi.model.Article;
import jabi.model.Book;
import jabi.model.Inproceedings;
import jabi.model.reflect.EntryTypeManager;
import jabi.ui.MainWindow;
import jabi.ui.actions.AboutAction;
import jabi.ui.actions.ActionRegistry;
import jabi.ui.actions.AddAction;
import jabi.ui.actions.CreateSampleEntriesAction;
import jabi.ui.actions.DeleteAction;
import jabi.ui.actions.EditAction;
import jabi.ui.actions.ExitAction;
import jabi.ui.actions.ExportAction;
import jabi.ui.actions.LoadAction;
import jabi.ui.actions.SaveAction;
import jabi.ui.actions.SaveAsAction;
import jabi.util.MacUtil;
import jabi.util.UIUtil;

import javax.swing.SwingUtilities;

/**
 * Jabi is an application for managing bibliographic references. The
 * management is done using a graphical user interface (UI) that is
 * implemented in Swing.  
 */
public class Jabi {

	/**
	 * Initializes the application, configures it, and
	 * starts up the UI.
	 * 
	 * @param args Command line arguments are not evaluated.
	 */
	public static void main(String[] args) {
		// Initialize Mac specific settings
		MacUtil.initMac();
		
		// Initialize the look and feel that is used by the UI
		UIUtil.initLnF();
		
		/* Configure the available actions that will respond to
		 * events that are issued through the UI.
		 */
		ActionRegistry.instance.registerAction(new AddAction());
		ActionRegistry.instance.registerAction(new EditAction());
		ActionRegistry.instance.registerAction(new DeleteAction());
		ActionRegistry.instance.registerAction(new SaveAction());
		ActionRegistry.instance.registerAction(new SaveAsAction());
		ActionRegistry.instance.registerAction(new LoadAction());
		ActionRegistry.instance.registerAction(new ExportAction());
		ActionRegistry.instance.registerAction(new AboutAction());
		ActionRegistry.instance.registerAction(new ExitAction());
		ActionRegistry.instance.registerAction(new CreateSampleEntriesAction());

		/* Configure all the reference types that can be managed with
		 * this application.
		 */
		EntryTypeManager.instance.registerEntryType(Book.class);
		EntryTypeManager.instance.registerEntryType(Article.class);
                EntryTypeManager.instance.registerEntryType(Inproceedings.class);
		
		/* Configure the filters that are available to export the
		 * managed references to other formats.
		 */
		ExportService.instance.register(new CSVExportFilter());
		
		/* Configure the extensions that implement user specific
		 * behavior. Each extension is available to be used through
		 * the UI.
		 */
		ExtensionManager.instance.register(new HelloExtension());
		ExtensionManager.instance.register(new StatisticsExtension());
		ExtensionManager.instance.register(new ChangelogExtension());

		/* Start up the UI. This is done using SwingUtilities
		 * which assigns the UI to the correct UI rendering
		 * thread.
		 */
		Runnable ui = new Runnable() {
			@Override
			public void run() {
				MainWindow.instance.setVisible(true);
			}
		};
		SwingUtilities.invokeLater(ui);
	}

}
