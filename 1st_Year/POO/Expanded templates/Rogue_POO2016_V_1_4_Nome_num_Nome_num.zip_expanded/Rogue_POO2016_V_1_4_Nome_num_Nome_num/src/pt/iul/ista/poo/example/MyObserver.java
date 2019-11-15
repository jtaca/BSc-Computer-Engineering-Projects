package pt.iul.ista.poo.example;

import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

public class MyObserver implements Observer {

	@Override
	public void update(Observable arg0, Object arg1) {
		Integer keyCode = (Integer) arg1;
		if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_LEFT
				|| keyCode == KeyEvent.VK_RIGHT)
			System.out.println("Arrow keys!");
		System.out.println("Key pressed " + KeyEvent.getKeyText(keyCode));

	}

}
