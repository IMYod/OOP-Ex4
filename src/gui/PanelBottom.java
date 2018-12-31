package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.sun.net.ssl.internal.www.protocol.https.Handler;

import gameData.Report;

public class PanelBottom extends JPanel {

	MainWindow window;
	JLabel score = new JLabel();
	JLabel timeLeft = new JLabel();
	JLabel killByGhosts = new JLabel();
	JLabel outOfBox = new JLabel();
	
	public PanelBottom(MainWindow window) {
		super();
		this.window = window;
		this.setLayout(new GridLayout(1, 4));
		initPanel();
	}

	private void initPanel() {
		add(score);
		add(timeLeft);
		add(killByGhosts);
		add(outOfBox);
	}
	
	public void refresh(Report report) {
		score.setText("score: " + report.getScore());
		timeLeft.setText("time left: " + report.getTimeLeft());
		killByGhosts.setText("kill by ghosts: " + report.getKillByGhosts());
		outOfBox.setText("out of box: " + report.getOutOfBox());
	}

}
