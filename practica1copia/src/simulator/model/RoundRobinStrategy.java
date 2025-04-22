package simulator.model;

import java.util.List;

public class RoundRobinStrategy implements LightSwitchingStrategy {

	private int ticks;

	public RoundRobinStrategy(int timeSlot) {
		this.ticks = timeSlot;
	}

	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		if (roads.isEmpty() || qs.isEmpty())
			return -1;

		if (currGreen == -1)
			return 0;

		if (currTime - lastSwitchingTime < ticks)
			return currGreen;

		// La proxima carretera tendra el semaforo verde, de forma circular
		int ret;
		if (currGreen >= qs.size() - 1)
			ret = 0;
		else
			ret = currGreen + 1;
		return ret;

	}
}
