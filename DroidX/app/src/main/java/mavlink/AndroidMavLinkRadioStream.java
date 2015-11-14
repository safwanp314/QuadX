package mavlink;

import android.hardware.usb.UsbManager;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import comm.serial.Cp21xxSerialDriver;
import comm.serial.UsbSerialDataEvent;
import comm.serial.UsbSerialDriver;
import comm.serial.UsbSerialPort;
import comm.serial.UsbSerialPortEventListener;
import quadx.comm.MavLinkRadioStream;
import quadx.comm.exceptions.RadioConnectionException;

/**
 * Created by SAFWAN on 8/20/15.
 */
public class AndroidMavLinkRadioStream extends MavLinkRadioStream implements UsbSerialPortEventListener {

    private UsbSerialPort serialPort;
    private int baudRate;

    public AndroidMavLinkRadioStream(UsbManager usbManager, int baudRate) {
        this.baudRate = baudRate;
        UsbSerialDriver driver = new Cp21xxSerialDriver(usbManager);
        serialPort = driver.getPorts().get(0);
    }

    @Override
    public void connect() throws RadioConnectionException {
        try {
            serialPort.open();
            serialPort.setParameters(baudRate, UsbSerialPort.DATABITS_8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
            serialPort.setSerialPortEventListener(this);
            serialInStream = null;
            serialOutStream = new SerialOutStream(serialPort);
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            serialPort.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dataReceivedEvent(UsbSerialDataEvent event) {
        try {
            byte[] bytes = event.getData();
            serialInStream = new ByteArrayInputStream(bytes);
            dataReceiveEvent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
