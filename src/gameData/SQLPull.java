package gameData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import gui.Printer;

public class SQLPull {

	String jdbcUrl="jdbc:mysql://ariel-oop.xyz:3306/oop"; //?useUnicode=yes&characterEncoding=UTF-8&useSSL=false";
	String jdbcUser="student";
	String jdbcPassword="student";

	String id1 = "204533632";
	String id2 = "206284267";
	String id3 = "";
	
	FilterSQLBasic basic = new FilterSQLBasic();
	Printer printer;
	
	int scenarioIndex = 0;

	public SQLPull(Printer printer) {
		super();
		this.printer = printer;
	}

	public void connect(FilterOption option) {

		try {		
			//get connection			
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = 
					DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);

			Statement statement = connection.createStatement();
			
			findScenarioIndex(statement);

			//print something...
			switch (option) {
			case getAllMyBest:
				printer.print(getAllMyBest(statement));
				break;			
			case getMaxForScenario:
				printer.print(getMaxForScenario(statement));
				break;
			case getMyBest:
				printer.print(getMyBest(statement));
				break;
			case getMyLastGame:
				printer.print(getMyLastGame(statement));
				break;
			case getAllBest:
				printer.print(getAllBest(statement));
				break;
			default:
				break;
			}

			//close
			statement.close();		
			connection.close();	
		}
		catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getHeadline() {
		return("FirstID\tSecondID\tThirdID\tLogTime\tPoint\tGameID");
	}

	public String getMaxForScenario(Statement statement) {
		return getMaxForScenario(statement, scenarioIndex, null);
	}
	
	public String getMaxForScenario(Statement statement, int gameIndex, FilterSQLDecorator otherFilter) {		
		if (gameIndex == 0)
			return null;
		
		//select data
		FilterSQL filter;
		if (otherFilter == null)
			filter = new FilterScenario(new FilterSQLBasic(), gameIndex);
		else
			filter = new FilterScenario(otherFilter, gameIndex);
		
		String currentScenarioQuery = filter.getFilter();

		try {
			ResultSet resultSet = statement.executeQuery(currentScenarioQuery);
			String maxResult = null;
			double maxPoints = Double.MIN_VALUE;

			while(resultSet.next())
			{
				double points = resultSet.getDouble("Point"); 
				if (points > maxPoints) {
					maxPoints = points;
					maxResult = printResault(resultSet);
				}
			}
			resultSet.close();
			return maxResult;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getMyLastGame(Statement statement) {

		//select data
		FilterSQL filter = new FilterMyID(new FilterSQLBasic(), id1, id2, id3);
		String currentScenarioQuery = filter.getFilter();

		try {
			ResultSet resultSet = statement.executeQuery(currentScenarioQuery);
			String lastResult = null;

			while(resultSet.next())
			{
				lastResult = printResault(resultSet);
			}

			resultSet.close();
			return lastResult;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private String getMyBest(Statement statement) {
		FilterSQLDecorator filter = new FilterMyID(new FilterSQLBasic(), id1, id2, id3);
		return getMaxForScenario(statement, scenarioIndex, filter);
	}

	public String getAllMyBest(Statement statement) {
		FilterSQLDecorator filter = new FilterMyID(new FilterSQLBasic(), id1, id2, id3);
		
		StringBuilder allGames = new StringBuilder();
		for (int i=1; i<10; i++) {
			allGames.append(getMaxForScenario(statement, i, filter));
			allGames.append('\n');
		}
		return allGames.toString();
	}
	
	public String getAllBest(Statement statement) {
		
		StringBuilder allGames = new StringBuilder();
		for (int i=1; i<10; i++) {
			allGames.append(getMaxForScenario(statement, i, null));
			allGames.append('\n');
		}
		return allGames.toString();
	}
	
	public void findScenarioIndex(Statement statement) {
		String lastGame = getMyLastGame(statement);
		
		//find the gameID in the string
		int startIndex = lastGame.lastIndexOf('\t')+1;
		long scenario = Long.parseLong(lastGame.substring(startIndex));
		
		//find the index of the string
		for (int i=1; i<FilterScenario.scenarios.length; i++)
			if (FilterScenario.scenarios[i] == scenario) {
				scenarioIndex = i;
				return;
			}

		//not found
		System.out.println("not found last game");
		scenarioIndex = 0;
	}


	public String printResault(ResultSet resultSet) {
		try {
			return (resultSet.getInt("FirstID")+"\t" +
					resultSet.getInt("SecondID")+"\t" +
					resultSet.getInt("ThirdID")+"\t" +
					resultSet.getTimestamp("LogTime") +"\t" +
					resultSet.getDouble("Point") +"\t" +
					resultSet.getLong("SomeDouble"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
