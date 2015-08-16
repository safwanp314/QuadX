package quadx.pidrone.throttle.listeners;

import quadx.comm.MAVLink.hudhud.msg_throttle;
import quadx.comm.listeners.MavLinkThrottleEventListener;
import quadx.pidrone.PiDrone;
import quadx.pidrone.throttle.DroneThrottle;

public class PiDroneThrottleEventListener extends MavLinkThrottleEventListener {

	private PiDrone drone;
	
	public PiDroneThrottleEventListener(PiDrone drone) {
		this.drone = drone;
	}

	@Override
	public void handleThrottleEvent(msg_throttle msg_throttle) {
		DroneThrottle throttle = new DroneThrottle();
		throttle.enngineFactor = msg_throttle.engine_factor;
		throttle.upDown = msg_throttle.up_down;
		throttle.leftRight = msg_throttle.left_right;
		throttle.frontBack = msg_throttle.left_right;
		throttle.rotation = msg_throttle.rotation;
		drone.getActionHandler().handleThrottle(throttle);
	}
}
