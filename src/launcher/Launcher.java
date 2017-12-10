package launcher;

import java.io.IOException;
import java.util.ArrayList;

import aiad20177.*;
import gui.Simulation;
import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
//import gui.Simulation;
import jade.wrapper.StaleProxyException;

public class Launcher {

	public static final int N_TAXIS = 2;
	public static final int N_PASSANGERS = 4;
	public static final int MAX_PASSENGERS_PERTAXI = 4;

	private Simulation simulation;

	private ArrayList<Taxi> taxis;
	private ArrayList<Passenger> passengers;
	private ContainerController container;

	public Launcher() throws IOException {
		this.simulation = new Simulation();
	}

	public void begin() throws StaleProxyException {
		buildModel();
		buidDisplay();
	}

	private void buildModel() throws StaleProxyException {
		this.taxis = new ArrayList<Taxi>();
		this.passengers = new ArrayList<Passenger>();

		Central central = new Central();

		AgentController centralService;
		centralService = this.container.acceptNewAgent("Central", central);
		centralService.start();

		ArrayList<String> roadTiles = simulation.getRoadTiles();
		int nrRoadTiles = roadTiles.size() - 1;

		// create taxis
		for (int i = 0; i < N_TAXIS; i++) {
			int numPass = (int) (Math.random() * MAX_PASSENGERS_PERTAXI + 0);

			int pos = (int) Math.random() * nrRoadTiles + 0;
			String[] coord = roadTiles.get(pos).split(";");
			Taxi taxi = new Taxi(Integer.parseInt(coord[0]), Integer.parseInt(coord[1]), numPass);
			simulation.setCityMapCoord(Integer.parseInt(coord[0]), Integer.parseInt(coord[1]), "taxi");
			roadTiles.remove(pos);
			nrRoadTiles--;

			AgentController taxiAgent;
			taxiAgent = this.container.acceptNewAgent("Taxi nr" + i, taxi);
			taxiAgent.start();

			taxis.add(taxi);
		}

		// create passengers
		for (int i = 0; i < N_PASSANGERS; i++) {

			int pos = (int) Math.random() * nrRoadTiles + 0;
			String[] coord = roadTiles.get(pos).split(";");
			roadTiles.remove(pos);
			nrRoadTiles--;

			// define passenger stop
			ArrayList<String> stopTiles = simulation.getStopTiles();
			int nrStopTiles = stopTiles.size() - 1;
			int stopIndex = (int) Math.random() * nrStopTiles + 0;
			String[] stopCoord = stopTiles.get(stopIndex).split(";");
			stopTiles.remove(stopIndex);
			
			Passenger passenger = new Passenger(Integer.parseInt(coord[0]), Integer.parseInt(coord[1]), Integer.parseInt(stopCoord[0]), Integer.parseInt(stopCoord[0]));
			simulation.setCityMapCoord(Integer.parseInt(coord[0]), Integer.parseInt(coord[1]), "passenger");
			

			AgentController passengerAgent;
			passengerAgent = this.container.acceptNewAgent("Passenger nr" + i, passenger);
			passengerAgent.start();

			passengers.add(passenger);
		}
	}

	private void buidDisplay() {
		simulation.startSimulation();
	}

	public void launchJade() throws StaleProxyException {

		Runtime rt = Runtime.instance();
		Profile p1 = new ProfileImpl();

		p1.setParameter(Profile.MAIN_HOST, "localhost");
		p1.setParameter(Profile.GUI, "true");

		this.container = rt.createMainContainer(p1);

		begin();

	}
}
