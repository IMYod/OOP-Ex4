package gui;

import java.awt.TextArea;

import javax.swing.JFrame;

import gameData.SQLPull;

public class StaticsWindow extends JFrame implements Printer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StaticsWindow() {
		this.setTitle("Statics");
	}

	@Override
	public void print(String text) {
		this.add(new TextArea(SQLPull.getHeadline() + text));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 500);
		this.setVisible(true);
	}

}
