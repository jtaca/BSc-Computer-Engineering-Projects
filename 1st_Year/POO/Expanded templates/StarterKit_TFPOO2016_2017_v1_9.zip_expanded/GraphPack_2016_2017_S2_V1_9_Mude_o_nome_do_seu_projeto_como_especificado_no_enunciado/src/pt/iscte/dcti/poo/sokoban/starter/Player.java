package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Position;
import pt.iul.ista.poo.utils.Vector2D;

public class Player extends GameElement implements ImageTile, Movable  {

	private static Player INSTANCE = new Player();
	private int movements = 0;
	private int battery = 100;
	
	private Player() {
		super(new Position(0,0), "Empilhadora_D", 2);
	}
	
	public static Player instanceOf(){
		return INSTANCE;
	}
	
	
	public int getMovements() {
		return movements;
	}
	

	public void setMovements(int movements) {
		this.movements = movements;
	}

	public int getBattery() {
		return battery;
	}

	public void setBattery(int battery) {
		this.battery = battery;
	}

	public void move(Direction d) {
		
		Vector2D vectorNew = d.asVector();
		Position newPosition = this.getPosition().plus(vectorNew);
		GameElement Ele = SokobanGame.elementInPosition(this, newPosition);
		System.out.println(Ele);
		objectReacting(Ele, d);
		if (canMove(newPosition)){
			rotate(d);
			this.setPosition(newPosition);
			movements++;
			battery--;
		}
		SokobanGame.checkWin();
		if((battery<=0)||(((Ele != null) && (Ele.isFallable())))){
			SokobanGame.gameOver(false);
		}
		ImageMatrixGUI.getInstance().setStatusMessage(SokobanGame.statusMessage());
		ImageMatrixGUI.getInstance().update();
	}
	
	public boolean canMove(Position position) { // Verificar se nao ha paredes a frente e que se pode mover
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
			if(ele.isMovable()){
				((Movable) ele).move(direction);
			}
			if(ele.isCatchable()){
				((Catchable) ele).use();
			}
			
		}
	}
	
	
	private void rotate(Direction d){
		String image = "Empilhadora_D" ;
		switch (d) {
		case UP:
			image = "Empilhadora_U";
			break;
		case DOWN:
			image = "Empilhadora_D";
			break;
		case LEFT:
			image = "Empilhadora_L";
			break;
		case RIGHT:
			image = "Empilhadora_R";
			break;
		}
		this.setImageName(image);
	}
}
