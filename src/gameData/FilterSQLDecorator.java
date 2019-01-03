package gameData;

abstract class FilterSQLDecorator implements FilterSQL {

	FilterSQL prevFilter;
	
	public String getFilter() {
		return prevFilter.getFilter();
	}


}
