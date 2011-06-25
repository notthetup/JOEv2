package datatypes;

import java.util.ArrayList;

import utils.OSCDataObjectFormatException;

public class OSCDataTypeArray extends OSCDataType{
	
	private ArrayList<OSCDataType> argList = new ArrayList<OSCDataType>();
	
	public OSCDataTypeArray() {
		this.typetag = 'a';
	}
	
	
	public OSCDataType get (int pos){
		return (OSCDataType) argList.get(pos);
	}
	
	
	public void add (OSCDataType arg){
		argList.add(arg);
	}


	@Override
	public byte[] getByteArray() throws OSCDataObjectFormatException{
		
		int argArraySize = 0;
		int tempArraySize = 0;
		
		for(int arrayIndex = 0; arrayIndex < argList.size(); arrayIndex++){
			argArraySize += ((OSCDataType)argList.get(arrayIndex)).getByteArray().length;
		}
		
		byte [] argByteArray = new byte [argArraySize];
				
		for(int arrayIndex = 0; arrayIndex < argList.size(); arrayIndex++){
			byte[] tempArray = ((OSCDataType)argList.get(arrayIndex)).getByteArray();
			System.arraycopy(tempArray, 0, argByteArray, tempArraySize, tempArray.length);
			tempArraySize += tempArray.length;
		}
		return argByteArray;
	}

	
	public boolean equals(OSCDataType dataObj) {

		if(!(dataObj instanceof OSCDataTypeArray))
			return false;

		if (((OSCDataTypeArray) dataObj).argList.size() != argList.size())
			return false;
		
		for (int arrayIndex = 0; arrayIndex < argList.size(); arrayIndex++) {
			if (!((OSCDataTypeArray) dataObj).argList.get(arrayIndex).equals(
					this.argList.get(arrayIndex)))
				return false;
		}
		
		return true;
	}
}
