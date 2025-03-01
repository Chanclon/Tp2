package simulator.model;

public class NewInterCityRoadEvent extends Event{
	
	int time;
	String id;
	String srcJun;
	String destJunc;
	int length;
	int co2Limit;
	int maxSpeed;
	Weather weather;
	
	public NewInterCityRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
		super(time);
		this.time = time;
		this.id = id;
		this.srcJun = srcJun;
		this.destJunc = destJunc;
		this.length = length;
		this.co2Limit = co2Limit;
		this.maxSpeed = maxSpeed;
		this.weather = weather;		
	}

	@Override
	void execute(RoadMap map) {
		Junction src = map.getJunction(srcJun);
		Junction dest = map.getJunction(destJunc);
		Road r = new InterCityRoad(id, src, dest, maxSpeed, co2Limit, length, weather);
		map.addRoad(r);		
	}
}
