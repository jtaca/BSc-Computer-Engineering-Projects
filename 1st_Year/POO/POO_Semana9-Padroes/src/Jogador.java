import java.util.Observable;
import java.util.Observer;

public class Jogador implements Observer {

	private Gamble gamble = Gamble.NONE;

	public Jogador() {}

	@Override
	public void update(Observable o, Object arg) {
		Gamble result = (Gamble)arg;
		if (result == gamble)
			System.out.println(gamble + ": GANHEI");		
	}

	public void setGamble(Gamble gamble) {
		this.gamble  = gamble;
	}

}
