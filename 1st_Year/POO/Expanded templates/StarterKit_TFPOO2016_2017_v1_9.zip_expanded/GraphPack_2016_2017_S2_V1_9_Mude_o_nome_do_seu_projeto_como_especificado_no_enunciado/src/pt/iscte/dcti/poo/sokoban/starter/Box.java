package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Position;
import pt.iul.ista.poo.utils.Vector2D;

public class Box extends GameElement implements ObjectiveCondition {

	public Box(Position position) {
		super(position,"Caixote", 2);
		this.setMovable(true);
		this.setObjectiveCondition(true);
	}

	@Override
	public void move(Direction d) {
		Vector2D vectorNew = d.asVector();
		Position newPosition = this.getPosition().plus(vectorNew);
		GameElement Ele = SokobanGame.elementInPosition(this, newPosition);
		objectReacting(Ele, d);
		if(canMove(newPosition)){
			this.setPosition(newPosition);
		}
	}

	@Override
	public boolean canMove(Position position) {
		boolean result = true;
		for(GameElement obj : SokobanGame.getElements()) {
			if(obj.getPosition().equals(position) && (!obj.isUnder() && !obj.isCatchable())){
				result = false;
			}
		}
		if(!(position.getX()>=0 && position.getX()<10 & position.getY()>=0 && position.getY()<10)){
			result = false;
		}
		return result;
	}
	private void objectReacting(GameElement ele, Direction direction){
		if(ele != null) { 
			if(ele.isFallable())
				((Fallable)ele).makeFall(this);
		}
	}


}
