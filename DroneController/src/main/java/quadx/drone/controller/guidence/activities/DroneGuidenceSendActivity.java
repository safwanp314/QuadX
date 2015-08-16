package quadx.drone.controller.guidence.activities;

import quadx.comm.MAVLink.Messages.MAVLinkMessage;
import quadx.comm.MAVLink.hudhud.msg_guidence;
import quadx.drone.controller.Drone;

public class DroneGuidenceSendActivity implements Runnable {

	private Drone drone;
	private MAVLinkMessage message;
	
	public DroneGuidenceSendActivity(Drone drone) {
		this.drone = drone;
	}
	
	private void createMessage() {
		msg_guidence msg_guidence = new msg_guidence();
		msg_guidence.target_pitch = drone.getGuidence().targetPitch;
		msg_guidence.target_roll = drone.getGuidence().targetRoll;
		msg_guidence.target_yaw = drone.getGuidence().targetYaw;
		msg_guidence.target_pitch_rate = drone.getGuidence().targetPitchRate;
		msg_guidence.target_roll_rate = drone.getGuidence().targetRollRate;
		msg_guidence.target_yaw_rate = drone.getGuidence().targetYawRate;
		
		message = msg_guidence;
	}

	public void run() {
		createMessage();
		drone.getRadioStream().sendMessage(message.pack());	
	}
}
