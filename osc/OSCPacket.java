package osc;

import datatypes.OSCByteStream;
import utils.OSCPacketToByteArrayConverter;

public abstract class OSCPacket {
	
	private static OSCPacketToByteArrayConverter converter = new OSCPacketToByteArrayConverter();
	
	public OSCByteStream getByteStream(){	
		return converter.convert(this);
	}
	
	public abstract boolean equals(OSCPacket dataObj);
}
