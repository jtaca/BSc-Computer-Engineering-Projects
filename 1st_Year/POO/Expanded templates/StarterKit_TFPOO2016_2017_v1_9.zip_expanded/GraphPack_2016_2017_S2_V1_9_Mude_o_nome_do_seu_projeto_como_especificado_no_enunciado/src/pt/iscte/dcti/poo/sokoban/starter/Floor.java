package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Position;

public class Floor extends GameElement {

	public Floor(Position position) {
		super(position, "Chao", 0);
		setUnder(true);
		
	}

}
