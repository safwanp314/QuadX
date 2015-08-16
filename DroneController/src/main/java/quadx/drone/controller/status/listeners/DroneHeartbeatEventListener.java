package quadx.drone.controller.status.listeners;

import quadx.comm.MAVLink.hudhud.msg_heartbeat;
import quadx.comm.listeners.MavLinkHeartbeatEventListener;
import quadx.drone.controller.Drone;
import quadx.drone.controller.status.enums.EnumUtils;

public class DroneHeartbeatEventListener extends MavLinkHeartbeatEventListener {

	private Drone drone;
	
	public DroneHeartbeatEventListener(Drone drone) {
		this.drone = drone;
	}
	
	@Override
	public void handleHeartbeatEvent(msg_heartbeat msg_heartbeat) {
		drone.getStatus().stat = EnumUtils.mavStatToDroneStat(msg_heartbeat.system_status);
		drone.getStatus().mode = EnumUtils.mavModeToDroneMode(msg_heartbeat.custom_mode);
		//-----------------------------------------
		drone.getEventHandler().handleDroneEventListener();
	}
}
