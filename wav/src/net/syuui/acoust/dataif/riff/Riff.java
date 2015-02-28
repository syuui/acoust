package net.syuui.acoust.dataif.riff;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.syuui.acoust.dataif.DataFormatException;

/**
 * Class Riff
 * 
 * RIFF Data format class
 * 
 * @author zhouw
 * @category DataIF
 * @version 0.9
 */
public abstract class Riff {

	public static int FOUR_CHARACTOR_CODE_LENGTH = 4;

	private static String RIFF_ID = "RIFF";
	private static String MSG01 = "Bad Riff ID specified. Should be \"RIFF\", current is ";
	private static String MSG02 = "Reached end of file while reading RIFF ID";
	private static String MSG03 = "Reached end of file wille reading RIFF Size";
	private static String MSG04 = "Reached end of file wille reading RIFF Type";
	private static String MSG05 = "Invalid RIFF Size";
	private static String MSG06 = "Out of data size";

	@SuppressWarnings("unused")
	private static String MSG07 = "";
	@SuppressWarnings("unused")
	private static String MSG08 = "";
	@SuppressWarnings("unused")
	private static String MSG09 = "";
	@SuppressWarnings("unused")
	private static String MSG10 = "";

	private byte[] riffId = new byte[FOUR_CHARACTOR_CODE_LENGTH];
	private int riffSize = 0;
	private byte[] riffType = new byte[FOUR_CHARACTOR_CODE_LENGTH];
	private byte[] riffDataBuf = new byte[0];

	private int ptr = 0;

	/**
	 * Construct Function
	 * 
	 * 
	 */
	public Riff() {

	}

	/**
	 * Get method of field RIFF ID.
	 * 
	 * @return Fixed "RIFF", or {' ',' ',' ',' '}
	 */
	public byte[] getRiffId() {
		return riffId;
	}

	/**
	 * Set method of field RIFF ID.
	 * 
	 * @param riffId
	 *            RIFF ID
	 * @throws DataFormatException
	 * 
	 *             According to RIFF Data Format, RIFf ID should always be
	 *             "RIFF". Otherwise, a DataFormatException will be thrown.
	 */
	public void setRiffId(byte[] riffId) throws DataFormatException {
		if (! RIFF_ID.equals(new String(riffId))) {
			throw new DataFormatException(MSG01 + new String(riffId));
		}
		this.riffId = riffId;
	}

	/**
	 * Get method for field RIFF Size.
	 * 
	 * @return RIFF Size
	 */
	public int getRiffSize() {
		return riffSize;
	}

	/**
	 * Set method for field RIFF Size.
	 * 
	 * @param riffSize
	 *            RIFF Size
	 */
	public void setRiffSize(int riffSize) {
		this.riffSize = riffSize;
	}

	/**
	 * Get method for field RIFF Type.
	 * 
	 * @return RIFF Data type as byte[4].
	 */
	public byte[] getRiffType() {
		return riffType;
	}

	/**
	 * Set method for field RIFF Type.
	 * 
	 * @param riffType
	 *            RIFF Data type, byte[4].
	 */
	public void setRiffType(byte[] riffType) {
		this.riffType = riffType;
	}

	/**
	 * Get method for field RIFF Data.
	 * 
	 * First this method will call abstract method {@link #spellRiffData()} to
	 * fill the data buf, and then return the data buf.
	 * 
	 * @return RIFF Data buf
	 * @throws DataFormatException
	 *             throw DataFormatException when data format error occurs.
	 */
	public byte[] getRiffData() throws DataFormatException {
		spellRiffData();
		return riffDataBuf;
	}

	/**
	 * Set method for field RIFF Data.
	 * 
	 * After set the data buf, this method will call abstract method
	 * {@link #phraseRiffData(byte[])} to phrase data in the buf and fill each
	 * field of each Type.
	 * 
	 * @param riffData
	 *            RIFF Data buf
	 * @throws DataFormatException
	 *             throw DataFormatException when data format error occurs.
	 */
	public void setRiffData(byte[] riffData) throws DataFormatException {
		this.riffDataBuf = riffData;
		this.riffSize = riffData.length;
		phraseRiffData();
	}

	// -------------------------| Read method |-------------------------

	/**
	 * Read field RIFF ID from DataInputStream.
	 * 
	 * @param dis
	 *            DataInputStream
	 * @return Read bytes, 4 for success.
	 * @throws IOException
	 *             If error occurs during reading, and IOException will be
	 *             thrown.
	 * @throws DataFormatException
	 *             According to RIFF Data Format, RIFf ID should always be
	 *             "RIFF". Otherwise, a DataFormatException will be thrown.
	 */
	public int readRiffId(DataInputStream dis) throws IOException,
			DataFormatException {
		int read = dis.read(riffId, 0, riffId.length);
		if (read == -1)
			throw new IOException(MSG02);

		if (!RIFF_ID.equals(new String(riffId)))
			throw new DataFormatException(MSG01 + new String(riffId));

		return read;
	}

	/**
	 * Read field RIFF Size from DataInputStream.
	 * 
	 * @param dis
	 *            DataInputStream
	 * @return Size of data buf on success, or -1 on failure.
	 * @throws IOException
	 *             If error occurs during reading, and IOException will be
	 *             thrown.
	 */
	public int readRiffSize(DataInputStream dis) throws IOException {
		int read = switchInt(dis.readInt());
		if (read == -1)
			throw new IOException(MSG03);
		setRiffSize(read);
		return read;
	}

	/**
	 * Read field RIFF Type from DataInputStream.
	 * 
	 * @param dis
	 *            DataInputStream
	 * @return Read bytes, 4 for success.
	 * @throws IOException
	 *             If error occurs during reading, and IOException will be
	 *             thrown.
	 */
	public int readRiffType(DataInputStream dis) throws IOException {
		int read = 0;
		read = dis.read(riffType, 0, riffType.length);
		if (read == -1)
			throw new IOException(MSG04);
		return read;
	}

	/**
	 * Read RIFF Header from DataInputStream.
	 * 
	 * This method equals executing {@link #readRiffId(DataInputStream)},
	 * {@link #readRiffSize(DataInputStream)},
	 * {@link #readRiffType(DataInputStream)} in order.
	 * 
	 * @param dis
	 *            DataInputStream
	 * @return Read bytes, 12 for success.
	 * @throws IOException
	 *             If error occurs during reading, and IOException will be
	 *             thrown.
	 * @throws DataFormatException
	 *             According to RIFF Data Format, RIFf ID should always be
	 *             "RIFF". Otherwise, a DataFormatException will be thrown.
	 */
	public int readRiffHeader(DataInputStream dis) throws IOException,
			DataFormatException {
		int read = readRiffId(dis);
		if (readRiffSize(dis) > 0)
			read += 4;
		read += readRiffType(dis);
		return read;
	}

	/**
	 * Read field RIFF Data from DataInputStream.
	 * 
	 * @param dis
	 *            DataInputStream
	 * @return Read bytes, -1 for failure.
	 * @throws DataFormatException
	 *             According to RIFF Data Format, RIFf ID should always be
	 *             "RIFF". Otherwise, a DataFormatException will be thrown.
	 * @throws IOException
	 *             If error occurs during reading, and IOException will be
	 *             thrown.
	 */
	public int readRiffData(DataInputStream dis) throws DataFormatException,
			IOException {
		if (riffSize <= 0)
			throw new DataFormatException(MSG05);
		riffDataBuf = new byte[riffSize];
		int read = dis.read(riffDataBuf, 0, riffDataBuf.length);
		if (read > 0)
			phraseRiffData();
		else {
			System.out.println("================= Read Riff Data: Size is 0!!! Riff size is " + riffSize);
		}
		return read;
	}

	/**
	 * Get one byte from data buf.
	 *
	 * @return The byte that Data Buffer Pointer currently pointing.
	 * @throws DataFormatException
	 *             If an overflowed pointer specified, a DataFormatException
	 *             will be thrown.
	 */
	public byte readByte() throws DataFormatException {
		if (ptr >= riffDataBuf.length)
			throw new DataFormatException(MSG06);
		return riffDataBuf[ptr++];
	}

	/**
	 * Get a short value from data buf.
	 * 
	 * @return Short value that Data Buffer Pointer currently pointing.
	 * @throws DataFormatException
	 *             If an overflowed pointer specified, a DataFormatException
	 *             will be thrown.
	 */
	public short readShort() throws DataFormatException {
		if (ptr >= riffDataBuf.length + 1)
			throw new DataFormatException(MSG06);

		byte[] s = new byte[2];
		s[0] = riffDataBuf[ptr++];
		s[1] = riffDataBuf[ptr++];
		return spellShort(s);
	}

	/**
	 * Get an int value from data buf.
	 * 
	 * @return Int value that Data Buffer Pointer currently pointing.
	 * @throws DataFormatException
	 *             If an overflowed pointer specified, a DataFormatException
	 *             will be thrown.
	 */
	public int readInt() throws DataFormatException {
		if (ptr >= riffDataBuf.length + 3)
			throw new DataFormatException(MSG06);

		byte[] s = new byte[4];
		s[0] = riffDataBuf[ptr++];
		s[1] = riffDataBuf[ptr++];
		s[2] = riffDataBuf[ptr++];
		s[3] = riffDataBuf[ptr++];
		return spellInt(s);
	}

	/**
	 * Get consecutive bytes from data buf.
	 * 
	 * @param len
	 *            Bytes to read
	 * @return Byte values that Data Buffer Pointer currently pointing, with a
	 *         length <CODE>len</CODE>.
	 * @throws DataFormatException
	 *             If an overflowed pointer specified, a DataFormatException
	 *             will be thrown.
	 */
	public byte[] readBytes(int len) throws DataFormatException {
		if (ptr >= riffDataBuf.length + len - 1)
			throw new DataFormatException(MSG06);
		byte[] s = new byte[len];
		for (int i = 0; i < len; i++)
			s[i] = riffDataBuf[ptr++];
		return s;
	}

	// -------------------------| Write method |-------------------------

	/**
	 * Write RIFF ID to DataOutputStream
	 * 
	 * @param dos
	 *            DataOutputStream
	 * @throws IOException
	 *             If error occurs during reading, and IOException will be
	 *             thrown.
	 */
	public void writeRiffId(DataOutputStream dos) throws IOException {
		dos.write(riffId, 0, riffId.length);
		return;
	}

	/**
	 * Write RIFF Size to DataOutputStream
	 * 
	 * @param dos
	 *            DataOutputStream
	 * @throws IOException
	 *             If error occurs during Writing, and IOException will be
	 *             thrown.
	 */
	public void writeRiffSize(DataOutputStream dos) throws IOException {
		dos.writeInt(switchInt(riffSize));
		return;
	}

	/**
	 * Write RIFF Type to DataOutputStream
	 * 
	 * @param dos
	 *            DataOutputStream
	 * @throws IOException
	 *             If error occurs during Writing, and IOException will be
	 *             thrown.
	 */
	public void writeRiffType(DataOutputStream dos) throws IOException {
		dos.write(riffType, 0, riffType.length);
		return;
	}

	/**
	 * Write RIFF Header to DataOutputStream
	 * 
	 * This method equals executing {@link #writeRiffId(DataOutputStream)},
	 * {@link #writeRiffSize(DataOutputStream)}
	 * {@link #writeRiffType(DataOutputStream)} in order.
	 * 
	 * @param dos
	 *            DataOutputStream
	 * @throws IOException
	 *             If error occurs during Writing, and IOException will be
	 *             thrown.
	 */
	public void writeRiffHeader(DataOutputStream dos) throws IOException {
		dos.write(riffId, 0, riffId.length);
		dos.writeInt(riffSize);
		dos.write(riffType, 0, riffType.length);
		return;
	}

	/**
	 * Write RIFF Data to DataOutputStream
	 * 
	 * Before writting, this method will first call abstract method
	 * {@link #spellRiffData()} to fill data buf.
	 * 
	 * @param dos
	 *            DataOutputStream
	 * @throws IOException
	 *             If error occurs during Writing, and IOException will be
	 *             thrown.
	 * @throws DataFormatException
	 *             throw DataFormatException when data format error occurs.
	 */
	public void writeRiffData(DataOutputStream dos) throws IOException,
			DataFormatException {
		spellRiffData();
		dos.write(riffDataBuf, 0, riffDataBuf.length);
		return;
	}

	/**
	 * Put a byte into data buf.
	 * 
	 * This method puts a byte into data buf, where the Data Buffer Pointer
	 * currently pointing.
	 * 
	 * @param b
	 *            Source data
	 * @throws DataFormatException
	 *             If an overflowed pointer specified, a DataFormatException
	 *             will be thrown.
	 */
	public void writeByte(byte b) throws DataFormatException {
		if (ptr >= riffDataBuf.length)
			throw new DataFormatException(MSG06);
		riffDataBuf[ptr++] = b;
		return;
	}

	/**
	 * Put a short variable into data buf.
	 * 
	 * This method puts a short variable into data buf.<br>
	 * First byte of input parameter <CODE>s</CODE> will be put to the element
	 * that Data Buffer Pointer currently pointing, second byte to the next
	 * element.
	 * 
	 * @param s
	 *            Source data
	 * @throws DataFormatException
	 *             If an overflowed pointer specified, a DataFormatException
	 *             will be thrown.
	 */
	public void writeShort(short s) throws DataFormatException {
		if (ptr >= riffDataBuf.length + 1)
			throw new DataFormatException(MSG06);

		byte[] b = splitShort(s);
		riffDataBuf[ptr++] = b[0];
		riffDataBuf[ptr++] = b[1];
		return;
	}

	/**
	 * Put an int variable into data buf.
	 * 
	 * This method puts an int variable into data buf.<br>
	 * First byte of input parameter <CODE>s</CODE> will be put to the element
	 * that Data Buffer Pointer currently pointing, second byte to the second
	 * element, third byte to third element and last byte to last element.
	 * 
	 * @param s
	 *            Source data
	 * @throws DataFormatException
	 *             If an overflowed pointer specified, a DataFormatException
	 *             will be thrown.
	 */
	public void writeInt(int s) throws DataFormatException {
		if (ptr >= riffDataBuf.length + 3)
			throw new DataFormatException(MSG06);

		byte[] b = splitInt(s);
		riffDataBuf[ptr++] = b[0];
		riffDataBuf[ptr++] = b[1];
		riffDataBuf[ptr++] = b[2];
		riffDataBuf[ptr++] = b[3];
		return;
	}

	/**
	 * Put a byte array into data buf.
	 * 
	 * This method will puts a byte array into data buf, with a start position
	 * that Data Buffer Pointer currently pointting.
	 * 
	 * @param s
	 *            Source Data
	 * @throws DataFormatException
	 *             If an overflowed pointer specified, a DataFormatException
	 *             will be thrown.
	 */
	public void writeBytes(byte[] s) throws DataFormatException {
		if (ptr >= riffDataBuf.length + s.length - 1)
			throw new DataFormatException(MSG06);
		for (int i = 0; i < s.length; i++)
			riffDataBuf[ptr++] = s[i];
		return;
	}

	// -------------------------| Data Buffer Pointer |-------------------------

	/**
	 * Set Data Buffer Pointer to the top of the data buf.
	 */
	public void rewind() {
		ptr = 0;
	}

	/**
	 * Set Data Buffer Pointer to a specified position.
	 * 
	 * @param pos
	 *            Position
	 * @throws DataFormatException
	 *             If an overflowed pointer specified, a DataFormatException
	 *             will be thrown.
	 */
	public void seek(int pos) throws DataFormatException {
		if (pos >= riffDataBuf.length)
			throw new DataFormatException(MSG06);
		ptr = pos;
	}

	/**
	 * End of Buffer.
	 * 
	 * @return true if reach end of buffer, otherwise false.
	 */
	public boolean eob() {
		return ptr >= riffDataBuf.length;
	}

	// -------------------------| Static method |-------------------------

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

	/**
	 * Phrase RIFF Data
	 * 
	 * This abstract method should phrase RIFF Data in data buf
	 * {@link #riffDataBuf}, and fill fields for each RIFF Type.<br>
	 * Use {@link #readByte()}, {@link #readShort()}, {@link #readInt()} and
	 * {@link #readBytes(int)} to read data from data buf.
	 * 
	 * @throws DataFormatException
	 *             throw DataFormatException when data format error occurs.
	 */
	public abstract void phraseRiffData() throws DataFormatException;

	/**
	 * Spell RIFF Data
	 * 
	 * This abstract method should spell a RIFF Data into data buf
	 * {@link #riffDataBuf} with fields in specified RIFF Type.<br>
	 * Use {@link #writeByte(byte)}, {@link #writeShort(short)},
	 * {@link #writeInt(int)} and {@link #writeBytes(byte[])} to write data to
	 * data buf.
	 * 
	 * @throws DataFormatException
	 *             throw DataFormatException when data format error occurs.
	 */
	public abstract void spellRiffData() throws DataFormatException;

}
