package quadx.drone.controller.propellers.listeners;

import quadx.comm.MAVLink.hudhud.msg_propeller;
import quadx.comm.listeners.MavLinkPropellerEventListener;
import quadx.drone.controller.Drone;

public class DronePropellerEventListener extends
		MavLinkPropellerEventListener {

	private Drone drone;

	public DronePropellerEventListener(Drone drone) {
		this.drone = drone;
	}

	@Override
	public void handlePropellerEvent(msg_propeller msg_propeller) {
		drone.getPropellers().frontLeftRotorSpeed = msg_propeller.propeller1;
		drone.getPropellers().frontRightRotorSpeed = msg_propeller.propeller2;
		drone.getPropellers().rearRightRotorSpeed = msg_propeller.propeller3;
		drone.getPropellers().rearLeftRotorSpeed = msg_propeller.propeller4;
		drone.getPropellers().avgRotorsSpeed = (msg_propeller.propeller1
				+ msg_propeller.propeller2 + msg_propeller.propeller3 + msg_propeller.propeller4) / 4;
		// -----------------------------------------
		drone.getEventHandler().handleDroneEventListener();
	}
}
