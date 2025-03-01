package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewCityRoadEvent;
import simulator.model.Weather;

public class NewInterCityRoadEventBuilder extends Builder<Event>{

	public NewInterCityRoadEventBuilder() {
		super("new_inter_city_road", "data");
	}

	@Override
	protected Event create_instance(JSONObject data) {
		 int time = data.getInt("time");
		 String id = data.getString("id");
		 String src = data.getString("src");
		 String dest = data.getString("dest");
		 int length = data.getInt("time");
		 int co2Limit = data.getInt("time");
		 int maxspeed = data.getInt("time");
		 Weather weather = data.getEnum(Weather.class, "weather");
		
		return new NewCityRoadEvent(time, id, src, dest, length, co2Limit, maxspeed, weather);
	}

}
