package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy> {

	public MostCrowdedStrategyBuilder() {
		super("most_crowded_lss", "data");
	}

	@Override
	protected LightSwitchingStrategy create_instance(JSONObject data) {
		int i = data.has("timeslot") ? data.getInt("timeslot") : 1;
		MostCrowdedStrategy mc = new MostCrowdedStrategy(i);
		return mc;
	}
}
