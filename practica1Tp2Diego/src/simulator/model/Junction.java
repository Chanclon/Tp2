package simulator.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Junction extends SimulatedObject{

	private List<Road> roads;
	private List<List<Vehicle>> vehiclesInRoad;
	//No se si es obligatorio o para que sirve
	//private Map<Road, List<Vehicle>> listVehiclesMap;
	private int currGreen;
	private int prevGreen;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	private int xCoor;
	private int yCoor;
	
	protected Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		  super(id);
		  if(lsStrategy == null || dqStrategy == null || xCoor < 0 || yCoor < 0 )
			  throw new IllegalArgumentException("Parametros no validos");
		  this.lsStrategy = lsStrategy;
		  this.dqStrategy = dqStrategy;
		  this.xCoor = xCoor;
		  this.yCoor = yCoor;
	}

	//No se si esta bien no entiendo lo del LinkedList
	public void addIncommingRoad(Road r) {
		roads.add(r);
		List<Road> s = new LinkedList<>();
		s.add(r);
	}
	
	public void addOutGoingRoad(Road r) {
		//Hay que hacer el mapa de carreteras antes
	}
	
	public void enter(Vehicle v) {
		List<Vehicle> s = new LinkedList<>();
		s.add(v);
		vehiclesInRoad.add(s);
	}
	
	public Road roadTo(Junction j) {
		//Hay que hacer el mapa de carreteras antes
		return null;
	}
	
	@Override
	void advance(int currTime) {
		int i = currTime;
		while(i > 0) {
			
		}
	}

	@Override
	public JSONObject report() {
		JSONObject joR = new JSONObject();
		joR.put("id", roads);
		joR.put("green",vehiclesInRoad);
		
		JSONObject jo = new JSONObject();
		jo.put("id", _id);
		jo.put("green",roads.get(currGreen).getId());
		jo.put("queues", joR);
		return jo;
	}
	
}
