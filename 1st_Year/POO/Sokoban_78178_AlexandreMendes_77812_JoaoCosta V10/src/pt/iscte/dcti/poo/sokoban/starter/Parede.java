package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Position;

public class Parede extends AbstractObject {

	public Parede(Position position) { // Constructor;
		super(position, "Parede");
	} // Fim do constructor;
	
	@Override
	public int getLevel() {
		return 1;
	}
	
	@Override
	public boolean isTransposable() {
		return false;
	}
	
} // Fim da class Parede;