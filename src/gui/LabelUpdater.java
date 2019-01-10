package gui;
import java.awt.Label;

import javax.swing.JLabel;

import gameData.Report;

/**
 * This class helps to update the labels on the bottom panel.
 * Part of the main window
 * @author Elad and Yoav.
 *
 */
public class LabelUpdater implements Runnable {

	JLabel label;
	String text;

////////////////////////***Constructor****///////////////////////////////////////////

	public LabelUpdater(JLabel killByGhosts, String text)
	  { 
	   this.label = killByGhosts;
	   this.text = text;
	  }
	

	/**
	 * This method Update the labal we want to!
	 */
	@Override
	public void run() {
		this.label.setText(text);
	}

}
