package simulator.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class RoadMap {
	
	private List<Junction> Junction;
	private List<Road> road;
	private List<Vehicle> vehicle;
	private Map<String,Junction> junctionMap;
	private Map<String,Road> roadMap;
	private Map<String,Vehicle> vehicleMap;
	
	protected RoadMap(List<Junction> Junction, List<Road> road, List<Vehicle> vehicle, Map<String,Junction> junctionMap, 
			Map<String,Road> roadMap, Map<String,Vehicle> vehicleMap) {
		this.Junction = Junction;
		this.road = road;
		this.vehicle = vehicle;
		this.junctionMap = junctionMap;
		this.roadMap = roadMap;
		this.vehicleMap = vehicleMap;
	}
	
	//Getters: /////////////////////////
	
	public Junction getJunction(String id) {
		return junctionMap.get(id);
	}	

	public Road getRoad(String id) {
		return roadMap.get(id);
	}

	public Vehicle getVehicle(String id) {
		return vehicleMap.get(id);
	}

	public List<Junction> getJunctions(){
		return Collections.unmodifiableList(Junction);
	}

	public List<Road> getRoads(){
		return Collections.unmodifiableList(road);
	}

	public List<Vehicle> getVehicles(){
		return Collections.unmodifiableList(vehicle);
	}
	
	////////////////////////////////////
	
	public void addJunction(Junction j) {
		Junction.add(j);
		if(!junctionMap.containsKey(j.getId())) junctionMap.put(j.getId(), j);
	}
	
	public void addRoad(Road r) {
		road.add(r);
		if(!roadMap.containsKey(r.getId())) roadMap.put(r.getId(), r);
	}

	public void addVehicle(Vehicle v) {
		vehicle.add(v);
		if(!vehicleMap.containsKey(v.getId())) vehicleMap.put(v.getId(), v);
	}
	
	void reset() {
		Junction.clear();
		road.clear();
		vehicle.clear();
		junctionMap.clear();
		roadMap.clear();
		vehicleMap.clear();
	}
	
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		
		
		return jo;
	}
}
