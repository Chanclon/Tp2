package simulator.model;

import java.util.Collections;
import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy {

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> r = Collections.unmodifiableList(q);
		return r;
	}
}
