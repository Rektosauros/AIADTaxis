package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class CityMap extends JPanel {

	private int city[][] = { 
			{ 2, 2, 2, 2, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 2, 2 },
			{ 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1 }, 
			{ 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1 }, 
			{ 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 2, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2 }, 
			{ 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1 },
			{ 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 2, 1, 1, 1, 1 },
			{ 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1 },
			{ 2, 2, 2, 1, 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 2, 2, 2, 2 },
			{ 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1 } };

	private BufferedImage taxi, road, sidewalk, passenger;

	public CityMap() throws IOException {
		loadIcons();
	}

	private void loadIcons() throws IOException {
		this.taxi = ImageIO.read(new File("images/taxi.jpg"));
		this.road = ImageIO.read(new File("images/road.jpg"));
		this.sidewalk = ImageIO.read(new File("images/sidewalk.jpg"));
		this.passenger = ImageIO.read(new File("images/passenger.jpg"));
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
				default:
					break;
				}
			}
		}
	}

	private void drawImages(Graphics2D graphics, BufferedImage image, int i, int j) {
		int sizeX, sizeY;

		if (getWidth() > getHeight()) {
			sizeX = getHeight() / 10;
			sizeY = sizeX;
		} else {
			sizeX = getWidth() / 10;
			sizeY = sizeX;
		}

		graphics.drawImage(image, j + sizeX, i + sizeY, null);

	}

}
