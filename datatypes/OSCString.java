package datatypes;

import java.io.UnsupportedEncodingException;

public class OSCString extends OSCDataType{

	private String string = null;
	
	public OSCString(String string) {
		this.string = string;
		super.typetag = 's';
	}
	
	public OSCString(byte [] stringbytes){
		
			
		try {
			this.string = new String(stringbytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("UTF-8 Encoding unavalible. Switching to default. String data might be corrupted");
			this.string = new String(stringbytes);			
		}
		
		string = string.trim();
		
		super.typetag = 's';
	}
	
	public String getString() {
		return string;
	}
			
	@Override
	public byte[] getByteArray() {
		
		int paddedLength = calculatePaddedLength(string.length()+1);
		byte [] stringBytes = new byte [paddedLength];
		byte [] tempBytes;
		
		try {
			tempBytes = string.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("UTF-8 Encoding unavalible. Switching to default. String data might be corrupted");
			tempBytes = string.getBytes();
		}	
		
		System.arraycopy(tempBytes, 0, stringBytes, 0, string.length());
		for (int index = string.length(); index < paddedLength; index++)
			stringBytes[index] = 0;
		
		return stringBytes;
	}

	@Override
	public boolean equals(OSCDataType dataObj) {
		if(!(dataObj instanceof OSCString))
			return false;
		
		if(!((OSCString)dataObj).string.equals(this.string))
			return false;
		
		return true;
	}
	
	
	
}
