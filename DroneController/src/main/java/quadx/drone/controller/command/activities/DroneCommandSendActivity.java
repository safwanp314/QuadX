package quadx.drone.controller.command.activities;

import java.util.HashMap;
import java.util.Map;

import quadx.comm.MAVLink.Messages.MAVLinkMessage;
import quadx.comm.MAVLink.hudhud.msg_command;
import quadx.drone.controller.Drone;
import quadx.drone.controller.utils.exceptions.CommandAckFailureException;

public class DroneCommandSendActivity {

	private enum CommandStat {
		IDEL, CMD_READY, CMD_SENT, ACK_RECV, ACK_FAIL
	}

	private static Map<Byte,DroneCommandSendActivity> map = new HashMap<Byte, DroneCommandSendActivity>();
	
	protected Drone drone;
	protected MAVLinkMessage message;
	
	private Thread currThread;
	private byte command;
	private CommandStat commandStat = CommandStat.IDEL;

	public DroneCommandSendActivity(Drone drone) {
		this.drone = drone;
	}

	public void createMessage(byte command) {
		msg_command msg_command = new msg_command();
		msg_command.command = command;
		
		message = msg_command;
	}
	
	public synchronized boolean sendCommand(byte command, long timeout, int repeat) throws CommandAckFailureException {
		if (commandStat == CommandStat.IDEL
				|| commandStat == CommandStat.ACK_RECV
				|| commandStat == CommandStat.ACK_FAIL) {

			this.command = command;
			commandStat = CommandStat.CMD_READY;
			currThread = Thread.currentThread();
			// --------------------------------------------------
			map.put(command, this);
			// --------------------------------------------------
			for (int i = 0; i < repeat; i++) {
				if (drone != null && drone.isConnected() && drone.getRadioStream().isConnected()) {

					createMessage(command);
					commandStat = CommandStat.CMD_SENT;
					drone.getRadioStream().sendMessage(message.pack());
				}
				try {
					Thread.sleep(timeout);
				} catch (InterruptedException e) {
				}
				if (commandStat == CommandStat.ACK_RECV) {
					break;
				}
			}
			// --------------------------------------------------
			if (commandStat == CommandStat.ACK_RECV) {
				commandStat = CommandStat.IDEL;
				return true;
			} else {
				commandStat = CommandStat.ACK_FAIL;
				throw new CommandAckFailureException(command);
			}
			// --------------------------------------------------
		} else {
			return false;
		}
	}

	public static void ackReceived(byte command) {
		DroneCommandSendActivity commandActivity = map.get(command);
		if (commandActivity!=null && commandActivity.commandStat == CommandStat.CMD_SENT && commandActivity.command == command) {
			commandActivity.commandStat = CommandStat.ACK_RECV;
			commandActivity.currThread.interrupt();
			map.remove(command);
		}
	}
}
