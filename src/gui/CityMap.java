package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class CityMap extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int city[][] = { 
			{ 2, 2, 2, 3, 2, 1, 1, 2, 1, 1, 1, 1, 1, 3, 2, 2 },
			{ 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1 }, 
			{ 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1 }, 
			{ 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 3, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 2, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 2, 2 }, 
			{ 1, 1, 1, 2, 1, 1, 1, 3, 1, 1, 1, 2, 1, 1, 1, 1 },
			{ 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 2, 1, 1, 1, 1 },
			{ 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1 },
			{ 2, 3, 2, 1, 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 2, 2, 2, 3 },
			{ 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1 } };

	private BufferedImage taxi, road, sidewalk, passenger, taxistop;

	public CityMap() throws IOException {
		loadIcons();
	}

	private void loadIcons() throws IOException {
		this.taxi = ImageIO.read(new File("images/taxi.png"));
		this.road = ImageIO.read(new File("images/road.gif"));
		this.sidewalk = ImageIO.read(new File("images/sidewalk.jpg"));
		this.passenger = ImageIO.read(new File("images/passenger.png"));
		this.taxistop = ImageIO.read(new File("images/taxistop.png"));
	}

	@Override
	public void paintComponent(Graphics graphic) {
		super.paintComponent(graphic); // clean
		Graphics2D graphics = (Graphics2D) graphic;
		drawMap(graphics);
	}

	private void drawMap(Graphics2D graphics) {
		for (int i = 0; i < city.length; i++) {
			for (int j = 0; j < city[i].length; j++) {
				switch (city[i][j]) {
				case 1:
					drawImages(graphics, this.sidewalk, i, j);
					break;
				case 2:
					drawImages(graphics, this.road, i, j);
					break;
				case 3:
					drawImages(graphics, this.taxistop, i, j);
					break;
				case 4:
					drawImages(graphics, this.taxi, i, j);
					break;
				case 5:
					drawImages(graphics, this.passenger, i, j);
					break;
				default:
					break;
				}
			}
		}
	}

	private void drawImages(Graphics2D graphics, BufferedImage image, int i, int j) {
		int sizeX, sizeY;

		/*if (getWidth() > getHeight()) {
			sizeX = image.getWidth();
			sizeY = sizeX;
		} else {
			sizeX = getWidth() / 10;
			sizeY = sizeX;
		}*/

		graphics.drawImage(image, j * image.getWidth(), i * image.getHeight(), null);

	}
	
	public int[][] getMap(){
		return city;
	}
	
	public boolean setCoord(int x, int y, String new_tile) {
		
		if(city[x][y]==2) {
			switch (new_tile) {
			case "taxi":
				city[x][y]=4;
				return true;
			case "passenger":
				city[x][y]=5;
				return true;
			default:
				return false;
			}
		}
		return false;
	}
	
	public String printCityMap() {
		String mapString="";
		
		for(int i=0;i<city.length;i++) {
			mapString+=System.getProperty("line.separator");
			for(int j=0;j<city[i].length;j++) {
				mapString+=""+city[j][i];
			}
		}
		return mapString;
	}

}
