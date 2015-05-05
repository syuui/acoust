package net.syuui.acoust.dataif.riff.wav;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import net.syuui.acoust.dataif.DataFormatException;
import net.syuui.acoust.dataif.StaticTools;
import net.syuui.acoust.dataif.riff.Riff;

/**
 * Wav.class
 * 
 * Wav data class of RIFF library
 * 
 * @author zhouw
 * @category DataIF
 * @version 1.0
 */
public class Wav extends Riff {

	private static String MSG01 = "fmt chunk ID must be a \"Four_Charactor_Code\".";
	private static String MSG02 = "fmt chunk size must be greater or equal 0.";
	@SuppressWarnings("unused")
	private static String MSG03 = "";
	@SuppressWarnings("unused")
	private static String MSG04 = "";
	@SuppressWarnings("unused")
	private static String MSG05 = "";
	@SuppressWarnings("unused")
	private static String MSG06 = "";

	// fmt Chunk header
	private byte[] fmtChunkId = new byte[FOUR_CHARACTOR_CODE_LENGTH];
	private int fmtChunkSize = 0;

	// Wav Format
	private short audioFormat = 0;
	private short numberChannel = 0;
	private int samplingFrequence = 0;
	private int byteRate = 0;
	private short blockAlign = 0;
	private short quantizingBits = 0;
	private byte[] additionalInfo = new byte[0];

	// fact Chunk
	private byte[] factChunkId = new byte[0];
	private int factChunkSize = 0;
	private byte[] factData = new byte[0];

	// data Chunk
	private byte[] dataChunkId = new byte[0];
	private int dataChunkSize = 0;

	private short channel1[] = new short[0];
	private short channel2[] = new short[0];

	@Override
	public void readFromFile(FileInputStream fis) throws DataFormatException,
			IOException {
		DataInputStream dis = new DataInputStream(fis);
		byte[] tmp = new byte[FOUR_CHARACTOR_CODE_LENGTH];

		dis.read(tmp, 0, tmp.length);
		setRiffId(tmp);

		setRiffSize(StaticTools.switchInt(dis.readInt()));

		dis.read(tmp, 0, tmp.length);
		setRiffType(tmp);

		dis.read(fmtChunkId, 0, fmtChunkId.length);
		fmtChunkSize = StaticTools.switchInt(dis.readInt());

		audioFormat = StaticTools.switchShort(dis.readShort());
		numberChannel = StaticTools.switchShort(dis.readShort());
		samplingFrequence = StaticTools.switchInt(dis.readInt());
		byteRate = StaticTools.switchInt(dis.readInt());
		blockAlign = StaticTools.switchShort(dis.readShort());
		quantizingBits = StaticTools.switchShort(dis.readShort());

		if (fmtChunkSize > 16) {
			additionalInfo = new byte[fmtChunkSize - 16];
			dis.read(additionalInfo, 0, additionalInfo.length);
		}

		dis.read(tmp, 0, tmp.length);
		if (new String(tmp).equals("fact")) {
			factChunkId = tmp;
			factChunkSize = StaticTools.switchInt(dis.readInt());
			factData = new byte[factChunkSize];
			dis.read(factData, 0, factData.length);
			dis.read(tmp, 0, tmp.length);
		}

		dataChunkId = tmp;
		dataChunkSize = StaticTools.switchInt(dis.readInt());

		int ndata = (dataChunkSize) / (quantizingBits / 8) / numberChannel;
		channel1 = new short[ndata];
		if (numberChannel == 2)
			channel2 = new short[ndata];
		for (int i = 0; i < ndata; i++) {
			if (quantizingBits == 8) {
				channel1[i] = (short) dis.readByte();
				if (numberChannel == 2) {
					channel2[i] = (short) (dis.readByte());
				}
			} else {
				channel1[i] = StaticTools.switchShort(dis.readShort());
				if (numberChannel == 2) {
					channel2[i] = StaticTools.switchShort(dis.readShort());
				}
			}
		}
		dis.close();

		return;
	}

	@Override
	public void writeToFile(FileOutputStream fos) throws IOException {
		DataOutputStream dos = new DataOutputStream(fos);

		dos.write(getRiffId());
		dos.writeInt(StaticTools.switchInt(getRiffSize()));
		dos.write(getRiffType());

		dos.write(fmtChunkId);
		dos.write(StaticTools.switchInt(fmtChunkSize));

		dos.writeShort(StaticTools.switchShort(getAudioFormat()));
		dos.writeShort(StaticTools.switchShort(getNumberChannel()));
		dos.writeInt(StaticTools.switchInt(getSamplingFrequence()));
		dos.writeInt(StaticTools.switchInt(getByteRate()));
		dos.writeShort(StaticTools.switchShort(getBlockAlign()));
		dos.writeShort(StaticTools.switchShort(getQuantizingBits()));
		if (fmtChunkSize == 18) {
			dos.write(additionalInfo, 0, additionalInfo.length);
		}

		if (getFactChunkId().length == 4) {
			dos.write(getFactChunkId());
			dos.writeInt(StaticTools.switchInt(getFactChunkSize()));
			dos.write(getFactData());
		}

		dos.write(getDataChunkId());
		dos.writeInt(StaticTools.switchInt(getDataChunkSize()));
		int ndata = (dataChunkSize) / (quantizingBits / 8) / numberChannel;
		for (int i = 0; i < ndata; i++) {
			if (quantizingBits == 8) {
				dos.write((byte) ((StaticTools.switchShort(channel1[i])) & 0xFF));
				if (numberChannel == 2) {
					dos.write((byte) ((StaticTools.switchShort(channel2[i])) & 0xFF));
				}
			} else {
				dos.writeShort(StaticTools.switchShort(channel1[i]));
				if (numberChannel == 2) {
					dos.writeShort(StaticTools.switchShort(channel2[i]));
				}
			}
		}
		dos.close();
		return;
	}

	public int autoSetDataChunkSize() {
		return dataChunkSize = channel1.length * (quantizingBits / 8)
				+ channel2.length * (quantizingBits / 8);
	}

	public int autoSetFactChunkSize() {
		return factChunkSize = factData.length;
	}

	public int autoSetFmtChunkSize() {
		return fmtChunkSize = 16;
	}

	public int autoSetRiffSize() {
		int rs = dataChunkSize + fmtChunkSize + 20;
		try {
			setRiffSize(rs);
		} catch (DataFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	public byte[] getFmtChunkId() {
		return Arrays.copyOf(fmtChunkId, fmtChunkId.length);
	}

	public void setFmtChunkId(byte[] fmtChunkId) throws DataFormatException {
		if (fmtChunkId == null || fmtChunkId.length != 4) {
			throw new DataFormatException(MSG01);
		} else {
			this.fmtChunkId = Arrays.copyOf(fmtChunkId, fmtChunkId.length);
		}

	}

	public void setFmtChunkId(String fmtChunkId) throws DataFormatException {
		if (fmtChunkId != null) {
			setFmtChunkId(fmtChunkId.getBytes());
		} else {
			throw new DataFormatException(MSG01);
		}
	}

	public int getFmtChunkSize() {
		return fmtChunkSize;
	}

	public void setFmtChunkSize(int fmtChunkSize) throws DataFormatException {
		if (fmtChunkSize >= 0) {
			this.fmtChunkSize = fmtChunkSize;
		} else {
			throw new DataFormatException(MSG02);
		}
	}

	public short getAudioFormat() {
		return audioFormat;
	}

	public void setAudioFormat(short audioFormat) {
		this.audioFormat = audioFormat;
	}

	public void setAudioFormat(int audioFormat) {
		setAudioFormat((short) audioFormat);
	}

	public short getNumberChannel() {
		return numberChannel;
	}

	public void setNumberChannel(short numberChannel) {
		this.numberChannel = numberChannel;
	}

	public void setNumberChannel(int numberChannel) {
		setNumberChannel((short) numberChannel);
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

	public void setBlockAlign(int blockAlign) {
		setBlockAlign((short) blockAlign);
	}

	public short getQuantizingBits() {
		return quantizingBits;
	}

	public void setQuantizingBits(short quantizingBits) {
		this.quantizingBits = quantizingBits;
	}

	public void setQuantizingBits(int quantizingBits) {
		setQuantizingBits((short) quantizingBits);
	}

	public byte[] getAdditionalInfo() {
		return Arrays.copyOf(additionalInfo, additionalInfo.length);
	}

	public void setAdditionalInfo(byte[] additionalInfo) {
		if (additionalInfo != null)
			this.additionalInfo = Arrays.copyOf(additionalInfo,
					additionalInfo.length);
	}

	public byte[] getFactChunkId() {
		return Arrays.copyOf(factChunkId, factChunkId.length);
	}

	public void setFactChunkId(byte[] factChunkId) {
		if (factChunkId != null)
			this.factChunkId = Arrays.copyOf(factChunkId, factChunkId.length);
	}

	public int getFactChunkSize() {
		return factChunkSize;
	}

	public void setFactChunkSize(int factChunkSize) {
		this.factChunkSize = factChunkSize;
	}

	public byte[] getFactData() {
		return Arrays.copyOf(factData, factData.length);
	}

	public void setFactData(byte[] factData) {
		if (factData != null)
			this.factData = Arrays.copyOf(factData, factData.length);
	}

	public byte[] getDataChunkId() {
		return Arrays.copyOf(dataChunkId, dataChunkId.length);
	}

	public void setDataChunkId(byte[] dataChunkId) {
		if (dataChunkId != null)
			this.dataChunkId = Arrays.copyOf(dataChunkId, dataChunkId.length);
	}

	public int getDataChunkSize() {
		return dataChunkSize;
	}

	public void setDataChunkSize(int dataChunkSize) {
		this.dataChunkSize = dataChunkSize;
	}

	public short[] getChannel1() {
		return Arrays.copyOf(channel1, channel1.length);
	}

	public void setChannel1(short[] channel1) {
		if (channel1 != null)
			this.channel1 = Arrays.copyOf(channel1, channel1.length);
	}

	public short[] getChannel2() {
		return Arrays.copyOf(channel2, channel2.length);
	}

	public void setChannel2(short[] channel2) {
		if (channel2 != null)
			this.channel2 = Arrays.copyOf(channel2, channel2.length);
	}

}
