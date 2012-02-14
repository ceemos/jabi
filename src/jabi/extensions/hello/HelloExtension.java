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
package jabi.extensions.hello;

import jabi.extensions.AbstractExtension;

/**
 * Sample extension that outputs "Hello Extensions" to the console
 */
@SuppressWarnings("serial")
public class HelloExtension extends AbstractExtension {

	public HelloExtension() {
		super("Hello Extension!");
	}
	
	@Override
	public void execute() {
		// actual business logic
		System.out.println("Hello world!");
	}

}
