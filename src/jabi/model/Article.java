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
import java.util.List;


/**
 * Provides bibliographic management for articles. 
 */
public class Article extends AbstractEntry {

	private static final long serialVersionUID = 42L;

	public Article() {
		super("article");
	}

	public Article(String articleId, String articleTitle) {
		super("article", articleId, articleTitle);
	}

	// mandatory attributes
	private String author;
	private String journal;
	private String year;

	// optional attributes
	private String volume;
	private String number;
	private String pages;

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
	 * @return the journal
	 */
	public String getJournal() {
		return journal;
	}

	/**
	 * @param journal to set
	 */
	public void setJournal(String journal) {
		this.journal = journal;
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
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the pages
	 */
	public String getPages() {
		return pages;
	}

	/**
	 * @param pages to set
	 */
	public void setPages(String pages) {
		this.pages = pages;
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
            boolean result = true;
            result = validationHelper("author", getAuthor(), "Ein Autor muss gesetzt werden.", ves)
                   & validationHelper("title", getTitle(), "Ein Titel muss gesetzt werden.", ves)
                   & validationHelper("journal", getJournal(), "Eine Zeitschrift muss gesetzt werden.", ves)
                   & validationHelper("year", getYear(), "Ein Jahr muss gesetzt werden.", ves)
                   & validateYear(ves, getYear());
            return new ValidationResult(result, ves);
	}

}
