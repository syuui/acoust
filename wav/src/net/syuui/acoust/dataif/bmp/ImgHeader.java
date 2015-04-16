package net.syuui.acoust.dataif.bmp;

import net.syuui.acoust.dataif.StaticTools;

public class ImgHeader {
	/*
	 * 位图信息头(40 字节 ) BMP 位图信息头数据用于说明位图的尺寸等信息。
	 */
	
	public static int IMG_HEADER_SIZE = 40;
	
	// (14-17 字节)
	// 本结构所占用字节数
	int Size;

	// (18-21 字节)
	// 位图的宽度，以像素为单位
	int image_width;

	// (22-25 字节)
	// 位图的高度，以像素为单位 　　
	int image_heigh;

	// (26-27 字节)
	// 目标设备的级别，必须为 1　
	short Planes;

	// (28-29 字节)
	// 每个像素所需的位数，必须是 1(双色 ),4(16 色),8(256 色) 或 24(真彩色)之一　　
	short biBitCount;

	// (30-33 字节)
	// 位图压缩类型，必须是 0(不压缩 ),1(BI_RLE8 压缩类型)或 2(BI_RLE4 压缩类型)之一
	int biCompression;

	// (34-37 字节)　　
	// 位图的大小，以字节为单位
	int SizeImage;

	// (38-41 字节)
	// 位图水平分辨率，每米像素数
	int biXPelsPerMeter;

	// (42-45 字节)
	// 位图垂直分辨率，每米像素数　　
	int biYPelsPerMeter;

	// (46-49 字节)
	// 位图实际使用的颜色表中的颜色数　　
	int biClrUsed;

	// (50-53 字节)
	// 位图显示过程中重要的颜色数
	int biClrImportant;

	public int getSize() {
		return Size;
	}

	public void setSize(int size) {
		Size = size;
	}

	public int getImage_width() {
		return image_width;
	}

	public void setImage_width(int image_width) {
		this.image_width = image_width;
	}

	public int getImage_heigh() {
		return image_heigh;
	}

	public void setImage_heigh(int image_heigh) {
		this.image_heigh = image_heigh;
	}

	public short getPlanes() {
		return Planes;
	}

	public void setPlanes(short planes) {
		Planes = planes;
	}

	public short getBiBitCount() {
		return biBitCount;
	}

	public void setBiBitCount(short biBitCount) {
		this.biBitCount = biBitCount;
	}

	public int getBiCompression() {
		return biCompression;
	}

	public void setBiCompression(int biCompression) {
		this.biCompression = biCompression;
	}

	public int getSizeImage() {
		return SizeImage;
	}

	public void setSizeImage(int sizeImage) {
		SizeImage = sizeImage;
	}

	public int getBiXPelsPerMeter() {
		return biXPelsPerMeter;
	}

	public void setBiXPelsPerMeter(int biXPelsPerMeter) {
		this.biXPelsPerMeter = biXPelsPerMeter;
	}

	public int getBiYPelsPerMeter() {
		return biYPelsPerMeter;
	}

	public void setBiYPelsPerMeter(int biYPelsPerMeter) {
		this.biYPelsPerMeter = biYPelsPerMeter;
	}

	public int getBiClrUsed() {
		return biClrUsed;
	}

	public void setBiClrUsed(int biClrUsed) {
		this.biClrUsed = biClrUsed;
	}

	public int getBiClrImportant() {
		return biClrImportant;
	}

	public void setBiClrImportant(int biClrImportant) {
		this.biClrImportant = biClrImportant;
	}

	public boolean setAllImgHeader(byte buf[]) {
		if (buf.length < 40)
			return false;

		setSize(StaticTools.spellInt(buf[3], buf[2], buf[1], buf[0]));
		setImage_width(StaticTools.spellInt(buf[7], buf[6], buf[5], buf[4]));
		setImage_heigh(StaticTools.spellInt(buf[11], buf[10], buf[9], buf[8]));
		setPlanes(StaticTools.spellShort(buf[13], buf[12]));
		setBiBitCount(StaticTools.spellShort(buf[15], buf[14]));
		setBiCompression(StaticTools.spellInt(buf[19], buf[18], buf[17],
				buf[16]));
		setSizeImage(StaticTools.spellInt(buf[23], buf[22], buf[21], buf[20]));
		setBiXPelsPerMeter(StaticTools.spellInt(buf[27], buf[26], buf[25],
				buf[24]));
		setBiYPelsPerMeter(StaticTools.spellInt(buf[31], buf[30], buf[29],
				buf[28]));
		setBiClrUsed(StaticTools.spellInt(buf[35], buf[34], buf[33], buf[32]));
		setBiClrImportant(StaticTools.spellInt(buf[39], buf[38], buf[37],
				buf[36]));
		return true;
	}
}
