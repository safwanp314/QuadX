package comm.win;

import java.io.IOException;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;

import quadx.comm.MavLinkRadioStream;
import quadx.comm.exceptions.RadioConnectionException;

public class WinMavLinkRadioStream extends MavLinkRadioStream implements SerialPortEventListener {

	private SerialPort serialPort;
	
	private final static String DEFAULT_COM_PORT = "COM1";
	private String port = DEFAULT_COM_PORT;
	private static final int DEFAULT_BAUD_RATE = 9600;
	private int baudRate = DEFAULT_BAUD_RATE;
		
	public static final int DEFAULT_MONITOR_INTERVAL = 50; // milliseconds
	// --------------------------------------------
	public WinMavLinkRadioStream(String port, int baudRate) {
		this.port = port;
		this.baudRate = baudRate;
	}
	// --------------------------------------------
	public void connect() throws RadioConnectionException {
		try {
			Enumeration<?> portList = CommPortIdentifier.getPortIdentifiers();
			while (portList.hasMoreElements()) {
				CommPortIdentifier portId = (CommPortIdentifier) portList
						.nextElement();
				System.out.println(portId);
				if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
					if (portId.getName().equals(port)) {
						serialPort = (SerialPort) portId.open("SerialPort"+ port, DEFAULT_MONITOR_INTERVAL);
						serialPort.setSerialPortParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
						serialPort.addEventListener(this);
						serialPort.notifyOnDataAvailable(true);
						serialOutStream = serialPort.getOutputStream();
						serialInStream = serialPort.getInputStream();
						connected = true;
						System.out.println("Serial port found " + portId.getName());	
					}
				}
			}

		} catch (IOException | PortInUseException
				| UnsupportedCommOperationException | TooManyListenersException e) {
			e.printStackTrace();
			throw new RadioConnectionException();
		}
		if (!connected) {
			throw new RadioConnectionException();
		}
	}
	// --------------------------------------------
	public void close() {
		try {
			serialOutStream.close();
			serialInStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			serialPort.close();
			connected = false;
		}
	}
	// --------------------------------------------
	@Override
	public void serialEvent(SerialPortEvent event) {
		switch (event.getEventType()) {
		case SerialPortEvent.DATA_AVAILABLE:
			try {
				dataReceiveEvent();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}
}
