package com.huston.panels.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import quadx.drone.controller.Drone;
import quadx.drone.controller.status.enums.DroneStat;
import quadx.drone.controller.utils.events.DroneEvent;
import quadx.drone.controller.utils.events.DroneEventListener;

import com.huston.HustonComponent;

public class DroneStatPanel extends JPanel {

	private static final long serialVersionUID = -611339376745903327L;
	private Drone drone = HustonComponent.getDrone();
	
	private JLabel lblNewLabel,lblMainBg;
	private Image connectImage;
	private Image bootImage;
	private Image failsafeImage;
	private Image disconnectImage;
	private int width, height;
	/**
	 * Create the panel.
	 */
	public DroneStatPanel() {
		super();
		width = 120;
		height = 100;
		initilize();
	}

	public DroneStatPanel(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		initilize();		
	}

	private void initilize() {
		createGUI();
		drone.getEventHandler().addDroneEventListener(new DroneStatDroneEventListener());
	}
	
	private void createGUI() {
		setBounds(0, 0, width, height);
		setBackground(Color.DARK_GRAY);
		setPreferredSize(new Dimension(width,height));
		setLayout(null);
		//--------------------------------------------------------------
		lblNewLabel = new JLabel();
		lblNewLabel.setText("CONNECT");
		lblNewLabel.setForeground(Color.LIGHT_GRAY);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(4, height-25, 112, 20);
		add(lblNewLabel);
		
		lblMainBg = new JLabel();
		lblMainBg.setBounds(0, 0, 120, 100);
		try {
			connectImage = ImageIO.read(getClass().getResource("/images/connect_icon.png"));
			bootImage = ImageIO.read(getClass().getResource("/images/boot_icon.png"));
			failsafeImage = ImageIO.read(getClass().getResource("/images/connect_icon.png"));			
			disconnectImage = ImageIO.read(getClass().getResource("/images/disconnect_icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setDisconnectedStat();
		add(lblMainBg);			
	}
	
	public void setStandByStat() {
		if(connectImage!=null) {
			int delta = 5;
			lblMainBg.setBounds((width-connectImage.getWidth(null))/2 , delta, connectImage.getWidth(null)+delta, connectImage.getHeight(null)+delta);
			lblMainBg.setIcon(new ImageIcon(connectImage));	
		}
		lblNewLabel.setText("STANDBY");
	}
	
	public void setActiveStat() {
		if(connectImage!=null) {
			int delta = 5;
			lblMainBg.setBounds((width-connectImage.getWidth(null))/2 , delta, connectImage.getWidth(null)+delta, connectImage.getHeight(null)+delta);
			lblMainBg.setIcon(new ImageIcon(connectImage));	
		}
		lblNewLabel.setText("ACTIVE");
	}
	
	public void setBootStat() {
		if(bootImage!=null) {
			int delta = 5;
			lblMainBg.setBounds((width-bootImage.getWidth(null))/2 , delta, bootImage.getWidth(null)+delta, bootImage.getHeight(null)+delta);
			lblMainBg.setIcon(new ImageIcon(bootImage));	
		}
		lblNewLabel.setText("BOOTING");
	}
	
	public void setCalibrateStat() {
		if(bootImage!=null) {
			int delta = 5;
			lblMainBg.setBounds((width-bootImage.getWidth(null))/2 , delta, bootImage.getWidth(null)+delta, bootImage.getHeight(null)+delta);
			lblMainBg.setIcon(new ImageIcon(bootImage));	
		}
		lblNewLabel.setText("CALIBRATING");
	}
	
	public void setCriticalStat() {
		if(failsafeImage!=null) {
			int delta = 5;
			lblMainBg.setBounds((width-failsafeImage.getWidth(null))/2 , delta, failsafeImage.getWidth(null)+delta, failsafeImage.getHeight(null)+delta);
			lblMainBg.setIcon(new ImageIcon(failsafeImage));	
		}
		lblNewLabel.setText("CRITICAL");
	}

	public void setDisconnectedStat() {
		if(disconnectImage!=null) {
			int delta = 5;
			lblMainBg.setBounds((width-disconnectImage.getWidth(null))/2 , delta, disconnectImage.getWidth(null)+delta, disconnectImage.getHeight(null)+delta);
			lblMainBg.setIcon(new ImageIcon(disconnectImage));	
		}
		lblNewLabel.setText("DISCONNECTED");
	}
	
	private class DroneStatDroneEventListener implements DroneEventListener {

		public void actionPerformed(DroneEvent event) {
			Drone drone = event.getDrone();
			if(drone!=null) {
				DroneStat stat = drone.getStatus().stat;
				if(stat.equals(DroneStat.ACTIVE)) {
					setActiveStat();
				} else if(stat.equals(DroneStat.STANDBY)) {
					setStandByStat();
				} else if(stat.equals(DroneStat.CRITICAL) || stat.equals(DroneStat.EMERGENCY)) {
					setCriticalStat();
				} else if(stat.equals(DroneStat.BOOTING) ) {
					setBootStat();
				} else if(stat.equals(DroneStat.CALIBRATING)) {
					setCalibrateStat();
				} else {
					setDisconnectedStat();
				}
			} else {
				setDisconnectedStat();
			}
		}
	}
}
