package quadx.drone.controller;

import quadx.comm.MAVLink.enums.MAV_CMD;
import quadx.drone.controller.command.DroneCommand;
import quadx.drone.controller.utils.exceptions.CommandAckFailureException;
import quadx.drone.controller.utils.exceptions.DroneConnectionException;

public class DroneActionHandler {

	private Drone drone;
	private boolean started = false;

	public DroneActionHandler(Drone drone) {
		this.drone = drone;
	}

	public void start() throws DroneConnectionException, CommandAckFailureException {
		try {
			DroneCommand command = drone.getCommand();
			command.command = MAV_CMD.MAV_CMD_NAV_TAKEOFF;
			command.timeout = 50;
			command.repeat = 20;
			
			started = drone.getCommand().sendCommand();
		} catch (CommandAckFailureException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void stop() throws DroneConnectionException, CommandAckFailureException {
		if(started) {
			try {
				DroneCommand command = drone.getCommand();
				command.command = MAV_CMD.MAV_CMD_NAV_EMERGENCY_LAND;
				command.timeout = 20;
				command.repeat = 1000;
				
				drone.getCommand().sendCommand();
			} catch (CommandAckFailureException e) {
				e.printStackTrace();
			}
			started = false;
		}
	}

	public void engineFactorSlider(double value, double delta)
			throws DroneConnectionException {
		if (started) {
			drone.getThrottle().engineFactorSlider  = value;
			drone.getThrottle().performThrottleChangeAction();
		}
	}
	
	public void upDownThrottle(double value, double delta)
			throws DroneConnectionException {
		if (started) {
			drone.getThrottle().upDownThrottle  = value;
			drone.getThrottle().performThrottleChangeAction();
		}
	}

	public void frontRearThrottle(double value, double delta)
			throws DroneConnectionException {
		if (started) {
			drone.getThrottle().frontBackThrottle = value;
			drone.getThrottle().performThrottleChangeAction();
		}
	}

	public void leftRightThrottle(double value, double delta)
			throws DroneConnectionException {
		if (started) {
			drone.getThrottle().leftRightThrottle = value;
			drone.getThrottle().performThrottleChangeAction();
		}
	}

	public void clockAnticlockThrottle(double value, double delta)
			throws DroneConnectionException {
		if (started) {
			drone.getThrottle().rotationThrottle  = value;
			drone.getThrottle().performThrottleChangeAction();
		}
	}
	
	public void lockYaw() throws DroneConnectionException {
		drone.getGuidence().targetYaw = drone.getNav().yaw;
		drone.getGuidence().performGuidenceAction();
	}
}
