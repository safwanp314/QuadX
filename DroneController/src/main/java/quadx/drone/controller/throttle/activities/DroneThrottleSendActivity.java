package quadx.drone.controller.throttle.activities;

import quadx.comm.MAVLink.Messages.MAVLinkMessage;
import quadx.comm.MAVLink.hudhud.msg_throttle;
import quadx.drone.controller.Drone;

public class DroneThrottleSendActivity implements Runnable {

	private Drone drone;
	private MAVLinkMessage message;

	public DroneThrottleSendActivity(Drone drone) {
		this.drone = drone;
	}

	private void createMessage() {
		msg_throttle msg_throttle = new msg_throttle();
		msg_throttle.engine_factor = (short) (drone.getThrottle().engineFactorSlider*Short.MAX_VALUE);
		msg_throttle.left_right = (short) (drone.getThrottle().leftRightThrottle*Short.MAX_VALUE);
		msg_throttle.front_back = (short) (drone.getThrottle().frontBackThrottle*Short.MAX_VALUE);
		msg_throttle.up_down = (short) (drone.getThrottle().upDownThrottle*Short.MAX_VALUE);
		msg_throttle.rotation = (short) (drone.getThrottle().rotationThrottle*Short.MAX_VALUE);
		message = msg_throttle;
	}

	public void run() {
		createMessage();
		drone.getRadioStream().sendMessage(message.pack());
	}
}