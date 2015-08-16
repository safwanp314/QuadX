package quadx.pidrone.resources;

import java.io.IOException;

import com.pi4j.io.spi.SpiChannel;
import com.pi4j.io.spi.SpiDevice;
import com.pi4j.io.spi.SpiFactory;


public class PiResources {

	public static SpiDevice spi;
	
	static {
		try {
			spi = SpiFactory.getInstance(SpiChannel.CS0, 62500, SpiDevice.DEFAULT_SPI_MODE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
