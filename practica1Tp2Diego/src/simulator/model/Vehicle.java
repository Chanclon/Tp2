package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject {

	private List<Junction> itinerary;
	private int maxSpeed;
	private int _actSpeed;
	private VehicleStatus _estado;
	private Road _road;
	private int _localizacion;
	private int _contClass;
	private int _contT;
	private int _distanciaT;

	// Getters: ////////////////

	public int getLocation() {
		return this._localizacion;
	}

	public int getSpeed() {
		return this._actSpeed;
	}

	public int getMaxSpeed() {
		return this.maxSpeed;
	}

	public int getContClass() {
		return this._contClass;
	}

	public VehicleStatus getStatus() {
		return this._estado;
	}

	public int getTotalCO2() {
		return this._contT;
	}

	public List<Junction> getItinerary() {
		return this.itinerary;
	}

	public Road getRoad() {
		return this._road;
	}

	//////////////////////////

	// Setters: ///////////////
	// Deberia estar en private pero lo necesita una funcion de interCityRoad
	public void setSpeed(int s) {

		if (s < 0)
			throw new IllegalArgumentException("La velocidad no puede ser negativo");
		if (_estado == VehicleStatus.TRAVELING)
			this._actSpeed = Math.min(s, maxSpeed);
		// Lo mismo que lo de abajo
		/*
		 * if (s < maxSpeed) actSpeed = s; else actSpeed = maxSpeed;
		 */

	}

	public void setContClass(int c) {
		if (c < 0 || c > 10)
			throw new IllegalArgumentException("La contaminacion debe estar entre 0 y 10");
		this._contClass = c;
	}

	//////////////////////////

	public Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		super(id);
		if (maxSpeed <= 0) {
			throw new IllegalArgumentException("La velocidad no puede ser ni negativa ni 0");
		}
		if (contClass > 10 || contClass < 0) {
			throw new IllegalArgumentException("La contaminacion no esta entre 0 y 10");
		}
		if (itinerary.size() < 2) {
			throw new IllegalArgumentException("Tiene que haber mas de dos cruces");
		}

		this.maxSpeed = maxSpeed;
		this._contClass = contClass;
		this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
		this._estado = VehicleStatus.PENDING;
		this._actSpeed=0;
		this._contT=0;
		this._distanciaT=0;
		this._localizacion = 0;
	}

	void advance(int currTime) {
		if(_estado != VehicleStatus.TRAVELING) return;
		// Se actualiza la localización:
		int localizacionPrev = this._localizacion;
		this._localizacion = Math.min(_localizacion + _actSpeed, _road.getLength());
		// Calcular la contaminacion:
		int I = this._localizacion - localizacionPrev;
		this._distanciaT = I;
		this._contT += this._contClass * I;
		_road.addContamination(this._contClass * I);
		// Comprobar si se sale de la carretera
		if (this._localizacion >= this._road.getLength()) {
			// El vehiculo entra en la cola del cruce
			_road.getDest().enter(this);
			_estado = VehicleStatus.WAITING;
			_actSpeed = 0;
		}
	}

	void moveToNextRoad() {
		if (_estado != VehicleStatus.PENDING && _estado != VehicleStatus.WAITING)
			throw new IllegalArgumentException("El coche tiene que estar parado");

		if (_road != null && itinerary.indexOf(_road.getSrc()) > 0) {
			_road.exit(this);
		}
		//Si no ha empezado la ruta el coche se inicia en la primera carretera
		if(_estado == VehicleStatus.PENDING)
		{
			_road = itinerary.get(0).roadTo(itinerary.get(1));
			_road.enter(this);
			_estado = VehicleStatus.TRAVELING;
			return; 
			//Si no habria que comprobar si en el if y else de 
			//despues el estado del vehiculo es Waiting
		}

		if (this._road.getDest() == this.itinerary.getLast() /*&& _estado == VehicleStatus.WAITING*/) {
			_estado = VehicleStatus.ARRIVED;
			this._road = null;
			this._actSpeed = 0;
			// Puede que mas cosas
		}

		else{
			//La carretera siguiente es la que hay entre el cruce de destino de la carretera actual 
			//y del siguiente cruce de destino que se obtiene sumando uno al indice del cruce destino de la carretera actual
			this._road = _road.getDest().roadTo( itinerary.get(itinerary.indexOf(_road.getDest())));
			_estado = VehicleStatus.TRAVELING;
			this._localizacion = 0;
			_road.enter(this);
		}
	}

	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put("id", _id);
		jo.put("speed", _actSpeed);
		jo.put("distance", _distanciaT);
		jo.put("co2", _contT);
		jo.put("class", _contClass);
		jo.put("status", _estado.toString());
		if (this._estado == VehicleStatus.TRAVELING || this._estado == VehicleStatus.WAITING) {
			jo.put("road", _road.getId());
			jo.put("location", _localizacion);
		}
		return jo;
	}
}
