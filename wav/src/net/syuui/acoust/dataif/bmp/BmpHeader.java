package net.syuui.acoust.dataif.bmp;

import java.util.Arrays;

import net.syuui.acoust.dataif.StaticTools;

public class BmpHeader {
	/*
	 * BMP文件头 (14 字节 ) 　 　 BMP文件头数据结构含有 BMP 文件的类型、文件大小和位图起始位置等信息。　
	 */

	public static int BMP_HEADER_SIZE = 14;

	// 位图文件的类型，必须为 ' B '' M '两个字母 (0-1字节 )
	byte bfType[] = new byte[2];

	// 位图文件的大小，以字节为单位 (2-5 字节 )
	int bfSize;

	// 位图文件保留字，必须为 0(6-7 字节 )
	short bfReserved1;

	// 位图文件保留字，必须为 0(8-9 字节 )
	short bfReserved2;

	// 位图数据的起始位置，以相对于位图 (10-13 字节 )
	int bfOffset;

	public byte[] getBfType() {
		return Arrays.copyOf(bfType, bfType.length);
	}

	public void setBfType(byte[] bfType) {
		this.bfType = Arrays.copyOf(bfType, bfType.length);
	}

	public int getBfSize() {
		return bfSize;
	}

	public void setBfSize(int bfSize) {
		this.bfSize = bfSize;
	}

	public short getBfReserved1() {
		return bfReserved1;
	}

	public void setBfReserved1(short bfReserved1) {
		this.bfReserved1 = bfReserved1;
	}

	public short getBfReserved2() {
		return bfReserved2;
	}

	public void setBfReserved2(short bfReserved2) {
		this.bfReserved2 = bfReserved2;
	}

	public int getBfOffset() {
		return bfOffset;
	}

	public void setBfOffset(int bfOffset) {
		this.bfOffset = bfOffset;
	}

	public boolean setAllBmpHeader(byte buf[]) {
		if (buf.length < 14)
			return false;

		setBfType(Arrays.copyOf(buf, 2));
		setBfSize(StaticTools.spellInt(buf[5], buf[4], buf[3], buf[2]));
		setBfReserved1(StaticTools.spellShort(buf[7], buf[6]));
		setBfReserved2(StaticTools.spellShort(buf[9], buf[8]));
		setBfOffset(StaticTools.spellInt(buf[13], buf[12], buf[11], buf[10]));
		return true;
	}
}
