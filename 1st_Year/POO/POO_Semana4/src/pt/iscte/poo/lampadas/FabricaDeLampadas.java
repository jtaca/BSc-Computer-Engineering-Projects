package pt.iscte.poo.lampadas;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FabricaDeLampadas {
	public static List<Lampada> lerLampadas(String nomeDoFicheiro) throws FileNotFoundException {
		Scanner s = new Scanner(new File(nomeDoFicheiro));
		List<Lampada> lampadas = new ArrayList<Lampada>();
		while (s.hasNextLine()) {
			String line = s.nextLine();
			String[] palavras = line.split(" ");
			if (palavras[0].equals("Lampada")) {
				lampadas.add(new Lampada(palavras[1], Integer.valueOf(palavras[2])));
			} else if (palavras[0].equals("LampadaDeCor")) {
				lampadas.add(new LampadaDeCor(palavras[1], Integer.valueOf(palavras[2]), getColorByName(palavras[3])));
			} else if (palavras[0].equals("LampadaDeCorVariavel")) {
				lampadas.add(new LampadaDeCorVariavel(palavras[1], Integer.valueOf(palavras[2])));
			} else if (palavras[0].equals("LampadaDeCores")) {
				lampadas.add(new LampadaDeCores(palavras[1], Integer.valueOf(palavras[2]),
						Arrays.copyOfRange(palavras, 3, palavras.length - 1)));
			}
		}
		return lampadas;
	}

	// Função que substitui o Color.valueOf que não existe para a Color porque
	// não é um enumerado. Não faz parte da matéria de POO.
	public static Color getColorByName(String colorName) {
		Field f;
		try {
			f = Color.class.getField(colorName);
			return (Color) f.get(null);
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

}
