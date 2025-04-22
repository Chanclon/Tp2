package simulator.view;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CloseDialog extends JDialog {

	public CloseDialog() {
		initGUI();
		pack();
		setLocation(650, 350);
	}

	private void initGUI() {
		this.setLayout(new BorderLayout());

		// Mensaje superior
		JLabel _text = new JLabel("Are sure you want to quit?");
		_text.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.add(_text, BorderLayout.PAGE_START);

		// Botones
		JPanel _bPanel = new JPanel();
		JButton _noB = new JButton("No");
		_noB.addActionListener((e) -> dispose());
		JButton _siB = new JButton("Yes");
		_siB.addActionListener((e) -> System.exit(0));

		_bPanel.add(_noB);
		_bPanel.add(_siB);

		this.add(_bPanel, BorderLayout.CENTER);

	}

}
