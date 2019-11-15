package pt.iscte.poot.alunos;

public class Aluno {

	// Cada aluno terá o numero de aluno, o
	// nome e o curso.

	private final String numero;
	private final String nome;
	private String curso;
	public Aluno(String numero, String nome, String curso) {
		super();
		this.numero = numero;
		this.nome = nome;
		this.curso = curso;
	}
	public String getNumero() {
		return numero;
	}
	public String getNome() {
		return nome;
	}
	public String getCurso() {
		return curso;
	}
	
	
	

}
