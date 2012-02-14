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

import jabi.ui.MainWindow;
import jabi.util.I18N;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JButton;

/**
 * Displays the about dialog of Jabi.
 */
@SuppressWarnings("serial")
public class AboutAction extends AbstractAction {

	public AboutAction() {
		ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/jabi/res/help.png")));
		putValue(Action.SMALL_ICON, icon);
		putValue(Action.NAME, I18N.instance.getMessage("AboutAction.Label"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Create dialog
		final JDialog infoDialog = new JDialog(MainWindow.instance,
				I18N.instance.getMessage("AboutAction.Title"), true);
		infoDialog.getContentPane().setLayout(new GridBagLayout());
		// Add header
		JLabel headerText = new JLabel(
				"<html><center><b>&nbsp;<br>&nbsp;Jabi - Java Bibliography Manager&nbsp;</b>"
						+ "<br>&nbsp;</center></html>");
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		infoDialog.getContentPane().add(headerText, gbc);
		// Add Jabi-logo
		ImageIcon jabiLogo = new ImageIcon(Toolkit.getDefaultToolkit()
				.getImage(getClass().getResource("/jabi/res/jabi_logo.png")));
		JLabel jabiLabel = new JLabel(jabiLogo);
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 2;
		infoDialog.getContentPane().add(jabiLabel, gbc);
		// Add copyright text
		JLabel copyright = new JLabel(
				"<html><center>&nbsp;Copyright 2010&nbsp;<br><br>"
						+ "&nbsp;Ivan Bogicevic&nbsp;<br>"
						+ "&nbsp;Markus Knauß&nbsp;<br>"
						+ "&nbsp;Daniel Kulesz&nbsp;<br>"
						+ "&nbsp;Holger Röder&nbsp;<br>"
						+ "&nbsp;Matthias Wetzel&nbsp;<br>&nbsp;</center></html>");
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 3;
		infoDialog.add(copyright, gbc);
		// Add se-logo
		ImageIcon seLogo = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/jabi/res/se_uni_logo.png")));
		JLabel seLabel = new JLabel(seLogo);
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 2;
		infoDialog.getContentPane().add(seLabel, gbc);
		// Add info text
		JLabel infoText = new JLabel("<html><center>"
				+ I18N.instance.getMessage("AboutAction.University") + "<br>"
				+ "&nbsp;" + I18N.instance.getMessage("AboutAction.Department")
				+ "&nbsp;<br>" + "&nbsp;"
				+ I18N.instance.getMessage("AboutAction.Group")
				+ "&nbsp;<br>&nbsp;<br>" + "&nbsp;"
				+ I18N.instance.getMessage("AboutAction.Uses") + "&nbsp;<br>"
				+ "http://www.famfamfam.com/lab/icons/silk/<br>"
				+ "http://www.everaldo.com/crystal/<br>" + "</center></html>");
		infoText.setAlignmentX(java.awt.Container.CENTER_ALIGNMENT);
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 3;
		infoDialog.getContentPane().add(infoText, gbc);
		// Add close-button
		JButton closeButton = new JButton(I18N.instance
				.getMessage("AboutAction.Close"));
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				infoDialog.setVisible(false);
			}
		});
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		infoDialog.add(closeButton, gbc);
		// Add ampty label at bottom right corner
		JLabel emptyLabel = new JLabel("   ");
		gbc = new GridBagConstraints();
		gbc.gridx = 3;
		gbc.gridy = 5;
		infoDialog.getContentPane().add(emptyLabel, gbc);
		// Show dialog
		infoDialog.pack();
		infoDialog.setLocationRelativeTo(MainWindow.instance);
		infoDialog.setVisible(true);
	}

}
