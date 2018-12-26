package convertor;

public class Report {
	
	String Date;
	double totalTime;
	double score;
	double timeLeft;
	int killByGhosts;
	int outOfBox;

	public Report() {
		super();
	}
	
	public static Report Parse(String report) {
		//TODO
	}

	public String getDate() {
		return Date;
	}

	public double getTotalTime() {
		return totalTime;
	}

	public double getScore() {
		return score;
	}

	public double getTimeLeft() {
		return timeLeft;
	}

	public int getKillByGhosts() {
		return killByGhosts;
	}

	public int getOutOfBox() {
		return outOfBox;
	}
	
}
