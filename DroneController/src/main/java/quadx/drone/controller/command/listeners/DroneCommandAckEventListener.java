package quadx.drone.controller.command.listeners;

import quadx.comm.MAVLink.hudhud.msg_command_ack;
import quadx.comm.listeners.MavLinkCommandAckEventListener;
import quadx.drone.controller.Drone;
import quadx.drone.controller.command.activities.DroneCommandSendActivity;

public class DroneCommandAckEventListener extends MavLinkCommandAckEventListener {

	public DroneCommandAckEventListener(Drone drone) {
	}

	@Override
	public void handleCommandAckEvent(msg_command_ack msg_command_ack) {
		byte command = msg_command_ack.command;
		DroneCommandSendActivity.ackReceived(command);
	}
}
