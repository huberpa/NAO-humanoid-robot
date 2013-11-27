package de.nao.application;

import com.aldebaran.proxy.ALVideoDeviceProxy;
import com.aldebaran.proxy.Variant;

public class PictureTaker implements Runnable {

	// @TODO
	private final String NAOQI_IP;
	private final int NAOQI_PORT;

	private Draw draw;
	private String name;

	public PictureTaker(Draw draw, String name, String NAOQI_IP, int NAOQI_PORT) {
		this.draw = draw;
		this.name = name;
		this.NAOQI_IP = NAOQI_IP;
		this.NAOQI_PORT = NAOQI_PORT;

	}

	@Override
	public void run() {
		ALVideoDeviceProxy videoDevice = new ALVideoDeviceProxy(NAOQI_IP,
				NAOQI_PORT);
		try {
			videoDevice.unsubscribe(name);
		} catch (RuntimeException re) {
			// System.out.println(name + ": unsub 1 fehler");
		}
		videoDevice.subscribe(name, 2, 11, 1);
		Variant ret = videoDevice.getImageRemote(name);
		try {
			videoDevice.unsubscribe(name);
		} catch (RuntimeException re) {
			// System.out.println(name + ": unsub 2 fehler");
		}

		// Video device documentation explain that image is element 6
		Variant imageV = ret.getElement(6);

		// display image from byte array
		byte[] binaryImage = imageV.toBinary();

		draw.paint(binaryImage, name);

		// in draw einfügen
		// showImage(binaryImage, name);
	}

}
