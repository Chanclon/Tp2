package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver {

	private List<Road> _roads;
	private static String[] _colNames = { "Id", "Length", "Weather", "Max.Speed", "Speed Limit", "Total CO2",
			"CO2 Limit" };

	public RoadsTableModel(Controller _ctrl) {
		_roads = new ArrayList<>();
		_ctrl.addObserver(this);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}

	@Override
	public int getColumnCount() {
		return _colNames.length;
	}

	@Override
	public int getRowCount() {
		return _roads == null ? 0 : _roads.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		Road road = _roads.get(rowIndex);
		switch (columnIndex) {
		case 0:
			s = road.getId();
			break;
		case 1:
			s = road.getLength();
			break;
		case 2:
			s = road.getWeather();
			break;
		case 3:
			s = road.getMaxSpeed();
			break;
		case 4:
			s = road.getSpeedLimit();
			break;
		case 5:
			s = road.getTotalCO2();
			break;
		case 6:
			s = road.getContLimit();
			break;
		default:
			return null;
		}
		return s;
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		_roads = new ArrayList<>(map.getRoads());
		fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		_roads = new ArrayList<>(map.getRoads());
		fireTableDataChanged();
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		_roads = new ArrayList<>(map.getRoads());
		fireTableDataChanged();
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		_roads = new ArrayList<>(map.getRoads());
		fireTableDataChanged();
	}

}
