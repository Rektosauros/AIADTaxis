package MSGS;

import java.io.Serializable;

public class CentralPassenger implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private int PassengerID;

	Boolean msg;

	public CentralPassenger(int id, Boolean msg1) {

		this.PassengerID = id;


		this.msg = msg1;

	}
	
	public int getPassengerID() {
		return PassengerID;
	}

	public void setPassengerID(int passengerID) {
		this.PassengerID = passengerID;
	}

	public Boolean getMSG() {
		
		return this.msg;
		
	}

}

