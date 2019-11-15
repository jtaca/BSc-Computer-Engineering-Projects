package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Position;

public class Bateria extends AbstractObject implements UsableObject{
	
	private int quantidade;

	public Bateria(Position position, int quantidade) { // Construtor;
		super(position, "Bateria");
		this.quantidade = quantidade;
	} // Fim do construtor;
	
	@Override
	public int getLevel() {
		return 1;
	}
	
	@Override
	public boolean isTransposable() {
		return false;
	}
	
	@Override
	public boolean isUsable() {
		return true;
	}
	
	public void use() {
		Player.getPlayer().charge(quantidade);
		disapear();
	}
	
	@Override
	public void disapear() { // Usa a bateria b, invocando a funcao charge(value);
		ImageMatrixGUI.getInstance().removeImage(this);
		SokobanGame.getGame().getObjects().remove(this);
		
	} // Fim da funcao use(Bateria b);

} // Fim da class Bateria;
