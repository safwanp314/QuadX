package quadx.comm.listeners;

import quadx.comm.MAVLink.hudhud.msg_attitude;

public abstract class MavLinkAttitudeEventListener {

	public abstract void handleAttitudeEvent(msg_attitude msg_attitude);
}
