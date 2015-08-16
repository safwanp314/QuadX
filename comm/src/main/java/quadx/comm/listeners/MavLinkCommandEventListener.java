package quadx.comm.listeners;

import quadx.comm.MAVLink.hudhud.msg_command;

public abstract class MavLinkCommandEventListener {

	public abstract void handleCommandEvent(msg_command msg_command);
}
