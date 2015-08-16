package com.huston.panels.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.huston.HustonComponent;
import com.huston.jxinput.InputDevice;

public class InputDevicePanel extends JPanel {

	private static final long serialVersionUID = -6085881036178553116L;
	private int width, height;

	/**
	 * Create the panel.
	 */
	public InputDevicePanel() {
		super();
		this.width = 1240;
		this.height = 640;
		initilize();
	}

	public InputDevicePanel(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		initilize();	
	}
	
	public void initilize() {
		createGUI();
	}

	private void createGUI() {
		// --------------------------------------------------------------
		setBounds(0, 0, width, height);
		setBackground(Color.DARK_GRAY);
		setPreferredSize(new Dimension(width, height));
		setLayout(null);
		
		JPanel tabbedPaneOuter = new JPanel();
		tabbedPaneOuter.setBounds(5, 5, width-10, height-10);
		tabbedPaneOuter.setLayout(null);
		tabbedPaneOuter.setBackground(getBackground());
		add(tabbedPaneOuter);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT, JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
		tabbedPane.setBounds(0, -4, width, height);
		tabbedPane.setBackground(getBackground());
		tabbedPaneOuter.add(tabbedPane);
		for(int i = 0;i<HustonComponent.getInputDevices().size();i++) {
			InputDevice inputDevice = HustonComponent.getInputDevices().get(i);
			JPanel inputDevicePanel = new JPanel();
			inputDevicePanel.setBackground(tabbedPane.getBackground());
			tabbedPane.addTab(inputDevice.getDeviceName(), inputDevicePanel);
			
			JPanel virtualDevPanel = inputDevice.getDevicePanel();
			virtualDevPanel.setBackground(inputDevicePanel.getBackground());
			virtualDevPanel.setPreferredSize(new Dimension(width-105,height-15));
			inputDevicePanel.add(virtualDevPanel);
		}
	}
}
