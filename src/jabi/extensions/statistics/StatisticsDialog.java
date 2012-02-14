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
package jabi.extensions.statistics;

import jabi.ui.MainWindow;
import jabi.util.I18N;
import jabi.util.SwingUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * <p>
 * This dialog shows some information about the entries managed by
 * Jabi. Statistical information to be displayed can be added to this dialog
 * by calling any of the <code>addStatistics</code> methods.
 * </p><p>
 * To display this dialog call 
 * <code>{@link StatisticsDialog#instance#setVisible(boolean)}</code>
 * with the parameter set to true.
 * </p>
 * </p><p>
 * <em>Note:</em> Added statistic entries are kept as long they are
 * not cleared using the {@link StatisticsDialog#clearStatistics()} method.
 * </p>
 */
@SuppressWarnings("serial")
public class StatisticsDialog extends JDialog {

	/*
	 * Holds all statistics that is displayed
	 */
	private Map<String, String> statistics = new LinkedHashMap<String, String>();

	/**
	 * Singleton attribute to access this dialog
	 */
	public static final StatisticsDialog instance = new StatisticsDialog();

	public StatisticsDialog() {
		super(MainWindow.instance);
		setModal(true);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				StatisticsDialog.instance.setVisible(false);
				super.windowClosing(e);
			}
		});
	}

	/**
	 * Adds a new string value to be displayed on this statistics dialog.
	 * 
	 * @param name of the value
	 * @param value to display
	 */
	public void addStatistic(String name, String value) {
		statistics.put(name, value);
	}

	/**
	 * Adds a new integer value to be displayed on this statistics dialog.
	 * 
	 * @param name of the value
	 * @param value to display
	 */
	public void addStatistic(String name, Integer value) {
		addStatistic(name, value.toString());
	}

	/**
	 * Adds a new double value to be displayed on this statistics dialog.
	 * 
	 * @param name of the value
	 * @param value to display
	 */
	public void addStatistic(String name, Double value) {
		addStatistic(name, value.toString());
	}

	/**
	 * Adds a new boolean value to be displayed on this statistics dialog.
	 * 
	 * @param name of the value
	 * @param value to display
	 */
	public void addStatistic(String name, Boolean value) {
		addStatistic(name, value ? 
				I18N.instance.getMessage("StatisticsDialog.YesLabel") : 
				I18N.instance.getMessage("StatisticsDialog.NoLabel"));
	}
	
	/**
	 * Removes all statistics displayed on this dialog.
	 */
	public void clearStatistics() {
		statistics.clear();
	}

	/*
	 * Builds the UI of this dialog
	 */
	private void buildUI() {
		// Reset the window
		Container contentPane = getContentPane();
		contentPane.removeAll();
		contentPane.setLayout(new BorderLayout(0, 5));
		setTitle(I18N.instance.getMessage("StatisticsDialog.StatisticsLabel"));

		// Set fancy header and styling
		JLabel title = new JLabel() {
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
		};
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
		title.setText(I18N.instance.getMessage("StatisticsDialog.StatisticsLabel"));
		title.setBackground(UIManager.getColor("nimbusBase")); //$NON-NLS-1$
		title.setForeground(Color.WHITE);
		title.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		contentPane.add(title, BorderLayout.NORTH);

		// Set basic layout
		JPanel fieldsPanel = new JPanel();
		fieldsPanel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = new Insets(0, 5, 5, 5);
		gc.anchor = GridBagConstraints.BASELINE_LEADING;
		gc.weighty = 1.0;

		// Show all statistic pairs (name and value of every statistic)
		JLabel nameLabel = null;
		JLabel valueLabel = null;
		for (Iterator<String> iterator = statistics.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			nameLabel = new JLabel(key);
			gc.fill = GridBagConstraints.HORIZONTAL;
			gc.gridwidth = 1;
			gc.weightx = 0.0;
			fieldsPanel.add(nameLabel, gc);

			String value = statistics.get(key);
			valueLabel = new JLabel(value);
			gc.fill = GridBagConstraints.HORIZONTAL;
			gc.gridwidth = GridBagConstraints.REMAINDER;
			gc.weightx = 1.0;
			fieldsPanel.add(valueLabel, gc);
		}

		// Add the close button
		contentPane.add(fieldsPanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JPanel innerButtonPanel = new JPanel();
		innerButtonPanel.setLayout(new GridLayout(1, 0));

		JButton closeButton = new JButton(I18N.instance.getMessage("StatisticsDialog.CloseLabel"));
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StatisticsDialog.instance.setVisible(false);
			}
		});
		innerButtonPanel.add(closeButton);
		buttonPanel.add(innerButtonPanel);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);

		pack();
	}

	/**
	 * @see JDialog#setVisible(boolean) 
	 */
	@Override
	public void setVisible(boolean isVisible) {
		if (isVisible) {
			buildUI();
			SwingUtil.centerDialog(MainWindow.instance, this);
		}
		super.setVisible(isVisible);
	}

}