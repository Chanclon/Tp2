package simulator.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class Junction extends SimulatedObject {
	private List<Road> roads;
	private Map<Junction, Road> outroads;
	private List<List<Vehicle>> vehicleperroad;
	private Map<Road, List<Vehicle>> road_vehicle;
	private int currGreen;
	private int prevGreen;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	private int xCoor;
	private int yCoor;

	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		super(id);
		if (lsStrategy == null || dqStrategy == null || xCoor < 0 || yCoor < 0) {
			throw new IllegalArgumentException("El/Los valores no son validos");
		}
		this.lsStrategy = lsStrategy;
		this.dqStrategy = dqStrategy;
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		this.prevGreen = 0;
	}

	void addIncommingRoad(Road r) {
		if (!r.getDest().equals(this)) {
			throw new IllegalArgumentException("Esta carretera no pertenece al cruce");
		}
		this.roads.add(r);
		List<Vehicle> colaR = new LinkedList<>();
		this.vehicleperroad.add(colaR);
		this.road_vehicle.put(r, colaR);

	}

	void addOutGoingRoad(Road r) {
		if (outroads.containsKey(r.getDest()) || !roads.contains(r)) {
			// Tienes que comprobar que ninguna otra carretera va desde this al cruce j
			// que la carretera r, es realmente una carretera saliente al cruce actual. En
			// otro caso debes lanzar una excepción.
			throw new IllegalArgumentException("Esta carretera no pertenece al cruce");
		}
		this.outroads.put(r.getDest(), r);
	}

	void enter(Vehicle v) {
		v.
	}
	
	@Override
	void advance(int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}

}
