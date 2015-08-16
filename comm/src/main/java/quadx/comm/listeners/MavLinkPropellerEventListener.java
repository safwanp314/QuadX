package quadx.comm.listeners;

import quadx.comm.MAVLink.hudhud.msg_propeller;

public abstract class MavLinkPropellerEventListener {

	public abstract void handlePropellerEvent(msg_propeller msg_propeller);
}
