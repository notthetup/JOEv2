package datatypes;

public class OSCInfinitum extends OSCDataType {

	public OSCInfinitum() {
		super.typetag = 'I';
	}

	@Override
	public byte[] getByteArray() {
		return null;
	}
	
	public boolean equals(OSCDataType dataObj) {
		if(!(dataObj instanceof OSCInfinitum))
			return false;
		
		return true;
	}
}
