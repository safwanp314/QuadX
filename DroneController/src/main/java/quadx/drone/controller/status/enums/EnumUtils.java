package quadx.drone.controller.status.enums;

import quadx.comm.MAVLink.enums.MAV_AUTOPILOT;
import quadx.comm.MAVLink.enums.MAV_MODE;
import quadx.comm.MAVLink.enums.MAV_STATE;
import quadx.comm.MAVLink.enums.MAV_TYPE;

public class EnumUtils {

	public static byte droneStatToMavStat(DroneStat stat) {
		switch (stat) {
		case UNINT:
			return MAV_STATE.MAV_STATE_UNINIT;
		case BOOTING:
			return MAV_STATE.MAV_STATE_BOOT;
		case CALIBRATING:
			return MAV_STATE.MAV_STATE_CALIBRATING;
		case STANDBY:
			return MAV_STATE.MAV_STATE_STANDBY;
		case ACTIVE:
			return MAV_STATE.MAV_STATE_ACTIVE;
		case CRITICAL:
			return MAV_STATE.MAV_STATE_CRITICAL;
		case EMERGENCY:
			return MAV_STATE.MAV_STATE_EMERGENCY;
		case POWEROFF:
			return MAV_STATE.MAV_STATE_POWEROFF;
		default:
			return MAV_STATE.MAV_STATE_ENUM_END;
		}
	}
	public static DroneStat mavStatToDroneStat(byte stat) {
		switch (stat) {
		case MAV_STATE.MAV_STATE_UNINIT:
			return DroneStat.UNINT;
		case MAV_STATE.MAV_STATE_BOOT:
			return DroneStat.BOOTING;
		case MAV_STATE.MAV_STATE_CALIBRATING:
			return DroneStat.CALIBRATING;
		case MAV_STATE.MAV_STATE_STANDBY:
			return DroneStat.STANDBY;
		case MAV_STATE.MAV_STATE_ACTIVE:
			return DroneStat.ACTIVE;
		case MAV_STATE.MAV_STATE_CRITICAL:
			return DroneStat.CRITICAL;
		case MAV_STATE.MAV_STATE_EMERGENCY:
			return DroneStat.EMERGENCY;
		case MAV_STATE.MAV_STATE_POWEROFF:
			return DroneStat.POWEROFF;
		default:
			return DroneStat.UNINT;
		}
	}
	
	public static byte droneModeToMavMode(ControlMode mode) {
		switch (mode) {
		case AUTO:
			return MAV_MODE.MAV_MODE_AUTO;
		case SEMIAUTO:
			return MAV_MODE.MAV_MODE_SEMIAUTO;
		case GUIDED:
			return MAV_MODE.MAV_MODE_GUIDED;
		default:
			return MAV_MODE.MAV_MODE_ENUM_END;
		}
	}
	public static ControlMode mavModeToDroneMode(byte mode) {
		switch (mode) {
		case MAV_MODE.MAV_MODE_AUTO:
			return ControlMode.AUTO;
		case MAV_MODE.MAV_MODE_SEMIAUTO:
			return ControlMode.SEMIAUTO;
		case MAV_MODE.MAV_MODE_GUIDED:
			return ControlMode.GUIDED;
		default:
			return ControlMode.AUTO;
		}
	}
	public static byte droneTypeToMavType(DroneType type) {
		switch (type) {
		case QUADXROTOR:
			return MAV_TYPE.MAV_TYPE_QUAD_X_ROTOR;
		case QUADPROTOR:
			return MAV_TYPE.MAV_TYPE_QUAD_P_ROTOR;
		default:
			return MAV_TYPE.MAV_TYPE_ENUM_END;
		}
	}
	
	public static byte droneAutopilotToMavAutopilot(AutoPilot autoPilot) {
		switch (autoPilot) {
		case GENERIC:
			return MAV_AUTOPILOT.MAV_AUTOPILOT_GENERIC;
		case QUADX:
			return MAV_AUTOPILOT.MAV_AUTOPILOT_QUADX;
		default:
			return MAV_AUTOPILOT.MAV_AUTOPILOT_ENUM_END;
		}
	}
}
