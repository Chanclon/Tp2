package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;

public class SetContClassEventBuilder  extends Builder<Event>{

	public SetContClassEventBuilder() {
		super("set_cont_class", "data");
	}

	@Override
	protected Event create_instance(JSONObject data) {
		// TODO Auto-generated method stub
		return null;
	}
}
