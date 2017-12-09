package gui;

import java.io.IOException;

import javax.swing.JFrame;

public class Simulation extends JFrame {
	
	private CityMap cityMap;
	
	public Simulation() throws IOException {
		this.cityMap = new CityMap();
		getContentPane().add(this.cityMap);
	}
	
	public void startSimulation() {
		setSize(700, 800);
		setVisible(true);
	}

}
