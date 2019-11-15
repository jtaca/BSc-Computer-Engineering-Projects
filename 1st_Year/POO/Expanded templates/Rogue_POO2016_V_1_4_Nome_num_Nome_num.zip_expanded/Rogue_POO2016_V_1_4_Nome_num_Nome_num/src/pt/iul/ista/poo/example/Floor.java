package pt.iul.ista.poo.example;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.rogue.utils.Position;

public class Floor implements ImageTile {

	private Position position;

	public Floor(Position position) {
		this.position = position;
	}

	@Override
	public String getName() {
		return "Floor";
	}

	@Override
	public Position getPosition() {
		return position;
	}

}