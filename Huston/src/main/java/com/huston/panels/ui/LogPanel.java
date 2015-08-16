package com.huston.panels.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quadx.drone.controller.Drone;
import quadx.drone.controller.utils.events.DroneEvent;
import quadx.drone.controller.utils.events.DroneEventListener;

import com.huston.HustonComponent;

public class LogPanel extends JPanel {

	private static final long serialVersionUID = 3552479587603259914L;
	private Drone drone = HustonComponent.getDrone();
	
	private int width, height;
	private JTextArea textArea;

	private SimpleDateFormat dateFormat = new SimpleDateFormat(
			"MM/dd/yyyy hh:mm:ss SSSS");
	private static final Logger logger = LoggerFactory.getLogger(LogPanel.class);

	/**
	 * Create the panel.
	 */
	public LogPanel() {
		super();
		this.width = 680;
		this.height = 170;
		initilize();
	}

	public LogPanel(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		initilize();
	}

	private void initilize() {
		createGUI();
		drone.getEventHandler().addDroneEventListener(new LogPanelDroneEventListener());
	}
	private void createGUI() {
		setBounds(0, 0, width, height);
		setBackground(Color.DARK_GRAY);
		setLayout(new BorderLayout(0, 0));
		setPreferredSize(new Dimension(width, height));
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		add(textArea, BorderLayout.CENTER);
	}

	public void write(String line) {
		textArea.append(line);
	}

	public void newLine() {
		textArea.append("\n");
	}

	public void writeLine(String line) {
		textArea.append(line + "\n");
	}

	
	private class LogPanelDroneEventListener implements DroneEventListener {

		public void actionPerformed(DroneEvent event) {
			Drone drone = event.getDrone();
			if (drone != null) {
				write(" [" + dateFormat.format(new Date()) + "]");
				String log = String.format("  %.3f", drone.getNav().pitch)
						+ String.format("  %.3f", drone.getNav().roll)
						+ String.format("  %.3f", drone.getNav().yaw)
						+ String.format("  %.3f", drone.getNav().altitude)
						+ String.format("  %.3f", drone.getNav().latitude)
						+ String.format("  %.3f", drone.getNav().longitude)
						+ String.format("  %.3f", drone.getGuidence().targetPitch)
						+ String.format("  %.3f", drone.getGuidence().targetRoll)
						+ String.format("  %.3f", drone.getGuidence().targetYaw)
						+ String.format("  %.3f", drone.getPropellers().frontLeftRotorSpeed)
						+ String.format("  %.3f", drone.getPropellers().frontRightRotorSpeed)
						+ String.format("  %.3f", drone.getPropellers().rearLeftRotorSpeed)
						+ String.format("  %.3f", drone.getPropellers().rearRightRotorSpeed);
				write(log);
				newLine();
				logger.info(log);
			}
		}	
	}
}
