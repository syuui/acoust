package net.syuui.acoust.dataif.riff.wav;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import net.syuui.acoust.dataif.DataFormatException;
import net.syuui.acoust.dataif.riff.Riff;

/**
 * Class Wav
 * 
 * @author zhouw
 * @category DataIF
 * @version 0.9
 */
public class Wav extends Riff {

	@SuppressWarnings("unused")
	private static String CHUNK_ID_FMT = "fmt ";
	@SuppressWarnings("unused")
	private static String CHUNK_ID_FACT = "fact";
	@SuppressWarnings("unused")
	private static String CHUNK_ID_DATA = "data";

	private static String MSG01 = "Invalid fmt chunk size, should equal 16 or 18, current ";
	private static String MSG02 = "Invalid Number of Channel, should equal 1 or 2.";
	private static String MSG03 = "Invalid Quantizing bits, should equal 8 or 16";
	@SuppressWarnings("unused")
	private static String MSG04 = "";
	@SuppressWarnings("unused")
	private static String MSG05 = "";
	@SuppressWarnings("unused")
	private static String MSG06 = "";

	private byte[] fmtChunkId;
	private int fmtChunkSize = 0;
	private short audioFormat = 0;
	private short numberChannel = 0;
	private int samplingFrequence = 0;
	private int byteRate = 0;
	private short blockAlign = 0;
	private short quantizingBits = 0;
	private byte[] additionalInfo;

	private byte[] factChunkId;
	private int factChunkSize = 0;
	private byte[] factData;

	private byte[] dataChunkId;
	private int dataChunkSize = 0;
	private byte[] wavDataBuf;
	@SuppressWarnings("rawtypes")
	private ArrayList channel1;
	@SuppressWarnings("rawtypes")
	private ArrayList channel2;

	public byte[] getFmtChunkId() {
		return fmtChunkId;
	}

	public void setFmtChunkId(byte[] fmtChunkId) {
		this.fmtChunkId = fmtChunkId;
	}

	public int getFmtChunkSize() {
		return fmtChunkSize;
	}

	public void setFmtChunkSize(int fmtChunkSize) {
		this.fmtChunkSize = fmtChunkSize;
	}

	public short getAudioFormat() {
		return audioFormat;
	}

	public void setAudioFormat(short audioFormat) {
		this.audioFormat = audioFormat;
	}

	public short getNumberChannel() {
		return numberChannel;
	}

	public void setNumberChannel(short numberChannel) {
		this.numberChannel = numberChannel;
	}

	public int getSamplingFrequence() {
		return samplingFrequence;
	}

	public void setSamplingFrequence(int samplingFrequence) {
		this.samplingFrequence = samplingFrequence;
	}

	public int getByteRate() {
		return byteRate;
	}

	public void setByteRate(int byteRate) {
		this.byteRate = byteRate;
	}

	public short getBlockAlign() {
		return blockAlign;
	}

	public void setBlockAlign(short blockAlign) {
		this.blockAlign = blockAlign;
	}

	public short getQuantizingBits() {
		return quantizingBits;
	}

	public void setQuantizingBits(short quantizingBits) {
		this.quantizingBits = quantizingBits;
	}

	public byte[] getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(byte[] additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public byte[] getFactChunkId() {
		return factChunkId;
	}

	public void setFactChunkId(byte[] factChunkId) {
		this.factChunkId = factChunkId;
	}

	public int getFactChunkSize() {
		return factChunkSize;
	}

	public void setFactChunkSize(int factChunkSize) {
		this.factChunkSize = factChunkSize;
	}

	public byte[] getFactData() {
		return factData;
	}

	public void setFactData(byte[] factData) {
		this.factData = factData;
	}

	public byte[] getDataChunkId() {
		return dataChunkId;
	}

	public void setDataChunkId(byte[] dataChunkId) {
		this.dataChunkId = dataChunkId;
	}

	public int getDataChunkSize() {
		return dataChunkSize;
	}

	public void setDataChunkSize(int dataChunkSize) {
		this.dataChunkSize = dataChunkSize;
	}

	public byte[] getWavDataBuf() {
		return wavDataBuf;
	}

	public void setWavDataBuf(byte[] wavDataBuf) {
		this.wavDataBuf = wavDataBuf;
	}

	public ArrayList getChannel1() {
		return channel1;
	}

	public void setChannel1(ArrayList channel1) {
		this.channel1 = channel1;
	}

	public ArrayList getChannel2() {
		return channel2;
	}

	public void setChannel2(ArrayList channel2) {
		this.channel2 = channel2;
	}

	@Override
	public void phraseRiffData() throws DataFormatException {
		rewind();

		fmtChunkId = readBytes(FOUR_CHARACTOR_CODE_LENGTH);
		fmtChunkSize = switchInt(readInt());

		if (fmtChunkSize != 16 && fmtChunkSize != 18)
			throw new DataFormatException(MSG01 + fmtChunkSize);

		audioFormat = switchShort(readShort());
		numberChannel = switchShort(readShort());
		samplingFrequence = switchInt(readInt());
		byteRate = switchInt(readInt());
		blockAlign = switchShort(readShort());
		quantizingBits = switchShort(readShort());

		if (fmtChunkSize == 18)
			additionalInfo = readBytes(2);

		dataChunkId = readBytes(FOUR_CHARACTOR_CODE_LENGTH);
		dataChunkSize = switchInt(readInt());

		wavDataBuf = readBytes(dataChunkSize);

		if (numberChannel == 1) {
			if (quantizingBits == 8) {
				channel1 = new ArrayList<Byte>();
				for (int i = 0; i < dataChunkSize; i++) {
					channel1.add(wavDataBuf[i]);
				}
			} else if (quantizingBits == 16) {
				channel1 = new ArrayList<Short>();
				for (int i = 0; i < dataChunkSize; i += 2) {
					byte[] stmp = new byte[2];

					// Already switched short variable here
					stmp[0] = wavDataBuf[i + 1];
					stmp[1] = wavDataBuf[i];
					channel1.add(spellShort(stmp));
				}
			} else {
				throw new DataFormatException(MSG03);
			}
		} else if (numberChannel == 2) {
			if (quantizingBits == 8) {
				channel1 = new ArrayList<Byte>();
				channel2 = new ArrayList<Byte>();
				for (int i = 0; i < dataChunkSize; i += 2) {
					channel1.add(wavDataBuf[i]);
					channel2.add(wavDataBuf[i + 1]);
				}
			} else if (quantizingBits == 16) {
				channel1 = new ArrayList<Short>();
				channel2 = new ArrayList<Short>();
				for (int i = 0; i < dataChunkSize; i += 4) {
					byte[] stmp = new byte[2];

					// Already switched short variable here
					stmp[0] = wavDataBuf[i + 1];
					stmp[1] = wavDataBuf[i];
					channel1.add(spellShort(stmp));

					stmp[0] = wavDataBuf[i + 3];
					stmp[1] = wavDataBuf[i + 2];
					channel2.add(spellShort(stmp));
				}
			} else {
				throw new DataFormatException(MSG03);
			}
		} else {
			throw new DataFormatException(MSG02);
		}

		return;
	}

	@Override
	public void spellRiffData() throws DataFormatException {
		// TODO Auto-generated method stub
		writeBytes("fmt ".getBytes());
		writeInt(wavDataBuf.length + 24);
		writeShort(audioFormat);
		writeShort(numberChannel);
		writeInt(samplingFrequence);
		writeInt(byteRate);
		writeShort(blockAlign);
		writeShort(quantizingBits);
		writeBytes("data".getBytes());
		writeInt(wavDataBuf.length);
		writeBytes(wavDataBuf);
		
		return;
	}

	public void printHeader() {
		System.out.println("RIFF ID: " + new String(getRiffId()));
		System.out.println("RIFF Size: " + getRiffSize());
		System.out.println("RIFF Type: " + new String(getRiffType()));
		System.out.println("FMT ID: " + new String(fmtChunkId));
		System.out.println("FMT Size: " + fmtChunkSize);
		System.out.println("Audio Format: " + audioFormat);
		System.out.println("Number of Channel: " + numberChannel);
		System.out.println("FS: " + samplingFrequence);
		System.out.println("Byte Rate: " + quantizingBits);
		System.out.println("Block Align: " + blockAlign);
		System.out.println("QB: " + quantizingBits);
		System.out.println("Data Chunk ID: " + new String(dataChunkId));
		System.out.println("Data Chunk Size: " + dataChunkSize);

		return;
	}
}
