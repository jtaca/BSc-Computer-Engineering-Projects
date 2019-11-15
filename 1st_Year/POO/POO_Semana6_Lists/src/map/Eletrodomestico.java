package map;

public class Eletrodomestico {
private String marca;
private String tipo;
private int potencia;

public Eletrodomestico(String marca, String tipo, int potencia) {
	super();
	this.marca = marca;
	this.tipo = tipo;
	this.potencia = potencia;
}
public String getMarca() {
	return marca;
}
public void setMarca(String marca) {
	this.marca = marca;
}
public String getTipo() {
	return tipo;
}
public void setTipo(String tipo) {
	this.tipo = tipo;
}
public int getPotencia() {
	return potencia;
}
public void setPotencia(int potencia) {
	this.potencia = potencia;
}
@Override
public String toString() {
	return "Eletrodomestico [marca=" + marca + ", tipo=" + tipo + ", potencia=" + potencia + "]";
}

}
