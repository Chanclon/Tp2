package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event> {

	public SetWeatherEventBuilder() {
		super("set_weather", "data");
	}

	@Override
	protected Event create_instance(JSONObject data) {

		int time = data.getInt("time");

		JSONArray info = data.getJSONArray("info");
		List<Pair<String, Weather>> ws1 = new ArrayList<>();
		String r;
		Weather w;
		for (int i = 0; i < info.length(); i++) {
			r = info.getJSONObject(i).getString("road");
			w = info.getJSONObject(i).getEnum(Weather.class, "weather");
			Pair p = new Pair(r, w);
			ws1.add(p);
		}
		return new SetWeatherEvent(time, ws1);

		/*
		 * String s; Weather w;
		 * 
		 * for(int i = 0; i < info.length(); i++) { s = data.getString("road"); w =
		 * data.getEnum(Weather.class, "weather"); Pair p = new Pair(s, w); ws.add(p); }
		 * return new SetWeatherEvent(time, );
		 */
	}
}
