package simulator.model;

import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public abstract class Road extends SimulatedObject {

	private Junction srcJunc;
	private Junction destJunc;
	private int length;
	private int maxSpeed;
	private int limiteV;
	private int contLimit;
	private Weather weather;
	private int contamT;
	private List<Vehicle> vehiculos; // Ordenada por la localizacion de los vehiculos (descendente)

	protected Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) {
		super(id);
		if (srcJunc == null || destJunc == null || weather == null || maxSpeed <= 0 || contLimit < 0 || length <= 0) {
			throw new IllegalArgumentException("El/Los Valores no son validos");
		} else {
			this.srcJunc = srcJunc;
			this.destJunc = destJunc;
			this.maxSpeed = maxSpeed;
			this.contLimit = contLimit;
			this.length = length;
			this.weather = weather;
		}
	}

	void enter(Vehicle v) {
		if(v.getSpeed() != 0 && v.getPos() != 0) throw new IllegalArgumentException("El/Los Valores no son validos");
		this.vehiculos.add(v);
		Collections.sort(vehiculos,new Vehiclecomparator());
	}

	void exit(Vehicle v) {
	this.vehiculos.remove(v);
	}
	
	void setWeather(Weather w) {
		if(w == null) throw new IllegalArgumentException("No se permite valor nulo para el temporal");
		this.weather = w;
	}
	
	void addContamination(int c) {
		if(c<0) throw new IllegalArgumentException("No se puede introducir contaminacion negativa");
		this.contamT+=c;
	}
	
	abstract void reduceTotalContamination(); //se implementara en las subclases de road
	
	abstract void updateSpeedLimit();
	
	abstract int calculateVehicleSpeed(Vehicle v);
	
	@Override
	void advance(int time) {
		for(int i = 0; i < time; time++) {
			reduceTotalContamination();
			updateSpeedLimit();
			for(Vehicle e : vehiculos) {
				calculateVehicleSpeed(e);
				e.advance(time);
			}
			Collections.sort(vehiculos,new Vehiclecomparator()); //sirve para ordenar el array en funcion de la posicion del vegiuclo

		}
	}

	@Override
	public JSONObject report() {
	JSONObject jo = new JSONObject();
	jo.put("id", _id);
	jo.put("speedlimit", maxSpeed);
	jo.put("co2", contamT);
	jo.put("vehicles",vehiculos); // no se si esto esttá bien
		return jo ;
	}
	//Getters: /////////////////////
	
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
			return contamT;
		}
		
		public int getSpeedLimit() {
			return limiteV;
		}
		
		public List<Vehicle> getVehicles(){
			//Hay que usar esta funcion pero no se si es asi
			return Collections.unmodifiableList(vehiculos);
		}
		
		///////////////////////////////
		
		//Setters: ////////////////////
		
		protected void setContT(int tc) {
			this.contamT = tc;
		}
		
		protected void setSpeedLimit(int s) {
			this.limiteV = s;
		}
		
		protected void setVehicleSpeed() {
			
		}

}
