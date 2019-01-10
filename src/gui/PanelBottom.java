package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

//import com.sun.net.ssl.internal.www.protocol.https.Handler;

import gameData.Report;
/**
 * This class is the Bottom Panel of main window for the GUI.
 * This panel in charge on display the user all the data of the game on live!.
 * @author Yoav and Elad.
 * @version 1.0
 *
 */
public class PanelBottom extends JPanel {

	MainWindow window;
	JLabel score = new JLabel();
	JLabel timeLeft = new JLabel();
	JLabel killByGhosts = new JLabel();
	JLabel outOfBox = new JLabel();
	
////////////////////////***Constructor****///////////////////////////////////////////

	public PanelBottom(MainWindow window) {
		super();
		this.window = window;
		this.setLayout(new GridLayout(1, 7));
		initPanel();
	}
	/**
	 * This method initialization the panel
	 */
	private void initPanel() {
		
		add(score);
		add(timeLeft);
		add(killByGhosts);
		add(outOfBox);
	}
	
	/**
	 * This method refresh the data of the panel to make it live.
	 * @param report The update report from the server.
	 */
	public void refresh(Report report) {
		score.setText("" + report.getScore());
		timeLeft.setText(" " + report.getTimeLeft());
		killByGhosts.setText(" " + report.getKillByGhosts());
		outOfBox.setText(" " + report.getOutOfBox());
	}

}
