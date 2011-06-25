package test;

import javax.sound.midi.ShortMessage;

import osc.OSCMessage;
import utils.OSCDataObjectFormatException;
import datatypes.OSCChar;
import datatypes.OSCDataType;
import datatypes.OSCDouble;
import datatypes.OSCFalse;
import datatypes.OSCFloat32;
import datatypes.OSCInt32;
import datatypes.OSCInt64;
import datatypes.OSCMIDIMessage;
import datatypes.OSCString;
import datatypes.OSCTrue;

public class RamdomMessageGenerator {

	private static int MAX_STRING_LENGTH = 100;
	private static int MAX_ARGUMENTS = 20;

	public OSCMessage generate() throws OSCDataObjectFormatException {

		OSCMessage message = new OSCMessage();

		message.setAddress(generateRandomAddress());
		message.addArguments(generateRandomArguments());
		
		return message;
	}

	private OSCDataType[] generateRandomArguments() {

		int number = (int) (Math.random() * MAX_ARGUMENTS);

		OSCDataType[] argArray = new OSCDataType[number];

		for (int index = 0; index < number; index++) {
			argArray[index] = generateRandomArgument();
		}

		return argArray;
	}

	private OSCDataType generateRandomArgument() {
		int type = (int) (Math.random() * 10);

		switch (type) {
		case 0:
			return new OSCInt32((int) (Math.random() * Integer.MAX_VALUE));
		case 1:
			return new OSCInt64((long) (Math.random() * Long.MAX_VALUE));
		case 2:
			return new OSCFloat32((float) (Math.random() * Float.MAX_VALUE));
		case 3:
			return new OSCDouble((double) (Math.random() * Double.MAX_VALUE));
		case 4:
			return generateRandomAddress();
		case 5:
			return new OSCChar((char) ((char) (Math.random() * 93) + 33));
		case 6:
			return new OSCTrue();
		case 7:
			return new OSCFalse();
		case 8:
			return new OSCFalse();
		case 9:
			return new OSCMIDIMessage(new ShortMessage());

		default:
			break;
		}
		return null;
	}

	private OSCString generateRandomAddress() {

		int length = (int) (Math.random() * MAX_STRING_LENGTH );
		String address = "/";

		for (int index = 0; index < length; index++) {
			address += generateRandomChar();
		}
		return new OSCString(address);
	}

	private char generateRandomChar() {

		return (char) ((char) (Math.random() * 93) + 33);
	}

}
