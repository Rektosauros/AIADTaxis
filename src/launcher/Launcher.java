package launcher;

import java.io.IOException;
import java.util.ArrayList;

import aiad20177.*;
import gui.Simulation;
import jade.core.MainContainer;
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

		// create taxis
		for (int i = 0; i < N_TAXIS; i++) {
			int numPass = (int) (Math.random() * 4 + 0);

			ArrayList<String> roadTiles = simulation.getRoadTiles();
			int nrRoadTiles = roadTiles.size() - 1;

			int pos = (int) Math.random() * nrRoadTiles + 0;
			String[] coord = roadTiles.get(pos).split(";");
			Taxi taxi = new Taxi(Integer.parseInt(coord[0]), Integer.parseInt(coord[1]), MAX_PASSENGERS_PERTAXI);
			
			AgentController taxiAgent;
			taxiAgent = this.container.acceptNewAgent("Taxi nr"+i, taxi);
			taxiAgent.start();
			
			taxis.add(taxi);
		}

		// create passengers
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
