package aiad20177;
import jade.core.Agent;

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

	public class Taxi extends Agent{
		
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
		
		public int getX()
		{
			return this.x;
		}
		
		public int getY()
		{
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
				
			}
			else {
				
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
	      if(args != null && args.length > 0) {
	         tipo = (String) args[0];
	      } else {
	         System.out.println("Não especificou o tipo");
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
	      } catch(FIPAException e) {
	         e.printStackTrace();
	      }
	      
	      addBehaviour(new CyclicBehaviour(){
	    	  private static final long serialVersionUID = 1L;
	    	  
	    	  private int step = 1;
	    	  
	    	  
	    	  // método action
		      public void action() {
		    	  
		    	  int state = 1;
		    	  
		    	  switch (state) {
		    	  
		    	  case 1:
		    		  
		    		  //message receive
		    	 MessageTemplate message = MessageTemplate.MatchPerformative(ACLMessage.REQUEST); 
		         ACLMessage msg = myAgent.receive(message);
		         if(msg != null) {
		        	 try {
						AskTaxi receive = (AskTaxi) msg.getContentObject();
					} catch (UnreadableException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		         }
		         //reply
		         TaxiCentral response;
		         
		         if(occupied == false) {
		        	 
		        response = new TaxiCentral(id, x, y, true);
		        	 
		         }
		         else {
		        	 
		       response = new TaxiCentral(id, x, y, false);
		        	 
		         }
		         
		         ACLMessage reply = msg.createReply();
		         reply.setPerformative(ACLMessage.INFORM);
					try {
						reply.setContentObject((Serializable)response);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					send(reply);
	    	  
		    	  }}


	   });}   // fim do metodo setup

	   
	   protected double distance(int x, int y, int x1, int y1) {
			return Math.sqrt(Math.pow(x - x1, 2) + Math.pow(y - y1, 2));
		}

}   //