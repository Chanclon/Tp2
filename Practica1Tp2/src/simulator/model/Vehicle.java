package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject {
	private List<Junction> itinerary; // junction representa los curces y se define ahi
	private int maxSpeed;
	private int actSpeed;
	private VehicleStatus estado;
	private Road road;// road es de tipo carreterea y se define alli
	private int localizacion;
	private int contClass;
	private int ContamT;
	private int DistT;

	protected Vehicle(String id, int maxSpeed, int contClass, List<Junciont> itinerary) {
		super(id);
		if (maxSpeed < 0 || contClass < 0 || contClass > 10 || itinerary.lenght() < 2) {
			throw new IllegalArgumentException("El/Los Valores no son validos");
		}
		this.maxSpeed = maxSpeed;
		this._id = id ;
		this.contClass = contClass;
		this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));		
	}

	void setSpeed(int s) {
		if(s < 0) throw new IllegalArgumentException("La velocidad tiene que ser igual o mayor que 0");

		this.actSpeed = Math.min(s,maxSpeed); // En vez de importar la libreria la hemos llamado desde la funcion directamente
	}
	void setContaminationClass(int c) {
		this.
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
