package test;

import osc.OSCBundle;
import utils.OSCDataObjectFormatException;

public class RandomBundleGenerator {
	
	private static int MAX_PACKETS = 10;
	
	private static int BUNDLE_MSG_RATIO = 100;
	
	
	public OSCBundle generate() throws OSCDataObjectFormatException {

		OSCBundle bundle = new OSCBundle();
		
		RamdomMessageGenerator rm = new RamdomMessageGenerator();
		
		int number = (int) (Math.random() * MAX_PACKETS);

		for (int arrayIndex = 0; arrayIndex < number; arrayIndex++) {
		
			int decideBundMsg = (int) (Math.random() * BUNDLE_MSG_RATIO);
			
			if ((decideBundMsg%BUNDLE_MSG_RATIO) == 0) {
				bundle.addPacket(this.generate());
			} 
			else {
				bundle.addPacket(rm.generate());
			}
		}
		
		return bundle;
	}

}
