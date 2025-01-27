package simulator.model;

import java.util.List;

public class Road extends SimulatedObject{

	private Junction srcJunc;
	private Junction destJunc;
	private int length;
	private int maxSpeed;
	private int limiteV;
	private int contLimit;
	private Weather weather;
	private int contamT;
	private List<Vehicle> vehiculos; //Ordenada por la localizacion de los vehiculos (descendente)
	
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id);
		if(srcJunc == null || destJunc == null || weather == null || maxSpeed <= 0 || contLimit < 0 || length <= 0) {
			throw new IllegalArgumentException("El/Los Valores no son validos");
		}
		else {
			this.srcJunc = srcJunc;
			this.destJunc = destJunc;
			this.maxSpeed = maxSpeed;
			this.contLimit = contLimit;
			this.length = length;
			this.weather = weather;			
		}
	}
	
	
}
