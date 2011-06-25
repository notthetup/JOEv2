package datatypes;

import java.math.BigInteger;

import utils.OSCDataObjectFormatException;

public class OSCInt64 extends OSCDataType {

	private BigInteger longVal;

	public OSCInt64(long longVal) {
		this.longVal = BigInteger.valueOf(longVal);
		super.typetag = 'h';
	}

	public OSCInt64(byte[] intBytes) throws OSCDataObjectFormatException {

		if (intBytes.length != 8)
			throw new OSCDataObjectFormatException(
					OSCDataObjectFormatException.ILEGAL_DATA_BYTE_ARRAY_LENGTH,
					8, intBytes.length, this);

		this.longVal = new BigInteger(intBytes);
		super.typetag = 'h';
	}

	public long getInteger() {
		return longVal.longValue();
	}

	@Override
	public byte[] getByteArray() {
		byte[] retBytes = new byte[8];
		byte[] byteVal = longVal.toByteArray();
		System.arraycopy(byteVal, 0, retBytes, 8 - byteVal.length,
				byteVal.length);
		return retBytes;
	}

	@Override
	public boolean equals(OSCDataType dataObj) {
		if(!(dataObj instanceof OSCInt64))
			return false;

		if(((OSCInt64)dataObj).longVal.compareTo(this.longVal) != 0)
			return false;
		
		return true;
	}

}
