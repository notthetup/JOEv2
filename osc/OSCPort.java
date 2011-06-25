package osc;

import java.net.DatagramSocket;

public abstract class OSCPort {
	
	protected DatagramSocket socket;
	
	public static final int defaultSCOSCPort = 57110;
	
	public static final int defaultSCLangOSCPort = 57120;

	public static final int defalutDatagramBufferSize = 1536;
	
	/**
	 * Close the socket if this hasn't already happened.
	 * @see java.lang.Object#finalize()
	 */
	protected void finalize() throws Throwable {
		super.finalize();
		socket.close();
	}
	
	/**
	 * Close the socket and free-up resources. It's recommended that clients call
	 * this when they are done with the port.
	 */
	public void close() {
		socket.close();
	}

}
