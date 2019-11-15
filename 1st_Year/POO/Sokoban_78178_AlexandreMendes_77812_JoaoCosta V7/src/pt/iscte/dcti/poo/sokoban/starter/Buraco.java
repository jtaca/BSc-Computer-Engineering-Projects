package pt.iscte.dcti.poo.sokoban.starter;

import java.util.Observable;

import pt.iul.ista.poo.utils.Position;

public class Buraco extends AbstractObject implements MakeDisapear{

	public Buraco(Position position) { // Constructor;
		super(position, "Buraco");
	} // Fim do constructor;
	
	@Override
	public int getLevel() {
		return 2;
	}
	
	public boolean isOcupied(AbstractObject obj) {
		return getPosition().equals(obj.getPosition()) ||
				getPosition().equals(Player.getPlayer().getPosition());
	}
	
	@Override
	public boolean makeDisapear() {
		return true;
	}
	
	public void makeDisapears(AbstractObject obj) {
		if(obj != null && obj.canFall())
			obj.disapear();
		else 
			notifyObj();
		
	}

	@Override
	public void update(Observable o, Object arg) {
		AbstractObject obj = (AbstractObject) arg;
		if(isOcupied(obj))
			makeDisapears(obj);
	}

} // Fim da class Buraco;
