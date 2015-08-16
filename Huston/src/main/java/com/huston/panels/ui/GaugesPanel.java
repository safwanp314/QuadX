package com.huston.panels.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import com.huston.panels.gauges.AltitudeGaugePanel;
import com.huston.panels.gauges.SpeedGaugePanel;

public class GaugesPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private int width, height;
	private int numOfGauges = 4,gaugeSize;
	/**
	 * Create the panel.
	 */
	public GaugesPanel() {
		super();
		this.width = 680;
		this.height = 170;
		calculateGaugeSize(numOfGauges);		
		createGUI();
		setPreferredSize(new Dimension(width,height));
	}
	
	public GaugesPanel(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		calculateGaugeSize(numOfGauges);
		createGUI();
		setPreferredSize(new Dimension(width,height));
	}
	
	public void calculateGaugeSize(int numOfGauges) {
		gaugeSize = (int) width/numOfGauges - 4;
	}
	
	private void createGUI() {
		setBounds(0, 0, width, height);
		setBackground(Color.DARK_GRAY);
		setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));
		//--------------------------------------------------------------
		JPanel speedGaugeOuter = new JPanel();
		speedGaugeOuter.setBackground(getBackground());
		speedGaugeOuter.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 4));
		add(speedGaugeOuter);
		
		JPanel speedGaugePanel = new SpeedGaugePanel(gaugeSize);
		speedGaugeOuter.add(speedGaugePanel);
		speedGaugePanel.setBackground(speedGaugeOuter.getBackground());
		//--------------------------------------------------------------
		JPanel altitudeGaugeOuter = new JPanel();
		altitudeGaugeOuter.setBackground(getBackground());
		altitudeGaugeOuter.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 4));
		add(altitudeGaugeOuter);
		
		JPanel altitudeGaugePanel = new AltitudeGaugePanel(gaugeSize);
		altitudeGaugeOuter.add(altitudeGaugePanel);
		altitudeGaugePanel.setBackground(altitudeGaugeOuter.getBackground());
		//--------------------------------------------------------------
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(getBackground());
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 4));
		add(panel_2);
		
		JPanel panel = new AltitudeGaugePanel(gaugeSize);
		panel_2.add(panel);
		panel.setBackground(panel_2.getBackground());
		//--------------------------------------------------------------
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(getBackground());
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 4));
		add(panel_3);
		
		JPanel panel_1 = new AltitudeGaugePanel(gaugeSize);
		panel_3.add(panel_1);
		panel_1.setBackground(panel_3.getBackground());
		//--------------------------------------------------------------
	}
}
