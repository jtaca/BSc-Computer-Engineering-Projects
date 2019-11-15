package Ex1;

import java.util.ArrayList;

import pt.iul.ista.poo.utils.Vector2D;


public class Main {
	public static void main(String[] args){
		ArrayList<Circulo> circulos = new ArrayList<Circulo>();
		circulos.add(new Circulo(1,1,1));
		for(Circulo a: circulos){
			System.out.println( a.toString());
			a.move(new Vector2D(1, 1));
			System.out.println( a.toString());
			
		}
		
		ArrayList<Ponto> pontos = new ArrayList<Ponto>();
		pontos.add(new Ponto(1,1));
		for(Ponto a: pontos){
			System.out.println( a.toString());
			a.move(new Vector2D(1, 1));
			System.out.println( a.toString());
			
		}
		
		
	}

}
