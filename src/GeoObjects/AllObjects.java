package GeoObjects;

import java.util.HashSet;
import java.util.Set;

import convertor.Csv2Game;
import guiObjects.Pixel;


/**
 * A class that includes a collection of fruit and a collection of robots, 
 * the department has the ability to be built
 * From the csv file, see the metadata format, and save its information to such a file.
 */
public class AllObjects {

	public Set <Fruit> fruits = new HashSet<Fruit>();
	public Set <Packman> packmans = new HashSet<Packman>();
	public Set <Ghost> ghosts = new HashSet<Ghost>();
	public Set <Box> boxes = new HashSet<Box>();
	public Player player;


	// clears all the objects from the game.
	public void clear() {	
		fruits.clear();
		packmans.clear();
		ghosts.clear();
		boxes.clear();
		player = null;
	}

}

