package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.w3c.dom.views.AbstractView;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver {

	private List<Vehicle> _vehicles;
	private static String[] _colNames = { "Id", "Location", "Itinerary", "CO2 Class", "Max.Speed", "Speed", "Total CO2",
			"Distance" };

	public VehiclesTableModel(Controller _ctrl) {
		_vehicles = new ArrayList<>();
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
		return _vehicles == null ? 0 : _vehicles.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		Vehicle vehicle = _vehicles.get(rowIndex);
		switch (columnIndex) {
		case 0:
			s = vehicle.getId();
			break;
		case 1:
			if (vehicle.getStatus() == VehicleStatus.PENDING)
				s = "Pending";
			else if (vehicle.getStatus() == VehicleStatus.TRAVELING)
				s = vehicle.getRoad() + ":" + vehicle.getLocation();
			else if (vehicle.getStatus() == VehicleStatus.WAITING)
				s = "Waiting:" + vehicle.getRoad().getDest();
			else if (vehicle.getStatus() == VehicleStatus.ARRIVED)
				s = "Arrived";
			break;
		case 2:
			s = vehicle.getItinerary();
			break;
		case 3:
			s = vehicle.getContClass();
			break;
		case 4:
			s = vehicle.getMaxSpeed();
			break;
		case 5:
			s = vehicle.getSpeed();
			break;
		case 6:
			s = vehicle.getTotalCO2();
			break;
		case 7:
			s = vehicle.getDistanciaT();
			break;
		default:
			return null;
		}
		return s;
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		_vehicles = new ArrayList<>(map.getVehilces());
		fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		_vehicles = new ArrayList<>(map.getVehilces());
		fireTableDataChanged();
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		_vehicles = new ArrayList<>(map.getVehilces());
		fireTableDataChanged();
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		_vehicles = new ArrayList<>(map.getVehilces());
		fireTableDataChanged();
	}

}
