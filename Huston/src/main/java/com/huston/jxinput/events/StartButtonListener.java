package com.huston.jxinput.events;

import quadx.drone.controller.Drone;
import quadx.drone.controller.utils.exceptions.CommandAckFailureException;
import quadx.drone.controller.utils.exceptions.DroneConnectionException;

import com.huston.jxinput.InputDevice;
import com.huston.jxinput.buttons.ToggleKeyButton;

import de.hardcode.jxinput.event.JXInputButtonEvent;
import de.hardcode.jxinput.event.JXInputButtonEventListener;

public class StartButtonListener implements JXInputButtonEventListener {

	private Drone drone;
	private InputDevice inputDevice;
	
	public StartButtonListener(Drone drone, InputDevice inputDevice) {
		this.drone = drone;
		this.inputDevice = inputDevice;
	}

	public void changed(JXInputButtonEvent event) {
		if(drone!=null) {
			try {
				if(event.getButton().getState()) {
					drone.getActionHandler().start();
				} else {
					drone.getActionHandler().stop();
					inputDevice.resetEngineFactor();
				}
			} catch (DroneConnectionException | CommandAckFailureException e) {
				((ToggleKeyButton)event.getButton()).setState(false);;
				e.printStackTrace();
			}
		}
	}
}
