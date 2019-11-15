package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Position;

public class Battery extends GameElement implements Catchable {

	public Battery(Position position) {
		super(position,"Bateria", 1);
		this.setCatchable(true);
	}

	@Override
	public void use() {
		Player.instanceOf().setBattery(SokobanGame.batteryMax);
		this.delete();
	}
	
	
	
	public void delete(){
		SokobanGame.getElements().remove(this);
		ImageMatrixGUI.getInstance().removeImage(this);
	}

}
