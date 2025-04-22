package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.SetContClassEvent;
import simulator.model.Vehicle;

public class ChangeCO2ClassDialog extends JDialog {

	private List<Vehicle> _vehicles;
	private Controller _ctrl;
	private int _t;
	private Vehicle _v;
	private int _CO2;
	private int _time;
	private Pair _pair;

	private JSpinner _vJ;
	private JSpinner _CO2J;
	private JSpinner _ticksJ;

	public ChangeCO2ClassDialog(JFrame f, List<Vehicle> v, Controller c, int t) {
		super(f, "Change CO2 Class", true);
		_vehicles = v;
		_ctrl = c;
		_time = t;
		initGUI();
		pack(); // Ajusta el tamaño al contenido
		setLocation(500, 350);
	}

	private void initGUI() {
		setLayout(new BorderLayout());

		// Mensaje superior
		JLabel _text = new JLabel("Schedule an event to change the CO2 class of a vehicle after "
				+ "a given number of simulation ticks from now");
		_text.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.add(_text, BorderLayout.PAGE_START);

		// Panel central
		JPanel formPanel = new JPanel(new FlowLayout());

		formPanel.add(new JLabel("Vehicle: "));
		_vJ = new JSpinner(new SpinnerListModel(_vehicles));
		_vJ.setPreferredSize(new Dimension(80, 20));
		formPanel.add(_vJ/* , BorderLayout.WEST */);

		formPanel.add(new JLabel("CO2 Class:"));
		_CO2J = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
		_CO2J.setPreferredSize(new Dimension(80, 20));
		formPanel.add(_CO2J/* , BorderLayout.CENTER */);

		formPanel.add(new JLabel("Ticks:"));
		_ticksJ = new JSpinner(new SpinnerNumberModel(10, 0, 100, 1));
		_ticksJ.setPreferredSize(new Dimension(80, 20));
		formPanel.add(_ticksJ/* , BorderLayout.EAST */);

		this.add(formPanel, BorderLayout.CENTER);

		// Botones
		JPanel buttonPanel = new JPanel();
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener((e) -> dispose());
		JButton ok = new JButton("OK");
		ok.addActionListener((e) -> {
			ok();
			dispose();
		});
		buttonPanel.add(cancel);
		buttonPanel.add(ok);
		this.add(buttonPanel, BorderLayout.PAGE_END);
	}

	private void ok() {
		List<Pair<String, Integer>> _cv = new ArrayList<>();
		_v = (Vehicle) _vJ.getValue();
		_CO2 = (Integer) _CO2J.getValue();
		_t = (Integer) _ticksJ.getValue();

		String _vId = _v.getId();

		_pair = new Pair(_vId, _CO2);
		_cv.add(_pair);

		_t += _time;// La suma del tiempo actual + el n de ticks que hay que esperar

		_ctrl.addEvent(new SetContClassEvent(_t, _cv));
	}

}
