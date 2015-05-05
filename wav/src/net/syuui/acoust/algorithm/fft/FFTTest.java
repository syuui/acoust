package net.syuui.acoust.algorithm.fft;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class FFTTest extends JFrame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		FFTTest win = new FFTTest();
	}

	public FFTTest() {
		// TODO Auto-generated constructor stub
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 360);
		setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);

		double d[] = new double[480];

		for (int i = 0; i < d.length; i++) {
			d[i] = Math.sin(2.0 * Math.PI * 4.0 * i / 480.0);
		}

//		FFT fft = new FFT();
//		fft.calculate(d);
		
		Graphics gc = getGraphics();
		gc.setColor(Color.BLACK);
		for (int i = 1; i < d.length; i++) {
			gc.drawLine(i - 1, (int) (180.0 - 120.0 * d[i - 1]), i,
					(int) (180.0 - 120.0 * d[i]));
		}

	}

}
