package simulator.model;

import java.util.Comparator;

public class VehicleComparator implements Comparator<Vehicle> {

	@Override
	public int compare(Vehicle o1, Vehicle o2) {
		// Como hay que ordenar de manera inversa ponemos un - antes de la funcion
		return -Integer.compare(o1.getLocation(), o2.getLocation());
	}

}
