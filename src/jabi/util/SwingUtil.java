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

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * Provides utilities for working with Swing.
 */
public class SwingUtil {

	/**
	 * Centers the given frame on the screen
	 * 
	 * @param frame
	 *            to center
	 */
	public static void centerFrame(JFrame frame) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((dim.width - frame.getWidth()) / 2,
				(dim.height - frame.getHeight()) / 2);
	}

	/**
	 * Centers the dialog above the given frame
	 * 
	 * @param frame
	 *            to center the dialog above
	 * @param dialog
	 *            to center above the frame
	 */
	public static void centerDialog(final JFrame frame, JDialog dialog) {
		int parWidth = frame.getWidth();
		int parHeight = frame.getHeight();
		int parX = frame.getLocation().x;
		int parY = frame.getLocation().y;
		Point loc = new Point(((parWidth - dialog.getWidth()) / 2) + parX,
				((parHeight - dialog.getHeight()) / 2) + parY);
		dialog.setLocation(loc);
	}

}
