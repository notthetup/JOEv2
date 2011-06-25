package datatypes;

import java.math.BigInteger;
import java.util.Date;

import utils.OSCDataObjectFormatException;

public class OSCTimeTag extends OSCDataType {
	
	public static final BigInteger SECONDS_FROM_1900_to_1970 = new BigInteger("2208988800");
	private Date timeTag;
	
	public OSCTimeTag(Date timetag) {
		this.timeTag = timetag;
		super.typetag = 't';
	}
	
	
	public OSCTimeTag(byte [] timeTagBytes) throws OSCDataObjectFormatException {
		
		if(timeTagBytes.length != 8)
			throw new OSCDataObjectFormatException(OSCDataObjectFormatException.ILEGAL_DATA_BYTE_ARRAY_LENGTH , 8 , timeTagBytes.length, this);
		
		byte[] secondBytes = new byte[4];
		byte[] picosecBytes = new byte[4];
		
		System.arraycopy(timeTagBytes, 0, secondBytes, 0, 4);
		System.arraycopy(timeTagBytes, 4, picosecBytes, 0, 4);
				
		BigInteger secsSince1900 = new BigInteger(secondBytes);
		long secsSince1970 =  secsSince1900.longValue() - SECONDS_FROM_1900_to_1970.longValue();
		
		if (secsSince1970 < 0) 
			secsSince1970 = 0; 
			
		BigInteger picosecs = new BigInteger(picosecBytes);
		
		long millisecs = (secsSince1970 * 1000) + (picosecs.longValue() / 1000 *232);
			
		this.timeTag = new Date(millisecs);
		
	}
	
	
	public Date getTimetag() {
		return timeTag;
	}

	@Override
	public byte[] getByteArray() throws OSCDataObjectFormatException {
		
		byte [] timeTagBytes = new byte [8];
		
		long millisecondsSince1970 =  timeTag.getTime();
		long picoseconds = (millisecondsSince1970%1000) * 1000 /232 ;
		long secondsSince1970 = (millisecondsSince1970/1000);
		
		
		
		BigInteger secondsSince1900 = BigInteger.valueOf(secondsSince1970+ SECONDS_FROM_1900_to_1970.longValue());
		BigInteger picosecondsSince1900 = BigInteger.valueOf(picoseconds);
		
		byte[] secByteArray = secondsSince1900.toByteArray();
		byte[] picoByteArray = picosecondsSince1900.toByteArray();
		
		if(picoByteArray.length > 4 )
			throw new OSCDataObjectFormatException(OSCDataObjectFormatException.ILEGAL_DATE_FORMAT, 4, picoByteArray.length, this);
		
		int signBit;
		
		if(secByteArray.length <= 4)
			signBit = 0;
		else if(secByteArray.length == 5)
			signBit = 1;
		else
			throw new OSCDataObjectFormatException(OSCDataObjectFormatException.ILEGAL_DATE_FORMAT, 4, secByteArray.length, this);
		
		
		System.arraycopy(secByteArray, signBit, timeTagBytes,4-secByteArray.length+signBit , secByteArray.length-signBit);
		System.arraycopy(picoByteArray, 0, timeTagBytes, 8-picoByteArray.length , picoByteArray.length);

		return timeTagBytes;
	}


	public boolean before(OSCTimeTag inTimeTag) {
		
		return timeTag.before(inTimeTag.timeTag);
		
	}

	public int compareTo(OSCTimeTag inTimeTag){
		return timeTag.compareTo(inTimeTag.timeTag);
	}
	
	public int compareTo(Date inTimeTag){
		return timeTag.compareTo(inTimeTag);
	}


	@Override
	public boolean equals(OSCDataType dataObj) {
	
		if(!(dataObj instanceof OSCTimeTag))
			return false;
		
		if(this.compareTo((OSCTimeTag)dataObj) != 0)
			return false;
		
		return true;
		
		
	}
	


}
