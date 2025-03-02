package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.MoveFirstStrategy;

public class MoveFirstStrategyBuilder extends Builder<DequeuingStrategy> {

	public MoveFirstStrategyBuilder() {
		super("move_first_dqs", "data");
	}

	@Override
	protected DequeuingStrategy create_instance(JSONObject data) {
		MoveFirstStrategy mf = new MoveFirstStrategy();
		return mf;
	}

}
