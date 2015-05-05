package net.syuui.acoust.dataif.bmp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BmpTest {

	private static int PASS_LINE = 60;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Bitmap B = new Bitmap();

		File f = new File("C:\\Users\\zhouw\\Desktop\\test\\card00.bmp");
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

		BitmapDot[] rec = additionalProc(img);

		int h, w;
		int cr, cg, cb;

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

		System.out.printf("(%d,%d) - (%d,%d)", rec[0].getX(), rec[0].getY(),
				rec[1].getX(), rec[1].getY());

		gc.setColor(Color.BLUE);
		gc.drawLine(rec[0].getX(), h - rec[0].getY(), rec[0].getX(),
				h - rec[1].getY());
		gc.drawLine(rec[0].getX(), h - rec[0].getY(), rec[1].getX(),
				h - rec[0].getY());
		gc.drawLine(rec[1].getX(), h - rec[0].getY(), rec[1].getX(),
				h - rec[1].getY());
		gc.drawLine(rec[0].getX(), h - rec[1].getY(), rec[1].getX(),
				h - rec[1].getY());

	}

	// Bitmaps saved in img[][], each dot contains a Palette class
	// Add your additional Approach here

	public static BitmapDot[] additionalProc(BitmapPalette[][] img) {

		// edgeVerticalDifferential(img);
		// edgeHorizontalDifferential(img);
		// edgeDerivative_1(img);

		int[][] gray = color2gray(img);
		return cutEdge(gray);
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

	public static BitmapDot[] cutEdge(int[][] img) {
		int i, j, h, w;
		h = img.length;
		w = img[0].length;

		BitmapDot[] rlt = new BitmapDot[2];
		rlt[0] = new BitmapDot();
		rlt[1] = new BitmapDot();

		int d[][] = new int[h][w];
		// 2-time diff ( horizonal )
		for (i = 0; i < h; i++) {
			for (j = 0; j < w; j++) {
				if (j == 0) {
					d[i][j] = Math.abs(img[i][j + 1] - img[i][j] / 2);
				} else if (j == w - 1) {
					d[i][j] = Math.abs(img[i][j] / 2 - img[i][j - 1]);
				} else {
					d[i][j] = Math.abs(img[i][j - 1] + img[i][j + 1]
							- img[i][j] * 2);
				}
				d[i][j] = d[i][j] > 60 ? 255 : 0;
			}
		}

		// Get horizonal integral
		int[] sum = new int[w];
		for (j = 0; j < w; j++)
			sum[j] = 0;

		for (i = 0; i < h; i++)
			for (j = 0; j < w; j++)
				sum[j] += d[i][j];

		for (j = 0; j < w; j++)
			System.out.println(sum[j]);

		int max = -1, x1 = 0, x2 = 0;
		for (j = 0; j < w; j++)
			if (sum[j] > max) {
				x1 = j;
				max = sum[j];
			}

		for (j = 0, max = -1; j < w; j++)
			if (sum[j] > max && j != x1) {
				x2 = j;
				max = sum[j];
			}

		rlt[0].setX(x1);
		rlt[1].setX(x2);

		// 2-time diff ( vertical )
		for (i = 0; i < h; i++) {
			for (j = 0; j < w; j++) {
				if (i == 0) {
					d[i][j] = Math.abs(img[i + 1][j] - img[i][j] / 2);
				} else if (i == h - 1) {
					d[i][j] = Math.abs(img[i][j] / 2 - img[i - 1][j]);
				} else {
					d[i][j] = Math.abs(img[i + 1][j] + img[i - 1][j]
							- img[i][j] * 2);
				}
				d[i][j] = d[i][j] > PASS_LINE ? 255 : 0;
			}
		}

		// Get vertical integral
		sum = new int[h];
		for (i = 0; i < h; i++)
			sum[i] = 0;

		for (i = 0; i < h; i++)
			for (j = 0; j < w; j++)
				sum[i] += d[i][j];

		max = -1;
		x1 = 0;
		x2 = 0;
		for (i = 0; i < h; i++)
			if (sum[i] > max) {
				x1 = i;
				max = sum[i];
			}

		for (i = 0, max = -1; i < h; i++)
			if (sum[i] > max && i != x1) {
				x2 = i;
				max = sum[i];
			}

		rlt[0].setY(x1);
		rlt[1].setY(x2);

		return rlt;

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
