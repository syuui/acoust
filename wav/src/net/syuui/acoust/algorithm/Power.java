package net.syuui.acoust.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.syuui.acoust.dataif.DataFormatException;

public class Power {
	static private String MSG001 = "Window size must be greater than 0.";
	static private String MSG002 = "Window shift must be greater than 0.";
	@SuppressWarnings("unused")
	static private String MSG003 = "";
	@SuppressWarnings("unused")
	static private String MSG004 = "";
	@SuppressWarnings("unused")
	static private String MSG005 = "";

	private double[] powerData = new double[0];
	private short[] pcmData;

	private int windowSize;
	private int windowShift;

	public int getWindowSize() {
		return windowSize;
	}

	public void setWindowSize(int windowSize) throws DataFormatException {
		if (windowSize <= 0)
			throw new DataFormatException(MSG001);
		this.windowSize = windowSize;
	}

	public int getWindowShift() {
		return windowShift;
	}

	public void setWindowShift(int windowShift) throws DataFormatException {
		if (windowShift <= 0)
			throw new DataFormatException(MSG002);
		this.windowShift = windowShift;
	}

	public double[] getPowerData() {
		if (powerData.length <= 0)
			calculatePower();
		return Arrays.copyOf(powerData, powerData.length);
	}

	public short[] getPcmData() {
		return pcmData;
	}

	public void setPcmData(short[] pcmData) {
		this.pcmData = pcmData;
	}

	protected void calculatePower() {
		if (pcmData == null)
			return;
		double p;
		List<Double> pdata = new ArrayList<Double>();

		for (int i = 0; i < pcmData.length; i += windowShift) {
			p = 0.0;
			for (int j = 0; j < windowSize && i + j < pcmData.length; j++) {
				p += pcmData[i + j] * pcmData[i + j];
			}
			pdata.add(Math.sqrt(p));
		}

		powerData = new double[pdata.size()];
		for (int i = 0; i < pdata.size(); i++) {
			powerData[i] = pdata.get(i);
		}

		pdata = null;
		return;
	}
}
