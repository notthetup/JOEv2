package datatypes;

public class OSCNIL extends OSCDataType{

	public OSCNIL() {
		super.typetag = 'N';
	}
	
	@Override
	public byte[] getByteArray(){
		return null;
	}

	@Override
	public boolean equals(OSCDataType dataObj) {
		if(!(dataObj instanceof OSCNIL))
			return false;
		
		return true;
	}
}
