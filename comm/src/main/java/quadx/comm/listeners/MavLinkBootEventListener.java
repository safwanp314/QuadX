package quadx.comm.listeners;

import quadx.comm.MAVLink.hudhud.msg_boot;

public abstract class MavLinkBootEventListener {

	public abstract void handleBootEvent(msg_boot msg_boot);
}
