package net.syuui.acoust.algorithm.Math;

import java.util.Arrays;

public class Determinant {
	private int length;
	private double data[][];

	public Determinant() {
		super();
		data = new double[0][0];
	}

	public Determinant(int len) {
		super();
		this.length = len;
		data = new double[len][len];
	}

	public Determinant(double[][] dat) {
		super();
		this.length = dat[0].length;
		this.data = new double[this.length][];
		for (int i = 0; i < this.length; i++) {
			this.data[i] = Arrays.copyOf(dat[i], this.length);
		}
	}

	public Determinant(Determinant val) {
		this.length = val.getLength();
		this.data = new double[this.length][this.length];
		for (int i = 0; i < this.length; i++) {
			this.data[i] = Arrays.copyOf(val.getData()[i], this.length);
		}
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public double[][] getData() {
		return data;
	}

	public void setDat(double[][] dat) {
		this.data = dat;
	}

	public static double getValue(Determinant val) {
		if (val.getLength() == 1) {
			return val.getData()[0][0];
		} else if (val.getLength() == 2) {
			return val.getData()[0][0] * val.getData()[1][1]
					- val.getData()[0][1] * val.getData()[1][0];
		} else {
			double sum = 0.0;
			for (int k = 0; k < val.getLength(); k++) {
				double sig = (k) % 2 == 0 ? 1.0 : -1.0;
				double M[][] = new double[val.getLength() - 1][val.getLength() - 1];

				for (int i = 1; i < val.getLength(); i++) {
					for (int j = 0, x = 0; j < val.getLength(); j++) {
						if (j != k) {
							M[i - 1][x++] = val.getData()[i][j];
						}
					}
				}
				Determinant AM = new Determinant(M);
				sum += sig * val.getData()[0][k] * Determinant.getValue(AM);
			}
			return sum;
		}
	}

	public static void main(String[] args) {
		double[][] ar = new double[3][3];
		ar[0][0] = 1;
		ar[0][1] = 2;
		ar[0][2] = 3;
		ar[1][0] = 4;
		ar[1][1] = 5;
		ar[1][2] = 6;
		ar[2][0] = 7;
		ar[2][1] = 8;
		ar[2][2] = 9;
		Determinant DX = new Determinant(ar);

		System.out.println(Determinant.getValue(DX));
	}
}
