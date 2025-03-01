package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
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
	
	protected RoadMap() {
		this.Junction = new LinkedList<>();;
		this.road = new ArrayList<>();
		this.vehicle = new ArrayList<>();
		this.junctionMap = new HashMap<>();
		this.roadMap = new HashMap<>();;
		this.vehicleMap = new HashMap<>();;
	}
	
	//Getters: /////////////////////////
	
	public Junction getJunction(String id) {
		if(junctionMap.containsKey(id)) return junctionMap.get(id);
		return null;
	}	

	public Road getRoad(String id) {
		if(roadMap.containsKey(id)) return roadMap.get(id);
		return null;
	}

	public Vehicle getVehicle(String id) {
		if(vehicleMap.containsKey(id)) return vehicleMap.get(id);
		return null;
	}

	public List<Junction> getJunctions(){
		return Collections.unmodifiableList(Junction);
	}

	public List<Road> getRoads(){
		return Collections.unmodifiableList(road);
	}

	public List<Vehicle> getVehilces(){
		return Collections.unmodifiableList(vehicle);
	}
	
	////////////////////////////////////
	
	public void addJunction(Junction j) {
		if(junctionMap.containsKey(j.getId())) 
			throw new IllegalArgumentException("ya existe un cruce con la misma id");
		junctionMap.put(j.getId(), j);
		Junction.add(j);
	}
	
	public void addRoad(Road r) {
		if(roadMap.containsKey(r.getId()))
			throw new IllegalArgumentException("ya existe una carretera con el mismo id");
		//Cambiado
		if(!junctionMap.containsKey(r.getSrc().getId()) || !junctionMap.containsKey(r.getDest().getId()))
			throw new IllegalArgumentException("No existe un cruce de inicio o uno de destino en el roadmap");
		roadMap.put(r.getId(), r);
		road.add(r);
	}

	public void addVehicle(Vehicle v) {
		if(vehicleMap.containsKey(v.getId()))
			throw new IllegalArgumentException("ya existe un vehiculo con el mismo id");
		for(int i = 0; i < v.getItinerary().size(); i++)
		{
			if(!junctionMap.containsValue(v.getItinerary().get(i)))				
				throw new IllegalArgumentException("El itinerario no es valido");
		}
		
		int i = 0;
		int j = 0;
		while(i < road.size() && j < v.getItinerary().size() - 1)
		{
			if(road.get(i).getSrc().getId().equals(v.getItinerary().get(j).getId())
					&& road.get(i).getDest().getId().equals(v.getItinerary().get(j + 1).getId()))
			{
				j++; 
				i = 0;
			}
			else i++;	
		}
		if(i == road.size())
			throw new IllegalArgumentException("El itinerario no es valido");

		vehicle.add(v);
		vehicleMap.put(v.getId(), v);
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
		List<JSONObject> jJunc = new ArrayList<>();
		List<JSONObject> jRoad = new ArrayList<>();
		List<JSONObject> jVehicle = new ArrayList<>();
		for(int i = 0; i < Junction.size(); i++)
		{
			jJunc.add(Junction.get(i).report());
		}
		for(int i = 0; i < road.size(); i++)
		{
			jRoad.add(road.get(i).report());

		}
		for(int i = 0; i < vehicle.size(); i++)
		{
			jVehicle.add(vehicle.get(i).report());

		}
		jo.put("junctions", jJunc);
		jo.put("road", jRoad);
		jo.put("vehicles", jVehicle);

		return jo;
	}
}
