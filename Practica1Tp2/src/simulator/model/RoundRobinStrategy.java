package simulator.model;

import java.util.List;

public class RoundRobinStrategy implements LightSwitchingStrategy {

	private int ticks;

	protected RoundRobinStrategy(int timeSlot) {
		this.ticks = timeSlot;
	}

	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		if (roads.size() == 0)
			return -1;
		if (currGreen == -1)
			return 0;
		if (currTime - lastSwitchingTime < ticks)
			return currGreen;
		else
			return currGreen + 1;
	}
}
