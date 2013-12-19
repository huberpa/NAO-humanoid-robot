/*
 * Make the robot wake up, move forward and then rest
 *
 * To run the example, make sure the robot is in proper position first.
 */

import com.aldebaran.proxy.*;

public class Move {
	private static String NAOQI_IP = "192.168.100.12";
	private static int NAOQI_PORT = 9559;

	private final ALMotionProxy motion;

	public static void main(String[] args) {
		ALMotionProxy motion = new ALMotionProxy(NAOQI_IP, NAOQI_PORT);

		motion.openHand("RHand");
		// motion.wakeUp();
		// motion.moveInit();
		// motion.moveTo(0.3f, 0.0f, 0.0f);
		// motion.rest();
	}

	public Move() {
		motion = new ALMotionProxy(NAOQI_IP, NAOQI_PORT);

	}

	public void laufe() {
		stellen();

		System.out.println("vor wakeUp");
		motion.wakeUp();
		motion.moveInit();
		motion.moveTo(5f, 0.0f, 0.0f);
	}

	private void stellen() {
		ALRobotPostureProxy pp = new ALRobotPostureProxy(NAOQI_IP, NAOQI_PORT);
		pp.stopMove();
		pp.goToPosture("Stand", 1f);
		System.out.println("nach aufstehen");
	}

	public void halteAn() {
		motion.stopMove();
	}
}
