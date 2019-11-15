package pt.iscte.dcti.poo.sokoban.starter;

import java.util.Observable;
import java.util.Observer;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Position;
import pt.iul.ista.poo.utils.Vector2D;

public class BigStone  extends AbstractObject implements ActiveObject, Observer{
	
	private boolean stuck;

	public BigStone(Position position) {
		super(position, "BigStone");
		stuck = false;
	}
	
	@Override
	public int getLevel() {
		return 3;
	}
	
	@Override
	public boolean isTransposable() {
		return false;
	}
	
	@Override
	public boolean canFall() {
		return false;
	}
	
	@Override
	public boolean isMovable() {
		return true;
	}
	
	public void move(Direction direction) { // Funcao para se mover;

		Vector2D move = direction.asVector(); // Guarda o vector de movimento proveniente da direcao;
		
		Position newPosition = getPosition().plus(move); // Adiciona o vetor de movimento com o vetor de posicao;
		
		if (newPosition.getX()>=0 && newPosition.getX()<10 &&
			newPosition.getY()>=0 && newPosition.getY()<10 &&
			isValidMove(newPosition)){ // Verifica se nao esta a sair do mapa ou se nao existem objetos a sua frente;
			
			setPosition(newPosition); // Muda-lhe a sua posicao;
			
		} // Acaba a verificacao de movimento;
	} // Fim da funcao move(Direction direction);

	public boolean isValidMove(Position position) {
		if(stuck)
			return false;
		
		
		for(AbstractObject obj : SokobanGame.getGame().getObjects()) {
			if(obj.getPosition().equals(position) && !obj.equals(this) && !obj.isTransposable())
				return false;
		}
		return true;
	}

	@Override
	public void update(Observable o, Object arg) {
		AbstractObject obj = (AbstractObject) arg;
		if(obj.makeDisapear())
			stuck = true;
	}

}
