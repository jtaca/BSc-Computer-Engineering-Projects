package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Position;
import pt.iul.ista.poo.utils.Vector2D;

public class SmallStone extends AbstractObject implements ActiveObject{

	public SmallStone(Position position) {
		super(position, "SmallStone");
	}
	
	@Override
	public int getLevel() {
		return 1;
	}
	
	@Override
	public boolean isTransposable() {
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
	
	@Override
	public void disapear() {
		ImageMatrixGUI.getInstance().removeImage(this);
		SokobanGame.getGame().getObjects().remove(this);
	}
	

	public boolean isValidMove(Position position) { // Verifica se nao tem um objecto a sua frente, impedindo que este se mova;
		for(AbstractObject obj : SokobanGame.getGame().getObjects()) {
			if(obj.getPosition().equals(position) && !obj.equals(this) && !obj.isTransposable())
				return false;
		}
		return true;
	}

}
