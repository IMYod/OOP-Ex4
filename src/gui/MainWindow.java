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
import convertor.Csv2Game;
import convertor.Data2Game;
import gameData.Report;
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
	public PanelBoard myBoard; 
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
		this.setMenuBar(menuBar);
	}

	private void initPanels() {
		this.add(myBoard);
		myBoard.setVisible(true);
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
		game = convertor.convert(file);
		chooseLocation();
	}

	public void startManuelGame() {
		press = Press.GO;
		Thread startGame = new Thread(new Runnable() {

			@Override
			public void run() {
				play.start();
				boolean continueGame = true;
				while (continueGame) {
					Report report = Report.Parse(play.getStatistics());
					//refresh the bottom menu!
					if (report.getTimeLeft() <= 0)
						continueGame = false;
					ArrayList<String> board_data = play.getBoard();
					game = dataConvertor.convert(board_data);
					play.rotate(azimuth);
					
					myBoard.repaintMe();
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				press = Press.NOTHING;

				// print the data & save to the course DB
				System.out.println("**** Done Game (user stop) ****");	
				String info = play.getStatistics();
				System.out.println(info);
			}
		});
		startGame.start();
	}

	private void chooseLocation() {
		press = Press.FIRST_LOCATION;
	}

	public void clear() {
		// TODO Auto-generated method stub
		game.clear();
	}

}

