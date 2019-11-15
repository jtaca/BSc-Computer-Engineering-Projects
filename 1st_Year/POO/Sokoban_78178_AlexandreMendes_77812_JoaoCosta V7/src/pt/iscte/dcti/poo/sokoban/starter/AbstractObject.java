package pt.iscte.dcti.poo.sokoban.starter;

import java.util.Observable;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Position;

public abstract class AbstractObject extends Observable implements ImageTile {
	
	private Position position;
	private String imageName;
	
	public AbstractObject(Position position, String imageName) {
		this.position = position;
		this.imageName = imageName;
	}
	
	public String getName() {
		return imageName;
	}
	
	public void setName(String imageName) {
		this.imageName = imageName;
	}
	
	public Position getPosition() {
		return position;
	}
	
	void setPosition(Position p) {
		position = p;
	}

	public int getLevel() {
		return 0;
	}
	
	public boolean isTransposable() {
		return true;
	}
	
	public boolean isMovable() {
		return false;
	}
	
	public boolean canFall() {
		return true;
	}
	
	public boolean isUsable() {
		return false;
	}
	
	public boolean isObjective() {
		return false;
	}
	
	public boolean isCaixa() {
		return false;
	}
	
	public boolean makeDisapear() {
		return false;
	}
	
	public void disapear() {
		ImageMatrixGUI.getInstance().removeImage(this);
	}
	
	public void notifyObj() {
		setChanged();
		notifyObservers(this);
	}

	
} // Fim da classe Abstracta AbstractObject;
