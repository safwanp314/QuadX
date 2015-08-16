package quadx.pidrone.status;

import quadx.pidrone.enums.AutoPilot;
import quadx.pidrone.enums.ControlMode;
import quadx.pidrone.enums.DroneStat;
import quadx.pidrone.enums.DroneType;
import quadx.pidrone.status.activity.HeartbeatSendActivity;

public class DroneStatus {
	// --------------------------------------------
	public DroneStat stat = DroneStat.UNINT;
	public ControlMode mode = ControlMode.GUIDED;
	public DroneType droneType = DroneType.QUADXROTOR;
	public AutoPilot autoPilot = AutoPilot.QUADX;
	// --------------------------------------------
	private HeartbeatSendActivity heartbeatSendActivity;
	public HeartbeatSendActivity getHeartbeatSendActivity() {
		return heartbeatSendActivity;
	}
	public void setHeartbeatSendActivity(HeartbeatSendActivity heartbeatSendActivity) {
		this.heartbeatSendActivity = heartbeatSendActivity;
	}
}
