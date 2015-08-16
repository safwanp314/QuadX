package quadx.drone.controller.navigation.listeners;

import quadx.comm.MAVLink.hudhud.msg_navigation;
import quadx.comm.listeners.MavLinkNavigationEventListener;
import quadx.drone.controller.Drone;

public class DroneNavigationEventListener extends MavLinkNavigationEventListener {

	private Drone drone;
	
	public DroneNavigationEventListener(Drone drone) {
		this.drone = drone;
	}

	@Override
	public void handleNavigationEvent(msg_navigation msg_navigation) {
		
		drone.getNav().latitude = msg_navigation.latitude;
		drone.getNav().longitude = msg_navigation.longitude;
		drone.getNav().altitude = msg_navigation.altitude;
		drone.getNav().speed  = msg_navigation.speed;
		drone.getNav().climbRate = msg_navigation.climb_rate;
		//-----------------------------------------
		drone.getEventHandler().handleDroneEventListener();
	}	
}
