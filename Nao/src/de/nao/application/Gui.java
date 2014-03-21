package de.nao.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Gui implements KeyListener {

	private final String NAOQI_IP;
	private final int NAOQI_PORT;
	final JFrame frame;
	final JPanel panel;
	private LinkedList<Integer> pressedKeys = new LinkedList<Integer>();
	// (vor/zurück), (links/rechts)-Drehung, (links/rechts)-Bewegung
	private int[] movement = new int[] { 0, 0, 0 };
	private TextToSpeech tts;
	private final JTextArea textArea;
	private JComboBox<String> historyBox;
	private LinkedList<String> historyList;
	private DefaultComboBoxModel<String> historyModel;

	public Gui(String NAOQI_IP, int NAOQI_PORT) {
		this.NAOQI_IP = NAOQI_IP;
		this.NAOQI_PORT = NAOQI_PORT;

		try {
			tts = new TextToSpeech(NAOQI_IP, NAOQI_PORT);
		} catch (Exception e) {
			System.out
					.println("Verbindungsfehler, bitte mit Router verbinden und IP prüfen");
		}

		frame = new JFrame("Nao Steuerung");
		panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		panel.setBackground(Color.gray);

		// Menüleiste erzeugen
		JMenuBar menuBar = new JMenuBar();

		// Untermenü
		JMenu dateiMenu = new JMenu("Datei");
		JMenu hilfeMenu = new JMenu("Hilfe");

		JMenuItem dateiLaden = new JMenuItem("Datei laden");
		dateiLaden.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});

		JMenuItem ende = new JMenuItem("Programm beenden");
		ende.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		JMenuItem hilfeItem = new JMenuItem("Dokumentation öffnen");
		hilfeItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});

		// Zusammenfügen
		dateiMenu.add(dateiLaden);
		dateiMenu.add(ende);
		hilfeMenu.add(hilfeItem);
		menuBar.add(dateiMenu);
		menuBar.add(hilfeMenu);

		/*
		 * Move Panel erzeugen
		 */
		JPanel controlMovePanel = new JPanel(new GridLayout(0, 3, 10, 10));
		controlMovePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,
				10));
		controlMovePanel.setBackground(Color.lightGray);

		JButton qButton = new JButton("Q");
		qButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		JButton wButton = new JButton("W");
		wButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		JButton eButton = new JButton("E");
		eButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		JButton aButton = new JButton("A");
		aButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		JButton sButton = new JButton("S");
		sButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		JButton dButton = new JButton("D");
		dButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		controlMovePanel.add(qButton);
		controlMovePanel.add(wButton);
		controlMovePanel.add(eButton);
		controlMovePanel.add(aButton);
		controlMovePanel.add(sButton);
		controlMovePanel.add(dButton);

		/*
		 * Steuerung für Kopf erzeugen
		 */

		JPanel controlHeadPanel = new JPanel(new GridLayout(0, 3, 10, 10));
		controlHeadPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,
				10));
		controlHeadPanel.setBackground(Color.lightGray);

		JButton upButton = new JButton("Up");
		upButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		JButton leftButton = new JButton("Left");
		leftButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		JButton downButton = new JButton("Down");
		downButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		JButton rightButton = new JButton("Right");
		rightButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		controlHeadPanel.add(new JLabel());
		controlHeadPanel.add(upButton);
		controlHeadPanel.add(new JLabel());
		controlHeadPanel.add(leftButton);
		controlHeadPanel.add(downButton);
		controlHeadPanel.add(rightButton);

		// Text-to-Speech
		textArea = new JTextArea();
		textArea.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					textArea.setText(textArea.getText().substring(0,
							textArea.getText().length() - 1));
					textToSpeech();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		textArea.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(textArea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(200, 50));

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textToSpeech();
			}
		});

		historyModel = new DefaultComboBoxModel<String>();
		historyBox = new JComboBox<String>(historyModel);

		JButton fillInButton = new JButton("Übertragen");
		fillInButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.setText((String) historyBox.getSelectedItem());
			}
		});

		/*
		 * Alles zusammenfügen
		 */

		// 1. Zeile
		int x = 0;
		int y = 0;
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = 4;
		gbc.gridheight = 1;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.BOTH;
		panel.add(controlMovePanel, gbc);

		x = x + 4;
		gbc.gridx = x;
		gbc.gridwidth = 4;
		gbc.insets = new Insets(5, 5, 5, 5);
		panel.add(controlHeadPanel, gbc);

		// 2. Zeile
		x = 0;
		y++;
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = 8;
		gbc.insets = new Insets(5, 5, 5, 5);
		panel.add(scrollPane, gbc);

		x = x + 8;
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		panel.add(okButton, gbc);

		// 3. Zeile
		x = 0;
		y++;
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = 8;
		gbc.insets = new Insets(5, 5, 5, 5);
		panel.add(historyBox, gbc);

		x = x + 8;
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		panel.add(fillInButton, gbc);

		// panel.add(controlMovePanel);
		// panel.add(controlHeadPanel);
		// panel.add(textField, BorderLayout.SOUTH);

		panel.setFocusable(true);
		panel.requestFocus();
		panel.addKeyListener(this);

		frame.add(menuBar, BorderLayout.NORTH);
		frame.add(panel, BorderLayout.CENTER);

		// frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.pack();
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// new Thread(new Video(panel, NAOQI_IP, NAOQI_PORT)).start();

		// initialisiereLaufen();
	}

	private void textToSpeech() {
		if (!textArea.getText().equals("")) {
			tts.say(textArea.getText());

			LinkedList<String> l = new LinkedList<>();
			for (int i = historyModel.getSize() - 1; i >= 0; i--) {
				System.out.println(historyModel.getElementAt(i));
				l.add(historyModel.getElementAt(i));
			}

			historyModel.removeAllElements();
			historyModel.addElement(textArea.getText());
			for (int i = 0; i < l.size(); i++) {
				historyModel.addElement(l.get(i));
			}

			textArea.setText("");
			panel.requestFocus();
		}

	}

	// private void initialisiereLaufen() {
	// final JButton laufenButton = new JButton("Laufen");
	// laufenButton.addActionListener(new ActionListener() {
	// @Override
	// public void actionPerformed(ActionEvent e) {
	// if (laufenButton.getText().equals("Laufen")) {
	// laufenButton.setText("Anhalten");
	// System.out.println("starte laufen");
	// Thread t = new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	// movement[0] = 1;
	// new Move(NAOQI_IP, NAOQI_PORT).walk(movement);
	// }
	// });
	// t.start();
	//
	// } else {
	// laufenButton.setText("Laufen");
	// System.out.println("anhalten");
	// Thread t = new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	// movement[0] = 0;
	// new Move(NAOQI_IP, NAOQI_PORT).stopMove();
	// }
	// });
	// t.start();
	//
	// }
	// }
	// });
	// panel.add(laufenButton);
	//
	// }

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
