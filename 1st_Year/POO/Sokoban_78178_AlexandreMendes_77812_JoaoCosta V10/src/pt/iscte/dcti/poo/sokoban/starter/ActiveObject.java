package pt.iscte.dcti.poo.sokoban.starter;


import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Position;

public interface ActiveObject {
	
	public void move(Direction direction); // Objecto muda de posição
	
	public boolean isValidMove(Position position); // Objecto verifica se pode mudar de posição
	
}
