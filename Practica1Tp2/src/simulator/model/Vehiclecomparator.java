package simulator.model;

import java.util.Comparator;

public class Vehiclecomparator implements Comparator<Vehicle> {

	@Override
	public int compare(Vehicle o1, Vehicle o2) {
		return Integer.compare(o1.getPos(), o2.getPos());
	}

}
