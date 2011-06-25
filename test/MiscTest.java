package test;

import java.io.UnsupportedEncodingException;

import datatypes.OSCByteStream;

import utils.OSCDataObjectFormatException;
import utils.OSCPacketToByteArrayConverter;

public class MiscTest {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 * @throws OSCDataObjectFormatException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException, OSCDataObjectFormatException, InterruptedException {
		
		OSCPacketToByteArrayConverter converter = new OSCPacketToByteArrayConverter();
		
		RamdomMessageGenerator rp = new RamdomMessageGenerator();
	
		System.out.println(System.currentTimeMillis()); 
		
		for(int index = 0; index < 100; index ++){
			print(converter.convert(rp.generate()));
			//System.out.println();
		}
		
		System.out.println(System.currentTimeMillis()); 
		System.out.println("Done");
	
	}

	private static void print(OSCByteStream stream) {
		
		for(int index = 0 ; index < stream.available(); index++){
			//System.out.print((int)bs[index] + " ");
		}		
	}

}
