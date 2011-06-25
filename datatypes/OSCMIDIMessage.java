package datatypes;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

import utils.OSCDataObjectFormatException;

public class OSCMIDIMessage extends OSCDataType {

	private ShortMessage midiMessage;

	private int portId = 0;

	private static int DEFAULT_PORT_ID = 0;

	public OSCMIDIMessage(MidiMessage midiMessage, int portID) {
		this.midiMessage = (ShortMessage) midiMessage;
		this.portId = portID;
		super.typetag = 'm';
	}

	public OSCMIDIMessage(MidiMessage midiMessage) {
		this(midiMessage, DEFAULT_PORT_ID);
	}

	public OSCMIDIMessage(byte[] midiBytes) throws OSCDataObjectFormatException {

		if (midiBytes.length != 4)
			throw new OSCDataObjectFormatException(
					OSCDataObjectFormatException.ILEGAL_DATA_BYTE_ARRAY_LENGTH, 4,
					midiBytes.length, this);

		this.midiMessage = new ShortMessage();
		try {
			midiMessage.setMessage(midiBytes[1], midiBytes[2], midiBytes[3]);
		} catch (InvalidMidiDataException e) {
			throw new OSCDataObjectFormatException(
					OSCDataObjectFormatException.INVALID_MIDI_DATA, 0, 0, e);
		}
		this.portId = (int) midiBytes[0];
		super.typetag = 'm';
	}

	public MidiMessage getMidiMessage() {
		return midiMessage;
	}

	public int getPortId() {
		return portId;
	}

	@Override
	public byte[] getByteArray() {
		
		byte [] retBytes = new byte [4];		
		byte [] byteVal = midiMessage.getMessage();		
		System.arraycopy(byteVal, 0, retBytes, 1, byteVal.length);
		retBytes[0] = (byte) portId;
		return retBytes;
		
	}
	
	public boolean equals(OSCDataType dataObj) {
		if(!(dataObj instanceof OSCMIDIMessage))
			return false;
		

		if(((OSCMIDIMessage)dataObj).midiMessage.getMessage().equals(this.midiMessage.getMessage()))
			return false;
				
		if(((OSCMIDIMessage)dataObj).portId != (this.portId))
			return false;
		
		return true;
	}

}
