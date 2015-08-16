package quadx.main;

import quadx.comm.exceptions.RadioConnectionException;
import quadx.pidrone.PiDrone;
import quadx.pidrone.exceptions.PiDroneConnectionException;

public class PiDroneMain {

	public static void main(String[] args) {
	
		PiDrone drone = new PiDrone(0x03, 0x04);
		try {
			drone.connect("/dev/ttyAMA0", 57600);
			drone.start();
		} catch (RadioConnectionException e) {
			e.printStackTrace();
		} catch (PiDroneConnectionException e) {
			e.printStackTrace();
		}
	}
}
