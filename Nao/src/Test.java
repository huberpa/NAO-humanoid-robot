import com.aldebaran.proxy.ALMotionProxy;
import com.aldebaran.proxy.ALPhotoCaptureProxy;
import com.aldebaran.proxy.ALRobotPostureProxy;
import com.aldebaran.proxy.ALVideoDeviceProxy;
import com.aldebaran.proxy.Variant;

public class Test {
	private static String NAOQI_IP = "192.168.100.7";
	private static int NAOQI_PORT = 9559;

	public static void main(String[] args) {

		System.out.println("start");

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

		ALRobotPostureProxy pp = new ALRobotPostureProxy(NAOQI_IP, NAOQI_PORT);
		pp.stopMove();
		pp.goToPosture("Stand", 1f);
		ALMotionProxy mp = new ALMotionProxy(NAOQI_IP, NAOQI_PORT);

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
