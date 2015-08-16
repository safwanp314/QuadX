package quadx.comm.listeners;

import quadx.comm.MAVLink.hudhud.msg_navigation;

public abstract class MavLinkNavigationEventListener {

	public abstract void handleNavigationEvent(msg_navigation msg_navigation);
}
