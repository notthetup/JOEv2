package utils;

import osc.OSCBundle;
import osc.OSCMessage;
import osc.OSCPacket;
import datatypes.*;

public class OSCByteArrayToPacketConverter {

	private OSCTimeTag enclosingTimeTag;

	private boolean enforceTypeTags = true;

	private static byte startChar = 0x00;;

	public OSCPacket convert(OSCByteStream byteStream)
			throws OSCDataObjectFormatException {

		try {
			if (isBundle(byteStream))
				return convertBundle(byteStream);
			else
				return convertMessage(byteStream);
		} catch (OSCByteStreamException e) {
			e.printStackTrace();
			return null;
		}

	}

	private OSCBundle convertBundle(OSCByteStream byteStream)
			throws OSCDataObjectFormatException, OSCByteStreamException {

		byteStream.skip(8);

		byte[] timeTagBytes = byteStream.read(8);

		OSCTimeTag timeTag = new OSCTimeTag(timeTagBytes);
		OSCBundle oscBundle = new OSCBundle(timeTag);

		if (enclosingTimeTag != null && timeTag.before(enclosingTimeTag))
			throw new OSCDataObjectFormatException(
					OSCDataObjectFormatException.TIME_TAG_ENCLOSING_ERROR,
					enclosingTimeTag.getTimetag().getTime(), timeTag
							.getTimetag().getTime(), timeTag);

		while (byteStream.available() != 0) {

			OSCInt32 packetLength = new OSCInt32(byteStream.read(4));

			if (!isMultipleofFour(packetLength.getInteger()))
				throw new OSCDataObjectFormatException(
						OSCDataObjectFormatException.ILEGAL_INPUT_BYTE_ARRAY_LENGTH,
						4, packetLength.getInteger(), this);

			if (isBundle(byteStream))
				oscBundle.addPacket(this.convertBundle(byteStream));
			else
				oscBundle.addPacket(this.convertMessage(byteStream));
		}

		enclosingTimeTag = timeTag;
		return oscBundle;
	}

	private OSCMessage convertMessage(OSCByteStream byteStream)
			throws OSCDataObjectFormatException, OSCByteStreamException {

		OSCMessage oscMessage = new OSCMessage();

		int addressPatternSize = byteStream.getImmediateStringLength();

		if (addressPatternSize == -1)
			throw new OSCDataObjectFormatException(
					OSCDataObjectFormatException.MISSING_ADDRESS_PATTERN_END,
					0, 0, this);

		OSCString addressPattern = new OSCString(byteStream
				.read(calculatePaddedLength(addressPatternSize)));

		oscMessage.setAddress(addressPattern);

		if (byteStream.available() == 0)
			return oscMessage;

		if (byteStream.readSilent(1)[0] != ',' && enforceTypeTags) {
			throw new OSCDataObjectFormatException(
					OSCDataObjectFormatException.ILLEGAL_ADDRESS_PATTERN_START,
					',', startChar, this);
		}

		int typesLength = byteStream.getImmediateStringLength();

		if (typesLength == 0) {
			return oscMessage;
		}

		OSCString typeTagString = new OSCString(byteStream
				.read(calculatePaddedLength(typesLength)));
		String typeString = typeTagString.getString();

		for (int index = 1; index < typeString.length(); index++) {

			if (typeString.charAt(index) == '[') {

				int arrayEndIndex = typeString.indexOf(']');
				OSCDataTypeArray argArray = new OSCDataTypeArray();

				for (int arrayIndex = index; arrayIndex < arrayEndIndex; arrayIndex++) {
					try {
						argArray.add(readArgument(
								typeString.charAt(arrayIndex), byteStream));
					} catch (OSCDataObjectFormatException e) {
						e.printStackTrace();
					}
				}
				index = arrayEndIndex;
				oscMessage.addArgument(argArray);

			} else {
				try {
					oscMessage.addArgument(readArgument(typeString
							.charAt(index), byteStream));
				} catch (OSCDataObjectFormatException e) {
					e.printStackTrace();
				}
			}
		}
		return oscMessage;
	}

	private OSCDataType readArgument(char c, OSCByteStream byteStream)
			throws OSCDataObjectFormatException, OSCByteStreamException {
		switch (c) {
		case 'i':
			return new OSCInt32(byteStream.read(4));
		case 'h':
			return new OSCInt64(byteStream.read(8));
		case 'f':
			return new OSCFloat32(byteStream.read(4));
		case 'd':
			return new OSCDouble(byteStream.read(8));
		case 's':
			return new OSCString(byteStream
					.read(calculatePaddedLength(byteStream
							.getImmediateStringLength())));
		case 'c':
			return new OSCChar(byteStream.read(4));
		case 'T':
			return new OSCTrue();
		case 'F':
			return new OSCFalse();
		case 'b':
			return OSCBlob.decodeBlob(byteStream.read(OSCBlob
					.getSize(byteStream.readSilent(4))));
		case 'm':
			return new OSCMIDIMessage(byteStream.read(4));

		default:
			return null;
		}
	}

	private boolean isBundle(OSCByteStream byteStream)
			throws OSCByteStreamException {

		String bytesAsString = new String(byteStream.readSilent(7));

		return bytesAsString.startsWith("#bundle");

	}

	private static boolean isMultipleofFour(int size) {

		if (size % 4 == 0)
			return true;
		else
			return false;
	}

	private static int calculatePaddedLength(int size) {

		if (isMultipleofFour(size))
			return size;
		else
			return size + (4 - size % 4);
	}
}
