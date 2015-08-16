package com.huston.frames;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.huston.panels.tabs.MainTabPanel;
import com.huston.panels.ui.DroneStatPanel;
import com.huston.panels.ui.InputDevicePanel;
import com.huston.panels.ui.MainPanel;
import com.huston.panels.ui.MissionPanel;

public class ControlFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JPanel tabContenet;
	private JPanel mainTab, missionTab, inputDeviceTab;
	private MainTabPanel tabContol;
	private int width, height;
	private DroneStatPanel droneStatPanel;
	/**
	 * Create the frame.
	 */
	public ControlFrame() {
		super();
		this.width = 1280;
		this.height = 860;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initilize();
		setVisible(true);
	}
	
	public ControlFrame(String title) {
		super(title);
		this.width = 1280;
		this.height = 860;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initilize();
		setVisible(true);
	}
	
	public void initilize() {
		initilizeDrone();
		createGUI();
	}
	
	private void createGUI() {
		//-----------------------------------------------------------------
		//-----------------------------------------------------------------
		setBounds(0, 0, width, height-90);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setLayout(null);
		setContentPane(contentPane);
		//-----------------------------------------------------------------			
		droneStatPanel = new DroneStatPanel(120,80);
		droneStatPanel.setLocation(width-160, 10);
		contentPane.add(droneStatPanel);
		//-----------------------------------------------------------------
		tabContenet = new JPanel();
		tabContenet.setBounds(10, 97, width-40, height-230);
		tabContenet.setBorder(new LineBorder(new Color(0, 0, 0)));
		tabContenet.setBackground(contentPane.getBackground());
		tabContenet.setLayout(null);
		contentPane.add(tabContenet);
		//-----------------------------------------------------------------
		mainTab = new MainPanel(width-50, height-240);
		mainTab.setName("mainTab");
		mainTab.setLocation(5, 5);
		tabContenet.add(mainTab);
		
		missionTab = new MissionPanel(width-50, height-240);
		missionTab.setName("statusTab");
		missionTab.setLocation(5,5);
		tabContenet.add(missionTab);
		
		inputDeviceTab = new InputDevicePanel(width-50, height-240);
		inputDeviceTab.setName("inputDeviceTab");
		inputDeviceTab.setLocation(5,5);
		tabContenet.add(inputDeviceTab);
		//-----------------------------------------------------------------
		//-----------------------------------------------------------------
		tabContol = new MainTabPanel(width-40, 85);
		tabContol.setLocation(10, 7);
		
		Image fligtDataImage = null;
		try {
			fligtDataImage = ImageIO.read(getClass().getResource("/images/flightdata_icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		tabContol.addTab("OVERVIEW", fligtDataImage, mainTab);
		
		Image fligtMissionImage = null;
		try {
			fligtMissionImage = ImageIO.read(getClass().getResource("/images/flightdata_icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		tabContol.addTab("FLIGHT MISSION", fligtMissionImage, missionTab);
		
		Image inputDeviceImage = null;
		try {
			inputDeviceImage = ImageIO.read(getClass().getResource("/images/flightdata_icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		tabContol.addTab("CONTROLLER", inputDeviceImage, inputDeviceTab);
		//-----------------------------------------------------------------
		tabContol.selectTab("OVERVIEW");
		contentPane.add(tabContol);
		//-----------------------------------------------------------------		
	}

	private void initilizeDrone() {
		
	}
}
