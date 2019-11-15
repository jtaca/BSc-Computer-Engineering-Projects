package pt.iscte.dcti.poo.sokoban.starter;

import java.util.Observable;

import pt.iul.ista.poo.utils.Position;

public class Alvo extends AbstractObject implements ObjectiveObject{
	
	private boolean ocupado;
	private Caixa caixa;
	
	public Alvo(Position position) { // Constructor;
		super(position, "Alvo");
		ocupado = false;
	} // Fim do constructor;
	
	@Override
	public boolean isObjective() {
		return true;
	}
	
	private void meterCaixa(Caixa obj) {
		if(isReady())
			caixa = obj;
		else 
			caixa = null;
	}
	
	private void mudarEstadoOcupado(Caixa obj) {
		ocupado = !ocupado;
		meterCaixa(obj);
		setChanged();
		notifyObservers(this);
	}
	
	public boolean isReady() {
		return ocupado;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		AbstractObject obj = (AbstractObject) arg1;
		if(obj.isCaixa()) {
			if(!isReady() && getPosition().equals(obj.getPosition())) {
				mudarEstadoOcupado((Caixa) obj);
			} else if(isReady() && caixa.equals(obj) && !getPosition().equals(obj.getPosition()))
				mudarEstadoOcupado((Caixa) obj);
		}
		
	}


} // Fim da class Alvo;