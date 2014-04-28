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
import javax.swing.JCheckBox;
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
	private MoveHead moveHead;
	private final JTextArea textArea;
	private JComboBox<String> historyBox;
	// private LinkedList<String> historyList;
	private DefaultComboBoxModel<String> historyModel;
	private final Color buttonColor;
	private final JCheckBox keysActivated;
	private ShowImage showImage = null;
	private final JFrame errorFrame;
	private JLabel errorLabel;
	private boolean error;

	// private Move move;

	public Gui(final String NAOQI_IP, final int NAOQI_PORT) {
		this.NAOQI_IP = NAOQI_IP;
		this.NAOQI_PORT = NAOQI_PORT;

		errorLabel = new JLabel();
		errorFrame = new JFrame();
		errorFrame.add(errorLabel);
		errorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		error = false;

		buttonColor = Color.gray;
		try {
			tts = new TextToSpeech(NAOQI_IP, NAOQI_PORT);
			moveHead = new MoveHead(NAOQI_IP, NAOQI_PORT);

		} catch (Exception e) {
			e.printStackTrace();
			error = true;
			errorLabel
					.setText("Verbindungsfehler,  bitte mit Router verbinden und IP prüfen");
			errorFrame.pack();
			errorFrame.setVisible(true);
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

		JMenuItem videoStarten = new JMenuItem("Video starten");
		videoStarten.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						if (null == showImage) {
							showImage = new ShowImage(NAOQI_IP, NAOQI_PORT);
						}
					}
				});
				t.start();

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
		dateiMenu.add(videoStarten);
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

		final JButton qButton = new JButton("Links");
		qButton.setBackground(buttonColor);
		qButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (qButton.getBackground() == buttonColor) {
					qButton.setBackground(Color.GREEN);
					movement[2]--;
					move();
				} else {
					qButton.setBackground(buttonColor);
					movement[2]++;
					stopMove();
				}
				textArea.requestFocus();
			}
		});

		final JButton wButton = new JButton("Vor");
		wButton.setBackground(buttonColor);
		wButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (wButton.getBackground() == buttonColor) {
					wButton.setBackground(Color.GREEN);
					movement[0]++;
					move();
				} else {
					wButton.setBackground(buttonColor);
					movement[0]--;
					stopMove();
				}
				textArea.requestFocus();
			}
		});

		final JButton eButton = new JButton("Rechts");
		eButton.setBackground(buttonColor);
		eButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (eButton.getBackground() == buttonColor) {
					eButton.setBackground(Color.GREEN);
					movement[2]++;
					move();
				} else {
					eButton.setBackground(buttonColor);
					movement[2]--;
					stopMove();
				}
				textArea.requestFocus();
			}
		});

		final JButton aButton = new JButton("Links drehen");
		aButton.setBackground(buttonColor);
		aButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (aButton.getBackground() == buttonColor) {
					aButton.setBackground(Color.GREEN);
					movement[1]--;
					move();
				} else {
					aButton.setBackground(buttonColor);
					movement[1]++;
					stopMove();
				}
				textArea.requestFocus();
			}
		});

		final JButton sButton = new JButton("Zurück");
		sButton.setBackground(buttonColor);
		sButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (sButton.getBackground() == buttonColor) {
					sButton.setBackground(Color.GREEN);
					movement[0]--;
					move();
				} else {
					sButton.setBackground(buttonColor);
					movement[0]++;
					stopMove();
				}
				textArea.requestFocus();
			}
		});

		final JButton dButton = new JButton("Rechts drehen");
		dButton.setBackground(buttonColor);
		dButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (dButton.getBackground() == buttonColor) {
					dButton.setBackground(Color.GREEN);
					movement[1]++;
					move();
				} else {
					dButton.setBackground(buttonColor);
					movement[1]--;
					stopMove();
				}
				textArea.requestFocus();
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

		JButton upButton = new JButton("Kopf hoch");
		upButton.setBackground(buttonColor);
		upButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						moveHead.up();
					}
				});
				t.start();
			}
		});

		JButton leftButton = new JButton("Kopf links");
		leftButton.setBackground(buttonColor);
		leftButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						moveHead.left();
					}
				});
				t.start();

			}
		});

		JButton downButton = new JButton("Kopf runter");
		downButton.setBackground(buttonColor);
		downButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						moveHead.down();
					}
				});
				t.start();
			}
		});

		JButton rightButton = new JButton("Kopf rechts");
		rightButton.setBackground(buttonColor);
		rightButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						moveHead.right();
					}
				});
				t.start();

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

		final JButton okButton = new JButton("OK");
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

		keysActivated = new JCheckBox("Tastatursteuerung");
		keysActivated.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (keysActivated.isSelected()) {
					panel.requestFocus();
					textArea.setEnabled(false);
					okButton.setEnabled(false);
				} else {
					textArea.setEnabled(true);
					okButton.setEnabled(true);
					textArea.requestFocus();
				}
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

		x = x + 4;
		gbc.gridx = x;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		panel.add(keysActivated, gbc);

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
		// panel.requestFocus();
		panel.addKeyListener(this);

		frame.add(menuBar, BorderLayout.NORTH);
		frame.add(panel, BorderLayout.CENTER);

		// frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.pack();
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (!error) {
			frame.setVisible(true);
		}
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

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (keysActivated.isSelected()) {
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

				move();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (keysActivated.isSelected()) {
			int keyCode = e.getKeyCode();
			if (pressedKeys.contains(keyCode)) {
				stopMove();

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
			}
		}
	}

	private void move() {

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
