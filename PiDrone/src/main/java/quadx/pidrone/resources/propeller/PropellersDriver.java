package quadx.pidrone.resources.propeller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import quadx.pidrone.resources.PiResources;

import com.pi4j.io.spi.SpiDevice;

public class PropellersDriver {

	public int motor1;
	public int motor2;
	public int motor3;
	public int motor4;

	private final byte[] inBuffer = { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
	private SpiDevice spi = PiResources.spi;

	public void update() {
		inBuffer[0] = (byte) motor1;
		inBuffer[1] = (byte) motor2;
		inBuffer[2] = (byte) motor3;
		inBuffer[3] = (byte) motor4;
		try {
			spi.write(new ByteArrayInputStream(inBuffer));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
