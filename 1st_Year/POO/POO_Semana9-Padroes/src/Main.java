
public class Main {
	// Exercício 1 Padrões (Observador - Observado)
	// Crie duas classes que jogam ao HIGH/LOW, uma delas implementa o observado
	// (que funciona
	// como o croupier) e outra o observador (da qual existirão duas instâncias
	// que representam dois
	// jogadores, um que aposta sempre HIGH outro sempre LOW). O observado gera
	// continuamente
	// números aleatórios entre 2 e 12 (equivalente ao lançamento de dois
	// dados), de cada vez que é
	// gerado um número abaixo de 5 ou acima de 10 os observadores (jogadores)
	// são avisados de
	// que há um número HIGH ou LOW e um deles (e só um) irá escrever “GANHEI”
	// nessa iteração.
	
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
