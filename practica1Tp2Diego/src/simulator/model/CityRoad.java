package simulator.model;

public class CityRoad extends Road{

	protected CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() {
		int x = 2;
		if(getWeather() == Weather.WINDY || getWeather() == Weather.STORM)	x = 10;
		int c = getTotalCO2() - x;
		if(c < 0) c = 0;
		setContT(c);
	}

	@Override
	void updateSpeedLimit() {
		setSpeedLimit(getMaxSpeed());
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		int s = ((11 - v.getContClass()* getSpeedLimit())/11);
		v.setSpeed(s);
		return v.getActSpeed();
	}

	
}
