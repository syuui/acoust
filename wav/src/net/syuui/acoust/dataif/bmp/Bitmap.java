package net.syuui.acoust.dataif.bmp;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;

import net.syuui.acoust.dataif.StaticTools;

public class Bitmap {
	public static int BI_BIT_COUNT_1 = 1;
	public static int BI_BIT_COUNT_4 = 4;
	public static int BI_BIT_COUNT_8 = 8;
	public static int BI_BIT_COUNT_16 = 16;
	public static int BI_BIT_COUNT_24 = 24;
	public static int BI_BIT_COUNT_32 = 32;
	public static int BI_NUM_PALETTE_1 = 2;
	public static int BI_NUM_PALETTE_4 = 16;
	public static int BI_NUM_PALETTE_8 = 256;

	// bitmap header
	BitmapFileHeader bmpHeader = new BitmapFileHeader();

	// image header
	BitmapInfo imgHeader = new BitmapInfo();

	// palette
	BitmapPalette[] palette = new BitmapPalette[0];

	// image data
	byte[] image = new byte[0];

	// Number of palettes
	int nPalette = 0;

	public BitmapFileHeader getBmpHeader() {
		return bmpHeader;
	}

	public void setBmpHeader(BitmapFileHeader bmpHeader) {
		this.bmpHeader = bmpHeader;
	}

	public BitmapInfo getImgHeader() {
		return imgHeader;
	}

	public void setImgHeader(BitmapInfo imgHeader) {
		this.imgHeader = imgHeader;
	}

	public int getnPalette() {
		return nPalette;
	}

	public void setnPalette(int nPalette) {
		this.nPalette = nPalette;
	}

	public byte[] getImage() {
		return Arrays.copyOf(image, image.length);
	}

	public void setImage(byte[] image) {
		this.image = Arrays.copyOf(image, image.length);
	}

	public void setPalette(BitmapPalette[] p) {
		palette = Arrays.copyOf(p, p.length);
	}

	public BitmapPalette[] getPalette() {
		return Arrays.copyOf(palette, palette.length);
	}

	public void setAllPalette(byte[] buf) {
		// 当biBitCount=1,4,8 时，分别有 2,16,256 个表项 ;
		// 当biBitCount=24 时，没有颜色表项。
		if (imgHeader.getBiBitCount() == BI_BIT_COUNT_1) {
			nPalette = BI_NUM_PALETTE_1;
		} else if (imgHeader.getBiBitCount() == BI_BIT_COUNT_4) {
			nPalette = BI_NUM_PALETTE_4;
		} else if (imgHeader.getBiBitCount() == BI_BIT_COUNT_8) {
			nPalette = BI_NUM_PALETTE_8;
		} else {
			nPalette = 0;
		}

		if (nPalette > 0) {
			palette = new BitmapPalette[nPalette];
			for (int i = 0; i < nPalette; i++) {
				palette[i] = new BitmapPalette();
				palette[i].setRgbBlue(buf[i * BitmapPalette.PALETTE_SIZE]);
				palette[i].setRgbGreen(buf[i * BitmapPalette.PALETTE_SIZE + 1]);
				palette[i].setRgbRed(buf[i * BitmapPalette.PALETTE_SIZE + 2]);
			}
		}
	}

	public void getBitmapFromBMPFile(DataInputStream dis) throws IOException {
		byte[] tmp = new byte[40];

		dis.read(tmp, 0, 14);
		bmpHeader.setAllBmpHeader(tmp);

		dis.read(tmp, 0, 40);
		imgHeader.setAllImgHeader(tmp);

		tmp = new byte[(bmpHeader.getBfOffset() - 54)];
		dis.read(tmp, 0, tmp.length);
		setAllPalette(tmp);

		tmp = new byte[imgHeader.getSizeImage()];
		dis.read(tmp, 0, tmp.length);
		setImage(tmp);
	}

	public BitmapPalette[][] getIntImageWithPalette() {
		BitmapPalette[][] intImage = null;
		if (imgHeader.biBitCount == BI_BIT_COUNT_1) {
			intImage = getIntImageWithPalette_1();
		} else if (imgHeader.biBitCount == BI_BIT_COUNT_4) {
			intImage = getIntImageWithPalette_4();
		} else if (imgHeader.biBitCount == BI_BIT_COUNT_8) {
			intImage = getIntImageWithPalette_8();
		} else if (imgHeader.biBitCount == BI_BIT_COUNT_16) {
			intImage = getIntImageWithPalette_16();
		} else if (imgHeader.biBitCount == BI_BIT_COUNT_24) {
			intImage = getIntImageWithPalette_24();
		} else if (imgHeader.biBitCount == BI_BIT_COUNT_32) {
			intImage = getIntImageWithPalette_32();
		}
		return intImage;
	}

	private BitmapPalette[][] getIntImageWithPalette_1() {
		int j, nbp, x;
		BitmapPalette[][] img = new BitmapPalette[imgHeader.getImage_heigh()][imgHeader
				.getImage_width()];
		nbp = imgHeader.getnBytePerLine();
		for (int i = 0; i < imgHeader.getImage_heigh(); i++) {
			for (j = 0; j < imgHeader.getImage_width(); j += 8) {
				x = image[i * nbp + j / 8];
				img[i][j] = palette[(x & 0x80) >> 7];

				int m;
				for (m = 0; m < 8; m++) {
					if (j + m >= imgHeader.getImage_width()) {
						break;
					}
					img[i][j + m] = palette[(x & (0x80 >> m)) >> (7 - m)];
				}
				if (m != 8)
					break;

			}
		}
		return img;
	}

	private BitmapPalette[][] getIntImageWithPalette_4() {
		int j, nbp, x;
		BitmapPalette[][] img = new BitmapPalette[imgHeader.getImage_heigh()][imgHeader
				.getImage_width()];
		nbp = imgHeader.getnBytePerLine();
		for (int i = 0; i < imgHeader.getImage_heigh(); i++) {
			for (j = 0; j < imgHeader.getImage_width(); j += 2) {
				x = image[i * nbp + j / 2];
				img[i][j] = palette[(x & 0xF0) >> 4];

				if (j + 1 < imgHeader.getImage_width())
					img[i][j + 1] = palette[x & 0x0F];
			}
		}
		return img;
	}

	private BitmapPalette[][] getIntImageWithPalette_8() {
		int j, nbp, x;
		BitmapPalette[][] img = new BitmapPalette[imgHeader.getImage_heigh()][imgHeader
				.getImage_width()];
		nbp = imgHeader.getnBytePerLine();
		for (int i = 0; i < imgHeader.getImage_heigh(); i++) {
			for (j = 0; j < imgHeader.getImage_width(); j++) {
				x = image[i * nbp + j];
				x = x & 0xFF;
				img[i][j] = palette[x];
			}
		}
		return img;
	}

	private BitmapPalette[][] getIntImageWithPalette_16() {
		if (imgHeader.biCompression != BitmapInfo.BI_RGB) {
			System.err.println("Only support 555 format!");
		}

		int i, j, nbp;
		int MaskR = 0x7C00;
		int MaskG = 0x03E0;
		int MaskB = 0x001F;
		int cr, cg, cb, x;
		BitmapPalette[][] img = new BitmapPalette[imgHeader.getImage_heigh()][imgHeader
				.getImage_width()];
		nbp = imgHeader.getnBytePerLine();

		for (i = 0; i < imgHeader.getImage_heigh(); i++) {
			for (j = 0; j < imgHeader.getImage_width(); j++) {
				img[i][j] = new BitmapPalette();
				x = StaticTools.spellInt((byte) 0, (byte) 0, image[i * nbp + j
						* 2], image[i * nbp + j * 2 + 1]);
				cr = (x & MaskR) >> 10;
				cg = (x & MaskG) >> 5;
				cb = (x & MaskB);
				System.out.printf("R:%d, G:%d, B:%d\n", cr, cg, cb);
				img[i][j].setRgbBlue((byte) cb);
				img[i][j].setRgbGreen((byte) cg);
				img[i][j].setRgbRed((byte) cr);
			}
		}
		return img;
	}

	private BitmapPalette[][] getIntImageWithPalette_24() {
		int i, j, nbp;
		BitmapPalette[][] img = new BitmapPalette[imgHeader.getImage_heigh()][imgHeader
				.getImage_width()];
		nbp = imgHeader.getnBytePerLine();
		for (i = 0; i < imgHeader.getImage_heigh(); i++) {
			for (j = 0; j < imgHeader.getImage_width(); j++) {
				img[i][j] = new BitmapPalette();
				img[i][j].setRgbBlue(image[i * nbp + j * 3]);
				img[i][j].setRgbGreen(image[i * nbp + j * 3 + 1]);
				img[i][j].setRgbRed(image[i * nbp + j * 3 + 2]);
			}
		}
		return img;
	}

	private BitmapPalette[][] getIntImageWithPalette_32() {
		return null;
	}
}
