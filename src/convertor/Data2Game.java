package convertor;

import java.util.ArrayList;

import GeoObjects.AllObjects;
/**
 * This class make data for game!
 * @author Yoav and Elad.
 *
 */
public class Data2Game {
	
	private String[] csvRow;

////////////////////////***Constructor****///////////////////////////////////////////
	
	public Data2Game() {
		super();
	}
	
///////////////////////////*** Methods ***//////////////////////////////////////////

	public AllObjects convert(ArrayList<String> board_data) {
		Csv2Game csvConvertor = new Csv2Game();
		csvConvertor.setGame(new AllObjects());
		String[] titles = {"Type","ID","Lat","Lon","Alt","Speed/Weight","Radius"};
		csvConvertor.setTitles(titles);
		
		for(int i=0;i<board_data.size();i++) {
			String line = board_data.get(i);
			csvRow = line.split(",");
			csvConvertor.addData(csvRow);
		}
		return csvConvertor.getGame();
	}

}
