package quadx.pidrone.propeller.activity;

import quadx.comm.MAVLink.hudhud.msg_propeller;
import quadx.pidrone.PiDrone;
import quadx.pidrone.propeller.DronePropellers;
import quadx.utils.mavlink.RepeativeMessageSendActivity;

public class PropellerSendActivity extends RepeativeMessageSendActivity {

	private PiDrone drone;
	private final static long REPEAT_INTERAVAL = 150;
	
	public PropellerSendActivity(PiDrone drone) {
		super(drone.getRadioStream(), REPEAT_INTERAVAL);
		this.drone = drone;
	}

	@Override
	public void createMessage() {
		msg_propeller msg_propeller = new msg_propeller();
		DronePropellers propellers = drone.getPropellers();
		msg_propeller.propeller1 = propellers.motor1;
		msg_propeller.propeller2 = propellers.motor2;
		msg_propeller.propeller3 = propellers.motor3;
		msg_propeller.propeller4 = propellers.motor4;
		
		message = msg_propeller;
	}
}
