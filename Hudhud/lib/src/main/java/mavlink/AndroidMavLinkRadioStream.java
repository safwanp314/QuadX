package mavlink;

import android.hardware.usb.UsbManager;

import java.io.IOException;

import comm.serial.Cp21xxSerialDriver;
import comm.serial.UsbSerialDriver;
import comm.serial.UsbSerialPort;
import quadx.comm.MavLinkRadioStream;
import quadx.comm.exceptions.RadioConnectionException;

/**
 * Created by SAFWAN on 8/20/15.
 */
public class AndroidMavLinkRadioStream extends MavLinkRadioStream {

    private UsbSerialPort serialPort;

    public AndroidMavLinkRadioStream(UsbManager usbManager, int baudRate) {
        super("UsbManager", baudRate);
        UsbSerialDriver driver = new Cp21xxSerialDriver(usbManager);
        serialPort = driver.getPorts().get(0);
    }

    @Override
    public void connect() throws RadioConnectionException {
        try {
            serialPort.open();
            serialPort.setParameters(57600, UsbSerialPort.DATABITS_8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {

    }
}
