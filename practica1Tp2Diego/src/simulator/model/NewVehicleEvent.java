package simulator.model;

import java.util.LinkedList;
import java.util.List;

public class NewVehicleEvent extends Event{
	int time;
	String id;
	int maxSpeed;
	int contClass;
	List<String> itinerary;
	
	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
		  super(time);
		  this.time = time;
		  this.id = id;
		  this.maxSpeed = maxSpeed;
		  this.contClass = contClass;
		  this.itinerary = itinerary;
	}

	@Override
	void execute(RoadMap map) {
		List<Junction> i = new LinkedList<>();
		for(int j = 0; j < itinerary.size(); j++)
		{
			//Pone que junctionMap esta vacio asiq da excepcion
			i.add(map.getJunction(itinerary.get(j)));
		}
		Vehicle v = new Vehicle(id, maxSpeed, contClass, i);
		map.addVehicle(v);
		v.moveToNextRoad();
	}
}
