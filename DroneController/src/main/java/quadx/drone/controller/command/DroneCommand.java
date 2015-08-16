package quadx.drone.controller.command;

import quadx.drone.controller.command.activities.DroneCommandSendActivity;
import quadx.drone.controller.utils.exceptions.CommandAckFailureException;

public class DroneCommand {

	public byte command;
	public long timeout;
	public int repeat;
	
	private DroneCommandSendActivity commandSendActivity;
	public void setCommandSendActivity(DroneCommandSendActivity commandSendActivity) {
		this.commandSendActivity = commandSendActivity;
	}
	
	public synchronized boolean sendCommand() throws CommandAckFailureException {
		return commandSendActivity.sendCommand(command,timeout,repeat);
	}
}