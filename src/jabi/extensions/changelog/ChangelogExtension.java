package jabi.extensions.changelog;

import jabi.extensions.AbstractExtension;
import jabi.model.IEntry;
import jabi.ui.MainWindow;

import java.awt.GridLayout;
import java.text.MessageFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * 0. Eigene Extension erstellen in eigenem Paket
 * 
 * 1. Wie kommt man an die selektierten Einträge?
 * 
 * 2. Fehlermeldung anzeigen, wenn keiner oder mehrere Einträge
 *    selektiert sind.
 *   2.1. Wie funktioniert die JOptionPane?
 *   2.2. Verschiedene Meldungen für verschiedene Fehlerfälle
 *   
 * 3. Erweiterung des Datenmodells -> Achtung Properties!
 *   3.1. Erweiterung IEntry
 *   3.2. Erweiterung AbstractEntry -> final!
 *   -> So  lange die Werte nicht gesetzt werden, soll einfach das
 *      aktuelle Datum und die aktuelle Zeit zurückgegeben werden.
 *   
 * 4. DateFormat-Objekt für Erstellungs- und Änderungsdatum erzeugen
 * 
 * 5. Panel für die Anzeige des Erstellungs- und Änderungsdatums erzeugen
 * 
 * 6. JOptionPane erzeugen
 * 
 * 7. Erweiterung beim ExtensionManager in Jabi#main registrieren
 * 
 * 8. Setzen des Erstellungs- und Änderungsdatums in Event-Handler
 *    einfügen
 *   8.1. AddAction ändern
 *   8.2. EditAction ändern
 *   -> Das Erstellen der Beispieleinträge ist eine eigene Action!
 *   
 * 9. RessourceBundle für die Strings erstellen
 *   9.1. Messageformat für parametrisierten Datums, Zeit String
 */

@SuppressWarnings("serial")
public class ChangelogExtension extends AbstractExtension {

	private static final ResourceBundle bundle = ResourceBundle.getBundle("jabi.extensions.changelog.ChangelogExtension");
	
	public ChangelogExtension() {
		super(bundle.getString("title"));
	}

	@Override
	public void execute() {
		IEntry[] selectedEntries = MainWindow.instance.getSelectedEntries();
		if (selectedEntries.length == 1) {
			IEntry entry = selectedEntries[0];
			Date createDate = entry.creationDate();
			Date changeDate = entry.changeDate();
			MessageFormat mfCreateDate = new MessageFormat(bundle.getString("creationDate"));
			MessageFormat mfChangeDate = new MessageFormat(bundle.getString("modificationDate"));
			
			JPanel datesPanel = new JPanel();
			datesPanel.setLayout(new GridLayout(2, 2, 5, 5));
			JLabel createDateLabel = new JLabel(bundle.getString("created"));
			datesPanel.add(createDateLabel);
			JLabel createDateValue = new JLabel(mfCreateDate.format(new Date[] {createDate, createDate}));
			datesPanel.add(createDateValue);
			JLabel changeDateLabel = new JLabel(bundle.getString("modified"));
			datesPanel.add(changeDateLabel);
			JLabel changeDateValue = new JLabel(mfChangeDate.format(new Date[] {changeDate, changeDate}));
			datesPanel.add(changeDateValue);
			
			JOptionPane.showMessageDialog(MainWindow.instance, datesPanel, bundle.getString("title"), JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			String msg = (selectedEntries.length > 1 ? 
					bundle.getString("errorMultiple") :
					bundle.getString("errorNone"));
			JOptionPane.showMessageDialog(MainWindow.instance, msg, bundle.getString("errorTitle"), JOptionPane.ERROR_MESSAGE);
		}
	}

}
