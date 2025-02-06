package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Vehicle extends SimulatedObject {

	private List<Junction> itinerary;
	private int maxSpeed;
	private int actSpeed;
	private VehicleStatus estado;
	private Road road;
	private int localizacion;
	private int contClass;
	private int contTotal;
	private int distanciaT;
	
	//Getters: ////////////////
	
	public int localización() {
		return this.localizacion;
	}
	
	public int actSpeed() {
		return this.actSpeed;
	}
	
	//////////////////////////

	public Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		super(id);
		if (maxSpeed < 0 || contClass < 0 || contClass > 0 || itinerary.length() < 2) {
			throw new IllegalArgumentException("El/Los valores no son validos");
		}

		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
		this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
		;
	}

	void setSpeed(int s) {

		if (s < 0)
			throw new IllegalArgumentException("El valor no puede ser negativo");
		this.actSpeed = Math.min(s, maxSpeed);
		// Lo mismo que lo de abajo
		/*
		 * if (s < maxSpeed) actSpeed = s; else actSpeed = maxSpeed;
		 */

	}

	void setContaminationClass(int c) {
		if (c < 0 || c > 10)
			throw new IllegalArgumentException();
		this.contClass = c;
	}

	void advance(int currTime) {
		int i = 0;
		while (this.estado == VehicleStatus.TRAVELING && i < currTime) {
			// Se actualiza la localización:
			int localizacionPrev = this.localizacion;
			this.localizacion = Math.min(localizacion + actSpeed, road/* carretera.length o algo asi */);
			// Calcular la contaminacion:
			int I = this.localizacion - localizacionPrev;
			this.contTotal += this.contClass * I;
			// Comprobar si se sale de la carretera
			if (this.localizacion >= this.road.length) {
				// El vehiculo entra en la cola del cruce
			}
			i++;
		}

	}

	void moveToNextRoad() {

	}

	public JSONObject report() {

	}
}
