package net.syuui.acoust.algorithm.fft;

public class FFT {
	int NumBit;
	int[] ReverseTable;

	// TODO: nBit should be specified by user, not automatically calculated.
	public void fft(double dat[], int nBit) {
		int i = (int) (Math.log((double) dat.length) / Math.log(2.0));

		if (dat.length != Math.pow(2.0, i)) {
			// Should add 0 to data
			if (NumBit != (i + 1)) {
				NumBit = i + 1;
				createReverseTable();
			}
			int tmp = (int) Math.pow(2.0, (double) NumBit);

			double newDat[] = new double[tmp];
			for (i = 0; i < dat.length; i++) {
				newDat[i] = dat[i];
			}
			for (; i < tmp; i++) {
				newDat[i] = 0;
			}
			dat = newDat;
		} else {
			if (NumBit != i) {
				NumBit = i;
				createReverseTable();
			}
		}
		System.out.println(("NumBit: " + NumBit));
		// sort array with reversed binary subscript
		reverse(dat);
	}

	private void createReverseTable() {
		int oSubscript, nSubscript;

		int len = (int) Math.pow(2.0, NumBit);

		ReverseTable = new int[len];

		for (oSubscript = 1; oSubscript < len; oSubscript++) {
			int nMask = 0x01 << (NumBit - 1);
			int oMask = 0x01;
			nSubscript = 0;
			for (int i = 0; i < NumBit; i++, nMask >>>= 1, oMask <<= 1) {
				if ((oSubscript & oMask) == oMask)
					nSubscript |= nMask;
			}
			ReverseTable[oSubscript] = nSubscript;
		}
		//
		// for (int i = 0; i < len; i++) {
		// System.out.println(i + " " + ReverseTable[i]);
		// }
	}

	private void reverse(double dat[]) {
		int len = (int) Math.pow(2.0, NumBit - 1);
		for (int i = 1; i < len; i++) {
			double tmp = dat[i];
			dat[i] = dat[ReverseTable[i]];
			dat[ReverseTable[i]] = tmp;
		}
	}

	public static void main(String[] args) {
		double d[] = new double[64];
		for (int i = 0; i < d.length; i++) {
			d[i] = (double) i;
		}

		FFT f = new FFT();
		f.fft(d, 4);

		for (int i = 0; i < d.length; i++) {
			System.out.println(i + " : " + d[i]);
		}
	}

}
