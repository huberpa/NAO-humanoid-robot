package de.nao.application;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainNao {

	public static void main(String[] args) {
		createStartGui();
	}

	public static void createStartGui() {

		final JFrame startFrame = new JFrame("Port und IP des Nao-Roboters");
		final JPanel panel = new JPanel(new GridLayout(3, 2));
		final JLabel ipLabel = new JLabel("IP:");
		final JTextField ipField = new JTextField("192.168.100.6");
		final JLabel portLabel = new JLabel("Port:");
		final JTextField portField = new JTextField("9559");
		final JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				final String ip = ipField.getText();
				final int port;
				try {
					port = Integer.parseInt(portField.getText());

					ipField.setEnabled(false);
					portField.setEnabled(false);
					okButton.setEnabled(false);
					okButton.setText("Bitter warten");
					new Thread(new Runnable() {

						@Override
						public void run() {
							new Gui(ip, port);
							startFrame.dispose();
						}
					}).start();
				} catch (Exception exc) {
					startFrame.dispose();
					createStartGui();
				}
			}
		});
		panel.add(ipLabel);
		panel.add(ipField);
		panel.add(portLabel);
		panel.add(portField);
		panel.add(okButton);
		startFrame.add(panel);
		startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startFrame.setBounds(100, 200, 400, 150);
		startFrame.setVisible(true);
	}
}
