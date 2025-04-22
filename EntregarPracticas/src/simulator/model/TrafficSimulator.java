package simulator.model;

import java.util.PriorityQueue;
import java.util.Queue;

import org.json.JSONObject;

public class TrafficSimulator {

	RoadMap _roadMap;
	Queue<Event> _events;
	int _time;

	public TrafficSimulator() {
		_roadMap = new RoadMap();
		_events = new PriorityQueue<>();
		_time = 0;
	}

	public void addEvent(Event e) {
		if (e.getTime() <= this._time)
			throw new IllegalArgumentException("No se pueden añadir eventos al pasado");
		_events.add(e);
	}

	public void advance() {
		_time++;
		// Peek mira el elemento con mas prioridad de la cola, comprueba si tiene
		// el mismo tiempo que el que pasamos por parametro y si es asi se elimina con
		// el poll
		while (_events.peek() != null && _events.peek().getTime() == _time) {
			_events.poll().execute(_roadMap);
		}
	    for (Junction j : _roadMap.getJunctions()) {
	        j.advance(_time);
	    }
	    for (Road r : _roadMap.getRoads()){
	        r.advance(_time);
	    }
	}

	public void reset() {
		_roadMap.reset();
		_events.clear();
		_time = 0;
	}

	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put("time", _time);
		jo.put("state", _roadMap.report());
		return jo;
	}
}
