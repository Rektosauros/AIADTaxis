package gui;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Simulation extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CityMap cityMap;
	public ArrayList<String> roadCoords;
	public ArrayList<String> stopCoords;
	
	public Simulation() throws IOException {
		this.cityMap = new CityMap();
		getContentPane().add(this.cityMap);
	}
	
	public void startSimulation() {
		setSize(700, 800);
		setVisible(true);
	}
	
	public ArrayList<String> getRoadTiles() {
		roadCoords = new ArrayList<String>();
		String coord;
		String coords="";
		for(int i = 0;i<cityMap.getMap().length;i++) {
			for(int j = 0;j<cityMap.getMap()[i].length;j++) {
				if(cityMap.getMap()[j][i] == 2) {
					coord = j+";"+i;
					coords+=coord+ " ";
					roadCoords.add(coord);
				}
			}
		}
		System.out.println(coords);
		return roadCoords;
	}
	
	public ArrayList<String> getStopTiles(){
		stopCoords = new ArrayList<String>();
		String coord;
		for(int i = 0;i<cityMap.getMap().length;i++) {
			for(int j = 0;j<cityMap.getMap()[i].length;j++) {
				if(cityMap.getMap()[j][i] == 3) {
					coord = j+";"+i;
					stopCoords.add(coord);
				}
			}
		}
		return stopCoords;
	}
	
	public boolean setCityMapCoord(int x, int y, String new_tile) {
		return cityMap.setCoord(x, y, new_tile);
	}
	
	public String printCityMap() {
		return cityMap.printCityMap();
	}

}
