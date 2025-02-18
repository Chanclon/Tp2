package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject {
	private List<Junction> itinerary; // junction representa los cruces y se define ahi
	private int maxSpeed;
	private int actSpeed;
	private VehicleStatus estado;
	private Road road;// road es de tipo carreterea y se define alli
	private int localizacion;
	private int contClass;
	private int ContamT;
	private int DistT;

	protected Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		super(id);
		if (maxSpeed < 0 || contClass < 0 || contClass > 10 || itinerary.lenght() < 2) {
			throw new IllegalArgumentException("El/Los Valores no son validos");
		}
		this.maxSpeed = maxSpeed;
		this._id = id;
		this.contClass = contClass;
		this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
	}

	public void setSpeed(int s) {
		if (s < 0)
			throw new IllegalArgumentException("La velocidad tiene que ser igual o mayor que 0");

		this.actSpeed = Math.min(s, maxSpeed); // En vez de importar la libreria la hemos llamado desde la funcion
												// directamente
	}

	public VehicleStatus getStatus() {
		return this.estado;
	}

	public int getSpeed() {
		return this.actSpeed;
	}

	public int getPos() {
		return this.localizacion;
	}

	public void setContaminationClass(int c) {
		if (c < 0 || c > 0)
			throw new IllegalArgumentException("El nivel de contaminación no entra en el rango de valores");
		this.contClass = c;
	}

	@Override

	public void advance(int time) {// avanza el numero de tics
		int i = 0;
		while (this.estado == VehicleStatus.TRAVELING || i < time) {
			int Nuevalocalizacion = Math.min(actSpeed + this.localizacion, road.getLength());// (hay que implementar el
																								// guetter);
			this.ContamT += this.contClass * (Nuevalocalizacion - this.localizacion);
			this.localizacion = Nuevalocalizacion;
			if (this.localizacion == road.getLength())// hay que implementarlo
			{
				// se llama a junction porque el vehiculo entra a la cola del cruce
				// se modifica el estado del vehiculo
			}
			i++;
		}
	}

	public int getContClass() {
		return this.contClass;
	}

	public List<Junction> getItinerary() {
		return this.itinerary;
	}

	public int getTotalCO2() {
		return this.ContamT;
	}
	public int getMaxSpeed() {
		return this.maxSpeed;
	}

	public Road getroad() {
		return this.road;
	}

	void moveToNextRoad() {

	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}

}
