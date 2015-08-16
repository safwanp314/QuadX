package com.huston.panels.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class InfoTabbedPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private int width, height;
	private JTabbedPane tabbedPane;
	
	/**
	 * Create the panel.
	 */
	public InfoTabbedPanel() {
		super();
		this.width = 680;
		this.height = 200;
		initilize();
	}

	public InfoTabbedPanel(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		initilize();
	}

	private void initilize() {
		createGUI();
	}

	private void createGUI() {
		setBounds(0, 0, width, height);
		setPreferredSize(new Dimension(width,height));
		setBackground(Color.DARK_GRAY);
		setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setName("infoTabbedPane");
		tabbedPane.setBounds(-3, -3, width+6, height+6);
		tabbedPane.setBackground(getBackground());
		add(tabbedPane);
	}
	
	public void addTab(String title, Component component) {
		tabbedPane.addTab(title, component);
	}
	
	public void addTab(String title, Icon icon, Component component) {
		tabbedPane.addTab(title,icon,component);
	}
	
	public void addTab(String title, Icon icon, Component component, String tooltip) {
		tabbedPane.addTab(title,icon,component,tooltip);
	}
	
	public void setBackgroundAt(int index, Color background) {
		tabbedPane.setBackgroundAt(index, background);
	}
}
