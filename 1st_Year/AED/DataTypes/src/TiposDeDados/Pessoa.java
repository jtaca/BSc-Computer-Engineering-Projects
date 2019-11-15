package TiposDeDados;

public class Pessoa implements Item {

    private long bi;
    private String nome;

    public Pessoa(long bi, String nome) {
	this.bi = bi;
	this.nome = nome;
    }

    public long getBI() {
	return bi;
    }

    public Object chave() {
	return bi;
    }

    public String toString() {
	return nome;
    }
}