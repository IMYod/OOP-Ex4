package gameData;

public class Report {
	
	String date;
	double totalTime;
	double score;
	double timeLeft;
	int killByGhosts;
	int outOfBox;

	public Report() {
		super();
	}
	
	public static Report Parse(String playReport) {
		Report report = new Report();
		playReport = "Play Report:Wed Dec 26 20:02:47 IST 2018 ,total time:1000.5 ,score:0.5, Time left:99000.5, kill by ghosts:56, out of box:56";
		String[] parse = playReport.split(",");
		report.date = parse[0].substring(12, parse[0].length()-1);
		report.totalTime = Double.parseDouble(parse[1].substring(11, parse[1].length()-1));
		report.score = Double.parseDouble(parse[2].substring(6, parse[2].length()));
		report.timeLeft = Double.parseDouble(parse[3].substring(11, parse[3].length()));
		report.killByGhosts = Integer.parseInt(parse[4].substring(16, parse[4].length()));
		report.outOfBox = Integer.parseInt(parse[5].substring(12, parse[5].length()));
		
		return report;
	}
	
	public static void main(String[] args) {
		Report.Parse("");
	}

	public String getDate() {
		return date;
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
