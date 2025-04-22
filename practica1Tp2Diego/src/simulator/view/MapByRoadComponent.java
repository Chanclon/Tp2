package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;
import simulator.model.Weather;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver {

	private static final Color _BG_COLOR = Color.WHITE;
	private static final Color _ROAD_COLOR = Color.BLACK;
	private static final int _JRADIUS = 10;
	private static final Color _JUNCTION_COLOR = Color.BLUE;
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;

	private RoadMap _map;
	private Image _car;
	private Map<String, Integer> _posYRoad;// Id de la carretera y su posicion Y
	private int x1;
	private int x2;// Se inicializa en drawRoad

	public MapByRoadComponent(Controller _ctrl) {
		setPreferredSize(new Dimension(300, 200));
		x1 = 50;
		_posYRoad = new HashMap<>();
		initGUI();
		_ctrl.addObserver(this);
	}

	private void initGUI() {
		_car = loadImage("car.png");
	}

	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// clear with a background color
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());

		if (_map == null || _map.getJunctions().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			updatePrefferedSize();
			drawMap(g);
		}
	}

	private void updatePrefferedSize() {
		int maxW = 200;
		int maxH = 200;
		for (Junction j : _map.getJunctions()) {
			maxW = Math.max(maxW, j.getX());
			maxH = Math.max(maxH, j.getY());
		}
		maxW += 20;
		maxH += 20;
		if (maxW > getWidth() || maxH > getHeight()) {
			setPreferredSize(new Dimension(maxW, maxH));
			setSize(new Dimension(maxW, maxH));
		}
	}

	private void drawRoad(Graphics g) {
		List<Road> _r = _map.getRoads();
		x2 = getWidth() - 100;
		// No se si es mejor hacer un for each porq el indice es necesario
		for (int i = 0; i < _r.size(); i++) {
			int y = (i + 1) * 50;
			_posYRoad.put(_r.get(i).getId(), y);
			g.setColor(_ROAD_COLOR);
			g.drawString(_r.get(i).getId(), x1 - 40, y);
			g.drawLine(x1, y, x2, y);
		}
	}

	private void drawJunction(Graphics g) {
		int y;
		int _green;
		for (Road r : _map.getRoads()) {
			Junction _ini = r.getSrc();
			Junction _dest = r.getDest();

			y = _posYRoad.get(r.getId());

			// Dibujar el cruce inicial
			g.setColor(_JUNCTION_COLOR);// Ponemos el color azul
			g.fillOval(x1 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			g.setColor(_ROAD_COLOR);// COLOR NEGRO
			g.drawString(_ini.getId(), x1, y - 6);

			// Dibujar el cruce final

			_green = _dest.getGreenLightIndex();

			if (_green != -1 && r.equals(_dest.getInRoads().get(_green)))
				g.setColor(_GREEN_LIGHT_COLOR);
			else
				g.setColor(_RED_LIGHT_COLOR);

			g.fillOval(x2 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			g.setColor(_ROAD_COLOR);// COLOR NEGRO
			g.drawString(_dest.getId(), x2, y - 6);

		}
	}

	private void drawVehicles(Graphics g) {
		int x = x2;
		int y;
		double A, B;
		for (Vehicle v : _map.getVehilces()) {

			if (v.getStatus() != VehicleStatus.ARRIVED) {
				y = _posYRoad.get(v.getRoad().getId());
				A = v.getLocation();
				B = v.getRoad().getLength();
				x = x1 + (int) ((x2 - x1) * ((double) A / (double) B));

				g.drawImage(_car, x, y - 10, 16, 16, this);
				g.drawString(v.getId(), x, y - 15);
			}
		}
	}

	private void drawWeatherAndCO2(Graphics g) {

		int x = x2 + 10;
		int y;
		int CO2Class;
		double A, B;
		Image _weatherI = null;
		Image _CO2 = null;
		Weather w;

		for (Road r : _map.getRoads()) {
			y = _posYRoad.get(r.getId());

			// Weather
			w = r.getWeather();
			switch (w) {
			case SUNNY:
				_weatherI = loadImage("sun.png");
				break;
			case CLOUDY:
				_weatherI = loadImage("cloud.png");
				break;
			case RAINY:
				_weatherI = loadImage("rain.png");
				break;
			case STORM:
				_weatherI = loadImage("storm.png");
				break;
			case WINDY:
				_weatherI = loadImage("wind.png");
				break;
			default:
				break;
			}

			g.drawImage(_weatherI, x, y - 15, 32, 32, this);

			// CO2
			A = r.getTotalCO2();
			B = r.getContLimit();

			CO2Class = (int) Math.floor(Math.min((double) A / (1.0 + (double) B), 1.0) / 0.19);

			switch (CO2Class) {
			case 0:
				_CO2 = loadImage("cont_0.png");
				break;
			case 1:
				_CO2 = loadImage("cont_1.png");
				break;
			case 2:
				_CO2 = loadImage("cont_2.png");
				break;
			case 3:
				_CO2 = loadImage("cont_3.png");
				break;
			case 4:
				_CO2 = loadImage("cont_4.png");
				break;
			case 5:
				_CO2 = loadImage("cont_5.png");
				break;
			default:
				break;
			}

			g.drawImage(_CO2, x + 40, y - 15, 32, 32, this);
		}
	}

	private void drawMap(Graphics g) {
		drawRoad(g);
		drawJunction(g);
		drawVehicles(g);
		drawWeatherAndCO2(g);
	}

	// loads an image from a file
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}

	public void update(RoadMap map) {
		SwingUtilities.invokeLater(() -> {
			_map = map;
			repaint();
		});
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		update(map);
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		update(map);

	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		update(map);
	}

}
