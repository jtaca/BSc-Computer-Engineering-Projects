package pt.iscte.dcti.poo.sokoban.starter;

import java.util.Observable;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Position;

public abstract class GameElement extends Observable implements ImageTile {
	private Position position;
	private String imageName;
	private int level;
	private boolean movable;
	private boolean objective;
	private boolean under;
	private boolean catchable;
	private boolean fallable;
	private boolean objectiveCondition;
	
	
	
	public GameElement(Position position, String imageName, int level) {
		super();
		this.position = position;
		this.imageName = imageName;
		this.level = level;
		this.movable = false;
		this.objective = false;
		this.under = false;
		this.catchable = false;
	}
	
	public String getName() {
		return imageName;
	}


	public Position getPosition() {
		return position;
	}
	
	void setPosition(Position p) {
		this.position = p;
	}
	
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public boolean isMovable() {
		return movable;
	}

	public void setMovable(boolean movable) {
		this.movable = movable;
	}

	public boolean isObjective() {
		return objective;
	}

	public void setObjective(boolean objective) {
		this.objective = objective;
	}

	public String getImageName() {
		return imageName;
	}
	
	

	@Override
	public String toString() {
		//return "GameElement [position=" + position + ", imageName=" + imageName + ", level=" + level + ", movable="
				//+ movable + ", objective=" + objective + "]";
		return  imageName; 
	}

	public boolean isUnder() {
		return under;
	}

	public void setUnder(boolean under) {
		this.under = under;
	}

	public boolean isCatchable() {
		return catchable;
	}

	public void setCatchable(boolean catchable) {
		this.catchable = catchable;
	}

	public boolean isFallable() {
		return fallable;
	}

	public void setFallable(boolean fallable) {
		this.fallable = fallable;
	}

	public boolean isObjectiveCondition() {
		return objectiveCondition;
	}

	public void setObjectiveCondition(boolean objectiveCondition) {
		this.objectiveCondition = objectiveCondition;
	}
	
	
}
