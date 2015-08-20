package quadx.pidrone.navigation;

import quadx.pidrone.navigation.activity.AttitudeSendActivity;
import quadx.pidrone.navigation.activity.NavigationSendActivity;
import quadx.pidrone.resources.imu.ArduImu;
import quadx.utils.MathUtils;

public class AhrsNav {

	public float pitch;
	public float roll;
	public float yaw;
	
	public float pitchRate;
	public float rollRate;
	public float yawRate;
	
	public float latitude;
	public float longitude;
	public float altitude;
	
	public float climbRate;	
	public float speed;	
	
	private long lastSampleTimeStamp;

	private ArduImu imu;
	private final float[] imuAngle = {0.0f, 0.0f, -90.0f};
		
	public AhrsNav(ArduImu imu) {
		this.imu = imu;
	}
	
	public void readNav() {
		imu.readImu();
		
		float[] imuAxis = {imu.pitch, -imu.roll, imu.yaw};
		float[] droneAxis = MathUtils.axisConversion(imuAxis, imuAngle);
		
		float tempPitch = droneAxis[0];
		float tempRoll = droneAxis[1];
		float tempYaw = droneAxis[2];
		float tempAltitude = imu.altitude;
		float tempLatitude = imu.latitude;
		float tempLongitude = imu.longitude;
		
		long dt = System.currentTimeMillis()-lastSampleTimeStamp;
		lastSampleTimeStamp = System.currentTimeMillis();
		
		pitchRate = (tempPitch-pitch)/dt;
		pitch = tempPitch;
		
		rollRate = (tempRoll-roll)/dt;
		roll = tempRoll;
		
		yawRate = (tempYaw-yaw)/dt;
		yaw = tempYaw;
		climbRate = (tempAltitude-altitude)/dt;
		altitude = tempAltitude;
		
		latitude = tempLatitude;
		
		longitude = tempLongitude;
	}
	
	private AttitudeSendActivity attitudeSendActivity;
	public AttitudeSendActivity getAttitudeSendActivity() {
		return attitudeSendActivity;
	}
	public void setAttitudeSendActivity(AttitudeSendActivity attitudeSendActivity) {
		this.attitudeSendActivity = attitudeSendActivity;
	}

	private NavigationSendActivity navigationSendActivity;
	public NavigationSendActivity getNavigationSendActivity() {
		return navigationSendActivity;
	}
	public void setNavigationSendActivity(
			NavigationSendActivity navigationSendActivity) {
		this.navigationSendActivity = navigationSendActivity;
	}
}
