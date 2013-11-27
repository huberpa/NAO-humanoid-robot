/*
 * Make the robot wake up, move forward and then rest
 *
 * To run the example, make sure the robot is in proper position first.
 */

import com.aldebaran.proxy.*;

public class OriginalMove {
	private static String NAOQI_IP = "192.168.100.7";
	private static int NAOQI_PORT = 9559;

	public static void main(String[] args) {
		
		
		
		System.out.println("test");
		ALRobotPostureProxy p = new ALRobotPostureProxy(NAOQI_IP, NAOQI_PORT);
		p.stopMove();
		p.setMaxTryNumber(5);
		p.goToPosture("Stand Up", 0.5f);
		
		System.out.println("test2");
		
//		ALMotionProxy motion = new ALMotionProxy(NAOQI_IP, NAOQI_PORT);
//		
//		
//		motion.wakeUp();
//		motion.moveInit();
//		
//		// X-Richtung, Y-Richtung, Winkel
//		motion.moveTo(1.0f, 0.0f, 0.0f);
//		motion.rest();
//		
		
		
	}
}
