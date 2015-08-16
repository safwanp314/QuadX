package quadx.pidrone.navigation.activity;

import quadx.comm.MAVLink.hudhud.msg_attitude;
import quadx.pidrone.PiDrone;
import quadx.utils.mavlink.RepeativeMessageSendActivity;

public class AttitudeSendActivity extends RepeativeMessageSendActivity {

	private PiDrone drone;
	private final static long REPEAT_INTERAVAL = 150;
	
	public AttitudeSendActivity(PiDrone drone) {
		super(drone.getRadioStream(), REPEAT_INTERAVAL);
		this.drone = drone;
	}

	@Override
	public void createMessage() {
		msg_attitude ma = new msg_attitude();
		ma.pitch = drone.getNav().pitch;
		ma.roll = drone.getNav().roll;
		ma.yaw = drone.getNav().yaw;
		ma.pitch_rate = drone.getNav().pitchRate;
		ma.roll_rate = drone.getNav().rollRate;
		ma.yaw_rate = drone.getNav().yawRate;
		
		message = ma;
	}
}
