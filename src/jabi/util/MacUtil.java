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

import jabi.ui.MainWindow;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Image;
import java.io.File;
import java.lang.reflect.Method;

import javax.swing.ImageIcon;

/**
 * Provides power methods for power users! 
 */
/**
 * @author roederhr
 * 
 */
public class MacUtil {

	public static final boolean isMac;

	static {
		String osName = System.getProperty("os.name");
		if ((osName != null) && (osName.toLowerCase().indexOf("mac") != -1)) {
			isMac = true;
		} else {
			isMac = false;
		}
	}

	/**
	 * Initialize Mac-specific settings
	 */
	@SuppressWarnings("unchecked")
	public static void initMac() {
		if (isMac) {
			/* Use MacOS X shared menu */
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			/* Set MacOS X application name (used as application menu label) */
			System.setProperty(
					"com.apple.mrj.application.apple.menu.about.name", "Jabi");
			try {
				/*
				 * Set MacOS X dock icon using reflection (to avoid static
				 * references to mac-only classes)
				 */
				Class applicationClass = Class
						.forName("com.apple.eawt.Application");
				Method getApplicationMethod = applicationClass.getMethod(
						"getApplication", (Class[]) null);
				Object applicationObject = getApplicationMethod.invoke(null);
				Method setDockIconImageMethod = applicationClass.getMethod(
						"setDockIconImage", Image.class);
				Image image = new ImageIcon(MacUtil.class
						.getResource("/jabi/res/jabi_logo.png")).getImage();
				setDockIconImageMethod.invoke(applicationObject, image);
			} catch (Exception e) {
				/* We're on a Mac, but cannot use Apple classes ... */
			}
		}
	}

	/**
	 * Shows the Mac-specific 'File Chooser' dialog
	 * 
	 * @param parent
	 *            Component
	 * @param forSaving
	 *            true if the dialog is for saving; false if the dialog is for
	 *            loading
	 * @return File to be opened/saved
	 */
	public static File chooseFile(Frame parent, boolean forSaving) {
		File file = null;
		System.setProperty("apple.awt.fileDialogForDirectories", Boolean.FALSE
				.toString());
		FileDialog fd = (forSaving ? new FileDialog(parent, "Datei wählen",
				FileDialog.SAVE) : new FileDialog(MainWindow.instance,
				"Datei wählen", FileDialog.LOAD));
		fd.setVisible(true);
		if (fd.getFile() != null) {
			String fileName = fd.getDirectory() + fd.getFile();
			file = new File(fileName);
		}
		return file;
	}

	/**
	 * Shows the Mac-specific 'Directory Chooser' dialog
	 * 
	 * @param parent
	 *            Component
	 * @param forSaving
	 *            true if the dialog is for saving; false if the dialog is for
	 *            loading
	 * @return Directory to be opened/saved
	 */
	public static File chooseDirectory(Frame parent, boolean forSaving) {
		File file = null;
		System.setProperty("apple.awt.fileDialogForDirectories", Boolean.TRUE
				.toString());
		FileDialog fd = (forSaving ? new FileDialog(parent, "Datei wählen",
				FileDialog.SAVE) : new FileDialog(MainWindow.instance,
				"Datei wählen", FileDialog.LOAD));
		fd.setVisible(true);
		if (fd.getFile() != null) {
			String fileName = fd.getDirectory() + fd.getFile();
			file = new File(fileName);
		}
		return file;
	}

}
