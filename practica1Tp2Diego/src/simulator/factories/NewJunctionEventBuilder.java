package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event>{

	private  Factory<DequeuingStrategy> dqsFactory ;
	private  Factory<LightSwitchingStrategy> lssFactory ;
	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory) {
		super("new_junction", "data");
		this.dqsFactory = dqsFactory;
		this.lssFactory = lssFactory;
	}

	@Override
	protected Event create_instance(JSONObject data) {
		int time = data.getInt("time");
        String id = data.getString("id");
        JSONArray coorArray = data.getJSONArray("coor");
        int xCoor = coorArray.getInt(0);
        int yCoor = coorArray.getInt(1);
        LightSwitchingStrategy lsstrategy = lssFactory.create_instance(data.getJSONObject("ls_strategy")); 
        DequeuingStrategy dq_strategy = dqsFactory.create_instance(data.getJSONObject("dq_strategy"));
        return new NewJunctionEvent(time, id, lsstrategy, dq_strategy, xCoor, yCoor);
	}

}
