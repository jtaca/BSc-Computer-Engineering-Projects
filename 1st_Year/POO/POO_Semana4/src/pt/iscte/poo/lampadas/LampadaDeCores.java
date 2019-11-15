package pt.iscte.poo.lampadas;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class LampadaDeCores extends Lampada {

	private List<Color> colors = new ArrayList<Color>();
	Color color = Color.WHITE;

	public LampadaDeCores(String id, int potencia, String[] nomesDasCores) {
		super(id, potencia);
		for (String nomeDaCor : nomesDasCores) {
			colors.add(FabricaDeLampadas.getColorByName(nomeDaCor));
		}
	}

	public void setColor(Color color) {
		if (!colors.contains(color))
			return;
		this.color = color;
	}

	@Override
	public String toString() {
		return "LampadaDeCores [colors=" + colors + "]";
	}

}
