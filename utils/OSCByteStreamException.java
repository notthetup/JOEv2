package utils;

public class OSCByteStreamException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int INPUT = 1;

	public static final int OUTPUT = 2;

	private int reason = 0;

	public OSCByteStreamException(int source) {
		this.reason = source;
	}

	@Override
	public void printStackTrace() {
		switch (reason) {

		case INPUT:
			System.err.println("Invalid read attempt from the input stream. Every read attempt should be a multiple of 4 bytes.");
			break;
			
		case OUTPUT:
			System.err.println("Invalid write attempt to the output stream. Every write attempt should be a multiple of 4 bytes.");
			break;
		}
		super.printStackTrace();
	}

}
