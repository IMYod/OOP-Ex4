package gameData;
/**
 * This class is a Decorator for the SQL filters.
 * This pattern will allow to make any combination for the filters.
 *  for more information https://en.wikipedia.org/wiki/Decorator_pattern
 * @author Yoav and Elad.
 *
 */
abstract class FilterSQLDecorator implements FilterSQL {

	FilterSQL prevFilter; 
	
///////////////////////////*** Methods ***//////////////////////////////////////////

	public String getFilter() {
		return prevFilter.getFilter();
	}


}
