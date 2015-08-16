package quadx.comm.win;

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
	// --------------------------------------------
	public WinMavLinkRadioStream() {
		super(DEFAULT_COM_PORT, DEFAULT_BUAD_RATE);
	}
	public WinMavLinkRadioStream(String port, int baudRate) {
		super(port, baudRate);
	}
	// --------------------------------------------
	public void connect() throws RadioConnectionException {
		try {
			Enumeration<?> portList = CommPortIdentifier.getPortIdentifiers();
			while (portList.hasMoreElements()) {
				CommPortIdentifier portId = (CommPortIdentifier) portList
						.nextElement();
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
	public void connect(String port, int baudRate) throws RadioConnectionException {
		this.port = port;
		this.baudRate = baudRate;
		connect();
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
