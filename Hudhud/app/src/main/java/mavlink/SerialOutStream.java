package mavlink;

import java.io.IOException;
import java.io.OutputStream;

import comm.serial.AbstractUsbSerialPort;
import comm.serial.UsbSerialPort;

public class SerialOutStream extends OutputStream {

    private UsbSerialPort serialPort;

    public SerialOutStream(UsbSerialPort serialPort) {
        this.serialPort = serialPort;
    }

    @Override
    public void write(int b) throws IOException {
        byte[] tb = new byte[1];
        tb[0] = (byte) b;
        serialPort.write(tb, AbstractUsbSerialPort.DEFAULT_USB_WRITE_TIMEOUT_MILLIS);
    }

    @Override
    public void write(byte[] b) throws IOException {
        serialPort.write(b, AbstractUsbSerialPort.DEFAULT_USB_WRITE_TIMEOUT_MILLIS);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        byte[] tb = new byte[len];
        for (int i = 0, j = off; i < len; i++, j++) {
            tb[i] = b[j];
        }
        serialPort.write(tb, AbstractUsbSerialPort.DEFAULT_USB_WRITE_TIMEOUT_MILLIS);
    }
}
