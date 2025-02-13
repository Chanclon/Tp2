package simulator.model;

import java.util.Comparator;

public class VehicleComparator implements Comparator<Vehicle>{

	@Override
	public int compare(Vehicle o1, Vehicle o2) {
		return Integer.compare(o1.getLoc(), o2.getLoc());
	}
	
}
