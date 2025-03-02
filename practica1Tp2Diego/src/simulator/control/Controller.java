package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

public class Controller {
	private TrafficSimulator _sim;
	private Factory<Event> _eventsFactory;

	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
		if (sim == null || eventsFactory == null)
			throw new IllegalArgumentException("Los datos no pueden ser nulos");
		_sim = sim;
		_eventsFactory = eventsFactory;
	}

	public void loadEvents(InputStream in) {
		JSONObject jo = new JSONObject(new JSONTokener(in));
		JSONArray joA = jo.getJSONArray("events");
		for (int i = 0; i < joA.length(); i++) {
			// Va añadiendo eventos al traficSimulator
			_sim.addEvent(_eventsFactory.create_instance(joA.getJSONObject(i)));
		}
	}

	public void run(int n, OutputStream out) {
		PrintStream p = new PrintStream(out);
		// print "{" to 'p'
		p.println("{");
		// print " \"states\": [" to 'p'
		p.println("[");

		// loop for the first n-1 states (to print comma after each state)
		for (int i = 0; i < n - 1; i++) {
			_sim.advance();
			p.print(_sim.report());
			p.println(",");
		}

		// last step, only if 'n > 0'
		if (n > 0) {
			_sim.advance();
			p.print(_sim.report());
		}

		// print "]" to 'p'
		p.println("]");
		// print "}" to 'p'
		p.println("}");
	}

	public void reset() {
		_sim.reset();
	}
}