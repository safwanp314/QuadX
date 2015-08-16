package quadx.drone.controller.guidence;

import quadx.drone.controller.guidence.activities.DroneGuidenceSendActivity;

public class DroneGuidence {
	
	public float targetPitch;
	public float targetRoll;
	public float targetYaw;

	public float targetPitchRate;
	public float targetRollRate;
	public float targetYawRate;
	
	private DroneGuidenceSendActivity guidenceSendActivity;
	public void setGuidenceSendActivity(DroneGuidenceSendActivity guidenceSendActivity) {
		this.guidenceSendActivity = guidenceSendActivity;
	}
	
	public void performGuidenceAction() {
		new Thread(guidenceSendActivity).start();
	}
}
