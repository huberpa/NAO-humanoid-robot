import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gui {

	final JFrame frame;
	final JPanel panel;

	public Gui() {
		frame = new JFrame("Nao Steuerung");
		panel = new JPanel();

		frame.add(panel);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

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
							new Move().laufe();
						}
					});
					t.start();

				} else {
					laufenButton.setText("Laufen");
					System.out.println("anhalten");
					Thread t = new Thread(new Runnable() {

						@Override
						public void run() {
							new Move().halteAn();
						}
					});
					t.start();
					
				}
			}
		});
		panel.add(laufenButton);

	}
}
