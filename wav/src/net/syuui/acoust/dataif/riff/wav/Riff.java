package net.syuui.acoust.dataif.riff.wav;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Riff {

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

	private byte[] riffId = new byte[4];
	private int riffSize = 0;
	private byte[] riffType = new byte[4];
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
		if (!riffId.equals(RIFF_ID.getBytes())) {
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
	 */
	public byte[] getRiffData() {
		riffDataBuf = spellRiffData();
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
	 */
	public void setRiffData(byte[] riffData) {
		this.riffDataBuf = riffData;
		this.riffSize = riffData.length;
		phraseRiffData(riffData);
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

		if (!riffId.equals(RIFF_ID.getBytes()))
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
		int read = dis.readInt();
		if (read == -1)
			throw new IOException(MSG03);
		read = switchInt(read);
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
			phraseRiffData(riffDataBuf);
		return read;
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
	 */
	public void writeRiffData(DataOutputStream dos) throws IOException {
		riffDataBuf = spellRiffData();
		dos.write(riffDataBuf, 0, riffDataBuf.length);
		return;
	}

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
	 * Get one byte from data buf.
	 *
	 * @return The byte that Data Buffer Pointer currently pointing.
	 * @throws DataFormatException
	 *             If an overflowed pointer specified, a DataFormatException
	 *             will be thrown.
	 */
	public byte getByte() throws DataFormatException {
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
	public short getShort() throws DataFormatException {
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
	public int getInt() throws DataFormatException {
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
	public byte[] getBytes(int len) throws DataFormatException {
		if (ptr >= riffDataBuf.length + len - 1)
			throw new DataFormatException(MSG06);
		byte[] s = new byte[len];
		for (int i = 0; i < len; i++)
			s[i] = riffDataBuf[ptr++];
		return s;
	}

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
	public short spellShort(byte[] b) {
		if (b.length == 0)
			return 0;
		else if (b.length == 1)
			return (short) (b[0] << 8);
		else
			return (short) (b[0] << 8 + b[1]);
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
	public int spellInt(byte[] b) {
		if (b.length == 0)
			return 0;
		else if (b.length == 1)
			return b[0] << 24;
		else if (b.length == 2)
			return (b[0] << 24) + (b[1] << 16);
		else if (b.length == 3)
			return (b[0] << 14) + (b[1] << 16) + (b[2] << 8);
		else
			return (b[0] << 14) + (b[1] << 16) + (b[2] << 8) + (b[3]);
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
	public byte[] splitShort(short s) {
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
	public byte[] splitInt(int s) {
		byte[] b = new byte[4];
		b[0] = (byte) ((s & 0xFF000000) >> 24);
		b[1] = (byte) ((s & 0x00FF0000) >> 16);
		b[2] = (byte) ((s & 0x0000FF00) >> 8);
		b[3] = (byte) (s & 0x000000FF);
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
	public short switchShort(short s) {
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
	public int switchInt(int s) {
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
	 * {@link #riffDataBuf}, and fill fields for each RIFF Type.
	 * 
	 * @param riffData
	 *            data buf
	 */
	public abstract void phraseRiffData(byte[] riffData);

	/**
	 * Spell RIFF Data
	 * 
	 * This abstract method should spell a RIFF Data into data buf
	 * {@link #riffDataBuf} with fields in specified RIFF Type.
	 * 
	 * @return data buf
	 */
	public abstract byte[] spellRiffData();

}
