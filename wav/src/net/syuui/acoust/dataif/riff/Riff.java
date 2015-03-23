package net.syuui.acoust.dataif.riff;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import net.syuui.acoust.dataif.DataFormatException;

/**
 * Riff.class
 * 
 * RIFF Data format class
 * 
 * @author zhouw
 * @category DataIF
 * @version 1.0
 */
public abstract class Riff {

	public static int FOUR_CHARACTOR_CODE_LENGTH = 4;
	public static String RIFF_ID = "RIFF";

	private static String MSG01 = "Bad RIFF ID specified. Should be \"RIFF\".";
	private static String MSG02 = "RIFF size must be greater or equal 0.";
	private static String MSG03 = "RIFF type must be a \"Four_Charactor_Code\".";
	@SuppressWarnings("unused")
	private static String MSG04 = "";
	@SuppressWarnings("unused")
	private static String MSG05 = "";
	@SuppressWarnings("unused")
	private static String MSG06 = "";

	// RIFF header
	private byte[] riffId = new byte[FOUR_CHARACTOR_CODE_LENGTH];
	private int riffSize = 0;
	private byte[] riffType = new byte[FOUR_CHARACTOR_CODE_LENGTH];

	public byte[] getRiffId() {
		return Arrays.copyOf(riffId, riffId.length);
	}

	public void setRiffId(byte[] riffId) throws DataFormatException {
		if (riffId != null) {
			if (!RIFF_ID.equals(new String(riffId))) {
				throw new DataFormatException(MSG01);
			} else {
				this.riffId = Arrays.copyOf(riffId, riffId.length);
			}
		}
	}

	public void setRiffId(String riffId) throws DataFormatException {
		if (riffId != null) {
			setRiffId(riffId.getBytes());
		} else {
			throw new DataFormatException(MSG01);
		}
	}

	public int getRiffSize() {
		return riffSize;
	}

	public void setRiffSize(int riffSize) throws DataFormatException {
		if (riffSize < 0) {
			throw new DataFormatException(MSG02);
		} else {
			this.riffSize = riffSize;
		}
	}

	public byte[] getRiffType() {
		return Arrays.copyOf(riffType, riffType.length);
	}

	public void setRiffType(byte[] riffType) throws DataFormatException {
		if (riffType != null) {
			if (riffType.length == 4) {
				this.riffType = Arrays.copyOf(riffType, riffType.length);
			} else {
				throw new DataFormatException(MSG03);
			}
		}
	}

	public void setRiffType(String riffType) throws DataFormatException {
		if (riffType != null) {
			setRiffType(riffType.getBytes());
		} else {
			throw new DataFormatException(MSG03);
		}
	}

	public abstract void readFromFile(FileInputStream fis)
			throws DataFormatException, IOException;

	public abstract void writeToFile(FileOutputStream fos)
			throws DataFormatException, IOException;
}
