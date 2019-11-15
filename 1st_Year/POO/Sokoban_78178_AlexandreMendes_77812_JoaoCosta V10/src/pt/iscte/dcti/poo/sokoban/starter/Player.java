package pt.iscte.dcti.poo.sokoban.starter;

//import java.util.Random;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Position;
import pt.iul.ista.poo.utils.Vector2D;

public class Player extends AbstractObject implements ActiveObject{

	private int bateria;
	private int movimentos;
	
	private static Player INSTANCE = new Player();
	
	private Player(){ // Construtora;
		super(new Position(0, 0), "Empilhadora_D");
		bateria = 100;
		movimentos = 0;
	} // Fim da construtora;
	
	public static Player getPlayer() {
		return INSTANCE;
	}
	
	public void resetStats() {
		bateria = 100;
		movimentos = 0;
		deleteObservers();
	}
	
	@Override
	public void setPosition(Position p) {
		super.setPosition(p);
	}
	
	@Override
	public int getLevel() {
		return 1;
	}
	
	@Override
	public boolean isTransposable() {
		return false;
	}
	
	public int getMovimentos() {
		return movimentos;
	} // Fim da funcao getMovimentos();
	
	public void charge(int quantidade) throws IllegalArgumentException { // Aumenta a bateria do carrinho, de acordo
		if(quantidade < 0)
			throw new IllegalArgumentException("Nao aceita numeros negativos");
		int bateria = this.bateria + quantidade; // com a quantidade dada em argumento;
		this.bateria = Math.min(101, Math.max(0, bateria)); // Bateria maxima de 100 mas como o carro so anda depois de carregar, bateria maxima de 101;
	} // Fim da funcao charge(int quantidade);
	
	
	public int getBateria() {
		return bateria;
	} // Fim da funcao getBateria();

	public void move(Direction direction) { // funcao para se mover
		
		Vector2D move = direction.asVector(); // Guarda o vector da direcao dada;
		
		int nivelActual = SokobanGame.getGame().getLevel(); // Guarda o nivel actual para comprar no final;
		
		Position newPosition = getPosition().plus(move); // Adiciona o vetor direcao a uma nova posicao;
		
		rotateCar(direction); // Usa a funcao rotateCar para rodar a sua imagem;
		
		AbstractObject obj = SokobanGame.objectInPosition(this ,newPosition);
		
		actionUsingObject(obj, direction); // chama a funcao estatica de descobrir o objecto na nova posicao, e diz-lhe para fazer uma acao de acordo com o tipo de objecto;
		
		if (newPosition.getX()>=0 && newPosition.getX()<10 &&
			newPosition.getY()>=0 && newPosition.getY()<10 &&
			isValidMove(newPosition)){ // verifica se o jogador se pode mover;
			
			bateria--; // reduz-lhe a bateria;
			
			movimentos++; // aumenta-lhe os movimentos;
			
			setPosition(newPosition); // da-lhe a nova posicao;
			
			if(bateria <= 0 && nivelActual == SokobanGame.getGame().getLevel()) // Se a bateria acabar e ainda nao passou de nivel;
				SokobanGame.getGame().gameOver(); // Reinicia o mapa;
			
			ImageMatrixGUI.getInstance().setStatusMessage(SokobanGame.getGame().statusMessage()); // faz update da mensagem do jogo (movimentos, bateria, level...);
			
			if(obj != null && !obj.makeDisapear()) {
				obj.notifyObj();
			} else {
				this.notifyObj();
			}
				
		}
		
		ImageMatrixGUI.getInstance().update(); // Pinta o frame de acordo com as mudancas;
	} // Fim da funcao move(Direction direction);
	
	private void actionUsingObject(AbstractObject obj, Direction direction) { // executa uma acao de acordo com o objecto dado;
		if(obj != null) { // Se existe objecto!
			if(obj.isMovable())
				((ActiveObject) obj).move(direction);
			if(obj.isUsable())
				((UsableObject) obj).use();
		}
	} // Fim da funcao actionUsingObject(AbstractObject obj, Direction direction);
	
	@Override
	public void disapear() {
		ImageMatrixGUI.getInstance().removeImage(this);
		SokobanGame.getGame().getObjects().remove(this);
		SokobanGame.getGame().gameOver();
	}
	
	private void rotateCar(Direction direction) { // Muda a Imagem do objecto Empelhadora de acordo com a direcao dada;
		switch (direction) {
		case UP:
			setName("Empilhadora_U");
			break;
		case DOWN:
			setName("Empilhadora_D");
			break;
		case LEFT:
			setName("Empilhadora_L");
			break;
		case RIGHT:
			setName("Empilhadora_R");
			break;

		default:
			break;
		}
	} // Fim da funcao rotateCar(Direction direction);
	
	public boolean isValidMove(Position position) { // Verificar se nao ha paredes a frente e que se pode mover
		for(AbstractObject obj : SokobanGame.getGame().getObjects()) {
			if(obj.getPosition().equals(position) && !obj.isTransposable())
				return false;
		}
		return true;
	} // Fim da funcao canMove(Position position);
	
} // Fim da classe Player;
