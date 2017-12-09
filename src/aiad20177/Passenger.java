package aiad20177;
import jade.core.Agent;
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;

	public class Passenger extends Agent{
		
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private static int idCounter = 1;
		int x, y, id;
		String destination;
		
		
		public Passenger(int x1, int y1, String destination1) {
			
			
			this.id = idCounter;
			idCounter++;
			this.x = x1;
			this.y = y1;
			this.destination = destination1;
			
			
		}
		
		
		public int getX()
		{
			return this.x;
		}
		
		public int getY()
		{
			return this.y;
		}
		
		public String getDestination() {
			
			return this.destination;
			
		}

	   // classe do behaviour
	   class PassengerBehaviour extends SimpleBehaviour {
	      private int n = 0;

	      // construtor do behaviour
	      public PassengerBehaviour(Agent a) {
	         super(a);
	      }

	      // método action
	      public void action() {
	    	  
	    	  int state = 1;
	    	  
	    	  switch (state) {
	    	  
	    	  case 1:
	    		  
	    		  //message receive
	         String receive;
	    	 MessageTemplate message = MessageTemplate.MatchPerformative(ACLMessage.REQUEST); 
	         ACLMessage msg = myAgent.receive(message);
	         if(msg != null) {
	        	 try {
					receive = (String) msg.getContentObject();
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	         }
	         //reply
	         
	         
	         
	    	  case 2:
	    		 String inform;
	 	    	 MessageTemplate informTemplate = MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL), 
	 	    			 									   MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL)); 
	 	         ACLMessage msg2 = myAgent.receive(informTemplate);
	    		  
	    	  }
	      }

	      // método done
	      public boolean done() {
	         return n==10;
	      }

	   }   // fim da classe Behaviour


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

	      // cria behaviour
/*	      TaxiBehaviour b = new TaxiBehaviour(this);
	      addBehaviour(b);
		  
	      // toma a iniciativa se for agente "pong"
	      if(tipo.equals("pong")) {
	         // pesquisa DF por agentes "ping"
	         DFAgentDescription template = new DFAgentDescription();
	         ServiceDescription sd1 = new ServiceDescription();
	         sd1.setType("Agente ping");
	         template.addServices(sd1);
	         try {
	            DFAgentDescription[] result = DFService.search(this, template);
	            // envia mensagem "pong" inicial a todos os agentes "ping"
	            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
	            for(int i=0; i<result.length; ++i)
	               msg.addReceiver(result[i].getName());
	            msg.setContent("pong");
	            send(msg);
	         } catch(FIPAException e) { e.printStackTrace(); }
	      }*/

	   }   // fim do metodo setup

	   // método takeDown
	   protected void takeDown() {
	      // retira registo no DF
	      try {
	         DFService.deregister(this);  
	      } catch(FIPAException e) {
	         e.printStackTrace();
	      }
	   }

	}   //