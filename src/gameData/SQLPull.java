package gameData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import gui.Printer;
/**
 * This class is responsible to pull the data from the SQL.
 * The motivation for this is then to show it to the user.
 * @author Elad and Yoav.
 *
 */
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

////////////////////////***Constructor****///////////////////////////////////////////

	public SQLPull(Printer printer) {
		super();
		this.printer = printer;
	}

///////////////////////////*** Methods ***//////////////////////////////////////////

	/**
	 * This method conccting to the server with a some filter and get the data.
	 * @param option The option that we want to filter.
	 */
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

	/**
	 * This method Get the headline of the table of SQl.
	 * @return A String of this.
	 */
	public static String getHeadline() {
		return("FirstID\tSecondID\tThirdID\tLogTime\tPoint\tGameID");
	}

	/**
	 * This method get the max For Scenario.
	 * @param statement That we use.
	 * @return A String
	 */
	public String getMaxForScenario(Statement statement) {
		return getMaxForScenario(statement, scenarioIndex, null);
	}
	
	/**
	 * This method get the max For Scenario by some Filter.
	 * @param statement That we use.
	 * @return A String
	 */
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

	/**
	 * This method get the last Game data of the users.
	 * @param statement That we want to check for.
	 * @return A String of the data.
	 */
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
	
	/**
	 * This method get the last Game data of the users.
	 * @param statement That we want to check for.
	 * @return A String of the data.
	 */
	private String getMyBest(Statement statement) {
		FilterSQLDecorator filter = new FilterMyID(new FilterSQLBasic(), id1, id2, id3);
		return getMaxForScenario(statement, scenarioIndex, filter);
	}

	/**
	 * This method get the best of All Games data of the users.
	 * @param statement That we want to check for.
	 * @return A String of the data.
	 */
	public String getAllMyBest(Statement statement) {
		FilterSQLDecorator filter = new FilterMyID(new FilterSQLBasic(), id1, id2, id3);
		
		StringBuilder allGames = new StringBuilder();
		for (int i=1; i<10; i++) {
			allGames.append(getMaxForScenario(statement, i, filter));
			allGames.append('\n');
		}
		return allGames.toString();
	}
	
	/**
	 * This method get the best Game data that that in the SQL .
	 * @param statement That we want to check for.
	 * @return A String of the data.
	 */
	public String getAllBest(Statement statement) {
		
		StringBuilder allGames = new StringBuilder();
		for (int i=1; i<10; i++) {
			allGames.append(getMaxForScenario(statement, i, null));
			allGames.append('\n');
		}
		return allGames.toString();
	}
	
	/**
	 * This method find the scenario Index of some statement.
	 * @param statement That we look for.
	 */
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


	/**
	 * This method print the result of that we send to her.
	 * @param resultSet  That we want to print.
	 * @return A String of The result
	 */
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
