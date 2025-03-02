package simulator.model;

public abstract class Event implements Comparable<Event> {

	private static long _counter = 0;
	protected int _time;
	protected long _time_stamp;

	Event(int time) {
		if (time < 1)
			throw new IllegalArgumentException("Time must be positive (" + time + ")");
		else {
			_time = time;
			_time_stamp = _counter++;
		}
	}

	int getTime() {
		return _time;
	}

	@Override
	public int compareTo(Event o) {
		// TODO complete the method to compare events according to their _time, and when
// _time is equal it compares the _time_stamp;
		int i = -1;
		if (this._time > o._time)
			i = 1;
		else if (this._time == o._time) {
			if (this._time_stamp > o._time_stamp)
				i = 1;
		}
		return i;
	}

	abstract void execute(RoadMap map);
}