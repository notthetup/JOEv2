package osc;

import java.util.ArrayList;

import utils.OSCDataObjectFormatException;

import datatypes.OSCDataType;
import datatypes.OSCString;

public class OSCMessage extends OSCPacket{
	
	private OSCString address;
	private ArrayList<OSCDataType> arguments;

	public OSCMessage(String string) throws OSCDataObjectFormatException {
		this(string,null);
	}
	
	public OSCMessage() throws OSCDataObjectFormatException{
		this("/",null);
	}

	public OSCMessage(String string, OSCDataType[] arguments) throws OSCDataObjectFormatException {
		if(string.charAt(0) != '/')
			throw new OSCDataObjectFormatException(OSCDataObjectFormatException.ILLEGAL_ADDRESS_PATTERN_START,'/',string.charAt(0),this);
		this.address = new OSCString(string);
		this.arguments = new ArrayList<OSCDataType>();
		if(arguments != null)
			this.addArguments(arguments);
	}

	public void addArgument(OSCDataType argument) {
		arguments.add(argument);
	}

	public void addArguments(OSCDataType[] arguments) {
		for(int index = 0; index < arguments.length; index++)
				this.arguments.add(arguments[index]);
	}

	public void setAddress(OSCString addressPattern) {
		address = addressPattern;
	}

	public OSCString getAddress() {
		return address;		
	}

	public OSCDataType [] getArguments() {
		
		OSCDataType[] retArray = new OSCDataType[arguments.size()];
		
		System.arraycopy(arguments.toArray(),0,retArray,0,arguments.size());
				
		return retArray;
	}
	
	public boolean equals(OSCPacket dataObj) {
		
		if(!(dataObj instanceof OSCMessage))
			return false;
		
		if(!((OSCMessage)dataObj).getAddress().equals(this.address))
			return false;
		
		if(((OSCMessage)dataObj).arguments.size() != this.arguments.size())
			return false;
		
		for(int arrayIndex = 0; arrayIndex < arguments.size(); arrayIndex++)
		{
			if(!((OSCMessage)dataObj).arguments.get(arrayIndex).equals(this.arguments.get(arrayIndex)))
				return false;
		}
		
		return true;
	}
}
