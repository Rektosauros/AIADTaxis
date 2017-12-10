package aiad20177;
import java.io.IOException;

import MSGS.AskTaxi;
import MSGS.CentralPassenger;
import MSGS.PassengerCentral;
import MSGS.TaxiCentral;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class Central extends Agent{
		
		private static final long serialVersionUID = 1L;
		
		public Central() {
			
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
	      sd.setType("Agente " + tipo);
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
	    	  PassengerCentral receive;
	    	  TaxiCentral receive2;
	    	  
	    	  switch (state) {
	    	  
	    	  case 1:
	    		  
	    		  //message receive
	    		MessageTemplate message = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
	  	        ACLMessage msg = myAgent.receive(message);
	  	         if(msg != null) {
	  	        	 receive = null;
	  	        	 try {
	  			receive = (PassengerCentral) msg.getContentObject();
	  				} catch (UnreadableException e) {
	  					// TODO Auto-generated catch block
	  					e.printStackTrace();
	  				}
	  	         
	  	         
	  	        AskTaxi inform = new AskTaxi(receive.getPassengerID(), receive.getCurrentX(), receive.getCurrentY(), receive.getFinalX(), receive.getFinalY());
	    		 ACLMessage msg2 = new ACLMessage(ACLMessage.INFORM);
	    		 try {
					msg2.setContentObject(inform);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		 
	    		 msg2 = blockingReceive();
	    		 msg2.addReceiver(new AID("taxi", AID.ISLOCALNAME));
	    		 send(msg2);
	    		 
	    		 state = 2;
	    		 
	  	         }
	         
	         
	         
	    	  case 2:
	    		  MessageTemplate message2 = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
		  	        ACLMessage msg3 = myAgent.receive(message2);
		  	         if(msg3 != null) {
		  	        	 receive2 = null;
		  	        	 try {
		  			receive2 = (TaxiCentral) msg3.getContentObject();
		  				} catch (UnreadableException e) {
		  					// TODO Auto-generated catch block
		  					e.printStackTrace();
		  				}
		  	         
		  	       CentralPassenger informPassenger = new CentralPassenger(receive2.getTaxiID(), receive2.getVacancy());
		    		 ACLMessage msg4 = new ACLMessage(ACLMessage.INFORM);
		    		 try {
						msg4.setContentObject(informPassenger);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    		 
		    		 msg4 = blockingReceive();
		    		 msg4.addReceiver(new AID("Passenger", AID.ISLOCALNAME));
		    		 send(msg4);
	    	}
	    	  }
	      }

	      });   // fim do metodo setup

	}
}//