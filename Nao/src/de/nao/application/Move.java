package de.nao.application;

/*
 * Make the robot wake up, move forward and then rest
 *
 * To run the example, make sure the robot is in proper position first.
 */

import com.aldebaran.proxy.*;

public class Move {
	private final ALMotionProxy motion;
	private final String NAOQI_IP;
	private final int NAOQI_PORT;

	public Move(String NAOQI_IP, int NAOQI_PORT) {
		this.NAOQI_IP = NAOQI_IP;
		this.NAOQI_PORT = NAOQI_PORT;
		motion = new ALMotionProxy(NAOQI_IP, NAOQI_PORT);

	}

	public void initLaufen() {

	}

	public void walk(int[] movement) {
		System.out.println("Anfang walk()-Methode");
		float forward_backward = 0f;
		float radian = 0f;
		float left_right = 0f;

		// Vor-Zurück Bewegung
		if (movement[0] == 1f) {
			forward_backward = 10f;
		} else if (movement[0] == -1f) {
			forward_backward = -5f;
		}

		// links-rechts Drehung
		if (movement[1] == 1) {
			radian = -3.14f;
		} else if (movement[1] == -1) {
			radian = +3.14f;
		}

		// links-rechts Bewegung
		if (movement[2] == 1) {
			left_right = -5f;
		} else if (movement[2] == -1) {
			left_right = +5f;
		}

		System.out.println("move param: " + forward_backward + ", "
				+ left_right + ", " + radian);

		if (forward_backward == 0 && left_right == 0 && radian == 0) {
			System.out.println("stop move");
			stopMove();
		} else {
			// standUp();

			ALRobotPostureProxy pp = new ALRobotPostureProxy(NAOQI_IP,
					NAOQI_PORT);
			if (!pp.getPostureFamily().equals("Standing")) {
				standUp();
			}

			System.out.println("walk");
			System.out.println();

			motion.wakeUp();
			motion.moveInit();
			motion.moveTo(forward_backward, left_right, radian);
		}
	}

	private void standUp() {
		ALRobotPostureProxy pp = new ALRobotPostureProxy(NAOQI_IP, NAOQI_PORT);
		pp.stopMove();
		pp.goToPosture("Stand", 1f);
	}

	public void stopMove() {
		motion.stopMove();
	}
}
