package simulator.factories;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event>{

	public SetWeatherEventBuilder() {
		super("set_weather", "data");
	}

	@Override
	protected Event create_instance(JSONObject data) {
		
		int time = data.getInt("time");
		
		JSONArray info = data.getJSONArray("info"); 
		JSONArray ws = new JSONArray();
		
		for(int i = 0; i < info.length(); i++)
		{
		String s = data.getString("vehicle");
		Weather w = data.getEnum(Weather.class, "weather");
		List<Pair<String,Weather>> ws1 = new Pair(s, w);
		}
		return new SetWeatherEvent();
	}

}
