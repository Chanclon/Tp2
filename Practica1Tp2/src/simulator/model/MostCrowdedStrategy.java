package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {

	private int ticks;

	protected MostCrowdedStrategy(int timeSlot) {
		this.ticks = timeSlot;
	}

	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		if (qs.size() == 0)
			return -1;
		if (currGreen == -1) {
			int max = -1;
			int ret = 0;
			for (int i = 0; i < qs.size(); i++) {
				if (qs.get(i).size() > max) {
					ret = i;
					max = qs.get(i).size();
				}
			}
			return ret;
		}
		if (currTime - lastSwitchingTime < ticks)
			return currGreen;
		
		int i = currGreen+1;
		int max = qs.get(currGreen).size();
		int ret = currGreen;
		while( i != currGreen)
		{
			if(qs.get(i).size() > max) {
				max = qs.get(i).size() ;
				ret = i;
			}
			i++;
			if(i == qs.size()) i = 0; 
		}
		return ret;
	}

}
