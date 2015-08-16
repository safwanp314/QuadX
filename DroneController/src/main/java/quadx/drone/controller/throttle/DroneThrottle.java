package quadx.drone.controller.throttle;

import quadx.drone.controller.throttle.activities.DroneThrottleSendActivity;


public class DroneThrottle {

	public double engineFactorSlider;
	public double upDownThrottle;
	public double leftRightThrottle;
	public double frontBackThrottle;	
	public double rotationThrottle;
	
	private DroneThrottleSendActivity throttleSendActivity;	
	public void setThrottleSendActivity(DroneThrottleSendActivity throttleSendActivity) {
		this.throttleSendActivity = throttleSendActivity;
	}

	public void performThrottleChangeAction() {
		new Thread(throttleSendActivity).start();
	}
}
