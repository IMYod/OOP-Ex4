package gameData;

public class FilterScenario extends FilterSQLDecorator {
	
	long scenario;
	final static long[] scenarios = new long[]{0, 2128259830L, 1149748017L, -683317070L, 1193961129L, 1577914705L, -1315066918L, -1377331871L, 306711633L, 919248096L};
	
	public FilterScenario(FilterSQL prevFilter, long scenario) {
		super.prevFilter = prevFilter;
		this.scenario = scenario;
	}
	
	public FilterScenario(FilterSQL prevFilter, int scenarioIndex) {
		this(prevFilter, scenarios[scenarioIndex]);
	}
	
	@Override
	public String getFilter() {
		return (super.getFilter() + (scenario!=0 ? " && SomeDouble = " + scenario : ""));
	}

}
