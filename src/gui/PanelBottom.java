package gui;

import java.awt.Label;

import javax.swing.JPanel;

import gameData.Report;

public class PanelBottom extends JPanel {

	MainWindow window;
	Label score = new Label();
	Label timeLeft = new Label();
	Label killByGhosts = new Label();
	Label outOfBox = new Label();
	
	public PanelBottom(MainWindow window) {
		super();
		this.window = window;
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
