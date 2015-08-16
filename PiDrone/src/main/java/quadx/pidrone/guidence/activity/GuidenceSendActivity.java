package quadx.pidrone.guidence.activity;

import quadx.comm.MAVLink.hudhud.msg_guidence;
import quadx.pidrone.PiDrone;
import quadx.utils.mavlink.RepeativeMessageSendActivity;

public class GuidenceSendActivity extends RepeativeMessageSendActivity {

	private PiDrone drone;
	private final static long REPEAT_INTERAVAL = 500;
		
	public GuidenceSendActivity(PiDrone drone) {
		super(drone.getRadioStream(), REPEAT_INTERAVAL);
		this.drone = drone;
	}

	@Override
	public void createMessage() {
		msg_guidence msg_guidence = new msg_guidence();
		msg_guidence.target_pitch = drone.getGuidence().targetPitch;
		msg_guidence.target_roll = drone.getGuidence().targetRoll;
		msg_guidence.target_yaw = drone.getGuidence().targetYaw;
		msg_guidence.target_pitch_rate = drone.getGuidence().targetPitchRate;
		msg_guidence.target_roll_rate = drone.getGuidence().targetPitchRate;
		msg_guidence.target_yaw_rate = drone.getGuidence().targetPitchRate;
		
		message = msg_guidence;
	}
}
