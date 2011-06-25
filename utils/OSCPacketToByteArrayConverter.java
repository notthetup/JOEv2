package utils;

import datatypes.OSCByteStream;
import datatypes.OSCDataType;
import datatypes.OSCString;

import osc.OSCBundle;
import osc.OSCMessage;
import osc.OSCPacket;

public class OSCPacketToByteArrayConverter {

	private static final OSCString bundleStart = new OSCString("#bundle");

	public OSCByteStream convert(OSCPacket packet) {

		try {
			if (packet instanceof OSCBundle) {
				return convertBundle((OSCBundle) packet);
			} else if (packet instanceof OSCMessage) {
				return convertMessage((OSCMessage) packet);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	private OSCByteStream convertMessage(OSCMessage message)
			throws OSCDataObjectFormatException, OSCByteStreamException {

		OSCByteStream byteStream = new OSCByteStream();

		byteStream.write(message.getAddress().getByteArray());

		OSCDataType[] argArray = message.getArguments();
		String typeString = ",";

		for (int index = 0; index < argArray.length; index++) {
			typeString += argArray[index].getTypetag();
		}

		byteStream.write((new OSCString(typeString)).getByteArray());

		for (int index = 0; index < argArray.length; index++) {
			byteStream.write(argArray[index].getByteArray());
		}

		return byteStream;

	}

	private OSCByteStream convertBundle(OSCBundle bundle)
			throws OSCDataObjectFormatException, OSCByteStreamException {

		OSCByteStream byteStream = new OSCByteStream();

		byteStream.write(bundleStart.getByteArray());

		byteStream.write(bundle.getTimestamp().getByteArray());

		OSCPacket[] packetArray = bundle.getPackets();

		for (int index = 0; index < packetArray.length; index++) {

			if (packetArray[index] instanceof OSCBundle) {
				convertBundle((OSCBundle) packetArray[index]);
			} else if (packetArray[index] instanceof OSCMessage) {
				convertMessage((OSCMessage) packetArray[index]);
			}
		}

		return byteStream;
	}
}
