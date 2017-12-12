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
	private ContainerController mainContainer;

	public Launcher() throws IOException, StaleProxyException {
		System.out.println("Creating Launcher");
		
		this.simulation = new Simulation();
	}

	public void begin() throws StaleProxyException {
		System.out.println("Beginning");
		buildModel();
		buidDisplay();
		//System.out.println(simulation.printCityMap());
		
		
	}

	private void buildModel() throws StaleProxyException {
		System.out.println("Building Model");
		this.taxis = new ArrayList<Taxi>();
		this.passengers = new ArrayList<Passenger>();

		Central central = new Central();

		AgentController centralService;
		centralService = this.mainContainer.acceptNewAgent("Central", central);
		centralService.start();

		ArrayList<String> roadTiles = simulation.getRoadTiles();
		int nrRoadTiles = roadTiles.size() - 1;

		// create taxis
		for (int i = 0; i < N_TAXIS; i++) {
			int numPass = (int) (Math.random() * MAX_PASSENGERS_PERTAXI + 0);

			int pos = (int) Math.floor(Math.random() * nrRoadTiles + 0);
			String[] coord = roadTiles.get(pos).split(";");
			Taxi taxi = new Taxi(Integer.parseInt(coord[0]), Integer.parseInt(coord[1]), numPass);
			simulation.setCityMapCoord(Integer.parseInt(coord[0]), Integer.parseInt(coord[1]), "taxi");
			roadTiles.remove(pos);
			nrRoadTiles--;

			AgentController taxiAgent;
			taxiAgent = this.mainContainer.acceptNewAgent("Taxi" + i, taxi);
			taxiAgent.start();

			taxis.add(taxi);
		}

		// create passengers
		for (int i = 0; i < N_PASSANGERS; i++) {

			int pos = (int) Math.floor(Math.random() * nrRoadTiles + 0);
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
			passengerAgent = this.mainContainer.acceptNewAgent("Passenger" + i, passenger);
			passengerAgent.start();

			passengers.add(passenger);
		}
	}

	private void buidDisplay() {
		System.out.println("Building Display");
		simulation.startSimulation();
	}

	
	
	public ArrayList<Taxi> getTaxis() {
		return taxis;
	}

	public void setTaxis(ArrayList<Taxi> taxis) {
		this.taxis = taxis;
	}

	public void launchJade() throws StaleProxyException {
		System.out.println("Launching Jade");

		Runtime rt = Runtime.instance();
		Profile p1 = new ProfileImpl();

		p1.setParameter(Profile.MAIN_HOST, "localhost");
		p1.setParameter(Profile.GUI, "true");

		this.mainContainer = rt.createMainContainer(p1);

		begin();

	}
	
	public static void main(String args[]) throws IOException, StaleProxyException  {
		
		System.out.println("Start program");
		Launcher n = new Launcher();
		n.launchJade();

	}
}
