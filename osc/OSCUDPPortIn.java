package osc;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;


public class OSCUDPPortIn extends OSCPort implements Runnable {

	protected boolean isListening;

	public OSCUDPPortIn(int port) throws SocketException {
		socket = new DatagramSocket(port);
	}

	public void run() {
		byte[] buffer = new byte[OSCPort.defalutDatagramBufferSize];
		DatagramPacket packet = new DatagramPacket(buffer, 1536);
		while (isListening) {
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void startListening() {
		isListening = true;
		Thread thread = new Thread(this);
		thread.start();
	}

	public void stopListening() {
		isListening = false;
	}

	public boolean isListening() {
		return isListening;
	}

	public void addListener(String anAddress, OSCListner listener) {
	}

	public void close() {
		super.close();
	}

}
