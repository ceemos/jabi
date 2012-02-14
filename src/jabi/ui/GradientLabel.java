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
package jabi.ui;

import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * Provides a label whose background is painted as gradient from
 * left to right. The gradient is rendered from background to
 * foreground color of the label.
 */
public class GradientLabel extends JLabel {
	
	private static final long serialVersionUID = 1447026610649880331L;

	public GradientLabel() {
		super();
	}

	public GradientLabel(Icon image, int horizontalAlignment) {
		super(image, horizontalAlignment);
	}

	public GradientLabel(Icon image) {
		super(image);
	}

	public GradientLabel(String text, Icon icon, int horizontalAlignment) {
		super(text, icon, horizontalAlignment);
	}

	public GradientLabel(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
	}

	public GradientLabel(String text) {
		super(text);
	}

	/**
	 * @see JLabel#paintComponents(Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Rectangle rect = g.getClipBounds();
		Paint gradient = new GradientPaint(rect.x, 0, getBackground(),
				rect.width, 0, getForeground());
		g2d.setPaint(gradient);
		g2d.fillRect(rect.x, rect.y, rect.width, rect.height);
		super.paintComponent(g);
	}
	
}
