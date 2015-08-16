package quadx.comm.listeners;

import quadx.comm.MAVLink.hudhud.msg_command_ack;

public abstract class MavLinkCommandAckEventListener {

	public abstract void handleCommandAckEvent(msg_command_ack msg_command_ack);
}
