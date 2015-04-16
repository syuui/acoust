package net.syuui.acoust.dataif.bmp;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BmpTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
