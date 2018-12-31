package gui;

import java.awt.Label;

import javax.swing.JLabel;

import gameData.Report;

public class LabelUpdater implements Runnable {

	JLabel label;
	String text;
	
	public LabelUpdater(JLabel killByGhosts, String text)
	  { 
	   this.label = killByGhosts;
	   this.text = text;
	  }
	
	@Override
	public void run() {
		this.label.setText(text);
	}

}
