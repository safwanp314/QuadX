package quadx.comm.listeners;

import quadx.comm.MAVLink.hudhud.msg_guidence;

public abstract class MavLinkGuidenceEventListener {

	public abstract void handleGuidenceEvent(msg_guidence msg_guidence);
}
