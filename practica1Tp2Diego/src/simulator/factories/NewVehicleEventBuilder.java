package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event> {

    public NewVehicleEventBuilder() {
        super("new_vehicle", "A new vehicle");
    }

 /*   @Override
    protected void fillInData(JSONObject o) {
        o.put("time", "The time at which the event is executed");
        o.put("maxspeed", "The vehicle's max speed");
        
    }

    @Override
    protected Event createInstance(JSONObject data) {
        int time = data.getInt("time");
        String id = data.getString("id");
        int maxSpeed = data.getInt("maxspeed");
        int contClass = data.getInt("class");
        List<String> itinerary = data.getJSONArray("itinerary");

        return new NewVehicleEvent(time, id, maxSpeed, contClass, itinerary);
    }*/
    
	@Override
	protected Event create_instance(JSONObject data) {
		int time = data.getInt("time");
        String id = data.getString("id");
        int maxSpeed = data.getInt("maxspeed");
        int contClass = data.getInt("class");
        JSONArray i = data.getJSONArray("itinerary");
        List<String> itinerary = new ArrayList<>();
        for(int j = 0; j < i.length(); j++) {
        	itinerary.add(i.getString(j));
        	//(i.getJSONObject(j).getString("itinerary"));
        }
        return new NewVehicleEvent(time, id, maxSpeed, contClass, itinerary);
	}
}
