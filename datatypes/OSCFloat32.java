package datatypes;

import java.math.BigInteger;

import utils.OSCDataObjectFormatException;

public class OSCFloat32 extends OSCDataType {

	private BigInteger floatVal;

	public OSCFloat32(float floatVal) {
		this.floatVal = BigInteger.valueOf(Float.floatToIntBits(floatVal));
		super.typetag = 'f';
	}

	public OSCFloat32(byte[] floatBytes) throws OSCDataObjectFormatException {

		if (floatBytes.length != 4)
			throw new OSCDataObjectFormatException(
					OSCDataObjectFormatException.ILEGAL_DATA_BYTE_ARRAY_LENGTH, 4,
					floatBytes.length, this);

		this.floatVal = new BigInteger(floatBytes);
		super.typetag = 'f';
	}

	public float getFloat() {
		return Float.intBitsToFloat(floatVal.intValue());
	}

	@Override
	public byte[] getByteArray() {
		
		byte [] retBytes = new byte [4];		
		byte [] byteVal = floatVal.toByteArray();		
		System.arraycopy(byteVal, 0, retBytes, 4-byteVal.length, byteVal.length);		
		return retBytes;
	}
	
	public boolean equals(OSCDataType dataObj) {
		if(!(dataObj instanceof OSCFloat32))
			return false;
		
		if(((OSCFloat32)dataObj).floatVal.compareTo(this.floatVal) !=0)
			return false;
		
		return true;
	}
}
