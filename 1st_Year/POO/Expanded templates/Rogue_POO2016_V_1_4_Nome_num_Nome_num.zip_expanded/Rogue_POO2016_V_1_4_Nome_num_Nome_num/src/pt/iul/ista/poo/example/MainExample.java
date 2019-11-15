package pt.iul.ista.poo.example;

import java.util.ArrayList;
import java.util.List;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.rogue.utils.Position;

public class MainExample {

	public static void main(String[] args) {

		ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
		gui.setName("Example");

		gui.addObserver(new MyObserver());
		
		List<ImageTile> tiles = new ArrayList<ImageTile>();
		for (int i = 0; i != 10; i++)
			for (int j = 0; j != 10; j++)
				tiles.add(new Floor(new Position(i, j)));

		Hero hero = new Hero(new Position(5, 5));

		tiles.add(hero);
		
		gui.newImages(tiles);
		gui.go();

		while (true) {
			hero.move();
			gui.update();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
