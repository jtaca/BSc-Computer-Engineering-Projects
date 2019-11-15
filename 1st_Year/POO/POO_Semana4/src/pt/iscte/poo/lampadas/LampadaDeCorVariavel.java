package pt.iscte.poo.lampadas;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class LampadaDeCorVariavel extends Lampada {

	private Color color;


	public LampadaDeCorVariavel(String id, int potencia) {
		super(id, potencia);
		this.color = Color.WHITE;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "LampadaDeCores [color=" + color + "]";
	}
	
}
