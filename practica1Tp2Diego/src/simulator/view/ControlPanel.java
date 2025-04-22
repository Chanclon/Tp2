package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

import java.io.InputStream;

public class ControlPanel extends JPanel implements TrafficSimObserver {

	private boolean _stopped;
	private boolean _fileLoaded;
	private Controller c;
	private RoadMap _map;
	private int _time;

	JButton _loadB;
	JButton _CO2B;
	JButton _weatherB;
	JButton _executeB;
	JButton _stopB;
	JButton _exitB;

	public ControlPanel(Controller c) {
		this.c = c;
		_fileLoaded = false;
		_time = 0;
		initGUI();
		c.addObserver(this);
	}

	public void initGUI() {
		setLayout(new BorderLayout());
		JToolBar _toolBar = new JToolBar();
		add(_toolBar, BorderLayout.PAGE_START);

		// Load Button
		JFileChooser _jfc = new JFileChooser();
		_jfc.setCurrentDirectory(new File("resources/resources/examples"));
		_loadB = new JButton();
		_loadB.setToolTipText("Load bodies file into the editor");
		_loadB.setIcon(new ImageIcon("resources/icons/open.png"));
		_loadB.addActionListener((e) -> loadFile());
		_toolBar.add(_loadB);

		_toolBar.addSeparator();

		// CO2 Button

		_CO2B = new JButton();
		_CO2B.setToolTipText("Change CO2 Class of a Vehicle");
		_CO2B.setIcon(new ImageIcon("resources/icons/co2class.png"));
		_CO2B.addActionListener((e) -> changeCO2Class());
		_toolBar.add(_CO2B);

		// Weather Button
		_weatherB = new JButton();
		_weatherB.setToolTipText("Change the weather of a Road");
		_weatherB.setIcon(new ImageIcon("resources/icons/weather.png"));
		_weatherB.addActionListener((e) -> changeWeather());
		_toolBar.add(_weatherB);

		_toolBar.addSeparator();

		// Ticks Button
		JLabel _ticksL = new JLabel("Ticks:");
		JSpinner _ticksS = new JSpinner(new SpinnerNumberModel(10, 0, 100, 1));
		_ticksS.setMaximumSize(new Dimension(1000, 40));

		// Execute Button
		_executeB = new JButton();
		_executeB.setToolTipText("Execute the simulator");
		_executeB.setIcon(new ImageIcon("resources/icons/run.png"));
		_executeB.addActionListener((e) -> {
			_stopped = false;
			disableB();
			run_sim((Integer) _ticksS.getValue());
		});
		_toolBar.add(_executeB);

		// Stop Button
		_stopB = new JButton();
		_stopB.setToolTipText("Stop the simulator");
		_stopB.setIcon(new ImageIcon("resources/icons/stop.png"));
		_stopB.addActionListener((e) -> stop());
		_toolBar.add(_stopB);

		_toolBar.add(_ticksL);
		_toolBar.add(Box.createRigidArea(new Dimension(5, 0)));
		_toolBar.add(_ticksS);

		// Exit Button
		_exitB = new JButton();
		_exitB.setToolTipText("Close the simulator");
		_exitB.setIcon(new ImageIcon("resources/icons/exit.png"));
		_exitB.addActionListener((e) -> close());
		_toolBar.add(Box.createHorizontalGlue());
		_toolBar.add(_exitB).isBackgroundSet();
	}

	// Puede que tenga que cambiar estas funciones
	// ////////////////////////////////////////////////

	private String loadFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("resources/resources/examples"));

		int result = fileChooser.showOpenDialog(null); // null = ventana centrada

		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				_fileLoaded = true;
				InputStream in = new FileInputStream(fileChooser.getSelectedFile());
				c.reset();
				c.loadEvents(in);

			} catch (Exception e) {
			}
		}

		return null;
	}

	private void changeCO2Class() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		ChangeCO2ClassDialog dialog = new ChangeCO2ClassDialog(frame, _map.getVehilces(), c, _time);
		dialog.setVisible(true);

	}

	private void changeWeather() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		ChangeWeatherDialog dialog = new ChangeWeatherDialog(frame, _map.getRoads(), c, _time);
		dialog.setVisible(true);
	}

	private void close() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		CloseDialog dialog = new CloseDialog();
		dialog.setVisible(true);
	}

	private void run_sim(int n) {

		if (n > 0 && !_stopped && _fileLoaded) {
			try {
				// Thread.sleep(100);

				c.run(1);

				// Esto se hace para que cuando le das al boton de stop le de tiempo a detener
				// la ejecucion, ya que si no pones los dos if no da tiempo y se ejecuta aunq no
				// se
				// cumpla la condicion
				if (!_stopped)
					SwingUtilities.invokeLater(() -> {
						if (!_stopped)
							run_sim(n - 1);
					});

			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Error during simulation: " + e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
				enableB();
			}
		} else {
			enableB();
		}
	}

	private void stop() {
		_stopped = true;
		enableB();
	}

	private void disableB() {
		_loadB.setEnabled(false);
		_CO2B.setEnabled(false);
		_weatherB.setEnabled(false);
		_executeB.setEnabled(false);
		_exitB.setEnabled(false);
	}

	private void enableB() {
		_stopped = true;
		_loadB.setEnabled(true);
		_CO2B.setEnabled(true);
		_weatherB.setEnabled(true);
		_executeB.setEnabled(true);
		_exitB.setEnabled(true);
	}
	//////////////////////////////////////////////////////////////////

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		_map = map;
		_time = time;
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {

	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		_map = map;
		_time = time;

	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		_map = map;
		_time = time;
	}

}
