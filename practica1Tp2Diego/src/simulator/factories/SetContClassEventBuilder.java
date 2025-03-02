package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event> {

	public SetContClassEventBuilder() {
		super("set_cont_class", "data");
	}

	@Override
	protected Event create_instance(JSONObject data) {
		int time = data.getInt("time");

		JSONArray info = data.getJSONArray("info");
		String v;
		int contClass;
		List<Pair<String, Integer>> contList = new ArrayList();
		for (int i = 0; i < info.length(); i++) {
			v = info.getJSONObject(i).getString("vehicle");
			contClass = info.getJSONObject(i).getInt("class");
			Pair p = new Pair(v, contClass);
			contList.add(p);
		}
		return new SetContClassEvent(time, contList);
	}
}
