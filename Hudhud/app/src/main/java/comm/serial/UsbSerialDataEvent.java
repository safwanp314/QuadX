package comm.serial;

/**
 * Created by SAFWAN on 8/26/15.
 */
public class UsbSerialDataEvent {

    private byte[] data;

    public UsbSerialDataEvent(byte[] data, int size) {
        synchronized (data) {
            this.data = new byte[size];
            for (int i = 0; i < size; i++) {
                this.data[i] = data[i];
            }
        }
    }

    public byte[] getData() {
        return data;
    }
}
