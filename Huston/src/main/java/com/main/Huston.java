package com.main;

import java.awt.EventQueue;

import quadx.drone.controller.Drone;
import quadx.drone.controller.utils.exceptions.DroneConnectionException;

import com.huston.HustonComponent;
import com.huston.data.RadioConfig;
import com.huston.frames.ControlFrame;
import com.huston.jxinput.InputDevice;
import com.huston.jxinput.KeyboardInputDevice;

public class Huston {

	private static Drone drone;
	private static InputDevice keyBoardInputDevice;
	private static ControlFrame frame;
	public static void main(String[] args) {
		// --------------------------------------------------------------
		try {
			drone = new Drone(RadioConfig.NETWORK_ID, RadioConfig.DRONE_ID);
			String port = RadioConfig.PORT;
			int buadRate = RadioConfig.BUAD_RATE;
			if(args.length>=1) {
				port = args[0];
			}
			if(args.length>=2) {
				buadRate = Integer.parseInt(args[1]);
			}
			drone.connect(port, buadRate);
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
