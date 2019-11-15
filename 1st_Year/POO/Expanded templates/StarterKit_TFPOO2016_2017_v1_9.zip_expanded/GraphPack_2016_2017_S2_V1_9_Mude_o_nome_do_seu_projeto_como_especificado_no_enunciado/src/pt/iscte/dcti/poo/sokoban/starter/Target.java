package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Position;

public class Target extends GameElement implements Objective{

	public Target(Position position) {
		super(position, "Alvo", 1);
		setUnder(true);
		setObjective(true);
		
	}
	
	public boolean checkBox(){
		if (!(SokobanGame.elementInPosition(this, this.getPosition()) == null)){
			return SokobanGame.elementInPosition(this, this.getPosition()).isObjectiveCondition();
		}
		return false;
	}

}
