package com.main;

import java.awt.EventQueue;

import quadx.comm.MavLinkRadioStream;
import quadx.drone.controller.Drone;
import quadx.drone.controller.utils.exceptions.DroneConnectionException;

import com.huston.HustonComponent;
import com.huston.frames.ControlFrame;
import com.huston.jxinput.InputDevice;
import com.huston.jxinput.KeyboardInputDevice;
import comm.win.WinMavLinkRadioStream;

public class Huston {

	private static Drone drone;
	private static InputDevice keyBoardInputDevice;
	private static ControlFrame frame;
	public static void main(String[] args) {
		// --------------------------------------------------------------
		try {
			String port = DroneConfig.PORT;
			int baudRate = DroneConfig.BAUD_RATE;
			if(args.length>=1) {
				port = args[0];
			}
			if(args.length>=2) {
				baudRate = Integer.parseInt(args[1]);
			}
			
			MavLinkRadioStream radioStream = new WinMavLinkRadioStream(port, baudRate);
			drone = new Drone(DroneConfig.NETWORK_ID, DroneConfig.DRONE_ID, radioStream);
			drone.connect();
			HustonComponent.setDrone(drone);
		} catch (DroneConnectionException e) {
			e.printStackTrace();
		}
		// --------------------------------------------------------------
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					keyBoardInputDevice = new KeyboardInputDevice(drone);
					HustonComponent.addInputDevice(keyBoardInputDevice);
					
					frame = new ControlFrame("Huston (Ground Station)");
					keyBoardInputDevice.attach(frame);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		// --------------------------------------------------------------
	}
}
