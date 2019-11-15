package pt.iscte.poo.lampadas;

public class Lampada {
	private String id;
	private int potencia;
	public Lampada(String id, int potencia) {
		super();
		this.id = id;
		this.potencia = potencia;
	}
	public String getId() {
		return id;
	}
	public int getPotencia() {
		return potencia;
	}
	@Override
	public String toString() {
		return "Lampada [id=" + id + ", potencia=" + potencia + "]";
	}
		
}
