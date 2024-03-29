package pt.iscte.poo.chess;

public class Move {
	private int x;
	private int y;
	
	public Move(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "Move [x=" + x + ", y=" + y + "]";
	}

}
