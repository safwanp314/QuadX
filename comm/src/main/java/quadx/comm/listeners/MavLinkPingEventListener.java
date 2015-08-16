package quadx.comm.listeners;

import quadx.comm.MAVLink.hudhud.msg_ping;

public abstract class MavLinkPingEventListener {

	public abstract void handlePingEvent(msg_ping msg_ping);
}
