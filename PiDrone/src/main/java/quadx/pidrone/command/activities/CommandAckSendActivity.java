package quadx.pidrone.command.activities;

import quadx.comm.MAVLink.enums.MAV_RESULT;
import quadx.comm.MAVLink.hudhud.msg_command_ack;
import quadx.pidrone.PiDrone;
import quadx.utils.mavlink.RepeativeMessageSendActivity;

public class CommandAckSendActivity extends RepeativeMessageSendActivity {

	private byte command;
	
	public CommandAckSendActivity(PiDrone drone, byte command) {
		super(drone.getRadioStream(), 0, 1);
		this.command = command;
	}
	
	@Override
	public void createMessage() {
		msg_command_ack ack = new msg_command_ack();
		ack.command = command;
		ack.result = MAV_RESULT.MAV_RESULT_ACCEPTED;
		
		message = ack;
	}
}
