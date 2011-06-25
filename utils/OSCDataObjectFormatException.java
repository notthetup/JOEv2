package utils;

import javax.sound.midi.InvalidMidiDataException;

public class OSCDataObjectFormatException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int ILEGAL_DATA_BYTE_ARRAY_LENGTH = 1;
	public static final int ILEGAL_DATE_FORMAT = 2;
	public static final int BLOB_SIZE_MISMATCH = 3;
	public static final int ILEGAL_INPUT_BYTE_ARRAY_LENGTH = 4;
	public static final int TIME_TAG_ENCLOSING_ERROR = 5;
	public static final int INVALID_MIDI_DATA = 6;
	public static final int MISSING_ADDRESS_PATTERN_END = 7;
	public static final int ILLEGAL_ADDRESS_PATTERN_LEGTH = 8;
	public static final int ILLEGAL_ADDRESS_PATTERN_START = 9;
	
	private long expectedLength = 0; 
	private long observedLength = 0;
	private int reason = 0;
	private Object source = null;

	public OSCDataObjectFormatException(int reason, long expected, long observed, Object source) {
		this.reason = reason;
		this.source = source;
		this.expectedLength = expected;
		this.observedLength = observed;
	}
	
	@Override
	public void printStackTrace() {
	    switch (reason) {
	    
	    case ILLEGAL_ADDRESS_PATTERN_START:
	    	System.err.println("Invalid OSC Address String start character. Expected : 0x" + (byte)expectedLength + " Observed : 0x" + (byte)observedLength);
			break;
	    case ILLEGAL_ADDRESS_PATTERN_LEGTH:
			System.err.println("OSC Address String not multiple of 4. Expected : " + expectedLength + " Observed : " + observedLength);	
			break;
	    case MISSING_ADDRESS_PATTERN_END:
			System.err.println("End of OSC Address Sring not found.");	
			break;
	    case INVALID_MIDI_DATA:
			System.err.println("MIDI Data Encapsulation Error : " + ((InvalidMidiDataException)source).getMessage());	
			break;
	    case TIME_TAG_ENCLOSING_ERROR:
			System.err.println("Outside Timetag is later than inside Timetag. Outside : " + this.expectedLength + " Inside : " + this.observedLength );	
			break;
		case ILEGAL_INPUT_BYTE_ARRAY_LENGTH:
			System.err.println("Ilegal Input Byte Array Length. Expected : " + expectedLength + " Observed : " + observedLength );
			break;
		case BLOB_SIZE_MISMATCH:
			System.err.println("Blob Size mismatch with byte array length. Expected : " + expectedLength + " Observed : " + observedLength );	
			break;
		case ILEGAL_DATE_FORMAT:
			System.err.println("Illegal OSCTimeTag format. Date out of range of the OSCTimeTag.  Expected : " + expectedLength + " Observed : " + observedLength );
			break;
		case ILEGAL_DATA_BYTE_ARRAY_LENGTH:
			System.err.println("Byte Array Length mismatch for OSCDataType " + source.getClass().getName() +" Expected : " + expectedLength + " Observed : " + observedLength );
			break;
		default:
			System.err.println("Unknown Exception!");
			break;
		}    
		super.printStackTrace();
	}
	
	

}
