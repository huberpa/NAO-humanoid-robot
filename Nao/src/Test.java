import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.aldebaran.proxy.ALMotionProxy;
import com.aldebaran.proxy.Variant;

public class Test {
	private static String NAOQI_IP = "192.168.100.15";
	private static int NAOQI_PORT = 9559;
	static Image image = null;

	public static void main(String[] args) {

		System.out.println("start");

		JFrame frame = new JFrame();
		JPanel panel = new JPanel();

		try {
			image = ImageIO
					.read(new File(
							"e:/Bilder & Videos/2013-06-02 Motorräder/Suzuki GSX-F 750/CIMG5485.JPG"));
			image = image.getScaledInstance(100, 100, Image.SCALE_FAST);
		} catch (IOException e) {
			e.printStackTrace();
		} // anpassen !
		final JButton b = new JButton(new ImageIcon(image));
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("test");
				try {
					image = ImageIO
							.read(new File(
									"e:/Bilder & Videos/2013-06-02 Motorräder/Suzuki GSX-F 750/CIMG5486.JPG"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				image = image.getScaledInstance(100, 100, Image.SCALE_FAST);
				b.setIcon(new ImageIcon(image));
			}
		});
		panel.add(b);
		frame.add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		// for (int i = 0; i < 9; i++) {
		// new Thread(new BilderThread("t" + i)).start();
		// warte(100);
		// }

		// ALVideoDeviceProxy videoDevice = new ALVideoDeviceProxy(NAOQI_IP,
		// NAOQI_PORT);
		// ALPhotoCaptureProxy pcp = new ALPhotoCaptureProxy(NAOQI_IP,
		// NAOQI_PORT);
		// pcp.setResolution(2);
		// pcp.setCameraID(0);
		// pcp.setPictureFormat("jpg");
		// pcp.takePicture("/home/nao/recordings/cameras", "test");

		// deaktiviereStiffness();

		// ALRobotPostureProxy pp = new ALRobotPostureProxy(NAOQI_IP,
		// NAOQI_PORT);
		// String pFam = pp.getPostureFamily();
		// System.out.println(pFam);
		//
		//
		// pp.stopMove();
		// pp.goToPosture("Stand", 1f);
		//
		// String pFam2 = pp.getPostureFamily();
		// System.out.println(pFam2);

		// ALMotionProxy mp = new ALMotionProxy(NAOQI_IP, NAOQI_PORT);

		// mp.move(0.5f, 0f, 0f);

	}

	public static void warte(int timeMillis) {
		try {
			Thread.sleep(timeMillis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void deaktiviereStiffness() {
		ALMotionProxy mp = new ALMotionProxy(NAOQI_IP, NAOQI_PORT);
		mp.stiffnessInterpolation(new Variant("LArm"), new Variant(0f),
				new Variant(0.01f));
		mp.stiffnessInterpolation(new Variant("RArm"), new Variant(0f),
				new Variant(0.01f));
		mp.stiffnessInterpolation(new Variant("LLeg"), new Variant(0f),
				new Variant(0.01f));
		mp.stiffnessInterpolation(new Variant("RLeg"), new Variant(0f),
				new Variant(0.01f));
		mp.stiffnessInterpolation(new Variant("Head"), new Variant(0f),
				new Variant(0.01f));
	}

}
