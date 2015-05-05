package net.syuui.acoust.iodevice.usbcamera;

import java.awt.Component;

import java.awt.Rectangle;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class UsbCameraTest extends JFrame {
	/** * */
	private static final long serialVersionUID = 1211056605389924001L;
	private static Player player = null;
	private CaptureDeviceInfo device = null;
	private MediaLocator locator = null;
	boolean proportion = true;
	String str1 = "vfw:Logitech USB Video Cam:0";
	String str2 = "vfw:Microsoft WDM Image Capture (Win32):0";
	Component component1;

	public UsbCameraTest() {
		super("ÉãÏñ»ú");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// Óë²Ù×÷ÏµÍ³·ç¸ñÒ»ÖÂ
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		try {
			jbInit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new UsbCameraTest();
	}

	private void jbInit() throws Exception {
		component1 = Box.createGlue();
		// =====================³õÊ¼»¯Éè±¸===================//
		// component1.addNotify();
		device = CaptureDeviceManager.getDevice(str2);
		locator = device.getLocator();
		try {
			player = Manager.createRealizedPlayer(locator);
			player.start();
			if ((component1 = player.getVisualComponent()) != null) {
				this.getContentPane().add(component1, "Center");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		component1.setBounds(new Rectangle(0, 0, 250, 280));
		this.setSize(380, 300);
		this.setVisible(true);
	}
}