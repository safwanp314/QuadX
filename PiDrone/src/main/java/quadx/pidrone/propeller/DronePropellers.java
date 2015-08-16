package quadx.pidrone.propeller;

import quadx.pidrone.propeller.activity.PropellerSendActivity;
import quadx.pidrone.resources.propeller.PropellersDriver;


public class DronePropellers {

	public int motor1;
	public int motor2;
	public int motor3;
	public int motor4;
	
	public float avgMotorsSpeed;
	
	private PropellersDriver propellersDriver;
	
	public DronePropellers(PropellersDriver propellersDriver) {
		this.propellersDriver = propellersDriver;
	}

	private PropellerSendActivity propellerSendActivity;
	public PropellerSendActivity getPropellerSendActivity() {
		return propellerSendActivity;
	}
	public void setPropellerSendActivity(PropellerSendActivity propellerSendActivity) {
		this.propellerSendActivity = propellerSendActivity;
	}

	public void update() {
		avgMotorsSpeed = (motor1+motor2+motor3+motor4)/4;
		propellersDriver.motor1 = motor1;
		propellersDriver.motor2 = motor2;
		propellersDriver.motor3 = motor3;
		propellersDriver.motor4 = motor4;
		
		propellersDriver.update();
	}
}
