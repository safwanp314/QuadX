package quadx.drone.controller.guidence.listeners;

import quadx.comm.MAVLink.hudhud.msg_guidence;
import quadx.comm.listeners.MavLinkGuidenceEventListener;
import quadx.drone.controller.Drone;

public class DroneGuidenceEventListener extends MavLinkGuidenceEventListener {

	private Drone drone;
	
	public DroneGuidenceEventListener(Drone drone) {
		this.drone = drone;
	}

	@Override
	public void handleGuidenceEvent(msg_guidence msg_guidence) {
		drone.getGuidence().targetPitch = msg_guidence.target_pitch;
		drone.getGuidence().targetRoll = msg_guidence.target_roll;
		drone.getGuidence().targetYaw = msg_guidence.target_yaw;
		
		drone.getGuidence().targetPitchRate = msg_guidence.target_pitch_rate;
		drone.getGuidence().targetRollRate = msg_guidence.target_roll_rate;
		drone.getGuidence().targetYawRate = msg_guidence.target_yaw_rate;
	}
}
