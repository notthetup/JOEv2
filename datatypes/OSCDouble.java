package datatypes;

import java.math.BigInteger;

import utils.OSCDataObjectFormatException;

public class OSCDouble extends OSCDataType {

	private BigInteger doubleVal;

	public OSCDouble(double doubleVal) {
		this.doubleVal = BigInteger.valueOf(Double.doubleToLongBits(doubleVal));
		super.typetag = 'd';
	}

	public OSCDouble(byte[] doubleBytes) throws OSCDataObjectFormatException {

		if (doubleBytes.length != 8)
			throw new OSCDataObjectFormatException(
					OSCDataObjectFormatException.ILEGAL_DATA_BYTE_ARRAY_LENGTH, 8,
					doubleBytes.length, this);

		this.doubleVal = new BigInteger(doubleBytes);
		super.typetag = 'd';
	}

	public double getDoubleVal() {
		return doubleVal.doubleValue();
	}

	@Override
	public byte[] getByteArray() {
		byte [] retBytes = new byte [8];
		byte [] byteVal = doubleVal.toByteArray();
		System.arraycopy(byteVal, 0, retBytes, 8-byteVal.length, byteVal.length);		
		return retBytes;
	}
	
	public boolean equals(OSCDataType dataObj) {
		if(!(dataObj instanceof OSCDouble))
			return false;
		

		if(((OSCDouble)dataObj).doubleVal.compareTo(this.doubleVal) != 0)
			return false;
		
		return true;
	}

}
