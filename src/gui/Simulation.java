package gui;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import jade.util.leap.LinkedList;

public class Simulation extends JFrame {
	
	private CityMap cityMap;
	public ArrayList<String> roadCoords;
	
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
		for(int i = 0;i<cityMap.getMap().length;i++) {
			for(int j = 0;j<cityMap.getMap()[i].length;j++) {
				if(cityMap.getMap()[j][i] == 2) {
					coord = j+";"+i;
					roadCoords.add(coord);
				}
			}
		}
		return roadCoords;
	}

}
