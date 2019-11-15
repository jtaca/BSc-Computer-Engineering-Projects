
public class Main {
	// Exerc�cio 1 Padr�es (Observador - Observado)
	// Crie duas classes que jogam ao HIGH/LOW, uma delas implementa o observado
	// (que funciona
	// como o croupier) e outra o observador (da qual existir�o duas inst�ncias
	// que representam dois
	// jogadores, um que aposta sempre HIGH outro sempre LOW). O observado gera
	// continuamente
	// n�meros aleat�rios entre 2 e 12 (equivalente ao lan�amento de dois
	// dados), de cada vez que �
	// gerado um n�mero abaixo de 5 ou acima de 10 os observadores (jogadores)
	// s�o avisados de
	// que h� um n�mero HIGH ou LOW e um deles (e s� um) ir� escrever �GANHEI�
	// nessa itera��o.
	
	private static final int N_THROWS = 10;

	public static void main(String[] args) {
		
		Croupier c = Croupier.getInstance(); //new Croupier(); 
		
		Jogador j1 = new Jogador();
		Jogador j2 = new Jogador();
		
		j1.setGamble(Gamble.HIGH);
		j2.setGamble(Gamble.LOW);
		
		c.addObserver(j1);
		c.addObserver(j2);
		
		int i = 0;
		while (i != N_THROWS) {
			c.throwTheDice();
			i++;
		}

	}

}
