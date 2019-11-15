package f1_2016;
import java.util.Random;

public class HighScore implements Comparable<HighScore>{
private String playerName;
private int points;


	public HighScore(String playerName, int points) {
	super();
	this.playerName = playerName;
	this.points = points;
	}
	
	@Override
	public int compareTo(HighScore o) {
		int i;
		if (this.points > o.points){
			i = 1;
		}else{
			i = 0;
		}
		return i;
	}
	
	
	@Override
	public String toString() {
		return  playerName + " = " + points ;
	}

	public String getPlayerName() {
		return playerName;
	}

	public int getPoints() {
		return points;
	}

	public static void main(String[] args) {
		Random i = new Random();
		for (int j = 0; j < args.length; j++) {
			System.out.println(i.nextInt (9));
		}
		

	}
}
