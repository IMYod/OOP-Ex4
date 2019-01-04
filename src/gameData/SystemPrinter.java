package gameData;

import java.util.Arrays;

import gui.Printer;

public class SystemPrinter implements Printer {

	@Override
	public void print(String text) {
		System.out.println(SQLPull.getHeadline());
		System.out.println(Arrays.toString(text.split("\n")));
	}

}
