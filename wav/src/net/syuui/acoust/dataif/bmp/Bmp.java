package net.syuui.acoust.dataif.bmp;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Bmp {
	public static int BI_BIT_COUNT_1 = 1;
	public static int BI_BIT_COUNT_4 = 4;
	public static int BI_BIT_COUNT_8 = 8;
	public static int BI_BIT_COUNT_16 = 16;
	public static int BI_BIT_COUNT_24 = 24;
	public static int BI_BIT_COUNT_32 = 32;
	public static int BI_NUM_PALETTE_1 = 2;
	public static int BI_NUM_PALETTE_4 = 16;
	public static int BI_NUM_PALETTE_8 = 256;

	BmpHeader bmpHeader = new BmpHeader();
	ImgHeader imgHeader = new ImgHeader();
	Palette[] palette = new Palette[0];
	byte[] image = new byte[0];
	int nPalette = 0;

	public BmpHeader getBmpHeader() {
		return bmpHeader;
	}

	public void setBmpHeader(BmpHeader bmpHeader) {
		this.bmpHeader = bmpHeader;
	}

	public ImgHeader getImgHeader() {
		return imgHeader;
	}

	public void setImgHeader(ImgHeader imgHeader) {
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

	public void setPalette(Palette[] p) {
		palette = Arrays.copyOf(p, p.length);
	}

	public Palette[] getPalette() {
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
			palette = new Palette[nPalette];
			System.out.println("palette size; " + buf.length);
			for (int i = 0; i < nPalette; i++) {
				palette[i] = new Palette();
				palette[i].setRgbBlue(buf[i * Palette.PALETTE_SIZE]);
				palette[i].setRgbGreen(buf[i * Palette.PALETTE_SIZE + 1]);
				palette[i].setRgbRed(buf[i * Palette.PALETTE_SIZE + 2]);
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

	public static void main(String[] args) {
		Bmp B = new Bmp();

		File f = new File("C:\\Users\\zhouw\\Desktop\\test\\4_1.bmp");
		FileInputStream fis;
		try {
			fis = new FileInputStream(f);
			DataInputStream dis = new DataInputStream(fis);

			B.getBitmapFromBMPFile(dis);

			// Show all fields.
			Class c = B.bmpHeader.getClass();
			Method[] ms = c.getDeclaredMethods();
			for (Method m : ms) {
				if (m.getName().substring(0, 3).equals("get")) {
					System.out.println(m.getName() + ": "
							+ m.invoke(B.bmpHeader));
				}
			}
			c = B.imgHeader.getClass();
			ms = c.getDeclaredMethods();
			for (Method m : ms) {
				if (m.getName().substring(0, 3).equals("get")) {
					System.out.println(m.getName() + ": "
							+ m.invoke(B.imgHeader));
				}
			}

			for (int i = 0; i < B.getnPalette(); i++) {
				System.out.println(B.getPalette()[i].getRgbRed() + ","
						+ B.getPalette()[i].getRgbGreen() + ","
						+ B.getPalette()[i].getRgbBlue());
			}

			dis.close();
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
