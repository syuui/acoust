package net.syuui.acoust.dataif.bmp;

public class BitmapPalette {
	/*
	 * 3 ：颜色表 颜色表用于说明位图中的颜色，它有若干个表项，每一个表项是一个RGBQUAD 类型的结构，定义一种颜色。 　　
	 */

	public static int PALETTE_SIZE = 4;

	// 蓝色的亮度 ( 值范围为 0-255) 　　
	byte rgbBlue;

	// 绿色的亮度 ( 值范围为 0-255) 　
	byte rgbGreen;

	// 红色的亮度 ( 值范围为 0-255) 　　
	byte rgbRed;

	// 保留，必须为 0
	byte rgbReserved;

	public byte getRgbBlue() {
		return rgbBlue;
	}

	public void setRgbBlue(byte rgbBlue) {
		this.rgbBlue = rgbBlue;
	}

	public byte getRgbGreen() {
		return rgbGreen;
	}

	public void setRgbGreen(byte rgbGreen) {
		this.rgbGreen = rgbGreen;
	}

	public byte getRgbRed() {
		return rgbRed;
	}

	public void setRgbRed(byte rgbRed) {
		this.rgbRed = rgbRed;
	}

	public BitmapPalette(byte rgbRed, byte rgbGreen, byte rgbBlue) {
		super();
		this.rgbBlue = rgbBlue;
		this.rgbGreen = rgbGreen;
		this.rgbRed = rgbRed;
	}

	public BitmapPalette() {
		super();
	}

}
