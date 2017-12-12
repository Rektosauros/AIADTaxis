package MSGS;

import java.io.Serializable;

public class CentralPassenger implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private int PassengerID;
	private int taxiId;

	Boolean msg;

	public CentralPassenger(int id,int taxiId, Boolean msg1) {

		this.PassengerID = id;
		this.setTaxiId(taxiId);

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

	public int getTaxiId() {
		return taxiId;
	}

	public void setTaxiId(int taxiId) {
		this.taxiId = taxiId;
	}

}

