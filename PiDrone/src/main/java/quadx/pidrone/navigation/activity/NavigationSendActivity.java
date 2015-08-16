package quadx.pidrone.navigation.activity;

import quadx.comm.MAVLink.hudhud.msg_navigation;
import quadx.pidrone.PiDrone;
import quadx.utils.mavlink.RepeativeMessageSendActivity;

public class NavigationSendActivity extends RepeativeMessageSendActivity {
	
	private PiDrone drone;
	private final static long REPEAT_INTERAVAL = 300;
	
	public NavigationSendActivity(PiDrone drone) {
		super(drone.getRadioStream(), REPEAT_INTERAVAL);
		this.drone = drone;
	}

	@Override
	public void createMessage() {
		msg_navigation msg_navigation = new msg_navigation();
		msg_navigation.latitude = drone.getNav().latitude;
		msg_navigation.longitude = drone.getNav().longitude;
		msg_navigation.altitude = drone.getNav().altitude;
		msg_navigation.speed = drone.getNav().speed;
		msg_navigation.climb_rate = drone.getNav().climbRate;
		
		message = msg_navigation;
	}
}
