/*
 * Copyright 2012 Marcel Schneider
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

import java.util.ArrayList;

/**
 *
 * @author marcel
 */
public class Inproceedings extends AbstractEntry {

    private static final long serialVersionUID = 42L;

    public Inproceedings() {
        super("inproceedings");
    }

    public Inproceedings(String articleId, String articleTitle) {
        super("inproceedings", articleId, articleTitle);
    }
    // mandatory attributes
    private String author;
    private String booktitle;
    private String year;
    // optional attributes
    private String editor;
    private String address;
    private String organization;
    private String publisher;
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
        boolean result = validationHelper("author", getAuthor(), "Ein Autor muss gesetzt werden.", ves)
                & validationHelper("title", getTitle(), "Ein Titel muss gesetzt werden.", ves)
                & validationHelper("booktitle", getBooktitle(), "Ein Buchtitel muss gesetzt werden.", ves)
                & validationHelper("year", getYear(), "Ein Jahr muss gesetzt werden.", ves)
                & validateYear(ves, getYear());
        return new ValidationResult(result, ves);
    }

    /**
     * @return the editor
     */
    public String getEditor() {
        return editor;
    }

    /**
     * @param editor the editor to set
     */
    public void setEditor(String editor) {
        this.editor = editor;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the organization
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * @param organization the organization to set
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /**
     * @return the publisher
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * @param publisher the publisher to set
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * @return the booktitle
     */
    public String getBooktitle() {
        return booktitle;
    }

    /**
     * @param booktitle the booktitle to set
     */
    public void setBooktitle(String booktitle) {
        this.booktitle = booktitle;
    }
}
