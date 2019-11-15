package pt.iul.ista.poo.example;

import java.util.Random;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.rogue.utils.Position;

public class Hero implements ImageTile {

	private Position position;

	public Hero(Position position) {
		this.position = position;
	}

	@Override
	public String getName() {
		return "Hero";
	}

	public void move() {
		Random gen = new Random();
		int x = gen.nextInt(3) - 1 + position.getX();
		int y = gen.nextInt(3) - 1 + position.getY();
		if (x < 0)
			x = 0;
		if (y < 0)
			y = 0;
		if (x > 9)
			x = 9;
		if (y > 9)
			y = 9;
		this.position = new Position(x, y);
		System.out.println(position);
	}

	@Override
	public Position getPosition() {
		return position;
	}

}
