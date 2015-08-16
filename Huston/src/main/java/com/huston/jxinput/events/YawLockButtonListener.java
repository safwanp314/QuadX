package com.huston.jxinput.events;

import quadx.drone.controller.Drone;
import quadx.drone.controller.utils.exceptions.DroneConnectionException;
import de.hardcode.jxinput.event.JXInputButtonEvent;
import de.hardcode.jxinput.event.JXInputButtonEventListener;

public class YawLockButtonListener implements JXInputButtonEventListener {

	private Drone drone;
	
	public YawLockButtonListener(Drone drone) {
		this.drone = drone;
	}

	@Override
	public void changed(JXInputButtonEvent event) {
		if(drone!=null) {
			try {
				drone.getActionHandler().lockYaw();
			} catch (DroneConnectionException e) {
				e.printStackTrace();
			}
		}
	}
}
