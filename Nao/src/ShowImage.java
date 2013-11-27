/*
 * Take a picture from the robot and show it on screen
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import com.aldebaran.proxy.*;

@SuppressWarnings("serial")
public class ShowImage extends Frame {

	final int width = 640;
	final int height = 480;
	final int aufloesung = width * height;

	static {
		System.loadLibrary("jnaoqi");
	}

	private BufferedImage img;
	private static String NAOQI_IP = "192.168.100.7";
	private static int NAOQI_PORT = 9559;

	public ShowImage(byte[] buff) {

		super("Image Frame");

		MediaTracker mt = new MediaTracker(this);
		int[] intArray;

		intArray = new int[aufloesung];
		for (int i = 0; i < aufloesung; i++) {
			intArray[i] = ((255 & 0xFF) << 24) | // alpha
					((buff[i * 3 + 0] & 0xFF) << 16) | // red
					((buff[i * 3 + 1] & 0xFF) << 8) | // green
					((buff[i * 3 + 2] & 0xFF) << 0); // blue
		}

		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		img.setRGB(0, 0, width, height, intArray, 0, width);

		mt.addImage(img, 0);
		setSize(width, height);
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				dispose();
			}
		});
	}

	public void update(Graphics g) {
		paint(g);
	}

	public void paint(Graphics g) {
		if (img != null) {
			g.drawImage(img, 0, 0, this);
		} else {
			System.out.println("null image");
		}
	}

	public static void main(String[] args) {

		System.out.println("start");
		ALVideoDeviceProxy videoDevice = new ALVideoDeviceProxy(NAOQI_IP,
				NAOQI_PORT);

		try {
			videoDevice.unsubscribe("java");
		} catch (RuntimeException re) {

		}
		System.out.println("nach unsub");

		videoDevice.subscribe("java", 2, 11, 1);
		System.out.println("nach subscribe");

		Variant ret = null;
		for (int i = 0; i < 5; i++) {
			// ret = videoDevice.getImageRemote("java");
			ret = videoDevice.getDirectRawImageRemote("java");
			System.out.println("nach getImage");

		}

		// Video device documentation explain that image is element 6
		Variant imageV = ret.getElement(6);
		System.out.println("nach getElem");

		// display image from byte array
		byte[] binaryImage = imageV.toBinary();
		System.out.println("nach toBina");

		new ShowImage(binaryImage);

		videoDevice.unsubscribe("java");
		System.out.println("nach unsub ende");
	}

}
