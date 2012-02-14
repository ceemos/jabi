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
package jabi.extensions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * Provides standard behavior and reflective services that are needed
 * to provide an extension model. 
 */
@SuppressWarnings("serial")
public abstract class AbstractExtension extends AbstractAction {


	@SuppressWarnings("unused")
	private AbstractExtension() {
		// Creating new extensions without specifying a name is not allowed
	}
	
	public AbstractExtension(String name) {		
		putValue(Action.NAME, name);
	}
	
	public String getName() {
		return (String) getValue(Action.NAME);
	}
	
	@Override
	public final void actionPerformed(ActionEvent e) {
		execute();
	}
	
	/**
	 * Main entry point for the initialization method of your Jabi extension
	 */
	public abstract void execute();

}
