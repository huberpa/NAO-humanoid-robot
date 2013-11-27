package de.nao.application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gui implements KeyListener {

	private final String NAOQI_IP;
	private final int NAOQI_PORT;
	final JFrame frame;
	final JPanel panel;

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

		new Thread(new Video(panel, NAOQI_IP, NAOQI_PORT)).start();

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
							new Move(NAOQI_IP, NAOQI_PORT).laufe();
						}
					});
					t.start();

				} else {
					laufenButton.setText("Laufen");
					System.out.println("anhalten");
					Thread t = new Thread(new Runnable() {

						@Override
						public void run() {
							new Move(NAOQI_IP, NAOQI_PORT).halteAn();
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
		System.out.println("Pressed: " + e.getKeyChar());

	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("Released: " + e.getKeyCode());

	}
}
