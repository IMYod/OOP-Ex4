package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import GeoObjects.Fruit;
import GeoObjects.Ghost;
import GeoObjects.AllObjects;
import GeoObjects.Packman;
import guiObjects.Line;
import guiObjects.Map;
import guiObjects.Pixel;

public class FrameBoard extends JPanel implements MouseListener {

	public MainWindow window;
	public AllObjects game;

	public Map map;
	private BufferedImage[] fruitsImages;
	private BufferedImage packmanImage;
	private BufferedImage ghostImage;
	private BufferedImage playerImage;

	public FrameBoard(MainWindow window, Map map) {
		this.window = window;
		this.map = map;

		//For painting a random images to the map.
		fruitsImages = new BufferedImage[6];
		try {
			fruitsImages[0] = ImageIO.read( new File("ImagesforGui\\apple.png" ));
			fruitsImages[1] = ImageIO.read( new File("ImagesforGui\\apple2.png" ));
			fruitsImages[2] = ImageIO.read( new File("ImagesforGui\\banana.png" ));
			fruitsImages[3] = ImageIO.read( new File("ImagesforGui\\orange.png" ));
			fruitsImages[4] = ImageIO.read( new File("ImagesforGui\\peach.png" ));
			fruitsImages[5] = ImageIO.read( new File("ImagesforGui\\watermalon.png" ));
			packmanImage = ImageIO.read( new File("ImagesforGui\\thePackman2.png" ));
			ghostImage =  ImageIO.read( new File("ImagesforGui\\ghost.jpg" ));
			playerImage =  ImageIO.read( new File("ImagesforGui\\layer.jpg" ));
		} catch (IOException exc) {
			System.out.println(exc.toString());
		}
		this.addMouseListener(this);
	}

	/////////////////////////////****Painting the map***///////////////////////////////////////

	public void paint(Graphics g)
	{
		//draw background
		g.drawImage(map.myImage,8, 51, this.getWidth(), this.getHeight(), this);

		//draw fruits
		for (Fruit fruit: game.fruits) {
			Pixel pixel = map.gps2pixel(fruit.getLocation(), this.getWidth(), this.getHeight());
			g.drawImage(fruitsImages[fruit.getRandImage()], pixel.x(), pixel.y(), this );
		}

		//draw packmans
		for (Packman packman: game.packmans) {
			Pixel pixel = map.gps2pixel(packman.getLocation(), this.getWidth(), this.getHeight());
			g.drawImage(packmanImage, pixel.x(), pixel.y(), this);
		}

		//draw ghosts
		for (Ghost ghost: game.ghosts) {
			Pixel pixel = map.gps2pixel(ghost.getLocation(), this.getWidth(), this.getHeight());
			g.drawImage(ghostImage, pixel.x(), pixel.y(), this);
		}


	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
