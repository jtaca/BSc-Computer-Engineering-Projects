import java.util.Observable;
import java.util.Random;

public class Croupier extends Observable {

	private Random generator = new Random();

	private static Croupier INSTANCE  = null;
	
	private Croupier() {
				
	}
	
	public static Croupier getInstance() {
		if (INSTANCE == null)
			INSTANCE = new Croupier();
		return INSTANCE;
	}
	
	public void throwTheDice() {
		int dice1 = 1 + generator.nextInt(6); 
		int dice2 = 1 + generator.nextInt(6);
		int lastResult = dice1 + dice2;
		System.out.println("Dice " + dice1 + " + " + dice2 + " = " + lastResult);
		setChanged();
		if (lastResult < 6)
			notifyObservers(Gamble.LOW);
		if (lastResult > 8)
			notifyObservers(Gamble.HIGH);
	}

	
	
}
