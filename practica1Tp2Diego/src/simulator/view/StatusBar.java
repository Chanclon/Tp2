package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Collection;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {

	private int _time;
	private String _eId;
	private JLabel _t;
	private JLabel _e;

	public StatusBar(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() {
		setLayout(new BorderLayout());
		JToolBar _toolBar = new JToolBar();
		add(_toolBar, BorderLayout.PAGE_END);

		// Time
		_t = new JLabel("Time: " + _time);
		_toolBar.add(_t);

		_toolBar.addSeparator(new Dimension(200, 0));

		// Event
		_e = new JLabel("Welcome!");
		_toolBar.add(_e);
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		_time = time;
		_t.setText("Time: " + Integer.toString(_time));
		_e.setVisible(false);
		;
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		_eId = e.toString();
		_e.setVisible(true);
		_e.setText("Event added: " + _eId);
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		_time = time;
		_t.setText("Time: " + Integer.toString(_time));
		_e.setVisible(true);
		_e.setText("Welcome!");
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		_time = time;
		_e.setVisible(true);
		_e.setText("Welcome!");
	}

}
