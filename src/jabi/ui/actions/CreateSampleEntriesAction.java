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

import jabi.model.Article;
import jabi.model.Book;
import jabi.model.Model;
import jabi.util.I18N;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Calendar;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

/**
 * Creates some entries for testing purposes.
 */
@SuppressWarnings("serial")
public class CreateSampleEntriesAction extends AbstractAction {

	public CreateSampleEntriesAction() {
		ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/jabi/res/wand.png")));
		putValue(Action.SMALL_ICON, icon);
		putValue(Action.NAME, I18N.instance
				.getMessage("CreateSampleEntriesAction.Label"));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Article a1 = new Article("ck1994",
				"A Metric Suite for Object Oriented Design");
		a1.setAuthor("Shyam R. Chidamber and Chris F. Kemerer");
		a1.setJournal("IEEE Transactions on Software Engineering");
		a1.setVolume("20");
		a1.setNumber("6");
		a1.setYear("1994");
		a1.setPages("476-493");
		a1.creationDate(Calendar.getInstance().getTime());
		a1.changeDate(Calendar.getInstance().getTime());
		Model.instance.addEntry(a1);
		
		Article a2 = new Article("sb1982", "On the Inevitable Intertwining of Specification and Implementation");
		a2.setAuthor("William Swartout and Robert Balzer");
		a2.setJournal("Communications of the ACM");
		a2.setVolume("25");
		a2.setNumber("7");
		a2.setYear("1982");
		a2.setPages("438-440");
		a2.creationDate(Calendar.getInstance().getTime());
		a2.changeDate(Calendar.getInstance().getTime());
		Model.instance.addEntry(a2);

		Book b1 = new Book("brooks1995",
				"The Mythical Man-Month: Essays on Software Engineering (Anniversary Edition)");
		b1.setAuthor("Fred Brooks");
		b1.setPublisher("Addison-Wesley");
		b1.setYear("1995");
		b1.creationDate(Calendar.getInstance().getTime());
		b1.changeDate(Calendar.getInstance().getTime());
		Model.instance.addEntry(b1);
                
                Book b2 = new Book("ludewig2010",
				"Software Engineering");
		b2.setAuthor("Jochen Ludewig, Horst Lichter");
		b2.setPublisher("dpunkt.verlag");
		b2.setYear("2010");
		b2.creationDate(Calendar.getInstance().getTime());
		b2.changeDate(Calendar.getInstance().getTime());
		Model.instance.addEntry(b2);
	}
}
