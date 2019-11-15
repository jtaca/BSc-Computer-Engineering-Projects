package pt.iscte.poot.alunos;
import java.util.ArrayList;

public class Sala {

	// Para tal terá de criar as
	// classes Sala e Aluno, cada sala terá a sua capacidade, o nome do bloco
	// (ex: B, C, D) e o numero da sala. 
	private int capacidade;
	private String bloco;
	private String numero;
	private ArrayList<Aluno> alunos = new ArrayList<Aluno>();

	public Sala(int capacidade, String bloco, String numero) {	
		this.capacidade = capacidade;
		this.bloco = bloco;
		this.numero = numero;
	}

	public int getCapacidade() {
		return capacidade;
	}

	public String getBloco() {
		return bloco;
	}

	public String getNumero() {
		return numero;
	}

	public void adicionar(Aluno a) {
		if (alunos.size() < capacidade)
			alunos.add(a);
	}

	@Override
	public String toString() {
		return "Sala [capacidade=" + capacidade + ", numero=" + numero + "]";
	}
	
	

	

}
