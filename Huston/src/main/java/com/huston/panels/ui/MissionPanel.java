package com.huston.panels.ui;

import java.awt.Dimension;

import javax.swing.JPanel;

public class MissionPanel extends JPanel {

	private static final long serialVersionUID = -2437341675744421579L;
	private int width, height;

	/**
	 * Create the panel.
	 */
	public MissionPanel() {
		super();
		this.width = 1240;
		this.height = 640;
		initilize();
	}

	public MissionPanel(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		initilize();
	}

	public void initilize() {
		createGUI();
	}

	private void createGUI() {
		setPreferredSize(new Dimension(width,height));
	}
}
