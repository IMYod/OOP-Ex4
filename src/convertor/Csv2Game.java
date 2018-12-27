package convertor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import GeoObjects.AllObjects;
import GeoObjects.Box;
import GeoObjects.Fruit;
import GeoObjects.Ghost;
import GeoObjects.Packman;
import GeoObjects.Player;
import GeoObjects.Point3D;
/**
 * This class takes a csv file, with a data of the game, and making the game.
 * The Game will show on the map. 
 *
 *
 */
public class Csv2Game {

	private AllObjects game;
	private File file;
	private String csvName;
	private int Type, id, Lat, Lon, Alt, speed, radius;

	public AllObjects convert(String fileName) {
		file = new File(fileName);
		csvName = fileName;
		createGame();
		return game;
	}
	public AllObjects convert(File f) {
		file = f;
		csvName = f.getName();
		createGame();
		return game;
	}

	private void createGame() 
	{
		game =  new AllObjects();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) 
		{

			String line = ""; //one row from the csv file				
			line = br.readLine();
			setTitles(line.split(","));

			while ((line = br.readLine()) != null) //add rows
			{
				String[] csvRow = line.split(","); //one row from the csv file, separated
				addData(csvRow); ///add to the sets
			}

		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}	

	}

	public void setTitles(String[] input) {
		for (int i=0; i<input.length; i++)
		{
			switch (input[i])
			{
			case "Type":
				Type = i;
				break;

			case "ID":
				id = i;
				break;

			case "Lat":
				Lat = i;
				break;

			case "Lon":
				Lon = i;
				break;

			case "Alt":
				Alt = i;
				break;

			case "Speed/Weight":
				speed = i;
				break;

			case "Radius":
				radius = i;
				break;

			default:
				break;
			}
		}
	}

	/**
	 * This function knows to add a fruit or packman to their Collections.
	 * The value that distinguishes is the "Type":
	 * "P" - packman.
	 * "F"- fruit.  
	 * @param csvRow
	 */
	public void addData(String[] csvRow)
	{

		Point3D point1 = new Point3D(Double.parseDouble(csvRow[Lat]),
				Double.parseDouble(csvRow[Lon]),Double.parseDouble(csvRow[Alt]));
		if (csvRow[Type].equals("P"))
			game.packmans.add(new Packman(point1, (int)Double.parseDouble(csvRow[id]), Double.parseDouble(csvRow[speed]),
					Double.parseDouble(csvRow[radius])));
		else if (csvRow[Type].equals("F"))
			game.fruits.add(new Fruit(point1, (int)Double.parseDouble(csvRow[id]), Double.parseDouble(csvRow[speed])));

		else if (csvRow[Type].equals("G"))
			game.ghosts.add(new Ghost(point1, (int)Double.parseDouble(csvRow[id]), Double.parseDouble(csvRow[speed]),
					Double.parseDouble(csvRow[radius])));

		else if (csvRow[Type].equals("B")) {
			Point3D point2 = new Point3D(Double.parseDouble(csvRow[speed]),
					Double.parseDouble(csvRow[radius]),0);
			game.boxes.add(new Box(point1, (int)Double.parseDouble(csvRow[id]), point2, 0.0, 0.0));
		}
		
		else if (csvRow[Type].equals("M")) {
			game.player = new Player(point1, (int)Double.parseDouble(csvRow[id]), Double.parseDouble(csvRow[speed]),
					Double.parseDouble(csvRow[radius]));
		}
	}

	public AllObjects getGame() {
		return game;
	}
	
	public void setGame(AllObjects game) {
		this.game = game;
	}
	
	

}
