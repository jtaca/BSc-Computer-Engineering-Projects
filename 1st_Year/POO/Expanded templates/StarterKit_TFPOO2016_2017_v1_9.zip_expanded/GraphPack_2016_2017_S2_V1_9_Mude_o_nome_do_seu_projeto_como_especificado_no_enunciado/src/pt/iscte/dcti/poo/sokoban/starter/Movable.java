package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Position;

public interface Movable {
	public void move(Direction d);
	public boolean canMove(Position p);
	
	
}
