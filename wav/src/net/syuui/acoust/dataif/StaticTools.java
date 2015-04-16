package net.syuui.acoust.dataif;

/**
 * StaticTools.class: Static tool functions for RIFF library
 * 
 * @author zhouw
 * @version 1.0
 */

public class StaticTools {

	/**
	 * Spell a short variable by 2 byte variables.
	 * 
	 * Input parameter b should be a 2-element array of byte. b[0] will be the
	 * fist byte of spelled short variable, and b[1] will be the last byte.<br>
	 * Example: for input parameter b[2] = {0xAB, 0xCD}, value of spelled short
	 * variable will be 0xABCD.
	 * 
	 * @param b
	 *            Input parameter, should be byte[2].
	 * @return Spelled short variable.
	 */
	public static short spellShort(byte[] b) {
		if (b.length == 0)
			return 0;
		else if (b.length == 1)
			return (short) ((b[0] & 0xFF) << 8);
		else
			return (short) (((b[0] & 0xFF) << 8) + (b[1] & 0xFF));
	}

	public static short spellShort(byte b0, byte b1) {
		byte tmp[] = new byte[2];
		tmp[0] = b0;
		tmp[1] = b1;
		return spellShort(tmp);
	}

	/**
	 * Spell an int variable by 4 byte variables.
	 * 
	 * Input parameter <CODE>b</CODE> should be a 4-element array of byte. b[0]
	 * will be the first byte of spelled int variable, b[1] will be the second
	 * byte, b[2] will be the third byte and b[3] will be the last byte.<br>
	 * Example: for input parameter b[4] = {0x01, 0x02, 0x03, 0x04}, value of
	 * spelled int variable will be 0x01020304;
	 * 
	 * @param b
	 *            Input parameter, should be byte[4].
	 * @return Spelled int variable.
	 */
	public static int spellInt(byte[] b) {

		int i[] = new int[4];

		i[0] = (b[0] & 0xFF) << 24;
		i[1] = (b[1] & 0xFF) << 16;
		i[2] = (b[2] & 0xFF) << 8;
		i[3] = (b[3]) & 0xFF;

		if (b.length == 0)
			return 0;
		else if (b.length == 1)
			return ((b[0] & 0xFF) << 24);
		else if (b.length == 2)
			return ((b[0] & 0xFF) << 24) + ((b[1] & 0xFF) << 16);
		else if (b.length == 3)
			return ((b[0] & 0xFF) << 24) + ((b[1] & 0xFF) << 16)
					+ ((b[2] & 0xFF) << 8);
		else
			return ((b[0] & 0xFF) << 24) + ((b[1] & 0xFF) << 16)
					+ ((b[2] & 0xFF) << 8) + ((b[3]) & 0xFF);
	}

	public static int spellInt(byte b0, byte b1, byte b2, byte b3) {
		byte[] tmp = new byte[4];
		tmp[0] = b0;
		tmp[1] = b1;
		tmp[2] = b2;
		tmp[3] = b3;
		return spellInt(tmp);
	}

	/**
	 * Split a short variable into a byte array.
	 * 
	 * This method will split a short variable <CODE>s</CODE> into a 2-element
	 * byte array. First byte of the short variable will be set into the first
	 * element of array, and second byte will be set into second element.<br>
	 * Example: For input parameter 0xABCD, the returned array will be {0xAB,
	 * 0xCD}.
	 * 
	 * @param s
	 *            Input short variable
	 * @return Splitted array
	 */
	public static byte[] splitShort(short s) {
		byte[] b = new byte[2];
		b[0] = (byte) ((s & 0xFF00) >> 8);
		b[1] = (byte) (s & 0x00FF);
		return b;
	}

	/**
	 * Split an int variable into a byte array.
	 * 
	 * This method will split an int variable <CODE>s</CODE> into a 4-element
	 * byte array. First byte of the int variable will be set into the first
	 * element of array, and second byte into second element, third byte into
	 * third element, last byte into last element.<br>
	 * Example: For input parameter 0x01020304, the returned array will be
	 * {0x01, 0x02, 0x03, 0x04}.
	 * 
	 * @param s
	 *            Input int variable
	 * @return Splitted array
	 */
	public static byte[] splitInt(int s) {
		byte[] b = new byte[4];
		b[0] = (byte) (s >> 24);
		b[1] = (byte) ((s >> 16) & 0x00FF);
		b[2] = (byte) ((s >> 8) & 0x00FF);
		b[3] = (byte) (s & 0xFF);
		return b;
	}

	/**
	 * Switch short variable between big-endian and small-endian.
	 * 
	 * This method switch sequence of bytes of the input parameter
	 * <CODE>s</CODE>, in order to convert a big-endian short variable to a
	 * small-endian short variable, or opposite.
	 * 
	 * @param s
	 *            Input parameter
	 * @return switched short variable
	 */
	public static short switchShort(short s) {
		byte b[] = splitShort(s);
		byte r[] = new byte[2];
		r[0] = b[1];
		r[1] = b[0];
		return spellShort(r);
	}

	/**
	 * Switch int variable between big-endian and small-endian.
	 * 
	 * This method switch sequence of bytes of the input parameter
	 * <CODE>s</CODE>, in order to conver a big-endian int variable to a
	 * small-endian int variable, or opposite.
	 * 
	 * @param s
	 *            Input parameter
	 * @return switched int variable
	 */
	public static int switchInt(int s) {
		byte[] b = splitInt(s);
		byte[] r = new byte[4];
		r[0] = b[3];
		r[1] = b[2];
		r[2] = b[1];
		r[3] = b[0];
		return spellInt(r);
	}

}
