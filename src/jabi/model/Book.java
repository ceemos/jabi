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
package jabi.model;

import jabi.model.reflect.EntryTypeManager;
import java.util.ArrayList;


/**
 * Provides bibligraphy management for books. 
 */
public class Book extends AbstractEntry {

	private static final long serialVersionUID = 42L;

	/* The attributes that are specific for a book.
	 */
	private String author;
	private String editor;
	private String publisher;
	private String year;
	private String volume;
	private String month;

	public Book() {
		super("book");
	}

	public Book(String bookId, String bookTitle) {
		super("book", bookId, bookTitle);
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the editor
	 */
	public String getEditor() {
		return editor;
	}

	/**
	 * @param editor to set
	 */
	public void setEditor(String editor) {
		this.editor = editor;
	}

	/**
	 * @return the publisher
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * @param publisher to set
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the volume
	 */
	public String getVolume() {
		return volume;
	}

	/**
	 * @param volume to set
	 */
	public void setVolume(String volume) {
		this.volume = volume;
	}

	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @param month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * @see jabi.model.AbstractEntry#getPreview()
	 */
	@Override
	public String getPreview() {
		// TODO: Insert code to format the preview in the UI.
		
		return getTitle();
	}

	/**
	 * @see jabi.model.AbstractEntryy#validate()
	 */
	@Override
	public ValidationResult validate() {
            ArrayList<ValidationEntry> ves = new ArrayList<>();
            boolean result = true, editor, author;
            result = validationHelper("publisher", getPublisher(), "Ein Verlag muss gesetzt werden.", ves)
                   & validationHelper("title", getTitle(), "Ein Titel muss gesetzt werden.", ves)
                   & validationHelper("year", getYear(), "Ein Jahr muss gesetzt werden.", ves)
                   & validateYear(ves, getYear())
                   & ((author = validationHelper("author", getAuthor(), "Ein Autor (oder Herausgeber) muss gesetzt werden.", ves))
                    | (editor = validationHelper("editor", getEditor(), "Ein Herausgeber (oder Autor) muss gesetzt werden.", ves)));
            
            if (editor && author) {
                ves.add(new ValidationEntry(EntryTypeManager.instance.getEntryType(this).getProperty("editor").getDisplayName(), "Es darf nur Herausgaber oder Autor angegeben werden."));
            }
            
            return new ValidationResult(result, ves);
	}

}
