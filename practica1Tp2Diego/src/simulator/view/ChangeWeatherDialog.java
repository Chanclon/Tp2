package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Road;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class ChangeWeatherDialog extends JDialog {

	private List<Road> _road;
	private Controller _ctrl;
	private int _t;
	private Road _r;
	private Weather _weather;
	private int _time;
	private Pair _pair;

	private JSpinner _rJ;
	private JSpinner _weatherJ;
	private JSpinner _ticksJ;

	public ChangeWeatherDialog(JFrame f, List<Road> r, Controller c, int t) {
		super(f, "Change Road Weather", true);
		_road = r;
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

		formPanel.add(new JLabel("Roads: "));
		_rJ = new JSpinner(new SpinnerListModel(_road));
		_rJ.setPreferredSize(new Dimension(80, 20));
		formPanel.add(_rJ);

		formPanel.add(new JLabel("Weather:"));
		_weatherJ = new JSpinner(new SpinnerListModel(Weather.values()));
		_weatherJ.setPreferredSize(new Dimension(80, 20));
		formPanel.add(_weatherJ);

		formPanel.add(new JLabel("Ticks:"));
		_ticksJ = new JSpinner(new SpinnerNumberModel(10, 0, 100, 1));
		_ticksJ.setPreferredSize(new Dimension(80, 20));
		formPanel.add(_ticksJ);

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
		List<Pair<String, Weather>> _wr = new ArrayList<>();
		_r = (Road) _rJ.getValue();
		_weather = (Weather) _weatherJ.getValue();
		_t = (Integer) _ticksJ.getValue();

		String _rId = _r.getId();

		_pair = new Pair(_rId, _weather);
		_wr.add(_pair);

		_t += _time;// La suma del tiempo actual + el n de ticks que hay que esperar

		_ctrl.addEvent(new SetWeatherEvent(_t, _wr));
	}

}
