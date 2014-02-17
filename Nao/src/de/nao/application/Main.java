package de.nao.application;

public class Main {

	private final static String NAOQI_IP = "192.168.100.6";
	private final static int NAOQI_PORT = 9559;

	public static void main(String[] args) {
		new Gui(NAOQI_IP, NAOQI_PORT);
	}

}
