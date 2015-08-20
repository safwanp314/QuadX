package comm.serial;

import java.io.IOException;

/**
 * Created by SAFWAN on 8/18/15.
 */

public interface UsbSerialPort {

    /**
     * 5 data bits.
     */
    public static final int DATABITS_5 = 5;

    /**
     * 6 data bits.
     */
    public static final int DATABITS_6 = 6;

    /**
     * 7 data bits.
     */
    public static final int DATABITS_7 = 7;

    /**
     * 8 data bits.
     */
    public static final int DATABITS_8 = 8;

    /**
     * No parity.
     */
    public static final int PARITY_NONE = 0;

    /**
     * Odd parity.
     */
    public static final int PARITY_ODD = 1;

    /**
     * Even parity.
     */
    public static final int PARITY_EVEN = 2;

    /**
     * Mark parity.
     */
    public static final int PARITY_MARK = 3;

    /**
     * Space parity.
     */
    public static final int PARITY_SPACE = 4;

    /**
     * 1 stop bit.
     */
    public static final int STOPBITS_1 = 1;

    /**
     * 1.5 stop bits.
     */
    public static final int STOPBITS_1_5 = 3;

    /**
     * 2 stop bits.
     */
    public static final int STOPBITS_2 = 2;

    public UsbSerialDriver getDriver();

    public int getPortNumber();

    public String getSerial();

    public void open() throws IOException;

    public void close() throws IOException;

    public int read(final byte[] dest, final int timeoutMillis) throws IOException;

    public int write(final byte[] src, final int timeoutMillis) throws IOException;

    public void setParameters(int baudRate, int dataBits, int stopBits, int parity) throws IOException;
}
