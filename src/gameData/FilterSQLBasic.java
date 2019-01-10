package gameData;
/**
 * This  class is a simple filter for the SQL.
 * @author Yaov and Elad.
 *
 */
public class FilterSQLBasic implements FilterSQL {

///////////////////////////*** Methods ***//////////////////////////////////////////

	@Override
	public String getFilter() {
		return "SELECT * FROM logs WHERE true";
	}

}
