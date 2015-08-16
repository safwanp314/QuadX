package quadx.drone.controller.utils.events;

import java.util.ArrayList;
import java.util.List;

import quadx.drone.controller.Drone;

public class DroneEventHandler {
	
	private Drone drone;
	
	public DroneEventHandler(Drone drone) {
		this.drone = drone;
	}

	private List<DroneEventListener> droneEventListeners = new ArrayList<DroneEventListener>();
	
	public void addDroneEventListener(DroneEventListener droneEventListener) {
		droneEventListeners.add(droneEventListener);
	}
	
	public void handleDroneEventListener() {
		for (DroneEventListener droneEventListener : droneEventListeners) {
			droneEventListener.actionPerformed(new DroneEvent(drone));
		}
	}
}
