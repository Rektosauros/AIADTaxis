package MSGS;

import java.io.Serializable;


public class TaxiCentral implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private int taxiID;

		private int currentX;
		private int currentY;
		
		private boolean occupied;

		public TaxiCentral(int id, int x, int y, boolean vacancy) {

			this.taxiID = id;
			this.occupied = vacancy;
			this.currentX = x;
			this.currentY = y;

		}

		public int getTaxiID() {
			return taxiID;
		}

		public void setTaxiID(int passengerID) {
			this.taxiID = passengerID;
		}

		public int getCurrentX() {
			return currentX;
		}

		public void setCurrentX(int currentX) {
			this.currentX = currentX;
		}

		public int getCurrentY() {
			return currentY;
		}

		public void setCurrentY(int curretnY) {
			this.currentY = curretnY;
		}
		
		public boolean getVacancy() {
			
			return occupied;
			
		}

}