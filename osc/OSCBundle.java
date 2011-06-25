package osc;

import java.util.ArrayList;
import java.util.Date;

import datatypes.OSCTimeTag;

public class OSCBundle extends OSCPacket {

	private OSCTimeTag timeTag;

	private ArrayList<OSCPacket> elements;

	public OSCBundle() {
		this(new OSCTimeTag(new Date()));
	}

	public OSCBundle(OSCTimeTag timeTag) {
		this.timeTag = timeTag;
		elements = new ArrayList<OSCPacket>();
	}

	public OSCTimeTag getTimestamp() {
		return timeTag;
	}

	public OSCPacket[] getPackets() {
		OSCPacket[] retArray = new OSCPacket[elements.size()];
		System.arraycopy(elements.toArray(), 0, retArray, 0, elements.size());

		return retArray;
	}

	public void addPacket(OSCPacket packet) {
		elements.add(packet);
	}

	public boolean equals(OSCPacket dataObj) {

		if (!(dataObj instanceof OSCBundle))
			return false;

		if (!((OSCBundle) dataObj).timeTag.equals(this.timeTag))
			return false;
		
		if (((OSCBundle) dataObj).elements.size() != elements.size())
			return false;

		for (int arrayIndex = 0; arrayIndex < elements.size(); arrayIndex++) {
			if (!((OSCBundle) dataObj).elements.get(arrayIndex).equals(
					this.elements.get(arrayIndex)))
				return false;
		}
		
		return true;
	}
}
