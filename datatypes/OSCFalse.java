package datatypes;

public class OSCFalse extends OSCDataType{

	public OSCFalse() {
		super.typetag = 'F';
	}
	
	@Override
	public byte[] getByteArray() {
		return null;
	}
	
	public boolean equals(OSCDataType dataObj) {
		if(!(dataObj instanceof OSCFalse))
			return false;
		
		return true;
	}
}
