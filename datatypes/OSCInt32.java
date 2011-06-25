package datatypes;

import java.math.BigInteger;

import utils.OSCDataObjectFormatException;

public class OSCInt32 extends OSCDataType {

	private BigInteger intVal;

	public OSCInt32(int intVal) {
		this.intVal = BigInteger.valueOf(intVal);
		super.typetag = 'i';
	}

	public OSCInt32(byte[] intBytes) throws OSCDataObjectFormatException {

		if (intBytes.length != 4)
			throw new OSCDataObjectFormatException(
					OSCDataObjectFormatException.ILEGAL_DATA_BYTE_ARRAY_LENGTH, 4,
					intBytes.length, this);

		this.intVal = new BigInteger(intBytes);
		super.typetag = 'i';
	}

	public int getInteger() {
		return intVal.intValue();
	}

	@Override
	public byte[] getByteArray() {
		
		byte [] retBytes = new byte [4];		
		byte [] byteVal = intVal.toByteArray();		
		System.arraycopy(byteVal, 0, retBytes, 4-byteVal.length, byteVal.length);		
		return retBytes;
	}

	@Override
	public boolean equals(OSCDataType dataObj) {
		if(!(dataObj instanceof OSCInt32))
			return false;

		if(((OSCInt32)dataObj).intVal.compareTo(this.intVal) != 0)
			return false;
		
		return true;
	}
	
}
