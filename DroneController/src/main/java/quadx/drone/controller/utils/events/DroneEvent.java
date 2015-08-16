package quadx.drone.controller.utils.events;

import java.io.Serializable;

import quadx.drone.controller.Drone;

public class DroneEvent implements Serializable {

	private static final long serialVersionUID = 7767213827532250055L;
	
	private Drone drone;
	
	public DroneEvent(Drone drone) {
		this.drone = drone;
	}

	public Drone getDrone() {
		return drone;
	}
}
