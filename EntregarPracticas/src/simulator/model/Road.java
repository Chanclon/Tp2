package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public abstract class Road extends SimulatedObject {

	private Junction srcJunc;
	private Junction destJunc;
	private int length;
	private int maxSpeed;
	private int limitSpeed;
	private int contLimit;
	private Weather weather;
	private int contT;
	private List<Vehicle> vehicles; // Ordenada por la localizacion de los vehiculos (descendente)

	// Getters: /////////////////////

	public int getLength() {
		return length;
	}

	public Junction getDest() {
		return destJunc;
	}

	public Junction getSrc() {
		return srcJunc;
	}

	public Weather getWeather() {
		return weather;
	}

	public int getContLimit() {
		return contLimit;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public int getTotalCO2() {
		return contT;
	}

	public int getSpeedLimit() {
		return limitSpeed;
	}

	public List<Vehicle> getVehicles() {
		return Collections.unmodifiableList(vehicles);
	}

	///////////////////////////////

	// Setters: ////////////////////

	protected void setContT(int tc) {
		this.contT = tc;
	}

	protected void setSpeedLimit(int s) {
		this.limitSpeed = s;
	}

	/*
	 * protected void setVehicleSpeed() {
	 * 
	 * }
	 */
	///////////////////////////////

	protected Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) {
		super(id);
		if (srcJunc == null || destJunc == null || weather == null || maxSpeed <= 0 || contLimit < 0 || length <= 0) {
			throw new IllegalArgumentException("El/Los Valores no son validos");
		}
		this.srcJunc = srcJunc;
		this.destJunc = destJunc;
		this.maxSpeed = maxSpeed;
		this.contLimit = contLimit;
		this.length = length;
		this.weather = weather;
		this.limitSpeed = maxSpeed;
		this.vehicles = new ArrayList<>();
		this.srcJunc.addOutGoingRoad(this);
		this.destJunc.addIncommingRoad(this);
	}

	public void enter(Vehicle v) {
		if (v.getLocation() != 0 && v.getSpeed() != 0)
			throw new IllegalArgumentException("El/Los valores no son validos");
		vehicles.add(v);
		// Para ordenar la lista de vehiculos
		Collections.sort(vehicles, new VehicleComparator());
	}

	public void exit(Vehicle v) {
		this.vehicles.remove(v);
	}

	public void setWeather(Weather w) {
		if (w == null)
			throw new IllegalArgumentException("Weather no puede ser null");
		this.weather = w;
	}

	public void addContamination(int c) {
		if (c < 0)
			throw new IllegalArgumentException("La contaminacion no puede ser negativa");
		this.contT += c;
	}

	// Reduce el total de la contaminacion y se definirá en las subclases de road
	abstract void reduceTotalContamination();

	// Actualiza la velocidad limite de la carretera y se definira en las subclases
	// de road
	abstract void updateSpeedLimit();

	// Calcula la posicion de un vehiculo y se definira en las subclases de road
	abstract int calculateVehicleSpeed(Vehicle v);

	@Override
	void advance(int currTime) {
		// Reduce la contaminacion total
		reduceTotalContamination();
		// Establece el limite de velocidad
		updateSpeedLimit();
		// Recorre la lista de vehiculos
		for (Vehicle ve : vehicles) {
			ve.setSpeed(calculateVehicleSpeed(ve));
			ve.advance(currTime);
		}
		/*
		 * for(int j = 0; j < vehicles.size(); j++) { //pone la velocidad del vehı́culo
		 * vehicles.get(j).setSpeed(calculateVehicleSpeed(vehicles.get(j))); //Llamamos
		 * al metodo advance vehicles.get(j).advance(currTime); }
		 */
		// Ordena el array en funcion de la posicion del vehicuo
		Collections.sort(vehicles, new VehicleComparator());
	}

	@Override
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put("id", _id);
		jo.put("speedlimit", limitSpeed);
		jo.put("weather", weather.toString());
		jo.put("co2", contT);
		List<String> vId = new ArrayList<>();
		for (int i = 0; i < vehicles.size(); i++) {
			vId.add(vehicles.get(i).getId());
		}
		jo.put("vehicles", vId);
		return jo;
	}
}
