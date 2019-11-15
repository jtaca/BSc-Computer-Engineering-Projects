package f1_2016;

//import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Escrita {
	
	public static void bestScoresUpdate(HighScore score,List<HighScore>highscores){
		
		highscores.add(score);
		Collections.sort(highscores);
		highscores.remove(highscores.size()-1);
		
		//PrintWriter p = new PrintWriter(new File(HS_FILE_NAME));
		
	}
	
	public static void main(String[] args) {
		Random i = new Random();
		List<HighScore> scores = new ArrayList<HighScore>();
		
		for (int j = 0; j < 10; j++) {
			scores.add(new HighScore("A",i.nextInt(30)));
		}
		
		Collections.sort(scores);
		bestScoresUpdate(new HighScore("A",i.nextInt(30)),scores);
		

	}

}
