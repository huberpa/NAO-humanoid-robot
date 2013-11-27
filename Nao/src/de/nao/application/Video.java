package de.nao.application;

import javax.swing.JPanel;

public class Video implements Runnable {

	private final String NAOQI_IP;
	private final int NAOQI_PORT;
	private Draw draw;
	private int threadCount = 5;
	// Zeit in Sekunden
	private int roundTime = 10;

	public Video(JPanel panel, String NAOQI_IP, int NAOQI_PORT) {
		draw = new Draw(panel);
		this.NAOQI_IP = NAOQI_IP;
		this.NAOQI_PORT = NAOQI_PORT;

	}

	@Override
	public void run() {
		//@TODO
		//subscribe aus schleife raus --> schneller
		
		Thread t1 = new Thread(new PictureTaker(draw, "t1", NAOQI_IP,
				NAOQI_PORT));
		t1.start();
		sleep(roundTime / threadCount * 1000);

		Thread t2 = new Thread(new PictureTaker(draw, "t2", NAOQI_IP,
				NAOQI_PORT));
		t2.start();
		sleep(roundTime / threadCount * 1000);

		Thread t3 = new Thread(new PictureTaker(draw, "t3", NAOQI_IP,
				NAOQI_PORT));
		t3.start();
		sleep(roundTime / threadCount * 1000);

		Thread t4 = new Thread(new PictureTaker(draw, "t4", NAOQI_IP,
				NAOQI_PORT));
		t4.start();
		sleep(roundTime / threadCount * 1000);

		Thread t5 = new Thread(new PictureTaker(draw, "t5", NAOQI_IP,
				NAOQI_PORT));
		t5.start();
		sleep(roundTime / threadCount * 1000);

		while (!Thread.interrupted()) {
			if (t1.isAlive()) {
				t1.interrupt();
			}
			t1 = new Thread(new PictureTaker(draw, "t1", NAOQI_IP, NAOQI_PORT));
			t1.start();
			sleep(roundTime / threadCount * 1000);

			if (t2.isAlive()) {
				t2.interrupt();
			}
			t2 = new Thread(new PictureTaker(draw, "t2", NAOQI_IP, NAOQI_PORT));
			t2.start();
			sleep(roundTime / threadCount * 1000);

			if (t3.isAlive()) {
				t3.interrupt();
			}
			t3 = new Thread(new PictureTaker(draw, "t3", NAOQI_IP, NAOQI_PORT));
			t3.start();
			sleep(roundTime / threadCount * 1000);

			if (t4.isAlive()) {
				t4.interrupt();
			}
			t4 = new Thread(new PictureTaker(draw, "t4", NAOQI_IP, NAOQI_PORT));
			t4.start();
			sleep(roundTime / threadCount * 1000);

			if (t5.isAlive()) {
				t5.interrupt();
			}
			t5 = new Thread(new PictureTaker(draw, "t5", NAOQI_IP, NAOQI_PORT));
			t5.start();
			sleep(roundTime / threadCount * 1000);

		}
	}

	private void sleep(int timeMillis) {
		try {
			Thread.sleep(timeMillis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
