package net.syuui.acoust.dataif.riff;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

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

	@SuppressWarnings("unused")
	private static String RIFF_ID = "RIFF";
	@SuppressWarnings("unused")
	private static String MSG01 = "Bad Riff ID specified. Should be \"RIFF\", current is ";
	@SuppressWarnings("unused")
	private static String MSG02 = "Reached end of file while reading RIFF ID";
	@SuppressWarnings("unused")
	private static String MSG03 = "Reached end of file wille reading RIFF Size";
	@SuppressWarnings("unused")
	private static String MSG04 = "Reached end of file wille reading RIFF Type";
	@SuppressWarnings("unused")
	private static String MSG05 = "Invalid RIFF Size";
	@SuppressWarnings("unused")
	private static String MSG06 = "Out of data size";

	// RIFF header
	private byte[] riffId = new byte[FOUR_CHARACTOR_CODE_LENGTH];
	private int riffSize = 0;
	private byte[] riffType = new byte[FOUR_CHARACTOR_CODE_LENGTH];
	
	public byte[] getRiffId() {
		return Arrays.copyOf(riffId, riffId.length);
	}

	public void setRiffId(byte[] riffId) {
		if (riffId != null)
			this.riffId = Arrays.copyOf(riffId, riffId.length);
	}

	public int getRiffSize() {
		return riffSize;
	}

	public void setRiffSize(int riffSize) {
		this.riffSize = riffSize;
	}

	public byte[] getRiffType() {
		return Arrays.copyOf(riffType, riffType.length);
	}

	public void setRiffType(byte[] riffType) {
		if (riffType != null)
			this.riffType = Arrays.copyOf(riffType, riffType.length);
	}
	
	public abstract void readFromFile( FileInputStream fis);
	public abstract void writeToFile( FileOutputStream fos);
}
