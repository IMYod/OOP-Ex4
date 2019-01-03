package gameData;

public class FilterMyID extends FilterSQLDecorator {

	String firstID = "204533632";
	String secondID = "206284267";
	String thirdID = "";
	
	public FilterMyID(FilterSQL prevFilter, String id1, String id2, String id3) {
		super.prevFilter = prevFilter;
		firstID = id1;
		secondID = id2;
		thirdID = id3;
	}

	public String getFilter() {
		return (super.getFilter() + 
				" && FirstID=" +firstID
				+ (!secondID.equals("") ? " && SecondID = " +secondID : "")
				+ (!thirdID.equals("") ? " && ThirdID = " +thirdID : ""));
	}



}
