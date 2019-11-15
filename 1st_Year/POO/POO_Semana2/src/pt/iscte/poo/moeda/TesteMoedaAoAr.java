package pt.iscte.poo.moeda;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TesteMoedaAoAr {

	private static final int N = 10;

	public static void main(String[] args) {
		
		Scanner teclado = new Scanner(System.in);
		String linha = null;
		do {
			System.out.println("Escolhe cara ou coroa?");
			linha = teclado.nextLine();
	    } while (!linha.equals("CARA") && !linha.equals("COROA"));
	
		System.out.println(linha);

		Random gerador = new Random();
		ArrayList<CaraOuCoroa> caraOuCoroa = new ArrayList<CaraOuCoroa>();
		for(int i = 0; i != N; i++) {
			boolean b = gerador.nextBoolean();
			if (b)
				caraOuCoroa.add(CaraOuCoroa.CARA);
			else
				caraOuCoroa.add(CaraOuCoroa.COROA);
		}
		
		int contador = 0;
		for(CaraOuCoroa b: caraOuCoroa) {			
			//if((b && linha.equals("CARA")) || (!b && linha.equals("COROA"))) {
			if(b.name().equals(linha)) {
				contador++;
			}
		}
		System.out.println(caraOuCoroa);
		double percentagem = contador / ((double)caraOuCoroa.size());
		System.out.println(percentagem);
	}

}
