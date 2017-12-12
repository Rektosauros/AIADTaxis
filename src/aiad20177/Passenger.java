package aiad20177;

import jade.core.Agent;

import java.io.IOException;

import MSGS.CentralPassenger;
import MSGS.PassengerCentral;
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;

public class Passenger extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int idCounter = 1;
	int x, y, stopX, stopY, id;

	public Passenger(int x1, int y1, int sX, int sY) {

		this.id = idCounter;
		idCounter++;
		this.x = x1;
		this.y = y1;
		this.stopX = sX;
		this.stopY = sY;

	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getStopX() {
		return stopX;
	}

	public void setStopX(int stopX) {
		this.stopX = stopX;
	}

	public int getStopY() {
		return stopY;
	}

	public void setStopY(int stopY) {
		this.stopY = stopY;
	}

	// fim da classe Behaviour

	// método setup
	protected void setup() {
		String tipo = "";
		// obtém argumentos
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			tipo = (String) args[0];
		} else {
			System.out.println("Não especificou o tipo");
		}

		// regista agente no DF
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName(getName());
		sd.setType("Agente " + tipo);
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			e.printStackTrace();
		}

		// cria behaviour
		addBehaviour(new CyclicBehaviour() {

			private static final long serialVersionUID = 1L;

			private int state = 2;

			public void action() {

				// int state = 1;

				switch (state) {

				case 1:

					// message receive
					MessageTemplate message = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
					ACLMessage msg = myAgent.receive(message);

					CentralPassenger receive;
					if (msg != null) {
						receive = null;
						try {
							receive = (CentralPassenger) msg.getContentObject();
						} catch (UnreadableException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("Passenger arrived at destination x:" + stopX + ",y:" + stopY
								+ " with the help of Taxi " + receive.getTaxiId());
					}
					try {
						Thread.sleep(1000000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					/*
					 * if(receive.getMSG() == true) {
					 * 
					 * System.out.println("Passenger is taking a ride \n" );
					 * 
					 * }
					 */
					// reply

					break;

				case 2:
					System.out.println("New Passenger with id " + id + " request a taxi at position x:" + x + ", y:" + y
							+ " to get to destination x:" + stopX + ", y:" + stopY);
					PassengerCentral inform = new PassengerCentral(id, x, y, stopX, stopY);
					ACLMessage msg2 = new ACLMessage(ACLMessage.REQUEST);
					try {
						msg2.setContentObject(inform);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// System.out.println("1");
					// msg2 = blockingReceive();
					// System.out.println("2");
					msg2.addReceiver(new AID("Central", AID.ISLOCALNAME));
					send(msg2);

					state = 1;
					break;
				}
			}

		}); // fim do metodo setup

	}
} //