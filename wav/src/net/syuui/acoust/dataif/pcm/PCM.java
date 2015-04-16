package net.syuui.acoust.dataif.pcm;

public class PCM {

	private int samplingFrequence;
	private short quantizingBits;
	private short[] pcmData;
	private int numberData;

	public int getSamplingFrequence() {
		return samplingFrequence;
	}

	public void setSamplingFrequence(int samplingFrequence) {
		this.samplingFrequence = samplingFrequence;
	}

	public short getQuantizingBits() {
		return quantizingBits;
	}

	public void setQuantizingBits(short quantizingBits) {
		this.quantizingBits = quantizingBits;
	}

	public short[] getPcmData() {
		return pcmData;
	}

	public void setPcmData(short[] pcmData) {
		this.pcmData = pcmData;
	}

	public int getNumberData() {
		return numberData;
	}

	public void setNumberData(int numberData) {
		this.numberData = numberData;
	}

}
