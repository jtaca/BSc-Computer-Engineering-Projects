package Ex1;

import pt.iul.ista.poo.utils.Vector2D;

public class Ponto implements Movable{
	private int x=0;
	private int y=0;
	
	@Override
	public void move(Vector2D v) {
		x=x+v.getX();
		y=y+v.getY();
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		return "Ponto [x=" + x + ", y=" + y + "]";
	}

	public Ponto(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	

}
