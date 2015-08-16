package quadx.pidrone.guidence;

import quadx.pidrone.guidence.activity.GuidenceSendActivity;

public class PiDroneGuidence {

	public float appliedThrust = 0.0f;
	
	public float engineFactor = 0.0f;
	
	public float targetPitch = 0.0f;
	public float targetRoll = 0.0f;
	public float targetYaw = 0.0f;

	public float targetPitchRate = 0.0f;
	public float targetRollRate = 0.0f;
	public float targetYawRate = 0.0f;
	
	private GuidenceSendActivity guidenceSendActivity;
	public GuidenceSendActivity getGuidenceSendActivity() {
		return guidenceSendActivity;
	}
	public void setGuidenceSendActivity(GuidenceSendActivity guidenceSendActivity) {
		this.guidenceSendActivity = guidenceSendActivity;
	}
}
