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
package jabi.ui.actions;

import java.util.HashMap;
import java.util.Map;

import javax.swing.Action;

/**
 * <p>
 * Manages all actions that are available in this application.
 * </p><p>
 * To make an own action available in Jabi it must be registered
 * with this registry using the {@link ActionRegistry#registerAction(Action)}
 * method.
 * </p>
 */
public class ActionRegistry {

	/**
	 * Singleton attribute to access this registry
	 */
	public static final ActionRegistry instance = new ActionRegistry();

	/**
	 * Holds all registered actions
	 */
	private Map<String, Action> actions = new HashMap<String, Action>();
	
	private ActionRegistry() {
	}
	
	/**
	 * Registers a new action with this.
	 * 
	 * @param action to register
	 */
	public void registerAction(Action action) {
		actions.put(action.getClass().getName(), action);
	}
	
	/**
	 * Determines if an action is registered for this action
	 * class.
	 * 
	 * @param clazz to look for a registerd action
	 * @return True if an action is registered for clazz otherwise
	 * false.
	 */
	public boolean hasAction(Class<? extends Action> clazz) {
		return actions.containsKey(clazz.getName());
	}
	
	/**
	 * Returns the action object that is registered for the
	 * given Action class.
	 * 
	 * @param clazz to get action object for
	 * @return Action object for class if it is registered otherwise false
	 */
	public Action getAction(Class<? extends Action> clazz) {
		return actions.get(clazz.getName());
	}

}
