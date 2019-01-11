package gameData;

import java.util.Arrays;

import gui.Printer;
/**
 * This class responsible on the printing of the filtered SQL That we 
 * going to Show the user.
 * @author YoavS and Elad
 *
 */
public class SystemPrinter implements Printer {

///////////////////////////*** Methods ***//////////////////////////////////////////

	@Override
	public void print(String text) {
		System.out.println(SQLPull.getHeadline());
		System.out.println(Arrays.toString(text.split("\n")));
	}

}
