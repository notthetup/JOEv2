//  Created by cramakri on Thu Dec 13 2001.
//  Copyright (c) 2001 Illposed Software. All rights reserved.
//

/*
 * This implementation is based on Markus Gaelli and
 * Iannis Zannos' OSC implementation in Squeak:
 * http://www.emergent.de/Goodies/
 */

package test;

import java.util.Date;

import javax.sound.midi.ShortMessage;

import osc.OSCMessage;
import utils.OSCDataObjectFormatException;
import utils.OSCPacketToByteArrayConverter;
import datatypes.OSCByteStream;
import datatypes.OSCDataType;
import datatypes.OSCDouble;
import datatypes.OSCFloat32;
import datatypes.OSCInt32;
import datatypes.OSCInt64;
import datatypes.OSCMIDIMessage;
import datatypes.OSCString;
import datatypes.OSCTimeTag;

public class OSCPacketToByteArrayConverterTest extends junit.framework.TestCase {

	/**
	 * @param name
	 *            java.lang.String
	 */
	public OSCPacketToByteArrayConverterTest() {
		super();
	}

	
	private OSCPacketToByteArrayConverter converter;

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		converter = new OSCPacketToByteArrayConverter();
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {

	}

	
	
	private void checkResultEqualsAnswer(byte[] result, byte[] answer) {
		if (result.length != answer.length)
			fail("Didn't convert correctly. Length Mismatch");
		for (int i = 0; i < result.length; i++) {
			if (result[i] != answer[i]){
				System.out.println(i);
				System.out.println(result[i]);
				System.out.println(answer[i]);
				fail("Didn't convert correctly. Byte Mismatch");
			}
		}
	}

	public void testFloatInt() throws OSCDataObjectFormatException {
		OSCDataType[] args = { new OSCInt32(1), new OSCFloat32((float) 0.2)};
		OSCMessage message = new OSCMessage("/sc/mixer/volume", args);
		byte[] answer = { 
			47, 115, 99, 47, 109, 105, 120, 101, 114, 47, 118, 111,
			108, 117, 109, 101, 0, 0, 0, 0, 44, 105, 102, 0, 0, 0, 0,
			1, 62, 76, -52, -51 };
		OSCByteStream result =converter.convert(message);
		checkResultEqualsAnswer(result.toByteArray(), answer);
	}

	public void testFloat() throws OSCDataObjectFormatException {
		OSCDataType[] args = { new OSCFloat32((float) 10.7567)};
		OSCMessage message = new OSCMessage("/sc/mixer/volume", args);
		byte[] answer = { 47, 115, 99, 47, 109, 105, 120, 101, 114, 47, 118, 111,
				108, 117, 109, 101, 0, 0, 0, 0, 44, 102, 0, 0, 65, 44, 27, 113 };
		OSCByteStream result = converter.convert(message);
		checkResultEqualsAnswer(result.toByteArray(), answer);
	}
	
	

	
	public void testString2() throws OSCDataObjectFormatException {
		OSCDataType[] args = { new OSCString("abcd")};
		OSCMessage message = new OSCMessage("/sc/mixer/volume", args);
		byte[] answer = { 47, 115, 99, 47, 109, 105, 120, 101, 114, 47, 118, 111,
				108, 117, 109, 101, 0, 0, 0, 0, 44, 's', 0, 0 , 97, 98, 99, 100, 0, 0, 0, 0 };
		OSCByteStream result = converter.convert(message);
		checkResultEqualsAnswer(result.toByteArray(), answer);
	}

	public void testString() throws OSCDataObjectFormatException {
		OSCDataType[] args = { new OSCString("abc")};
		OSCMessage message = new OSCMessage("/sc/mixer/volume", args);
		byte[] answer = { 47, 115, 99, 47, 109, 105, 120, 101, 114, 47, 118, 111,
				108, 117, 109, 101, 0, 0, 0, 0, 44, 's', 0, 0 , 97, 98, 99, 0};
		OSCByteStream result = converter.convert(message);
		checkResultEqualsAnswer(result.toByteArray(), answer);
	}

	public void testBigIntegerOnStream() throws OSCDataObjectFormatException {
		OSCDataType[] args = { new OSCInt64(1124)};
		OSCMessage message = new OSCMessage("/sc/mixer/volume", args);
		byte[] answer = { 47, 115, 99, 47, 109, 105, 120, 101, 114, 47, 118, 111,
				108, 117, 109, 101, 0, 0, 0, 0, 44, 'h', 0, 0 , 0, 0, 0, 0, 0, 0, 4, 100};
		OSCByteStream result = converter.convert(message);
		checkResultEqualsAnswer(result.toByteArray(), answer);
	}
	
	public void testMIDIMessage() throws OSCDataObjectFormatException {
		OSCDataType[] args = { new OSCMIDIMessage(new ShortMessage())};
		OSCMessage message = new OSCMessage("/sc/mixer/volume", args);
		byte[] answer = { 47, 115, 99, 47, 109, 105, 120, 101, 114, 47, 118, 111,
				108, 117, 109, 101, 0, 0, 0, 0, 44, 'm', 0, 0 , 0 , (byte) 144 , 64, 127};
		OSCByteStream result = converter.convert(message);
		checkResultEqualsAnswer(result.toByteArray(), answer);
	}
	
	public void testTimeTag() throws OSCDataObjectFormatException {
		OSCDataType[] args = { new OSCTimeTag(new Date(-(long)(OSCTimeTag.SECONDS_FROM_1900_to_1970.longValue()*1000)))};
		OSCMessage message = new OSCMessage("/sc/mixer/volume", args);
		byte[] answer = { 47, 115, 99, 47, 109, 105, 120, 101, 114, 47, 118, 111,
				108, 117, 109, 101, 0, 0, 0, 0, 44, 't', 0, 0, 0,0,0,0,0,0,0,0 };
		OSCByteStream result = converter.convert(message);
		checkResultEqualsAnswer(result.toByteArray(), answer);
	}
	
	public void testDouble() throws OSCDataObjectFormatException {
		OSCDataType[] args = { new OSCDouble(15.586)};
		OSCMessage message = new OSCMessage("/sc/mixer/volume", args);
		byte[] answer = { 47, 115, 99, 47, 109, 105, 120, 101, 114, 47, 118, 111,
				108, 117, 109, 101, 0, 0, 0, 0, 44, 'd', 0, 0 , 0x40, 0x2F, 0x2C, 0x08, 0x31, 0x26, (byte) 0xE9, 0x79};
		OSCByteStream result = converter.convert(message);
		checkResultEqualsAnswer(result.toByteArray(), answer);
	}
	
	
}