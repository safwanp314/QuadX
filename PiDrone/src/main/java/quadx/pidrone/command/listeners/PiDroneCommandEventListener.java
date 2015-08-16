package quadx.pidrone.command.listeners;

import quadx.comm.MAVLink.enums.MAV_CMD;
import quadx.comm.MAVLink.hudhud.msg_command;
import quadx.comm.listeners.MavLinkCommandEventListener;
import quadx.pidrone.PiDrone;
import quadx.pidrone.command.activities.CommandAckSendActivity;

public class PiDroneCommandEventListener extends MavLinkCommandEventListener {

	private PiDrone drone;
	
	public PiDroneCommandEventListener(PiDrone drone) {
		this.drone = drone;
	}
	
	@Override
	public void handleCommandEvent(msg_command msg_command) {
		
		new CommandAckSendActivity(drone, msg_command.command).start();
		
		switch (msg_command.command) {
		
		case MAV_CMD.MAV_CMD_NAV_INC_ALTITUDE:
			break;
		case MAV_CMD.MAV_CMD_NAV_LOITER_UNLIM:
			break;
		case MAV_CMD.MAV_CMD_NAV_LOITER_TURNS:
			break;
		case MAV_CMD.MAV_CMD_NAV_LOITER_TIME:
			break;
		case MAV_CMD.MAV_CMD_NAV_RETURN_TO_LAUNCH:
			break;
		case MAV_CMD.MAV_CMD_NAV_LAND:
			break;
		case MAV_CMD.MAV_CMD_NAV_EMERGENCY_LAND:
			drone.getActionHandler().emergencyLand();
			break;
		case MAV_CMD.MAV_CMD_NAV_TAKEOFF:
			drone.getActionHandler().takeoff();
			break;
		default:
			break;
		}
	}
}
