package quadx.main;

import quadx.comm.MavLinkRadioStream;
import quadx.comm.exceptions.RadioConnectionException;
import quadx.pidrone.PiDrone;
import quadx.pidrone.exceptions.PiDroneConnectionException;

import comm.pi4j.Pi4jMavLinkRadioStream;

public class PiDroneMain {

	public static void main(String[] args) {
	
		try {
			String port = DroneConfig.PORT;
			int baudRate = DroneConfig.BAUD_RATE;
			if(args.length>=1) {
				port = args[0];
			}
			if(args.length>=2) {
				baudRate = Integer.parseInt(args[1]);
			}
			
			MavLinkRadioStream radioStream = new Pi4jMavLinkRadioStream(port, baudRate);
			PiDrone drone = new PiDrone(DroneConfig.NETWORK_ID, DroneConfig.DRONE_ID, radioStream);
			drone.connect();
			drone.start();
		} catch (RadioConnectionException e) {
			e.printStackTrace();
		} catch (PiDroneConnectionException e) {
			e.printStackTrace();
		}
	}
}
