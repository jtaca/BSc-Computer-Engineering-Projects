package Ex1;

import pt.iul.ista.poo.utils.Vector2D;

public class Circulo implements Movable{
	private int x=0;
	private int y=0;
	private int raio=1;
	
	@Override
	public void move(Vector2D v) {
		x=x+v.getX();
		y=y+v.getY();
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		return "Circulo [x=" + x + ", y=" + y + ", raio=" + raio + "]";
	}

	public Circulo(int x, int y, int raio) {
		super();
		this.x = x;
		this.y = y;
		this.raio = raio;
	}
	
	
	
}
