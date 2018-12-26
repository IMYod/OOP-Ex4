package convertor;

import java.util.ArrayList;

import GeoObjects.AllObjects;

public class Data2Game {
	
	private String[] csvRow;
	
	public Data2Game() {
		super();
	}
	
	public AllObjects convert(ArrayList<String> board_data) {
		Csv2Game csvConvertor = new Csv2Game();
		for(int i=0;i<board_data.size();i++) {
			String line = board_data.get(i);
			csvRow = line.split(",");
			csvConvertor.addData(csvRow);
		}
		return csvConvertor.getGame();
	}

}
