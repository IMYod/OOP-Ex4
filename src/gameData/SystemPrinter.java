package gameData;

import gui.Printer;

public class SystemPrinter implements Printer {

	@Override
	public void print(String text) {
		System.out.println(SQLPull.getHeadline());
		System.out.println(text);
	}

}
