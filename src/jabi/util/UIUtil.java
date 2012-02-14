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

import java.awt.Frame;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

/**
 * @author wetzelms
 *
 */
public class UIUtil {

		
	/**
	 * Initializes the Look and feel; uses Nimbus if possible
	 *  
	 */
	public static void initLnF() {
		try {
			/* Give Nimbus a chance! */
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); 
		} catch (Exception e) {
			// Loading of nimbus failed, falling back to standard l'n'f.
			 try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * Shows the 'File Chooser' dialog; use Mac-specific dialog if on a Mac
	 * 
	 * @param parent
	 *            Component
	 * @param forSaving
	 *            true if the dialog is for saving; false if the dialog is for
	 *            loading
	 * @return File to be opened/saved
	 */
	
	public static File chooseFile(Frame parent, boolean forSaving) {
		if (MacUtil.isMac) {
			return MacUtil.chooseFile(parent, forSaving);
		}
		else {
			JFileChooser fc = getFileChooser();
			int option = JFileChooser.CANCEL_OPTION;
			if (forSaving) {
				option = fc.showSaveDialog(parent);
			}
			else {
				option = fc.showOpenDialog(parent);
			}
			return (option == JFileChooser.APPROVE_OPTION ? fc.getSelectedFile() : null);
		}
	}

	private static JFileChooser fileChooser = null;
	
	/**
	 * Returns a JFileChooser with the filter 'All Files'
	 * @return JFileChooser 
	 */
	private static JFileChooser getFileChooser() {
		if (fileChooser == null) {
			fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new FileFilter() {
				@Override
				public String getDescription() {
					return "Alle Dateien";
				}
				@Override
				public boolean accept(File f) {
					return true;
				}
			});
		}
		return fileChooser;
	}

	/**
	 * Shows the 'Directory Chooser' dialog; use Mac-specific dialog if on a Mac
	 * 
	 * @param parent
	 *            Component
	 * @param forSaving
	 *            true if the dialog is for saving; false if the dialog is for
	 *            loading
	 * @return Directory to be opened/saved
	 */
	public static File chooseDirectory(Frame parent, boolean forSaving) {
		if (MacUtil.isMac) {
			return MacUtil.chooseDirectory(parent, forSaving);
		}
		else {
			JFileChooser fc = getDirectoryChooser();
			int option = JFileChooser.CANCEL_OPTION;
			if (forSaving) {
				option = fc.showSaveDialog(parent);
			}
			else {
				option = fc.showOpenDialog(parent);
			}
			return (option == JFileChooser.APPROVE_OPTION ? fc.getSelectedFile() : null);
		}
	}

	private static JFileChooser directoryChooser = null;
	
	/**
	 * Returns a JFileChooser with the filter 'All Directories'
	 * @return JFileChooser 
	 */
	private static JFileChooser getDirectoryChooser() {
		if (directoryChooser == null) {
			directoryChooser = new JFileChooser();
			directoryChooser.setFileFilter(new FileFilter() {
				@Override
				public String getDescription() {
					return "Alle Verzeichnisse";
				}
				@Override
				public boolean accept(File f) {
					return f.isDirectory();
				}
			});
		}
		return directoryChooser;
	}

}
