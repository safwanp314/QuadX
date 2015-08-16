package quadx.drone.controller.status;

import quadx.drone.controller.status.enums.ControlMode;
import quadx.drone.controller.status.enums.DroneStat;

public class DroneStatus {
	// --------------------------------------------
	public DroneStat stat = DroneStat.UNINT;
	public ControlMode mode = ControlMode.GUIDED;
	// --------------------------------------------
}
