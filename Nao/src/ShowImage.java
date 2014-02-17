/*
 * Take a picture from the robot and show it on screen
 */

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.aldebaran.proxy.ALVideoDeviceProxy;
import com.aldebaran.proxy.Variant;

@SuppressWarnings("serial")
public class ShowImage extends Frame {

	private JPanel panel;
	private int width = 160;
	private int height = 120;
	private int scaleFactor = 3;
	private int resolution = width * height;

	// for subscribe: 160x120 = 0
	private int resolutionID = 0;

	// RGB = 0xbbggrr
	private int colorSpace = 11;

	// FPS
	private int fps = 15;

	static {
		System.loadLibrary("jnaoqi");
	}

	private BufferedImage img;
	private static String NAOQI_IP = "192.168.100.6";
	private static int NAOQI_PORT = 9559;

	public ShowImage() {
		super("Image Frame");

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				ALVideoDeviceProxy videoDevice = new ALVideoDeviceProxy(
						NAOQI_IP, NAOQI_PORT);
				videoDevice.unsubscribe("java");
				System.exit(0);
			}
		});

		panel = new JPanel();
//		panel.setSize(width * scaleFactor, height * scaleFactor);

		setSize(width * scaleFactor, height * scaleFactor);
		setVisible(true);

		showImage();
	}

	public void showImage() {

		ALVideoDeviceProxy videoDevice = new ALVideoDeviceProxy(NAOQI_IP,
				NAOQI_PORT);

		try {
			for (int j = 0; j < 8; j++) {
				videoDevice.unsubscribe("java");
			}
		} catch (Exception e) {
			System.out.println("alles unsubscribed");
		}

		videoDevice.subscribe("java", resolutionID, colorSpace, fps);

		long timeDifferece;
		long timeMillis;
		Variant ret;
		JLabel l = new JLabel();
		panel.add(l);
		add(panel);

		for (int n = 0; n < 500000; n++) {
			timeMillis = System.currentTimeMillis();
			ret = videoDevice.getImageRemote("java");
			timeDifferece = System.currentTimeMillis() - timeMillis;
			System.out.println("getImage: " + timeDifferece);

			// Video device documentation explain that image is element 6
			Variant imageV = ret.getElement(6);

			// display image from byte arrayf
			byte[] binaryImage = imageV.toBinary();

			System.out.println("Bild: " + n);

			// MediaTracker mt = new MediaTracker(panel);
			int[] intArray;
			intArray = new int[resolution];
			for (int i = 0; i < resolution; i++) {
				intArray[i] = ((255 & 0xFF) << 24) | // alpha
						((binaryImage[i * 3 + 0] & 0xFF) << 16) | // red
						((binaryImage[i * 3 + 1] & 0xFF) << 8) | // green
						((binaryImage[i * 3 + 2] & 0xFF) << 0); // blue
			}

			img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			img.setRGB(0, 0, width, height, intArray, 0, width);

			l.setIcon(new ImageIcon(img.getScaledInstance(width * scaleFactor,
					height * scaleFactor, Image.SCALE_FAST)));

			try {
				if (timeDifferece < 1000) {
					timeDifferece = System.currentTimeMillis() - timeMillis;
					// System.out.println("Differenz: " + timeDifferece);
					if (timeDifferece < 1000) {
						Thread.sleep(1000 - timeDifferece);
					} else {
						System.out.println("######too slow");
						Thread.sleep(10);
					}
				} else {
					System.out.println("######too slow");
					Thread.sleep(10);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		videoDevice.unsubscribe("java");
	}

	public void update(Graphics g) {
		paint(g);
	}

//	public void paint(Graphics g) {
//		if (img != null) {
//			g.drawImage(img, 0, 0, panel);
//		} else {
//			System.out.println("null image");
//		}
//	}

	public static void main(String[] args) {
		new ShowImage();
	}
}
