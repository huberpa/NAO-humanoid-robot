/*
 * Take a picture from the robot and show it on screen
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import com.aldebaran.proxy.*;

@SuppressWarnings("serial")
public class ShowImage extends Frame {

	private int width = 160;
	private int height = 120;
	private int resolution = width * height;
	
	// for subscribe: 160x120 = 0
	private static int resolutionID = 0;
	
	//RGB = 0xbbggrr
	private static int colorSpace = 11;
	
	//FPS
	private static int fps = 5;

	static {
		System.loadLibrary("jnaoqi");
	}

	private BufferedImage img;
	private static String NAOQI_IP = "192.168.100.15";
	private static int NAOQI_PORT = 9559;

	public ShowImage(byte[] buff) {
		super("Image Frame");
		MediaTracker mt = new MediaTracker(this);
		int[] intArray;
		intArray = new int[resolution];
		for (int i = 0; i < resolution; i++) {
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
		ALVideoDeviceProxy videoDevice = new ALVideoDeviceProxy(NAOQI_IP,
				NAOQI_PORT);

		videoDevice.subscribe("java", resolutionID, colorSpace, 5);
		Variant ret = videoDevice.getImageRemote("java");
		videoDevice.unsubscribe("java");

		// Video device documentation explain that image is element 6
		Variant imageV = ret.getElement(6);

		// display image from byte array
		byte[] binaryImage = imageV.toBinary();

		new ShowImage(binaryImage);
	}

}
