package quadx.drone.controller.navigation.listeners;

import quadx.comm.MAVLink.hudhud.msg_attitude;
import quadx.comm.listeners.MavLinkAttitudeEventListener;
import quadx.drone.controller.Drone;

public class DroneAttitudeEventListener extends MavLinkAttitudeEventListener {

	private Drone drone;
	
	public DroneAttitudeEventListener(Drone drone) {
		this.drone = drone;
	}
	
	@Override
	public void handleAttitudeEvent(msg_attitude msg_attitude) {
		//-----------------------------------------
		drone.getNav().pitch = msg_attitude.pitch;
		drone.getNav().roll = msg_attitude.roll;
		drone.getNav().yaw = msg_attitude.yaw;
		
		drone.getNav().pitchRate = msg_attitude.pitch_rate;
		drone.getNav().rollRate = msg_attitude.roll_rate;
		drone.getNav().yawRate = msg_attitude.yaw_rate;
		//-----------------------------------------
		drone.getEventHandler().handleDroneEventListener();
	}
}
