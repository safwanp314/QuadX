package comm.serial;

import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.util.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SAFWAN on 8/18/15.
 */
public class Cp21xxSerialDriver implements UsbSerialDriver {

    private static final String TAG = Cp21xxSerialDriver.class.getSimpleName();

    public static final int VENDOR_SILABS = 0x10c4;
    public static final int SILABS_CP2102 = 0xea60;
    public static final int SILABS_CP2105 = 0xea70;
    public static final int SILABS_CP2108 = 0xea71;
    public static final int SILABS_CP2110 = 0xea80;

    private final UsbDevice mDevice;
    private final UsbSerialPort mPort;

    public Cp21xxSerialDriver(UsbManager usbManager) {
        UsbDevice device = null;
        for (final UsbDevice usbDevice : usbManager.getDeviceList().values()) {
            if (usbDevice.getVendorId() == VENDOR_SILABS) {
                if (usbDevice.getProductId() == SILABS_CP2102
                        || usbDevice.getProductId() == SILABS_CP2105
                        || usbDevice.getProductId() == SILABS_CP2108
                        || usbDevice.getProductId() == SILABS_CP2110) {

                    device = usbDevice;
                    break;
                }
            }
        }
        mDevice = device;
        mPort = new Cp21xxSerialPort(usbManager, mDevice, 0);
        Log.d(TAG, mPort.toString());
    }

    @Override
    public UsbDevice getDevice() {
        return mDevice;
    }

    @Override
    public List<UsbSerialPort> getPorts() {
        return Collections.singletonList(mPort);
    }

    public static Map<Integer, int[]> getSupportedDevices() {
        final Map<Integer, int[]> supportedDevices = new LinkedHashMap<Integer, int[]>();
        supportedDevices.put(Integer.valueOf(VENDOR_SILABS),
                new int[]{
                        SILABS_CP2102,
                        SILABS_CP2105,
                        SILABS_CP2108,
                        SILABS_CP2110
                });
        return supportedDevices;
    }

    public class Cp21xxSerialPort extends CommonUsbSerialPort {

        private static final int DEFAULT_BAUD_RATE = 9600;

        private static final int USB_WRITE_TIMEOUT_MILLIS = 5000;
        private static final int REQTYPE_HOST_TO_DEVICE = 0x41;

        private static final int IFC_ENABLE_REQUEST_CODE = 0x00;
        private static final int SET_BAUDDIV_REQUEST_CODE = 0x01;
        private static final int SET_LINE_CTL_REQUEST_CODE = 0x03;
        private static final int SET_BREAK_REQUEST_CODE = 0x05;
        private static final int SET_MHS_REQUEST_CODE = 0x07;
        private static final int PURGE_REQUEST_CODE = 0x12;
        private static final int SET_BAUDRATE_REQUEST_CODE = 0x1E;

        private static final int UART_ENABLE = 0x0001;
        private static final int UART_DISABLE = 0x0000;

        private static final int BAUD_RATE_GEN_FREQ = 0x384000;

        private static final int MCR_DTR = 0x0001;
        private static final int MCR_RTS = 0x0002;
        private static final int MCR_ALL = 0x0003;

        private static final int CONTROL_WRITE_DTR = 0x0100;
        private static final int CONTROL_WRITE_RTS = 0x0200;

        private UsbEndpoint mReadEndpoint;
        private UsbEndpoint mWriteEndpoint;

        public Cp21xxSerialPort(UsbManager usbManager, UsbDevice device, int portNumber) {
            super(usbManager, device, portNumber);
        }

        @Override
        public UsbSerialDriver getDriver() {
            return Cp21xxSerialDriver.this;
        }

        private int setConfigSingle(int request, int value) {
            return mConnection.controlTransfer(REQTYPE_HOST_TO_DEVICE, request, value,
                    0, null, 0, USB_WRITE_TIMEOUT_MILLIS);
        }

        @Override
        public void open() throws IOException {
            if (mConnection != null) {
                throw new IOException("Already opened.");
            }
            mConnection = mUsbManager.openDevice(mDevice);
            if (mConnection == null) {
                throw new IOException("Connection failed.");
            }
            boolean opened = false;
            try {
                for (int i = 0; i < mDevice.getInterfaceCount(); i++) {
                    UsbInterface usbInterface = mDevice.getInterface(i);
                    if (mConnection.claimInterface(usbInterface, true)) {
                        Log.d(TAG, "Interface " + i + " SUCCESS");
                    } else {
                        Log.d(TAG, "Interface " + i + " FAIL");
                    }
                }
                UsbInterface dataInterface = mDevice.getInterface(mDevice.getInterfaceCount() - 1);
                for (int i = 0; i < dataInterface.getEndpointCount(); i++) {
                    UsbEndpoint endpoint = dataInterface.getEndpoint(i);
                    if (endpoint.getType() == UsbConstants.USB_ENDPOINT_XFER_BULK) {
                        if (endpoint.getDirection() == UsbConstants.USB_DIR_IN) {
                            mReadEndpoint = endpoint;
                        } else {
                            mWriteEndpoint = endpoint;
                        }
                    }
                }
                setConfigSingle(IFC_ENABLE_REQUEST_CODE, UART_ENABLE);
                setConfigSingle(SET_MHS_REQUEST_CODE, MCR_ALL | CONTROL_WRITE_DTR | CONTROL_WRITE_RTS);
                setConfigSingle(SET_BAUDDIV_REQUEST_CODE, BAUD_RATE_GEN_FREQ / DEFAULT_BAUD_RATE);
                opened = true;
            } catch (Exception e) {
                throw e;
            } finally {
                if (!opened) {
                    close();
                }
            }
        }

        @Override
        public void close() throws IOException {
            if (mConnection == null) {
                throw new IOException("Already closed");
            }
            try {
                mConnection.close();
            } finally {
                mConnection = null;
            }
        }

        @Override
        public int read(byte[] dest, int timeoutMillis) throws IOException {
            final int numReadBytes;
            synchronized (mReadBufferLock) {
                int readLength = Math.min(dest.length, mReadBuffer.length);
                numReadBytes = mConnection.bulkTransfer(mReadEndpoint, mReadBuffer, readLength, timeoutMillis);
                if (numReadBytes < 0) {
                    return 0;
                }
                System.arraycopy(mReadBuffer, 0, dest, 0, numReadBytes);
            }
            return numReadBytes;
        }

        @Override
        public int write(byte[] src, int timeoutMillis) throws IOException {
            int offset = 0;

            while (offset < src.length) {
                final int numWriteBytes;
                final int bytesWritten;

                synchronized (mWriteBufferLock) {
                    final byte[] writeBuffer;

                    numWriteBytes = Math.min(src.length - offset, mWriteBuffer.length);
                    if (offset == 0) {
                        writeBuffer = src;
                    } else {
                        // bulkTransfer does not support offsets, make a copy.
                        System.arraycopy(src, offset, mWriteBuffer, 0, numWriteBytes);
                        writeBuffer = mWriteBuffer;
                    }

                    bytesWritten = mConnection.bulkTransfer(mWriteEndpoint, writeBuffer, numWriteBytes,
                            timeoutMillis);
                }
                if (bytesWritten <= 0) {
                    throw new IOException("Error writing " + numWriteBytes
                            + " bytes at offset " + offset + " length=" + src.length);
                }

                Log.d(TAG, "Wrote amt=" + bytesWritten + " attempted=" + numWriteBytes);
                offset += bytesWritten;
            }
            return offset;
        }

        private void setBaudRate(int baudRate) throws IOException {
            byte[] data = new byte[]{
                    (byte) (baudRate & 0xff),
                    (byte) ((baudRate >> 8) & 0xff),
                    (byte) ((baudRate >> 16) & 0xff),
                    (byte) ((baudRate >> 24) & 0xff)
            };
            int ret = mConnection.controlTransfer(REQTYPE_HOST_TO_DEVICE, SET_BAUDRATE_REQUEST_CODE,
                    0, 0, data, 4, USB_WRITE_TIMEOUT_MILLIS);
            if (ret < 0) {
                throw new IOException("Error setting baud rate.");
            }
        }

        @Override
        public void setParameters(int baudRate, int dataBits, int stopBits, int parity) throws IOException {

            setBaudRate(baudRate);
            int configDataBits = 0;
            switch (dataBits) {
                case DATABITS_5:
                    configDataBits |= 0x0500;
                    break;
                case DATABITS_6:
                    configDataBits |= 0x0600;
                    break;
                case DATABITS_7:
                    configDataBits |= 0x0700;
                    break;
                case DATABITS_8:
                    configDataBits |= 0x0800;
                    break;
                default:
                    configDataBits |= 0x0800;
                    break;
            }

            switch (parity) {
                case PARITY_ODD:
                    configDataBits |= 0x0010;
                    break;
                case PARITY_EVEN:
                    configDataBits |= 0x0020;
                    break;
            }

            switch (stopBits) {
                case STOPBITS_1:
                    configDataBits |= 0;
                    break;
                case STOPBITS_2:
                    configDataBits |= 2;
                    break;
            }
            setConfigSingle(SET_LINE_CTL_REQUEST_CODE, configDataBits);
        }
    }
}
