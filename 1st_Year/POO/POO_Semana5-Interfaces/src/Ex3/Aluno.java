package Ex3;

public class Aluno {
	private int numero;
	private String nome;
	
	public Aluno(String nome,int numero) {
		super();
		this.numero = numero;
		this.nome = nome;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	@Override
	public String toString() {
		return "Aluno [numero=" + numero + ", nome=" + nome + "]";
	}
	
}
