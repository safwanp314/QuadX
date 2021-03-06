package quadx.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DroneConfig {
	
	public static int NETWORK_ID = 0x03;
	public static int DRONE_ID = 0x02;
	
	public static String PORT = "/dev/ttyAMA0";
	public static int BAUD_RATE = 57600;
	
	static {
		try {
			Properties props = new Properties();
			props.load(new FileInputStream("config.properties"));
			
			NETWORK_ID = Integer.parseInt(props.getProperty("drone.networkid"));
			DRONE_ID = Integer.parseInt(props.getProperty("drone.id"));
			
			PORT = props.getProperty("drone.port");
			BAUD_RATE = Integer.parseInt(props.getProperty("drone.baudrate"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
