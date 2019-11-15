package pt.iscte.poo.chess;

public class Board {

	private static final int N_ROWS = 8;
	private static final int N_COLS = 8;

	public boolean isValid(Position newPosition) {
		return newPosition.getX() >= 0 && newPosition.getY() >= 0 && newPosition.getX() < N_ROWS && newPosition.getY() < N_COLS;
	}

}
