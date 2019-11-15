package pt.iscte.poo.lampadas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		try {
			List<Lampada> lampadas = FabricaDeLampadas.lerLampadas("lampadas.txt");
			System.out.println(lampadas);
			
//			PrintWriter p = new PrintWriter(new File("outro.txt"));
//			for (Lampada l : lampadas) {
//				p.println(l);
//			}
//			p.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Ficheiro não existe");
		}

		
		
	}

	
}
