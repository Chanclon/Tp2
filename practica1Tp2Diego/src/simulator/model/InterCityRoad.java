package simulator.model;

public class InterCityRoad extends Road {

	protected InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	public void reduceTotalContamination() {
		int x = 0;
		switch (getWeather()) {

		// case Weather.SUNNY: x = 2;
		// break;
		case Weather.CLOUDY:
			x = 3;
			break;

		case Weather.RAINY:
			x = 10;
			break;

		case Weather.WINDY:
			x = 15;
			break;

		case Weather.STORM:
			x = 20;
			break;
		// El defaut es el SUNNY
		default:
			x = 2;
			break;
		}
		int c = ((100 - x) * getTotalCO2()) / 100;
		setContT(c);
	}

	@Override
	public void updateSpeedLimit() {
		/*
		 * if(getTotalCO2() > getContLimit()) { setSpeedLimit(getMaxSpeed()/2); } else
		 * setSpeedLimit(getMaxSpeed());
		 */

		setSpeedLimit((getTotalCO2() > getContLimit()) ? getMaxSpeed() / 2 : getMaxSpeed());
	}

	@Override
	public int calculateVehicleSpeed(Vehicle v) {
		/*
		 * if(getWeather() != Weather.STORM) { v.setSpeed(getMaxSpeed()); } else { int s
		 * =(v.getMaxSpeed()*8)/10; v.setSpeed(s); }
		 */

		return (getWeather() == Weather.STORM) ? (this.getSpeedLimit() * 8) / 10 : getSpeedLimit();
	}

}
