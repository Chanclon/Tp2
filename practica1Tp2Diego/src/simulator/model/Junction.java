package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject {

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

	// Getters
	public int getX() {
		return xCoor;
	}

	public int getY() {
		return yCoor;
	}

	public int getGreenLightIndex() {
		return currGreen;
	}

	public List<Road> getInRoads() {
		return Collections.unmodifiableList(roads);
	}

	// No se puede pasar la lista directamente porque se podria modificar desde
	// cualquier sitio,
	// hy que hacerla inmodificable
	public Map<Road, List<Vehicle>> getQueue() {
		Map<Road, List<Vehicle>> newMap = new HashMap<>();
		for (Road r : vehiclesInRoadMap.keySet()) {
			newMap.put(r, Collections.unmodifiableList(vehiclesInRoadMap.get(r)));
		}
		return newMap;
	}
	//

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
		Road r = v.getRoad();
		List<Vehicle> q = vehiclesInRoadMap.get(r);

		if (q == null)
			throw new IllegalArgumentException("La carretera no contiene el vehiculo");

		q.add(v);
	}

	public Road roadTo(Junction j) {
		return outRoads.get(j);
	}

	@Override
	void advance(int currTime) {
		if (currGreen != -1) {
			List<Vehicle> q = vehiclesInRoad.get(currGreen);
			for (Vehicle v : dqStrategy.dequeue(q)) {
				v.moveToNextRoad();
				q.remove(v);
			}
		}
		int newGreen = lsStrategy.chooseNextGreen(roads, vehiclesInRoad, currGreen, prevGreen, currTime);
		if (newGreen != currGreen) {
			prevGreen = currTime;
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
	}
}
