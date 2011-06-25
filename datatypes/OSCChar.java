package datatypes;

import utils.OSCDataObjectFormatException;

public class OSCChar extends OSCDataType {

	private char charVal;

	public OSCChar(char charVal) {
		this.charVal = charVal;
		super.typetag = 'c';
	}

	public OSCChar(byte[] charBytes) throws OSCDataObjectFormatException {
		if (charBytes.length != 4)
			throw new OSCDataObjectFormatException(
					OSCDataObjectFormatException.ILEGAL_DATA_BYTE_ARRAY_LENGTH, 4,
					charBytes.length, this);

		this.charVal = (char) charBytes[3];
		super.typetag = 'c';
	}

	public char getChar() {
		return charVal;
	}

	@Override
	public byte[] getByteArray(){
		
		byte[] charBytes = new byte[4];
		charBytes[3] = (byte) charVal;
		return charBytes;
	}
	
	public boolean equals(OSCDataType dataObj) {
		if(!(dataObj instanceof OSCChar))
			return false;
		
		if(((OSCChar)dataObj).charVal != this.charVal)
			return false;
		
		return true;
	}

}
