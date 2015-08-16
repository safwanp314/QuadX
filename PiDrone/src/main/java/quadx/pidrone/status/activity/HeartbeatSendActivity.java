package quadx.pidrone.status.activity;

import quadx.comm.MAVLink.hudhud.msg_heartbeat;
import quadx.pidrone.PiDrone;
import quadx.pidrone.enums.utils.EnumUtils;
import quadx.utils.mavlink.RepeativeMessageSendActivity;

public class HeartbeatSendActivity extends RepeativeMessageSendActivity {

	private PiDrone drone;
	private final static long REPEAT_INTERAVAL = 2000;
	
	public HeartbeatSendActivity(PiDrone drone) {
		super(drone.getRadioStream(), REPEAT_INTERAVAL);
		this.drone = drone;		
	}


	@Override
	public void createMessage() {
		msg_heartbeat mh = new msg_heartbeat();

		mh.system_status = EnumUtils.droneStatToMavStat(drone.getStatus().stat);
		mh.custom_mode = EnumUtils.droneModeToMavMode(drone.getStatus().mode);
		mh.type = EnumUtils.droneTypeToMavType(drone.getStatus().droneType);
		mh.autopilot = EnumUtils.droneAutopilotToMavAutopilot(drone.getStatus().autoPilot);
		
		message = mh;
	}
}
