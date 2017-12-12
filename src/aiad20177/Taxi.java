package aiad20177;

import java.io.IOException;
import java.io.Serializable;

import MSGS.AskTaxi;
import MSGS.TaxiCentral;
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;

public class Taxi extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int idCounter = 1;
	int id;
	int x, y;
	double taximeter = 0.0;
	boolean occupied = false;
	int freeSpace = 4;

	public Taxi(int x1, int y1, int capacity) {

		this.id = idCounter;
		idCounter++;
		this.x = x1;
		this.y = y1;
		this.freeSpace = capacity;

	}

	public int getID() {

		return this.id;

	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public boolean getOccupied() {

		return this.occupied;

	}

	public int getFreeSpace() {

		return freeSpace;

	}

	public double getTaximeter() {

		return taximeter;

	}

	public void setOccupied() {

		if (this.occupied == true) {

			this.occupied = false;

		} else {

			this.occupied = true;

		}

	}

	public void setFreeSpace(int clients) {

		this.freeSpace = this.freeSpace - clients;

	}

	// método setup
	protected void setup() {
		String tipo = "";
		// obtém argumentos
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			tipo = (String) args[0];
		} else {
			System.out.println("Não especificou o tipo ");
		}

		// regista agente no DF
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName(getName());
		sd.setType("TaxiAgent " + tipo);
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			e.printStackTrace();
		}

		addBehaviour(new CyclicBehaviour() {
			private static final long serialVersionUID = 1L;

			private int state = 1;

			// método action
			public void action() {

				switch (state) {

				case 1:
					// message receive
					MessageTemplate message = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
					ACLMessage msg = myAgent.receive(message);
					if (msg != null) {
						AskTaxi receive = null;
						try {
							receive = (AskTaxi) msg.getContentObject();
							System.out.println("Passenger id is " + receive.getIdPassenger() + "\nl");
						} catch (UnreadableException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// reply
						TaxiCentral response;

						if (occupied == false) {

							System.out.println("Taxi " + id + " at position x:" + x + ", y:" + y
									+ " is going to pickup passenger " + receive.getIdPassenger() + "at position x:"
									+ receive.getCurrentX() + ", y:" + receive.getCurrentY()
									+ "and is going to drop him off at the stop at x:" + receive.getFinalX() + ",y:"
									+ receive.getFinalY());
							response = new TaxiCentral(id, receive.getIdPassenger(), x, y, true);

						} else {
							System.out.println("Taxi is already occupied, cannot carry anymore passengers");

							response = new TaxiCentral(id, receive.getIdPassenger(), x, y, false);

						}
						ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);
						try {
							msg1.setContentObject(response);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						msg1.addReceiver(new AID("Central", AID.ISLOCALNAME));
						/*
						 * ACLMessage reply = msg.createReply();
						 * reply.setPerformative(ACLMessage.INFORM); try {
						 * reply.setContentObject((Serializable) response); } catch (IOException e) { //
						 * TODO Auto-generated catch block e.printStackTrace(); }
						 */

						send(msg1);
					}
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		});
	} // fim do metodo setup

	protected double distance(int x, int y, int x1, int y1) {
		return Math.sqrt(Math.pow(x - x1, 2) + Math.pow(y - y1, 2));
	}

} //