package net.syuui.acoust.dataif.bmp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.dgc.DGC;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.hamcrest.DiagnosingMatcher;
import org.junit.Ignore;

import net.syuui.acoust.dataif.StaticTools;

public class BmpTest {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Bitmap B = new Bitmap();

		File f = new File("C:\\Users\\zhouw\\Desktop\\test\\card01.bmp");
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
					System.out.println(c.getName() + "." + m.getName() + ": "
							+ m.invoke(B.bmpHeader));
				}
			}
			c = B.imgHeader.getClass();
			ms = c.getDeclaredMethods();
			for (Method m : ms) {
				if (m.getName().substring(0, 3).equals("get")) {
					System.out.println(c.getName() + "." + m.getName() + ": "
							+ m.invoke(B.imgHeader));
				}
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
		Draw(B);
	}

	/**
	 * Create the frame.
	 */
	public static void Draw(Bitmap B) {
		JFrame jf = new JFrame();
		JPanel jp = new JPanel();

		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setBounds(100, 100, B.imgHeader.getImage_width(),
				B.imgHeader.getImage_heigh());

		// jp.setBorder(new EmptyBorder(5, 5, 5, 5));
		jp.setLayout(new BorderLayout(0, 0));

		jf.setContentPane(jp);
		jf.setVisible(true);

		Graphics gc = jp.getGraphics();
		BitmapPalette[][] img = B.getIntImageWithPalette();

		BitmapDot[] dt = additionalProc(img);

		int h, w;
		int cr, cg, cb;
		int x1, y1, x2, y2;
		h = B.imgHeader.getImage_heigh();
		w = B.imgHeader.getImage_width();

		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				cr = img[i][j].getRgbRed() & 0xFF;
				cg = img[i][j].getRgbGreen() & 0xFF;
				cb = img[i][j].getRgbBlue() & 0xFF;

				gc.setColor(new Color(cr, cg, cb));
				gc.drawRect(j, h - i, 1, 1);
			}
		}
	}

	// Bitmaps saved in img[][], each dot contains a Palette class
	// Add your additional Approach here

	public static BitmapDot[] additionalProc(BitmapPalette[][] img) {

		// edgeVerticalDifferential(img);
		// edgeHorizontalDifferential(img);
		// edgeDerivative_1(img);

		int[][] gray = color2gray(img);

		img = gray2color(gray);

		return null;
	}

	public static int[][] color2gray(BitmapPalette[][] img) {
		int i, j, h, w;
		h = img.length;
		w = img[0].length;

		int g[][] = new int[h][w];
		for (i = 0; i < h; i++) {
			for (j = 0; j < w; j++) {
				g[i][j] = (30 * img[i][j].getRgbRed() + 59
						* img[i][j].getRgbGreen() + 11 * img[i][j].getRgbBlue()) / 100;
			}
		}

		return g;
	}

	public static BitmapPalette[][] gray2color(int[][] img) {
		int i, j, h, w;
		h = img.length;
		w = img[0].length;

		BitmapPalette g[][] = new BitmapPalette[h][w];
		for (i = 0; i < h; i++) {
			for (j = 0; j < w; j++) {
				g[i][j] = new BitmapPalette((byte) img[i][j], (byte) img[i][j],
						(byte) img[i][j]);
			}
		}

		return g;
	}

	private static BitmapDot[] edgeDerivative_1(BitmapPalette[][] img) {
		int i, j, h, w;
		h = img.length;
		w = img[0].length;

		int g[][] = new int[h][w], d1[][] = new int[h][w];
		for (i = 0; i < h; i++) {
			for (j = 0; j < w; j++) {
				g[i][j] = (30 * img[i][j].getRgbRed() + 59
						* img[i][j].getRgbGreen() + 11 * img[i][j].getRgbBlue()) / 100;
			}
		}

		// D1
		for (i = 0; i < h; i++) {
			for (j = 0; j < w; j++) {
				if (j == 0) {
					d1[i][j] = g[i][j + 1] - g[i][j];
				} else if (j == w - 1) {
					d1[i][j] = g[i][j] - g[i][j - 1];
				} else {
					d1[i][j] = g[i][j + 1] + g[i][j - 1] - 2 * g[i][j];
				}
			}
		}
		for (i = 0; i < h; i++) {
			for (j = 1; j < w; j++) {
				if (d1[i][j] * d1[i][j - 1] < 0) {
					img[i][j].setRgbRed((byte) 255);
					img[i][j].setRgbGreen((byte) 255);
					img[i][j].setRgbBlue((byte) 255);
				} else {
					img[i][j].setRgbRed((byte) 0);
					img[i][j].setRgbGreen((byte) 0);
					img[i][j].setRgbBlue((byte) 0);

				}
			}
		}

		return null;
	}

	private static void edgeVerticalDifferential(BitmapPalette[][] img) {
		int i, j, h, w;
		BitmapPalette[] pals = new BitmapPalette[256];

		for (i = 0; i < 256; i++) {
			pals[i] = new BitmapPalette((byte) i, (byte) i, (byte) i);
		}

		h = img.length;
		w = img[0].length;
		int g[][] = new int[h][w];

		for (i = 0; i < h; i++) {
			for (j = 0; j < w; j++) {
				g[i][j] = (30 * img[i][j].getRgbRed() + 59
						* img[i][j].getRgbGreen() + 11 * img[i][j].getRgbBlue()) / 100;
			}
		}
		for (j = 0; j < w; j++) {
			img[0][j] = pals[255];
		}

		for (i = 1; i < h; i++) {
			for (j = 0; j < w; j++) {
				img[i][j] = pals[255 - Math.abs(g[i][j] - g[i - 1][j])];
			}
		}
	}

	private static void edgeHorizontalDifferential(BitmapPalette[][] img) {
		int i, j, h, w;
		BitmapPalette[] pals = new BitmapPalette[256];

		for (i = 0; i < 256; i++) {
			pals[i] = new BitmapPalette((byte) i, (byte) i, (byte) i);
		}

		h = img.length;
		w = img[0].length;
		int g[][] = new int[h][w];

		for (i = 0; i < h; i++) {
			for (j = 0; j < w; j++) {
				g[i][j] = (30 * img[i][j].getRgbRed() + 59
						* img[i][j].getRgbGreen() + 11 * img[i][j].getRgbBlue()) / 100;
			}
		}
		for (j = 0; j < w; j++) {
			img[0][j] = pals[255];
		}

		for (i = 0; i < h; i++) {
			for (j = 1; j < w; j++) {
				img[i][j] = pals[255 - Math.abs(g[i][j] - g[i][j - 1])];
			}
		}
	}

	@SuppressWarnings("unused")
	private static void edgeAverage(BitmapPalette[][] img) {
		// Get edge of image

		int passLine = 48;

		int i, j, h, w;

		h = img.length;
		w = img[0].length;

		// Gray image
		int g[][] = new int[h][w];

		BitmapPalette black = new BitmapPalette((byte) 0, (byte) 0, (byte) 0);
		BitmapPalette white = new BitmapPalette((byte) 255, (byte) 255,
				(byte) 255);

		for (i = 0; i < h; i++) {
			for (j = 0; j < w; j++) {
				// True colr to gray
				g[i][j] = (30 * img[i][j].getRgbRed() + 59
						* img[i][j].getRgbGreen() + 11 * img[i][j].getRgbBlue()) / 100;
			}
		}

		// OK now got a gray image in grayImg
		// Cut edge now with an avarage method

		// 1. Four corners of image

		// Left top
		if ((g[0][1] + g[1][0] + g[1][1]) / 3 > passLine) {
			img[0][0] = black;
		} else {
			img[0][0] = white;
		}

		// Right top
		if ((g[0][w - 2] + g[1][w - 2] + g[1][w - 1]) / 3 > passLine) {
			img[0][w - 1] = black;
		} else {
			img[0][w - 1] = white;
		}

		// Left bottom
		if ((g[h - 2][0] + g[h - 2][1] + g[h - 1][1]) / 3 > passLine) {
			img[h - 1][0] = black;
		} else {
			img[h - 1][0] = white;
		}

		// Right bottom
		if ((g[h - 2][w - 1] + g[h - 2][w - 2] + g[h - 1][w - 2]) / 3 > passLine) {
			img[h - 1][w - 1] = black;
		} else {
			img[h - 1][w - 1] = white;
		}

		// 2. The first and the last column, except corners
		for (i = 1; i < h - 2; i++) {
			if ((g[i - 1][0] + g[i - 1][1] + g[i][1] + g[i + 1][1] + g[i + 1][0]) / 5 > passLine) {
				img[i][0] = black;
			} else {
				img[i][0] = white;
			}

			if ((g[i - 1][w - 1] + g[i - 1][w - 2] + g[i][w - 2]
					+ g[i + 1][w - 2] + g[i + 1][w - 1]) / 5 > passLine) {
				img[i][w - 1] = black;
			} else {
				img[i][w - 1] = white;
			}
		}

		// 3. The first and the last Row, except corners
		for (j = 1; j < w - 2; j++) {
			if ((g[0][j - 1] + g[1][j - 1] + g[1][j] + g[1][j + 1] + g[0][j + 1]) / 5 > passLine) {
				img[0][j] = black;
			} else {
				img[0][j] = white;
			}

			if ((g[h - 1][j - 1] + g[h - 2][j - 1] + g[h - 2][j]
					+ g[h - 2][j + 1] + g[h - 1][j + 1]) / 5 > passLine) {
				img[h - 1][j] = black;
			} else {
				img[h - 1][j] = white;
			}
		}

		// 4. others
		for (i = 1; i < h - 2; i++) {
			for (j = 1; j < w - 2; j++) {
				if ((g[i - 1][j - 1] + g[i - 1][j] + g[i - 1][j + 1]
						+ g[i][j - 1] + g[i][j + 1] + g[i + 1][j - 1]
						+ g[i + 1][j] + g[i + 1][j + 1]) / 8 > passLine) {
					img[i][j] = black;
				} else {
					img[i][j] = white;
				}
			}
		}
	}

	public static void drawRectangle(BitmapPalette img[][], int x1, int y1,
			int x2, int y2) {
		int i, j;
		if (x1 < 0 || x2 < 0 || x1 >= img[0].length || x2 >= img[0].length
				|| y1 < 0 || y2 < 0 || y1 >= img.length || y2 >= img.length) {
			return;

		}

		System.out.printf("X1:%d\nY1:%d\nX2:%d\nY2:%d\n", x1, y1, x2, y2);

		for (i = y1; i <= y2; i++) {
			img[i][x1].setRgbRed((byte) 255);
			img[i][x1].setRgbGreen((byte) 0);
			img[i][x1].setRgbBlue((byte) 0);

			img[i][x2].setRgbRed((byte) 255);
			img[i][x2].setRgbGreen((byte) 0);
			img[i][x2].setRgbBlue((byte) 0);

		}
		for (j = x1; j <= x2; j++) {
			img[y1][j].setRgbRed((byte) 0);
			img[y1][j].setRgbGreen((byte) 0);
			img[y1][j].setRgbBlue((byte) 255);

			img[y2][j].setRgbRed((byte) 0);
			img[y2][j].setRgbGreen((byte) 0);
			img[y2][j].setRgbBlue((byte) 255);
		}
	}
}
