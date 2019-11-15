package pt.iscte.poo.lampadas;

import java.awt.Color;

public class LampadaDeCor extends Lampada {

	private Color color;

	public LampadaDeCor(String id, int potencia, Color color) {
		super(id, potencia);
		this.color = color;
	}

	@Override
	public String toString() {
		return "LampadaDeCor [color=" + color + "]";
	}

}
