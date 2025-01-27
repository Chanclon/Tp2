package simulator.model;

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
