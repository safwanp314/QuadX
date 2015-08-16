package quadx.comm.pi4j;

import java.io.IOException;
import java.io.OutputStream;

import com.pi4j.io.serial.Serial;

public class SerialOutStream extends OutputStream {

	private Serial serial;

	public SerialOutStream(Serial serial) {
		this.serial = serial;
	}

	@Override
	public void write(int b) throws IOException {
		serial.write((byte) b);
	}

	@Override
	public void write(byte[] b) throws IOException {
		serial.write(b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		byte[] tb = new byte[len];
		for (int i = 0, j = off; i < len; i++, j++) {
			tb[i] = b[j];
		}
		serial.write(tb);
	}
}
