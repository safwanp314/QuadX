package com.huston;

import java.util.ArrayList;
import java.util.List;

import quadx.drone.controller.Drone;

import com.huston.jxinput.InputDevice;

public class HustonComponent {

	private static Drone drone;
	private static List<InputDevice> inputDevices = new ArrayList<InputDevice>();

	public static Drone getDrone() {
		return drone;
	}
	public static void setDrone(Drone drone) {
		HustonComponent.drone = drone;
	}

	public static List<InputDevice> getInputDevices() {
		return inputDevices;
	}
	public static void addInputDevice(InputDevice inputDevice) {
		inputDevices.add(inputDevice);
	}
}
