package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver {

	private List<Event> _events;
	private String[] _colNames = { "Time", "Desc." };

	public EventsTableModel(Controller _ctrl) {
		_events = new ArrayList<>();
		_ctrl.addObserver(this);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	// si no pongo esto no coge el nombre de las columnas
	//
	// this is for the column header
	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}

	@Override
	// método obligatorio, probad a quitarlo, no compila
	//
	// this is for the number of columns
	public int getColumnCount() {
		return _colNames.length;
	}

	@Override
	// método obligatorio
	//
	// the number of row, like those in the events list
	public int getRowCount() {
		return _events == null ? 0 : _events.size();
	}

	@Override
	// método obligatorio
	// así es como se va a cargar la tabla desde el ArrayList
	// el índice del arrayList es el número de fila pq en este ejemplo
	// quiero enumerarlos.
	//
	// returns the value of a particular cell
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		Event event = _events.get(rowIndex);
		switch (columnIndex) {
		case 0:
			s = event.getTime();
			break;
		case 1:
			s = event.toString();
			break;
		default:
			return null;
		}
		return s;
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		_events = new ArrayList<>(events);
		fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		_events = new ArrayList<>(events);
		fireTableDataChanged();
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		_events = new ArrayList<>(events);
		fireTableDataChanged();
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		_events = new ArrayList<>(events);
		fireTableDataChanged();
	}

}
