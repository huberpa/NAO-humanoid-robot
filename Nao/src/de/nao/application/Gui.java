package de.nao.application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gui implements KeyListener {

	private final String NAOQI_IP;
	private final int NAOQI_PORT;
	final JFrame frame;
	final JPanel panel;
	private LinkedList<Integer> pressedKeys = new LinkedList<Integer>();
	// (vor/zurück), (links/rechts)-Drehung, (links/rechts)-Bewegung
	private int[] movement = new int[] { 0, 0, 0 };

	public Gui(String NAOQI_IP, int NAOQI_PORT) {
		this.NAOQI_IP = NAOQI_IP;
		this.NAOQI_PORT = NAOQI_PORT;

		frame = new JFrame("Nao Steuerung");
		panel = new JPanel();
		panel.setFocusable(true);
		panel.requestFocus();
		panel.addKeyListener(this);

		frame.add(panel);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

//		new Thread(new Video(panel, NAOQI_IP, NAOQI_PORT)).start();

		initialisiereLaufen();
	}

	private void initialisiereLaufen() {
		final JButton laufenButton = new JButton("Laufen");
		laufenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (laufenButton.getText().equals("Laufen")) {
					laufenButton.setText("Anhalten");
					System.out.println("starte laufen");
					Thread t = new Thread(new Runnable() {

						@Override
						public void run() {
							movement[0] = 1;
							new Move(NAOQI_IP, NAOQI_PORT).walk(movement);
						}
					});
					t.start();

				} else {
					laufenButton.setText("Laufen");
					System.out.println("anhalten");
					Thread t = new Thread(new Runnable() {

						@Override
						public void run() {
							movement[0] = 0;
							new Move(NAOQI_IP, NAOQI_PORT).stopMove();
						}
					});
					t.start();

				}
			}
		});
		panel.add(laufenButton);

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// System.out.println("Typed: " + e.getKeyCode() + ", " +
		// e.getKeyChar());

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (!pressedKeys.contains(keyCode)) {
			pressedKeys.add(keyCode);

			// w: 87
			if (keyCode == 87) {
				// vorwärts
				movement[0]++;
			}

			// s: 83
			else if (keyCode == 83) {
				// rückwärts
				movement[0]--;
			}

			// a: 65
			else if (keyCode == 65) {
				// links drehen
				movement[1]--;
			}

			// d: 68
			else if (keyCode == 68) {
				// rechts drehen
				movement[1]++;
			}

			// q: 81
			else if (keyCode == 81) {
				// links gehen
				movement[2]--;
			}

			// e: 69
			else if (keyCode == 69) {
				// rechts gehen
				movement[2]++;
			}

			System.out.print("Pressed Code: " + keyCode + ", movement: ");
			for (int i = 0; i < movement.length; i++) {
				System.out.print(movement[i] + ", ");
			}
			System.out.println();

			stopMove();

			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					System.out.println("vor new Move()");
					new Move(NAOQI_IP, NAOQI_PORT).walk(movement);
				}
			});
			t.start();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		pressedKeys.remove((Object) keyCode);

		// w: 87
		if (keyCode == 87) {
			// vorwärts
			movement[0]--;
		}

		// s: 83
		else if (keyCode == 83) {
			// rückwärts
			movement[0]++;
		}

		// a: 65
		else if (keyCode == 65) {
			// links drehen
			movement[1]++;
		}

		// d: 68
		else if (keyCode == 68) {
			// rechts drehen
			movement[1]--;
		}

		// q: 81
		else if (keyCode == 81) {
			// links gehen
			movement[2]++;
		}

		// e: 69
		else if (keyCode == 69) {
			// rechts gehen
			movement[2]--;
		}

		System.out.print("Released: " + keyCode + ", movement: ");
		for (int i = 0; i < movement.length; i++) {
			System.out.print(movement[i] + ", ");
		}
		System.out.println();

		stopMove();

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				new Move(NAOQI_IP, NAOQI_PORT).walk(movement);
			}
		});
		t.start();

	}

	private void stopMove() {
		System.out.println("anhalten");
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				new Move(NAOQI_IP, NAOQI_PORT).stopMove();
				System.out.println("fertig: stopMove");
			}
		});
		t.start();

		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("nach stop-join");
	}

}
