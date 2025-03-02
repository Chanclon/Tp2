package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy> {

	public RoundRobinStrategyBuilder() {
		super("round_robin_lss", "data");
	}

	@Override
	protected LightSwitchingStrategy create_instance(JSONObject data) {
		int i = data.has("timeslot") ? data.getInt("timeslot") : 1;
		RoundRobinStrategy rs = new RoundRobinStrategy(i);
		return rs;
	}
}
