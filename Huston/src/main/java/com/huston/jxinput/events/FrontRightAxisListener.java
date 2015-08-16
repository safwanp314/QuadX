package com.huston.jxinput.events;

import quadx.drone.controller.Drone;
import quadx.drone.controller.utils.exceptions.DroneConnectionException;
import de.hardcode.jxinput.event.JXInputAxisEvent;
import de.hardcode.jxinput.event.JXInputAxisEventListener;

public class FrontRightAxisListener implements JXInputAxisEventListener {

	private Drone drone;
	
	public FrontRightAxisListener(Drone drone) {
		this.drone = drone;
	}
	
	@Override
	public void changed(JXInputAxisEvent event) {
		if(drone!=null) {
			try {
				drone.getActionHandler().frontRearThrottle(event.getAxis().getValue(), event.getDelta());
			} catch (DroneConnectionException e) {
				e.printStackTrace();
			}
		}
	}
}
