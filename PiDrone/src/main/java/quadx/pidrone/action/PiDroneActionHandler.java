package quadx.pidrone.action;

import quadx.pidrone.PiDrone;
import quadx.pidrone.enums.DroneStat;
import quadx.pidrone.throttle.DroneThrottle;

public class PiDroneActionHandler {

	private PiDrone drone;
	
	public PiDroneActionHandler(PiDrone drone) {
		this.drone = drone;
	}
	
	public void takeoff() {
		drone.active = true;
		drone.getStatus().stat = DroneStat.ACTIVE;
	}
	
	public void land() {
		
	}
	
	public void emergencyLand() {
		drone.active = false;
		drone.getStatus().stat = DroneStat.EMERGENCY;
		drone.getGuidence().engineFactor = 0.0f;
	}
	
	private static final float ENGINE_FACTOR_SCALE = 1.0f/32767.0f;
	private static final float UP_DOWN_THROTTLE_SCALE = 23.0f/32767.0f;
	private static final float FRONT_BACK_THROTTLE_SCALE = 30.0f/32767.0f;
	private static final float LEFT_RIGHT_THROTTLE_SCALE = 30.0f/32767.0f;
	private static final float ROTATION_THROTTLE_SCALE = 0.5f/32767.0f;

	public void handleThrottle(DroneThrottle throttle) {
		
		drone.getGuidence().engineFactor = throttle.enngineFactor*ENGINE_FACTOR_SCALE;
		drone.getGuidence().appliedThrust = throttle.upDown*UP_DOWN_THROTTLE_SCALE;
		drone.getGuidence().targetPitch = throttle.frontBack*FRONT_BACK_THROTTLE_SCALE;
		drone.getGuidence().targetRoll = throttle.leftRight*LEFT_RIGHT_THROTTLE_SCALE;
		drone.getGuidence().targetYaw += throttle.rotation*ROTATION_THROTTLE_SCALE;
	}
}
