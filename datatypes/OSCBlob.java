package datatypes;

import java.math.BigInteger;

import utils.OSCDataObjectFormatException;

public class OSCBlob extends OSCDataType {

	private byte[] blobData;

	public OSCBlob(byte[] blobBytes) {
		this.blobData = blobBytes;
		super.typetag = 'b';
	}

	/**
	 * For 
	 * 
	 * @param blobBytes
	 * @return
	 * @throws OSCDataObjectFormatException
	 */
	public static OSCBlob decodeBlob(byte[] blobBytes)
			throws OSCDataObjectFormatException {

		int blobSize = getSize(blobBytes);

		if (blobSize != calculatePaddedLength(blobSize)-4) {
			throw new OSCDataObjectFormatException(
					OSCDataObjectFormatException.BLOB_SIZE_MISMATCH, blobSize,
					(calculatePaddedLength(blobSize)-4), OSCBlob.class);
		}

		byte[] blobData = new byte[blobSize];

		System.arraycopy(blobBytes, 4, blobData, 0, blobSize);
		
		return new OSCBlob(blobData);
	}

	public static final int getSize(byte[] blobBytes) {
		byte[] intBytes = new byte[4];

		System.arraycopy(blobBytes, 0, intBytes, 0, 4);
		
		BigInteger sizeInt = new BigInteger(intBytes);

		return sizeInt.intValue();
	}

	public byte[] getBlobData() {
		return blobData;
	}

	@Override
	public byte[] getByteArray() throws OSCDataObjectFormatException {

		int paddedLength = calculatePaddedLength(blobData.length)-4;

		byte[] binaryData = new byte[paddedLength + 4];

		byte[] sizeByes = BigInteger.valueOf(paddedLength).toByteArray();

		if (sizeByes.length != 4) {
			throw new OSCDataObjectFormatException(
					OSCDataObjectFormatException.ILEGAL_DATA_BYTE_ARRAY_LENGTH, 4,
					sizeByes.length, this);
		}

		System.arraycopy(sizeByes, 0, binaryData, 0, 4);

		System.arraycopy(blobData, 4, binaryData, 0,  blobData.length);
		
		for (int arrayIndex = blobData.length; arrayIndex < paddedLength; arrayIndex++)
			binaryData[arrayIndex + 4] = 0;

		return binaryData;
	}
	
	public boolean equals(OSCDataType dataObj) {
		if(!(dataObj instanceof OSCBlob))
			return false;
		
		if(!((OSCBlob)dataObj).blobData.equals(this.blobData))
			return false;
		
		return true;
	}

}
