/*
 * Make the robot move its head
 */

import com.aldebaran.proxy.*;

public class MoveHead {
	private static String NAOQI_IP = "192.168.100.6";
	private static int NAOQI_PORT = 9559;

	// This is required so that we can use the 'Variant' object
	static {
	System.loadLibrary("jnaoqi");
	}

	public static void main(String[] args) {
		ALMotionProxy motion = new ALMotionProxy(NAOQI_IP, NAOQI_PORT);
		// This lets you use bound methods that excpects ALValue from Java:
		Variant names  = new Variant(new String[] {"HeadPitch" });
//		Variant angles = new Variant(new float[] { -4.0f, 4.0f, 0.0f });
		Variant angles = new Variant(new float[] { -4.0f, 4.0f });
		Variant times  = new Variant(new float[] {	3.0f, 6.0f });

//		motion.setStiffnesses(new Variant(new String[] {"HeadYaw"}), new Variant(new float[] {1.0f}));
		motion.angleInterpolation(names, angles, times, true);
//		motion.setStiffnesses(new Variant(new String[] {"HeadYaw"}), new Variant(new float[] {0.0f}));
	}
}
