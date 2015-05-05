package net.syuui.acoust.algorithm.Math;

public class Complex {
	private double real;
	private double imag;
	private double modl;
	private double argZ;

	public Complex() {
		super();
		real = 0.0;
		imag = 0.0;
		modl = 0.0;
		argZ = 0.0;
	}

	public Complex(Complex val) {
		super();
		real = val.getReal();
		imag = val.getImag();
		calcZ();
	}

	public Complex(double real, double imag) {
		super();
		real = 0.0;
		imag = 0.0;
		modl = 0.0;
		argZ = 0.0;
		this.real = real;
		this.imag = imag;
		calcZ();
	}

	public void setComplex(double real, double imag) {
		this.real = real;
		this.imag = imag;
		calcZ();
	}

	public void setComplexZ(double module, double argZ) {
		this.modl = module;
		this.argZ = argZ;
		calc();
	}

	private void calc() {
		real = modl * Math.cos(argZ);
		imag = modl * Math.sin(argZ);
	}

	private void calcZ() {
		modl = Math.sqrt(real * real + imag * imag);
		if (modl != 0) {
			double cs = Math.acos(real / modl);
			double si = Math.asin(imag / modl);

			if (si > 0) {
				if (cs > 0)
					argZ = si; // quadrant 1
				else
					argZ = Math.PI - si; // quadrant 2
			} else {
				if (cs > 0)
					argZ = Math.PI * 2 + si; // quadrant 4
				else
					argZ = Math.PI - si; // quadrant 3
			}
		}
	}

	public double getReal() {
		return real;
	}

	public double getImag() {
		return imag;
	}

	public double getModl() {
		return modl;
	}

	public void setModl(double modl) {
		this.modl = modl;
	}

	public double getArgZ() {
		return argZ;
	}

	public void setArgZ(double argZ) {
		this.argZ = argZ;
	}

	public double getModule() {
		return Math.sqrt(real * real + imag * imag);
	}

	public Complex getAdjoint() {
		return new Complex(real, -imag);
	}

	public boolean isRealNumber() {
		return imag == 0;
	}

	public boolean isPureImaginary() {
		return real == 0;
	}

	public static boolean isRealNumber(Complex val) {
		return val.getImag() == 0;
	}

	public static boolean isPureImaginary(Complex val) {
		return val.getReal() == 0;
	}

	public static double getArgZ(Complex val) {
		return Math.acos(val.real / val.getModule());
	}

	public static double module(Complex val) {
		return Math.sqrt(val.getReal() * val.getReal() + val.getImag()
				* val.getImag());

	}

	public static Complex addition(Complex val1, Complex val2) {
		return new Complex(val1.getReal() + val2.getReal(), val1.getImag()
				+ val2.getImag());
	}

	public static Complex addition(int val1, Complex val2) {
		return new Complex((double) val1 + val2.getReal(), val2.getImag());
	}

	public static Complex addition(float val1, Complex val2) {
		return new Complex((double) val1 + val2.getReal(), val2.getImag());
	}

	public static Complex addition(double val1, Complex val2) {
		return new Complex(val1 + val2.getReal(), val2.getImag());
	}

	public static Complex subtraction(Complex minuend, Complex subtrahend) {
		return new Complex(minuend.getReal() - subtrahend.getReal(),
				minuend.getImag() - subtrahend.getImag());
	}

	public static Complex subtraction(int minuend, Complex subtrahend) {
		return new Complex((double) minuend - subtrahend.getReal(),
				-subtrahend.getImag());
	}

	public static Complex subtraction(float minuend, Complex subtrahend) {
		return new Complex((double) minuend - subtrahend.getReal(),
				-subtrahend.getImag());
	}

	public static Complex subtraction(double minuend, Complex subtrahend) {
		return new Complex(minuend - subtrahend.getReal(),
				-subtrahend.getImag());
	}

	public static Complex subtraction(Complex minuend, int subtrahend) {
		return new Complex(minuend.getReal() - (double) subtrahend,
				minuend.getImag());
	}

	public static Complex subtraction(Complex minuend, float subtrahend) {
		return new Complex(minuend.getReal() - (double) subtrahend,
				minuend.getImag());
	}

	public static Complex subtraction(Complex minuend, double subtrahend) {
		return new Complex(minuend.getReal() - subtrahend, minuend.getImag());
	}

	public static Complex multiplication(Complex val1, Complex val2) {
		return new Complex(val1.getReal() * val2.getReal() - val1.getImag()
				* val2.getImag(), val1.getReal() * val2.getImag()
				- val1.getImag() * val2.getReal());
	}

	public static Complex multiplication(int val1, Complex val2) {
		return new Complex((double) val1 * val2.getReal(), val1
				* val2.getImag());
	}

	public static Complex multiplication(float val1, Complex val2) {
		return new Complex((double) val1 * val2.getReal(), val1
				* val2.getImag());
	}

	public static Complex multiplication(double val1, Complex val2) {
		return new Complex((double) val1 * val2.getReal(), val1
				* val2.getImag());
	}

	public static Complex division(Complex dividend, Complex divisor) {
		return new Complex(
				(dividend.getReal() * divisor.getReal() + dividend.getImag()
						* divisor.getImag())
						/ (divisor.getModule() * divisor.getModule()),
				(dividend.getImag() * divisor.getReal() - dividend.getReal()
						* divisor.getImag())
						/ (divisor.getModule() * divisor.getModule()));
	}

	public static Complex division(int dividend, Complex divisor) {
		return new Complex(((double) dividend * divisor.getReal())
				/ (divisor.getModule() * divisor.getModule()),
				-(dividend * divisor.getReal())
						/ (divisor.getModule() * divisor.getModule()));
	}

	public static Complex division(float dividend, Complex divisor) {
		return new Complex(((float) dividend * divisor.getReal())
				/ (divisor.getModule() * divisor.getModule()),
				-(dividend * divisor.getReal())
						/ (divisor.getModule() * divisor.getModule()));
	}

	public static Complex division(double dividend, Complex divisor) {
		return new Complex((dividend * divisor.getReal())
				/ (divisor.getModule() * divisor.getModule()),
				-(dividend * divisor.getReal())
						/ (divisor.getModule() * divisor.getModule()));
	}

	public static Complex division(Complex dividend, int divisor) {
		return new Complex(dividend.getReal() / (double) divisor,
				dividend.getImag() / (double) divisor);
	}

	public static Complex division(Complex dividend, float divisor) {
		return new Complex(dividend.getReal() / (double) divisor,
				dividend.getImag() / (double) divisor);
	}

	public static Complex division(Complex dividend, double divisor) {
		return new Complex(dividend.getReal() / divisor, dividend.getImag()
				/ divisor);
	}

	public static Complex adjoint(Complex val) {
		return new Complex(val.getReal(), -val.getImag());
	}

	public static Complex Power(Complex val, double pow) {
		double R = val.getModl();
		double T = val.getArgZ();
		Complex rlt = new Complex();
		rlt.setComplexZ(R * pow, T * pow);
		return rlt;
	}

	public static boolean isAdjoint(Complex val1, Complex val2) {
		return val1.getReal() == val2.getReal()
				&& val1.getImag() == -val2.getImag();
	}
}
