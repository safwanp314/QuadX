package quadx.pidrone.resources.imu;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import quadx.pidrone.resources.PiResources;

import com.pi4j.io.spi.SpiDevice;

public class ArduImu {

	public float pitch;
	public float roll;
	public float yaw;
	
	public float latitude;
	public float longitude;
	public float altitude;

	private final byte[] inBuffer = { (byte) 0x01, (byte) 0x02, (byte) 0x03,
			(byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08,
			(byte) 0x09, (byte) 0x0A, (byte) 0x0B, (byte) 0x0C, (byte) 0x0D,
			(byte) 0x0E, (byte) 0x0F, (byte) 0x10, (byte) 0x11, (byte) 0x12,
			(byte) 0x13, (byte) 0x14, (byte) 0x15, (byte) 0x16, (byte) 0x17,
			(byte) 0x18 };
	private SpiDevice spi = PiResources.spi;

	private final float IMU_SCALE_FACTOR = 100.0f;

	public void readImu() {
		try {
			byte[] outBuffer = spi.write(new ByteArrayInputStream(inBuffer));
			int rollInt = ((int) outBuffer[0] << 0) & 0x000000ff
					| ((int) outBuffer[1] << 8) & 0x0000ff00
					| ((int) outBuffer[2] << 16) & 0x00ff0000
					| ((int) outBuffer[3] << 24) & 0xff000000;
			int pitchInt = ((int) outBuffer[4] << 0) & 0x000000ff
					| ((int) outBuffer[5] << 8) & 0x0000ff00
					| ((int) outBuffer[6] << 16) & 0x00ff0000
					| ((int) outBuffer[7] << 24) & 0xff000000;
			int yawInt = ((int) outBuffer[8] << 0) & 0x000000ff
					| ((int) outBuffer[9] << 8) & 0x0000ff00
					| ((int) outBuffer[10] << 16) & 0x00ff0000
					| ((int) outBuffer[11] << 24) & 0xff000000;
			int altitudeInt = ((int) outBuffer[12] << 0) & 0x000000ff
					| ((int) outBuffer[13] << 8) & 0x0000ff00
					| ((int) outBuffer[14] << 16) & 0x00ff0000
					| ((int) outBuffer[15] << 24) & 0xff000000;
			int latitudeInt = ((int) outBuffer[16] << 0) & 0x000000ff
					| ((int) outBuffer[17] << 8) & 0x0000ff00
					| ((int) outBuffer[18] << 16) & 0x00ff0000
					| ((int) outBuffer[19] << 24) & 0xff000000;
			int longitudeInt = ((int) outBuffer[20] << 0) & 0x000000ff
					| ((int) outBuffer[21] << 8) & 0x0000ff00
					| ((int) outBuffer[22] << 16) & 0x00ff0000
					| ((int) outBuffer[23] << 24) & 0xff000000;
			
			pitch = Float.intBitsToFloat(pitchInt)/IMU_SCALE_FACTOR;
			roll = Float.intBitsToFloat(rollInt)/IMU_SCALE_FACTOR;
			yaw = Float.intBitsToFloat(yawInt)/IMU_SCALE_FACTOR;
			altitude = Float.intBitsToFloat(altitudeInt)/IMU_SCALE_FACTOR;
			latitude = Float.intBitsToFloat(latitudeInt)/IMU_SCALE_FACTOR;
			longitude = Float.intBitsToFloat(longitudeInt)/IMU_SCALE_FACTOR;
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
