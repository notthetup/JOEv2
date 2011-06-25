package oscudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

import osc.OSCPort;

public class OSCPortUDP extends OSCPort {

	private DatagramSocket socket = null;

	private final static int MAX_LENGTH = 1536;

	private byte[] packetBuffer = new byte[MAX_LENGTH];

	private InetSocketAddress portAddress;

	public OSCPortUDP(InetSocketAddress portAddress) {

		this.portAddress = portAddress;

		try {
			socket = new DatagramSocket(portAddress);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public boolean portClose() {

		if (socket != null && !socket.isClosed()) {
			socket.close();
		}

		return socket.isClosed();
	}

	public byte[] portListen() {

		DatagramPacket inPacket = new DatagramPacket(packetBuffer, MAX_LENGTH);

		try {
			socket.receive(inPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return inPacket.getData();
	}

	public boolean portOpen() {

		if (socket.isClosed()) {
			try {
				socket.connect(portAddress);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return socket.isConnected();
	}

	public void portSend(byte[] bytes, InetSocketAddress address) {

		if (bytes != null) {
			try {
				socket.send(new DatagramPacket(bytes, bytes.length,address));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
