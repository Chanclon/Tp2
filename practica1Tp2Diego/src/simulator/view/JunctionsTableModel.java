package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {

	private List<Junction> _junctions;
	private static String[] _colNames = { "Id", "Green", "Queues" };

	public JunctionsTableModel(Controller _ctrl) {
		_junctions = new ArrayList<>();
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
		return _junctions == null ? 0 : _junctions.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		Junction junction = _junctions.get(rowIndex);
		switch (columnIndex) {
		case 0:
			s = junction.getId();
			break;
		case 1:
			if (junction.getGreenLightIndex() == -1)
				s = "NONE";
			else
				s = junction.getInRoads().get(junction.getGreenLightIndex());
			break;
		case 2:
			String _roads = junction.getQueue().toString();
			s = _roads.replaceAll("[{},]", "");
			;
			break;
		default:
			return null;
		}
		return s;
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		_junctions = new ArrayList<>(map.getJunctions());
		fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		_junctions = new ArrayList<>(map.getJunctions());
		fireTableDataChanged();
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		_junctions = new ArrayList<>(map.getJunctions());
		fireTableDataChanged();
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		_junctions = new ArrayList<>(map.getJunctions());
		fireTableDataChanged();
	}

}
