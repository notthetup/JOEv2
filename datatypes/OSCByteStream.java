package datatypes;

import utils.OSCByteStreamException;
import utils.OSCDataObjectFormatException;

public class OSCByteStream {

	private byte[] buf;

	private int readIndex = 0;

	private int writeIndex = 0;

	public OSCByteStream() throws OSCDataObjectFormatException {
		this(new byte[32]);
	}

	public OSCByteStream(int size) throws OSCDataObjectFormatException {
		this(new byte[size]);
	}

	public OSCByteStream(byte[] byteArray) throws OSCDataObjectFormatException {

		if (byteArray == null)
			throw new OSCDataObjectFormatException(
					OSCDataObjectFormatException.ILEGAL_DATA_BYTE_ARRAY_LENGTH,
					4, 0, this);

		if (!isMultipleofFour(byteArray.length))
			throw new OSCDataObjectFormatException(
					OSCDataObjectFormatException.ILEGAL_DATA_BYTE_ARRAY_LENGTH,
					4, byteArray.length, this);

		this.buf = byteArray;
	}

	public byte[] read(int size) throws OSCByteStreamException {

		if (size % 4 != 0 || size <= 0)
			throw new OSCByteStreamException(OSCByteStreamException.INPUT);

		if (size > (buf.length - readIndex))
			throw new IndexOutOfBoundsException();

		byte[] byteArray;
		byteArray = this.readSilent(size);
		readIndex += size;
		return byteArray;
	}

	public int getImmediateStringLength() {

		for (int index = 0; index < (buf.length- readIndex); index++) {
			if (buf[index + readIndex] == 0) {
				return index + 1;
			}
		}
		return -1;
	}

	public byte[] readSilent(int size) throws OSCByteStreamException {

		byte[] byteArray = new byte[size];
		System.arraycopy(buf, readIndex, byteArray, 0, size);
		return byteArray;
	}

	public void skip(int size) throws OSCByteStreamException {
		if (size % 4 != 0)
			throw new OSCByteStreamException(OSCByteStreamException.INPUT);

		readIndex += size;
	}

	public int available() {
		return (buf.length - readIndex);
	}

	public void write(byte[] byteArray) throws OSCByteStreamException {

		if (byteArray == null)
			return;

		if (!isMultipleofFour(byteArray.length))
			throw new OSCByteStreamException(OSCByteStreamException.OUTPUT);

		int newBufLength = writeIndex + byteArray.length ;
		
		if (newBufLength > buf.length) {
			byte newbuf[] = new byte[Math.max((this.buf.length * 2),
					newBufLength)];
			System.arraycopy(this.buf, 0, newbuf, 0, this.buf.length);
			this.buf = newbuf;
		}

		System.arraycopy(byteArray, 0, this.buf, writeIndex, byteArray.length);
		writeIndex += byteArray.length;
	}

	public byte[] toByteArray() {
		byte[] byteArray = new byte[this.writeIndex];
		
		System.arraycopy(buf, 0, byteArray, 0, this.writeIndex);
		
		return byteArray;
	}

	private static boolean isMultipleofFour(int size) {

		if (size % 4 == 0)
			return true;
		else
			return false;
	}
	
	public boolean equals(OSCByteStream inStream) {
		
		if(this.buf.length != inStream.buf.length)
			return false;
		
		for(int arrayIndex = 0 ; arrayIndex < this.buf.length; arrayIndex++){
			if(this.buf[arrayIndex] != inStream.buf[arrayIndex])
				return false;
		}
		
		return true;
		
	}

}
