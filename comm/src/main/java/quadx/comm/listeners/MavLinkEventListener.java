package quadx.comm.listeners;

import java.util.EventListener;

import quadx.comm.MAVLink.Messages.MAVLinkMessage;

public interface MavLinkEventListener extends EventListener {

	void handleMavlinkEvent(MAVLinkMessage mavLinkMessage);
}
