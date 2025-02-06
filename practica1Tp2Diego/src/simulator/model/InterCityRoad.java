package simulator.model;

public class InterCityRoad extends Road{

	protected InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	public void reduceTotalContamination() {
		int x = 0;
		switch(getWeather()) {
		
		case SUNNY: x = 2; 
		break;
		
		case CLOUDY: x = 3;
		break;
		
		case RAINY: x = 10;
		break;
		
		case WINDY: x = 15;
		break;
		
		case STORM: x = 20;
		break;	
		
		//Falta el default
		
		}
		int c = ((100 - x)*getTotalCO2())/100;
		setContT(c);
	}

	@Override
	public void updateSpeedLimit() {
		
	}

	@Override
	public int calculateVehicleSpeed(Vehicle v) {
		
		return 0;
	}

	
}
