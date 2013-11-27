package de.nao.application;

/*
 * Make the robot wake up, move forward and then rest
 *
 * To run the example, make sure the robot is in proper position first.
 */

import com.aldebaran.proxy.*;

public class Move {
	private final ALMotionProxy motion;

	public Move(String NAOQI_IP, int NAOQI_PORT) {
		motion = new ALMotionProxy(NAOQI_IP, NAOQI_PORT);

	}
	
	public void initLaufen(){
		
	}

	public void laufe() {
		motion.wakeUp();
		motion.moveInit();
		motion.moveTo(5f, 0.0f, 0.0f);
	}

	public void halteAn() {
		motion.stopMove();
	}
}
