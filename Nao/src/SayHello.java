/*
 * Make the robot say "Hello, World"
 */

import com.aldebaran.proxy.*;

public class SayHello {
	private static String NAOQI_IP = "192.168.100.6";
	private static int NAOQI_PORT = 9559;

	public static void main(String[] args) {
		ALTextToSpeechProxy tts = new ALTextToSpeechProxy(NAOQI_IP, NAOQI_PORT);
//		tts.setLanguage("german");
		// tts.say("33 Komma 3 3 3 3 3 3 Überhitzt 3 3 3 3 3 3 3 3 3 3 3");
		tts.say("have a break have a kit kat");
	}
}
