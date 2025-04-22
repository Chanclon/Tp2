package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event {

	int time;
	List<Pair<String, Weather>> ws;

	public SetWeatherEvent(int time, List<Pair<String, Weather>> ws) {
		super(time);
		this.time = time;
		this.ws = ws;
	}

	@Override
	void execute(RoadMap map) {
		if (ws == null)
			throw new IllegalArgumentException("El parametro no puede ser nulo");
		for (int i = 0; i < ws.size(); i++) {
			String id = ws.get(i).getFirst();
			Weather w = ws.get(i).getSecond();
			if (map.getRoad(id) == null)
				throw new IllegalArgumentException("La carretera no existe en el mapa de carreteras");
			map.getRoad(id).setWeather(w);
		}
	}
}
