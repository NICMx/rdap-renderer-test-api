package mx.nic.rdap.renderer;

import java.io.OutputStream;

/**
 *
 * Class to represent a /dev/null device, to discard all data and report that
 * the operation succeeded
 *
 */
public class DevNullOutputStream extends OutputStream {

	@Override
	public void write(int b) {
		// no code

	}

	public void write(byte[] b, int off, int len) {
		// no code
	};

	@Override
	public void write(byte[] b) {
		// no code
	}

}
