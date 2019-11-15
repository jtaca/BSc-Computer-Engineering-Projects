package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Position;

public class Hole extends GameElement implements Fallable{
	
	public Hole(Position position) {
		super(position, "Buraco", 1);
		setUnder(true);
		setFallable(true);
		
	}

	@Override
	public void makeFall(GameElement Elem) {
		SokobanGame.getElements().remove(Elem);
		ImageMatrixGUI.getInstance().removeImage(Elem);	
	}


	
	

}
