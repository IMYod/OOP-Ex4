package gameData;

public class FilterSQLBasic implements FilterSQL {

	@Override
	public String getFilter() {
		return "SELECT * FROM logs WHERE true";
	}

}
