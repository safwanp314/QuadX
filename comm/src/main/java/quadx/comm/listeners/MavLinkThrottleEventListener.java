package quadx.comm.listeners;

import quadx.comm.MAVLink.hudhud.msg_throttle;

public abstract class MavLinkThrottleEventListener {

	public abstract void handleThrottleEvent(msg_throttle msg_throttle);
}
