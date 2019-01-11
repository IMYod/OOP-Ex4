package gameData;
/**
 * This class is part of the filters of the statistics.
 * This specific class enables filtering the mysql according to the users ID. 
 * @author Yaov and Elad
 *
 */
public class FilterMyID extends FilterSQLDecorator {

	String firstID = "204533632";
	String secondID = "206284267";
	String thirdID = "";

////////////////////////***Constructor****///////////////////////////////////////////

	public FilterMyID(FilterSQL prevFilter, String id1, String id2, String id3) {
		super.prevFilter = prevFilter;
		firstID = id1;
		secondID = id2;
		thirdID = id3;
	}
	
///////////////////////////*** Methods ***//////////////////////////////////////////

	
	/**
	 *This method return a String of the filter. 
	 */
	public String getFilter() {
		return (super.getFilter() + 
				" && FirstID=" +firstID
				+ (!secondID.equals("") ? " && SecondID = " +secondID : "")
				+ (!thirdID.equals("") ? " && ThirdID = " +thirdID : ""));
	}



}
