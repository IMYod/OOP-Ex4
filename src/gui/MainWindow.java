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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import Coords.MyCoords;
import GeoObjects.AllObjects;
import GeoObjects.Fruit;
import GeoObjects.Packman;
import GeoObjects.Point3D;
import Robot.Play;
import algorithm.Shortest;
import convertor.Csv2Game;
import convertor.Data2Game;
import gameData.Report;
import guiObjects.Pixel;
import guiObjects.Line;
import guiObjects.Map;
import guiObjects.Path;

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
	public PanelBoard myBoard;
	public PanelBottom bottom;
	public AllObjects game;
	public Press press = Press.NOTHING;
	public Play play;
	public File file;

	double azimuth = 0;

	private Csv2Game convertor = new Csv2Game();
	private Data2Game dataConvertor = new Data2Game();

	////////////////////***Constructors****///////////////////////////////////

	public MainWindow(Map map) 
	{		
		initMenu();
		myBoard = new PanelBoard(this, map);
		initPanels();
	}

	////////////////////***Menu Bar****///////////////////////////////////

	private void initMenu() 
	{
		MenuBar menuBar = new MenuBar();

		//new game menu
		Menu newGame = new Menu("new game");

		MenuItem importGame = new MenuItem("import");
		importGame.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				importCsv();
			}
		});

		MenuItem tryAgain = new MenuItem("try again");
		tryAgain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newGame();
			}
		});

		newGame.add(importGame);
		newGame.add(tryAgain);
		menuBar.add(newGame);

		//start menu
		Menu start = new Menu("start!");

		//playing by the mouse
		MenuItem manual = new MenuItem("manual");
		manual.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				chooseManualLocation();
			}
		});

		//playing automatic
		MenuItem auto = new MenuItem("auto");
		auto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Point3D startingPoint = chooseAutoLocation();
				play.setInitLocation(startingPoint.x(), startingPoint.y());
				startGame(true);
			}
		});

		start.add(manual);
		start.add(auto);
		menuBar.add(start);

		this.setMenuBar(menuBar);
	}

	private void initPanels() {
		this.add("Center", myBoard);
		myBoard.setVisible(true);

		bottom = new PanelBottom(this);
		add("South", bottom);
		bottom.setSize(this.getWidth(), 50);
		bottom.setVisible(true);
	}

	public boolean importCsv() {
		JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
		fc.setFileFilter(filter);
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
			newGame();
			return true;
		}
		return false;
	}

	private void newGame() {
		if (file == null)
			return;
		play = new Play(file.getAbsolutePath());
		play.setIDs(204533632, 206284267);
		game = convertor.convert(file);
		myBoard.setBounding(play.getBoundingBox());
		myBoard.repaintMe();
	}

	public void startGame(boolean automatic) {
		if (!automatic)
			press = Press.GO;
		Thread startAutoGame = new Thread(new Runnable() {

			@Override
			public void run() {
				play.start();
				Shortest algo = new Shortest(game, myBoard);
				while (play.isRuning()) {
					algo.refresh(game, myBoard);
					Report report = Report.Parse(play.getStatistics());
					//refresh the bottom menu!
					ArrayList<String> board_data = play.getBoard();
					game = dataConvertor.convert(board_data);
					play.rotate(azimuth);
					bottom.refresh(Report.Parse(play.getStatistics()));
					myBoard.repaintMe();
					
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if (automatic)
						autoRotate(algo);
				}

				if (!automatic)
					press = Press.NOTHING;

				// print the data & save to the course DB
				System.out.println("**** Done Game (user stop) ****");	
				String info = play.getStatistics();
				System.out.println(info);
			}
		});
		startAutoGame.start();
	}

	private void chooseManualLocation() {
		press = Press.FIRST_LOCATION;
	}

	private Point3D chooseAutoLocation() {
		Shortest algo = new Shortest(game, myBoard);
		Point3D startingPoint = algo.mostCenteral(100);
		System.out.println(startingPoint);
		if (startingPoint != null)
			return startingPoint;
		return new Point3D(32.1044700993651, 35.2079930001858, 0);
	}

	private void autoRotate(Shortest algo) {
		if (game.player != null && !game.fruits.isEmpty()) {
			Pixel playerPixelLocation = myBoard.map.gps2pixel(game.player.getLocation(), myBoard.getWidth(), myBoard.getHeight());
			Pixel nextPixel = algo.findPath(playerPixelLocation); //calculate what is the next target (in pixels)
			if (nextPixel != null) {
				azimuth = myBoard.mc.azimuth(game.player.getLocation(), //refresh the azimuth
						myBoard.map.pixel2gps(nextPixel, myBoard.getWidth(), myBoard.getHeight()));
			}
		}
	}

	public void clear() {
		// TODO Auto-generated method stub
		game.clear();
	}

}

