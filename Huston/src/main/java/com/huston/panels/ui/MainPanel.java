package com.huston.panels.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.net.URISyntaxException;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainPanel extends JPanel {

	private static final long serialVersionUID = -1683081442710808887L;
	private int width, height;
	/**
	 * Create the panel.
	 */
	public MainPanel() {
		super();
		this.width = 1240;
		this.height = 640;
		initilize();
	}
	
	public MainPanel(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		initilize();	
	}
	
	private void initilize() {
		createGUI();
	}

	private void createGUI() {
		//--------------------------------------------------------------
		setBounds(0, 0, width, height);
		setBackground(Color.DARK_GRAY);
		setLayout(null);
		setPreferredSize(new Dimension(width,height));
		//--------------------------------------------------------------
		InfoTabbedPanel infoTabbedPane = new InfoTabbedPanel(710,220);
		infoTabbedPane.setLocation(520, 5);
		add(infoTabbedPane);
		
		GaugesPanel guagesPanel = new GaugesPanel(700,190);
		guagesPanel.setBackground(infoTabbedPane.getBackground());
		infoTabbedPane.addTab("GAUGES", null, guagesPanel, null);
		infoTabbedPane.setBackgroundAt(0, Color.LIGHT_GRAY);
		
		StatusPanel statusPanel = new StatusPanel(700,190,7,4);
		statusPanel.setBackground(infoTabbedPane.getBackground());
		infoTabbedPane.addTab("STATUS", null, statusPanel, null);
		infoTabbedPane.setBackgroundAt(1, Color.LIGHT_GRAY);
		
		LogPanel logPanel = new LogPanel(700,190);
		logPanel.setBackground(infoTabbedPane.getBackground());
		infoTabbedPane.addTab("LOG", null, logPanel, null);
		infoTabbedPane.setBackgroundAt(2, Color.LIGHT_GRAY);
		//--------------------------------------------------------------
		JPanel frontCameraPanel = new JPanel();
		frontCameraPanel.setBackground(Color.LIGHT_GRAY);
		frontCameraPanel.setBounds(10, 5, 500, 300);
		add(frontCameraPanel);
		
		JPanel bottomCameraPanel = new JPanel();
		bottomCameraPanel.setBackground(Color.LIGHT_GRAY);
		bottomCameraPanel.setBounds(10, 315, 500, 300);
		add(bottomCameraPanel);
		
		final LocationGaugePanel locationMapPanel = new LocationGaugePanel(625,385);
		locationMapPanel.setBackground(Color.LIGHT_GRAY);
		locationMapPanel.setLocation(575, 230);
		add(locationMapPanel);
		SwingUtilities.invokeLater(new Runnable() {
		public void run() {
			try {
				String url = getClass().getResource("/map.html").toURI().toString();
				((LocationGaugePanel)locationMapPanel).loadURL(url);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}});
		//--------------------------------------------------------------
	}
}
