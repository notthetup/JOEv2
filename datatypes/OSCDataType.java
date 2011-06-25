package datatypes;

import utils.OSCDataObjectFormatException;

public abstract class OSCDataType {
	
	protected char typetag = 'u';
	
	
	public final char getTypetag() {
		return typetag;
	}
	
	public abstract byte[] getByteArray() throws OSCDataObjectFormatException;
	
	public abstract boolean equals (OSCDataType dataObj);
	
	protected static boolean isMultipleofFour(int size) {

		if (size%4 == 0)
			return true;
		else
			return false;
	}
	
	protected static int calculatePaddedLength(int size){
		
		if(isMultipleofFour(size))
			return size;
		else
			return size + (4 - size%4);
	}
	
	

}
