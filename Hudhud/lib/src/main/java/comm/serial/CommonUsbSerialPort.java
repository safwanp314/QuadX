package comm.serial;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;

import java.io.IOException;

/**
 * Created by SAFWAN on 8/18/15.
 */
public abstract class CommonUsbSerialPort implements UsbSerialPort {

    public static final int DEFAULT_READ_BUFFER_SIZE = 16 * 1024;
    public static final int DEFAULT_WRITE_BUFFER_SIZE = 16 * 1024;

    protected  final UsbManager mUsbManager;
    protected final UsbDevice mDevice;
    protected final int mPortNumber;

    protected UsbDeviceConnection mConnection = null;

    protected final Object mReadBufferLock = new Object();
    protected final Object mWriteBufferLock = new Object();

    protected final byte[] mReadBuffer;
    protected final byte[] mWriteBuffer;

    public CommonUsbSerialPort(UsbManager usbManager, UsbDevice device, int portNumber) {
        mUsbManager = usbManager;
        mDevice = device;
        mPortNumber = portNumber;

        mReadBuffer = new byte[DEFAULT_READ_BUFFER_SIZE];
        mWriteBuffer = new byte[DEFAULT_WRITE_BUFFER_SIZE];
    }

    @Override
    public String toString() {
        return String.format("<%s device_name=%s device_id=%s port_number=%s>",
                getClass().getSimpleName(), mDevice.getDeviceName(),
                mDevice.getDeviceId(), mPortNumber);
    }

    @Override
    public int getPortNumber() {
        return mPortNumber;
    }

    @Override
    public String getSerial() {
        return mConnection.getSerial();
    }

    @Override
    public abstract void open() throws IOException;

    @Override
    public abstract void close() throws IOException;

    @Override
    public abstract int read(final byte[] dest, final int timeoutMillis) throws IOException;

    @Override
    public abstract int write(final byte[] src, final int timeoutMillis) throws IOException;

    @Override
    public abstract void setParameters(int baudRate, int dataBits, int stopBits, int parity) throws IOException;
}