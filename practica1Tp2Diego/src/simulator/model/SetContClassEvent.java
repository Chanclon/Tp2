package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetContClassEvent extends Event {

	int time;
	List<Pair<String, Integer>> cs;

	public SetContClassEvent(int time, List<Pair<String, Integer>> cs) {
		super(time);
		this.time = time;
		this.cs = cs;
	}

	@Override
	void execute(RoadMap map) {
		if (cs == null)
			throw new IllegalArgumentException("El parametro no puede ser nulo");
		for (int i = 0; i < cs.size(); i++) {
			String id = cs.get(i).getFirst();
			Integer c = cs.get(i).getSecond();
			if (map.getVehicle(id) == null)
				throw new IllegalArgumentException("El vehiculo no existe en el mapa de vehiculos");
			map.getVehicle(id).setContClass(c);
		}
	}

	@Override
	public String toString() {
		return "New Contanimation Class '" + time + "'";
	}
}
