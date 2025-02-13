package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy{

	
	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		//if(q.size() == 0) return null; //No se si hace falta esto
		List<Vehicle> r = new ArrayList<>();
		r.add(q.getFirst());
		return r;
	}

}
