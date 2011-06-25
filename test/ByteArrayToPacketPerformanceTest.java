package test;

import junit.framework.TestCase;
import osc.OSCBundle;
import osc.OSCMessage;
import osc.OSCPacket;
import utils.OSCByteArrayToPacketConverter;
import datatypes.OSCByteStream;
import datatypes.OSCDataType;
import datatypes.OSCString;

public class ByteArrayToPacketPerformanceTest extends TestCase {

	OSCByteArrayToPacketConverter converter;

	/**
	 * Run the OSCByteArrayToJavaConverter through its paces
	 */
	public ByteArrayToPacketPerformanceTest() {
		super();
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		converter = new OSCByteArrayToPacketConverter();
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {

	}

	public void testConverionSpeedPacketWith10000RandomPackets() throws Exception {

		RandomBundleGenerator rp = new RandomBundleGenerator();
			
		for(int index = 0; index < 10000; index ++){
			rp.generate().getByteStream();
		}
	}
	
	public void testConversionAcuracy1PacketWith10000RandomPackets() throws Exception {

		OSCByteArrayToPacketConverter b2Pconverter = new OSCByteArrayToPacketConverter();
		
		RandomBundleGenerator rp = new RandomBundleGenerator();
			
		for(int index = 0; index < 10; index ++){
			OSCPacket inpac = rp.generate();
			OSCByteStream inBStream = inpac.getByteStream();
			OSCPacket outpac = b2Pconverter.convert(inBStream);
			if(!inpac.equals(outpac))
				fail();
		}
	}
	
	public void testreConversionAcuracy1PacketWith10000RandomPackets() throws Exception {

		OSCByteArrayToPacketConverter b2Pconverter = new OSCByteArrayToPacketConverter();
		
		RandomBundleGenerator rp = new RandomBundleGenerator();
			
		for(int index = 0; index < 10; index ++){
			OSCPacket inpac = rp.generate();
			OSCByteStream inBStream = inpac.getByteStream();
			OSCPacket outpac = b2Pconverter.convert(inBStream);
			OSCByteStream outBStream = outpac.getByteStream();
			if(!inBStream.equals(outBStream))
				fail();
		}
	}
	
	
private boolean compare(OSCPacket inPac, OSCPacket outPac) throws Exception {
		
		if((inPac instanceof OSCBundle) && (outPac instanceof OSCPacket)){
			
			OSCPacket inPacketArray []= ((OSCBundle)inPac).getPackets();
			OSCPacket outPacketArray []= ((OSCBundle)outPac).getPackets();
			
			if(inPacketArray.length != outPacketArray.length)
				return false;
			
			for(int arrayIndex = 0; arrayIndex < inPacketArray.length; arrayIndex++)
				if(!compare(inPacketArray[arrayIndex],outPacketArray[arrayIndex]))
					return false;
			
			return true;
		}
		else if((inPac instanceof OSCMessage) && (outPac instanceof OSCMessage)){
		
			OSCString inAdd = ((OSCMessage) inPac).getAddress();
			OSCString outAdd = ((OSCMessage)outPac).getAddress();
			
			if(!inAdd.getString().equals(outAdd.getString()))
					return false;
			
			OSCDataType inArgs [] = ((OSCMessage) inPac).getArguments();
			OSCDataType outArgs [] = ((OSCMessage) outPac).getArguments();
			
			if(inArgs.length != outArgs.length)
				return false;
			
			for(int arrayIndex = 0; arrayIndex < inArgs.length; arrayIndex++)
				if(!inArgs[arrayIndex].equals(outArgs[arrayIndex]))
					return false;
					
			return true;
		}
		else
			return false;
	}

}
