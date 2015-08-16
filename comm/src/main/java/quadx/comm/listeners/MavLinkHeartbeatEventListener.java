package quadx.comm.listeners;

import quadx.comm.MAVLink.hudhud.msg_heartbeat;


public abstract class MavLinkHeartbeatEventListener {

	public abstract void handleHeartbeatEvent(msg_heartbeat msg_heartbeat);
}
