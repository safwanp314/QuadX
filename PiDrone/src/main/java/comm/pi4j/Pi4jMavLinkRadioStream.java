package comm.pi4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import quadx.comm.MavLinkRadioStream;
import quadx.comm.exceptions.RadioConnectionException;

import com.pi4j.io.serial.Baud;
import com.pi4j.io.serial.DataBits;
import com.pi4j.io.serial.FlowControl;
import com.pi4j.io.serial.Parity;
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataEventListener;
import com.pi4j.io.serial.SerialFactory;
import com.pi4j.io.serial.StopBits;

public class Pi4jMavLinkRadioStream extends MavLinkRadioStream implements SerialDataEventListener {

	private Serial serial;

	private final static String DEFAULT_COM_PORT = "/dev/ttyAMA0";
	private String port = DEFAULT_COM_PORT;
	private static final int DEFAULT_BAUD_RATE = 9600;
	private int baudRate = DEFAULT_BAUD_RATE;
		
	public Pi4jMavLinkRadioStream(String port, int baudRate) {
		this.port = port;
		this.baudRate = baudRate;
		serial = SerialFactory.createInstance();
	}

	@Override
	public void connect() throws RadioConnectionException {
		try {
			serial.open(port, Baud.valueOf("_"+baudRate), DataBits._8, Parity.NONE, StopBits._1, FlowControl.HARDWARE);
			serialInStream = null;
			serialOutStream = new SerialOutStream(serial);
			serial.addListener(this);
			connected = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

	@Override
	public void close() {
		try {
			serial.close();
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		} finally {
			connected = false;
		}
	}

	@Override
	public void dataReceived(SerialDataEvent event) {
		try {
			byte[] bytes = event.getBytes();
			serialInStream = new ByteArrayInputStream(bytes);
			dataReceiveEvent();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
