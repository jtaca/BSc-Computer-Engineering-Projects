package pt.iscte.poot.alunos;

public class TesteGestaoDeAlunos {

	// Crie um sistema de gest�o de alunos numa sala. Deve ser possivel no
	// programa, adicionar alunos a uma
	// sala, at� ao limite da sua capacidade e tamb�m remover um aluno
	// espec�fico.

	public static void main(String[] args) {

		Sala sala = new Sala(52, "B", "0S01");

		Aluno a = new Aluno("75678", "Manuel Silva", "LEI");
		
		sala.adicionar(a);
		
		System.out.println(sala);
		
	}

}
