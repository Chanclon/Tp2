package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;


public class Vehicle extends SimulatedObject {

	private List<Junction> itinerary;
	private int maxSpeed;
	private int actSpeed;
	private VehicleStatus estado;
	private Road road;
	private int localizacion;
	private int contClass;
	private int contT;
	private int distanciaT;
	
	//Getters: ////////////////
	
	public int getLoc() {
		return this.localizacion;
	}
	
	public int getActSpeed() {
		return this.actSpeed;
	}
	public int getMaxSpeed() {
		return this.maxSpeed;
	}
	
	public int getContClass() {
		return this.contClass;
	}
	
	public VehicleStatus getStatus() {
		return this.estado;
	}
	
	public int getTotalCO2() {
		return this.contT;
	}
	
	public List<Junction> getItinerary() {
		return this.itinerary;
	}
	
	public Road getRoad() {
		return this.road;
	}
	
	//////////////////////////

	//Setters: ///////////////
	//Deberia estar en private pero lo necesita una funcion de interCityRoad
	public void setSpeed(int s) {

		if (s < 0)
			throw new IllegalArgumentException("El valor no puede ser negativo");
		this.actSpeed = Math.min(s, maxSpeed);
		// Lo mismo que lo de abajo
		/*
		 * if (s < maxSpeed) actSpeed = s; else actSpeed = maxSpeed;
		 */

	}

	public void setContaminationClass(int c) {
		if (c < 0 || c > 10)
			throw new IllegalArgumentException("La contaminacion debe estar entre 0 y 10");
		this.contClass = c;
	}
	
	//////////////////////////
	
	public Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		super(id);
		if (maxSpeed < 0 || contClass < 0 || contClass > 0 || itinerary.size() < 2) {
			throw new IllegalArgumentException("El/Los valores no son validos");
		}

		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
		this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
		;
	}

	void advance(int currTime) {
		int i = 0;
		while (this.estado == VehicleStatus.TRAVELING && i < currTime) {
			// Se actualiza la localización:
			int localizacionPrev = this.localizacion;
			this.localizacion = Math.min(localizacion + actSpeed, road.getLength());
			// Calcular la contaminacion:
			int I = this.localizacion - localizacionPrev;
			this.contT += this.contClass * I;
			// Comprobar si se sale de la carretera
			if (this.localizacion >= this.road.getLength()) {
				// El vehiculo entra en la cola del cruce
			}
			i++;
		}

	}

	void moveToNextRoad() {

	}

	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put("_id", _id);
		jo.put("speed", actSpeed);
		jo.put("distance", distanciaT);
		jo.put("co2", contT);
		jo.put("class", contClass);
		jo.put("status", estado);
		jo.put("road", road.getId());
		jo.put("location", localizacion);
		return jo;
		
	}
}
