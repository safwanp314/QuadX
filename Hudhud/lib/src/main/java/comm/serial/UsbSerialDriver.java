package comm.serial;

import android.hardware.usb.UsbDevice;

import java.util.List;

/**
 * Created by SAFWAN on 8/18/15.
 */

public interface UsbSerialDriver {

    public UsbDevice getDevice();

    public List<UsbSerialPort> getPorts();
}
