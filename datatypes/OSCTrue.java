package datatypes;

public class OSCTrue extends OSCDataType {

	public OSCTrue() {
		super.typetag = 'T';
	}

	public byte[] getByteArray() {
		return null;
	}

	public boolean equals(OSCDataType dataObj) {

		if (!(dataObj instanceof OSCTrue))
			return false;

		return true;
	}

}
