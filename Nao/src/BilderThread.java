import java.awt.Graphics;
import java.awt.MediaTracker;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.aldebaran.proxy.ALVideoDeviceProxy;
import com.aldebaran.proxy.Variant;

public class BilderThread extends JFrame implements Runnable {

	final private String name;
	final int width = 640;
	final int height = 480;
	final int aufloesung = width * height;
	private BufferedImage img;
	private static String NAOQI_IP = "192.168.100.7";
	private static int NAOQI_PORT = 9559;

	static {
		System.loadLibrary("jnaoqi");
	}

	static {
		System.loadLibrary("jnaoqi");
	}

	public BilderThread(String name) {
		this.name = name;
	}

	@Override
	public void run() {

		System.out.println("start");
		ALVideoDeviceProxy videoDevice = new ALVideoDeviceProxy(NAOQI_IP,
				NAOQI_PORT);

		// videoDevice.setResolution0);
		try {
			videoDevice.unsubscribe(name);
		} catch (RuntimeException re) {

		}
		System.out.println("nach unsub");

		videoDevice.subscribe(name, 2, 11, 1);
		System.out.println("nach subscribe");

		Variant ret = videoDevice.getImageRemote(name);
		System.out.println("nach getImage");

		// Video device documentation explain that image is element 6
		Variant imageV = ret.getElement(6);
		System.out.println("nach getElem");

		// display image from byte array
		byte[] binaryImage = imageV.toBinary();
		System.out.println("nach toBina");

		showImage(binaryImage, name);
		videoDevice.unsubscribe(name);
		System.out.println("nach unsub ende");
	}

	public void showImage(byte[] buff, String name) {
		this.setName(name);

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

}
