package gui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import Coords.MyCoords;
import GeoObjects.Fruit;
import GeoObjects.Packman;
import GeoObjects.Point3D;
import convertor.Csv2Game;
import convertor.Game2Csv;
import convertor.Game2kml;
import gameObjects.Game;
import gameObjects.Path;
import gameObjects.PathPoint;
import guiObjects.Pixel;
import guiObjects.Line;
import guiObjects.Map;

/**
 * This class is the main window of the GUI.
 * This class contains all the GUI Objects, and arranges them for a user-friendly window
 * This GUI powered by the main class.
 * @author Yoav and Elad.
 * @version 1.0
 *
 */
public class MainWindow extends JFrame
{
	public FrameBoard myBoard; 
	
////////////////////***Constructors****///////////////////////////////////

	public MainWindow(Map map) 
	{		
		myBoard = new FrameBoard(this, map);
		initFrame();		
	}

	private void initFrame() 
	{



///////////////////////***Mouse Listeners****//////////////////////////////
	
	@Override
	public void mouseClicked(MouseEvent arg) {
		Pixel pixel = new Pixel(arg.getX()-8, arg.getY()-51);
		System.out.println(pixel);
		if (addFruit) {
			Fruit fruit = new Fruit(map.pixel2gps(pixel, this.getWidth()-16, this.getHeight()-59), 1,
					game.fruits.size()+1);
			game.fruits.add(fruit);
			game.calculated = false;
			repaint();
		}
		if (addPackman) {
			AddPackman adder = new AddPackman(this, map.pixel2gps(pixel, this.getWidth()-16, this.getHeight()-59),
					game.getNextPackmanID());
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public boolean importCsv() {
		JFileChooser fc = new JFileChooser();
		Csv2Game convertor = new Csv2Game();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
		fc.setFileFilter(filter);
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			myBoard.game = convertor.convert(file);
			return true;
		}
		return false;
	}

	private int randNumber() {
		return (int)(Math.random()*100000000);
	}

	public void clear() {
		// TODO Auto-generated method stub
		myBoard.game.clear();
	}

	public void startPoint() {///kind of clear
		for (Packman packman: game.packmans) {
			packman.setLocation(packman.getStartLocation());
		}
		lines.clear();
		repaint();
	}
	
	public void stopRunning() {
		if (stopRunning)
			return;
		stopRunning=true;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}

