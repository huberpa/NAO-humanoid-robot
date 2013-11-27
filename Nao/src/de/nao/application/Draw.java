package de.nao.application;

import java.awt.MediaTracker;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Draw {

	private final int width = 640;
	private final int height = 480;
	private final int aufloesung = width * height;
	private final JPanel panel;
	private BufferedImage img;
	private long time;

	public Draw(JPanel panel) {
		this.panel = panel;
		time = System.currentTimeMillis();
	}

	synchronized void paint(byte[] buff, String name) {

		System.out.println(name + ": Ausgabe Bild für Patrick: "
				+ (System.currentTimeMillis() - time));
		time = System.currentTimeMillis();
		MediaTracker mt = new MediaTracker(panel);
		int[] intArray;

		intArray = new int[aufloesung];
		for (int i = 0; i < aufloesung; i++) {
			intArray[i] = ((255 & 0xFF) << 24) | // alpha
					((buff[i * 3 + 0] & 0xFF) << 16) | // red
					((buff[i * 3 + 1] & 0xFF) << 8) | // green
					((buff[i * 3 + 2] & 0xFF) << 0); // blue
		}

		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		img.setRGB(0, 0, width, height, intArray, 0, width);

		mt.addImage(img, 0);

		// @TODO size in gui
		panel.setSize(width, height);
		panel.setVisible(true);
	}

}
