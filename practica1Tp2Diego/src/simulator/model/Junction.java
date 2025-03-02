package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject implements LightSwitchingStrategy, DequeuingStrategy {

	private List<Road> roads;
	private List<List<Vehicle>> vehiclesInRoad;
	private Map<Road, List<Vehicle>> vehiclesInRoadMap;
	private Map<Junction, Road> outRoads;// Carreteras salientes de un cruce
	private int currGreen;
	private int prevGreen;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	private int xCoor;
	private int yCoor;

	protected Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor,
			int yCoor) {
		super(id);
		if (lsStrategy == null || dqStrategy == null || xCoor < 0 || yCoor < 0)
			throw new IllegalArgumentException("Parametros no validos");
		this.lsStrategy = lsStrategy;
		this.dqStrategy = dqStrategy;
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		this.currGreen = -1;
		this.prevGreen = 0;
		this.roads = new ArrayList<>();
		this.vehiclesInRoad = new ArrayList<>();
		this.vehiclesInRoadMap = new HashMap<>();
		this.outRoads = new HashMap<>();
	}

	// No se si esta bien no entiendo lo del LinkedList
	public void addIncommingRoad(Road r) {
		if (r.getDest() != this)
			throw new IllegalArgumentException("La carretera no pertenece l cruce");
		this.roads.add(r);
		List<Vehicle> listaV = new LinkedList<>();
		this.vehiclesInRoad.add(listaV);
		this.vehiclesInRoadMap.put(r, this.vehiclesInRoad.getLast());
	}

	public void addOutGoingRoad(Road r) {
		if (r.getSrc() != this)
			throw new IllegalArgumentException("La carretera no pertenece al cruce");
		this.outRoads.put(r.getDest(), r);
	}

	public void enter(Vehicle v) {
		if (!roads.contains(v.getRoad()))
			throw new IllegalArgumentException("La carretera no contiene el vehiculo");

		vehiclesInRoad.get(vehiclesInRoad.indexOf(vehiclesInRoadMap.getOrDefault(v.getRoad(), null))).add(v);
	}

	public Road roadTo(Junction j) {
		return outRoads.get(j);
	}

	@Override
	void advance(int currTime) {
		if (currGreen != -1) {
			if (vehiclesInRoad.size() == 0)
				return;
			List<Vehicle> v = vehiclesInRoadMap.getOrDefault(vehiclesInRoad.get(currGreen), null);
			List<Vehicle> l = dqStrategy.dequeue(v);
			if (l == null)
				return;
			for (int i = 0; i < l.size(); i++) {
				l.get(i).moveToNextRoad();
				boolean eliminado = false;
				int j = 0;
				while (!eliminado || j < v.size()) {
					if (l.get(i) == v.get(j)) {
						v.remove(j);
						eliminado = true;
					}
					j++;
				}
			}
		}
		int newGreen = lsStrategy.chooseNextGreen(roads, vehiclesInRoad, currGreen, prevGreen, currTime);
		if (newGreen != currGreen) {
			prevGreen = currGreen;
			currGreen = newGreen;
		}
	}

	@Override
	public JSONObject report() {

		JSONObject jo = new JSONObject();
		jo.put("id", getId());
		if (currGreen == -1)
			jo.put("green", "none");
		else
			jo.put("green", roads.get(currGreen).getId());

		JSONArray queuesArray = new JSONArray();

		for (Road road : roads) {
			JSONObject queueObj = new JSONObject();
			queueObj.put("road", road.getId());

			JSONArray vehiclesArray = new JSONArray();
			List<Vehicle> vehicles = vehiclesInRoadMap.get(road);
			if (vehicles != null) {
				for (Vehicle v : vehicles) {
					vehiclesArray.put(v.getId());
				}
			}
			queueObj.put("vehicles", vehiclesArray);
			queuesArray.put(queueObj);
		}
		jo.put("queues", queuesArray);

		return jo;

		/*
		 * JSONObject jo = new JSONObject(); jo.put("id", _id); if(outRoads.size() == 0
		 * && currGreen >= outRoads.size() - 1) jo.put("green", "none"); jo.put("green",
		 * currGreen); if(jo.isNull("queues") && jo.getJSONObject("queues").isEmpty())
		 * jo.put("queues", "[]"); else { JSONArray queues = jo.getJSONArray("queues");
		 * 
		 * JSONArray vehicles = jo.getJSONArray("vehicles"); List<List<String>> v = new
		 * ArrayList<>(); List<String> vId = new ArrayList<>(); for(int j = 0; j <
		 * vehicles.length(); j++) { JSONObject vObject = queues.getJSONObject(j);
		 * vId.add(vObject.getString("vehicles")); } v.add(vId);
		 * 
		 * ArrayList<String> r = new ArrayList<>(); for(int i = 0; i < queues.length();
		 * i++) { JSONObject q = queues.getJSONObject(i); r.add(q.getString("road")); }
		 * 
		 * JSONObject q = new JSONObject(); q.put("road", r); q.put("vehicles", v);
		 * jo.put("queues", q); } return jo;
		 */
	}

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		// TODO Auto-generated method stub
		return 0;
	}

}
