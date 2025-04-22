package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {

	private int ticks;

	public MostCrowdedStrategy(int timeSlot) {
		this.ticks = timeSlot;
	}

	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		
		
		
		if (roads.isEmpty() || qs.isEmpty())
			return -1;

		if (currTime - lastSwitchingTime < ticks)
			return currGreen;

		// Si todos los semaforos estan en rojo:
		int ret = 0;
		int max = qs.get(0).size();
		if (currGreen == -1) {
			for (int i = 0; i < qs.size(); i++) {
				if (qs.get(i).size() > max) {
					max = qs.get(i).size();
					ret = i;
				}
			}
			return ret;
		}

		// Si nada de lo anterior se cumple se recorre de forma circular
		// la lista de vehiculos de cada carretera y la que tenga un mayor tamaño
		// tendra el semaforo en verde
		int i = (currGreen + 1) % qs.size(); //Siguiente carril
		ret = i;
		max = qs.get(i).size();
	
		while (i != currGreen) {
			if (qs.get(i).size() > max) {
				max = qs.get(i).size();
				ret = i;
			}
			i = (i + 1) % qs.size(); //Siguiente indice de forma circular
		}
		return ret;
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*if (roads.isEmpty() || qs.isEmpty())
			return -1;

		if (currTime - lastSwitchingTime < ticks)
			return currGreen;

		// Si todos los semaforos estan en rojo:
		int i = 0;
		int ret = i;
		int max = qs.get(i).size();
		if (currGreen == -1) {
			while (i < qs.size()) {
				if (qs.get(i).size() > max) {
					max = qs.get(i).size();
					ret = i;
				}
				i++;
			}
			return ret;
		}

		// Si nada de lo anterior se cumple se recorre de forma circular
		// la lista de vehiculos de cada carretera y la que tenga un mayor tamaño
		// tendra el semaforo en verde
		if (currGreen == qs.size() - 1)
			i = 0;
		else
			i = currGreen + 1;
		ret = i;
		max = qs.get(i).size();
		while (i != currGreen) {
			if (qs.get(i).size() > max) {
				max = qs.get(i).size();
				ret = i;
			}
			if (i == qs.size() - 1)
				i = 0;
			else
				i++;
		}
		return ret;*/
	}

}
