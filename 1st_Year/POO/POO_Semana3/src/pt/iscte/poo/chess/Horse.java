package pt.iscte.poo.chess;
import java.util.ArrayList;
import java.util.List;

public class Horse extends Piece {

	private List<Move> moves = new ArrayList<Move>();

	public Horse(Board board, Position position, Side side) {
		super(board, position, side, "Horse");
		moves.add(new Move(1, 2));
		moves.add(new Move(1, -2));
		moves.add(new Move(-1, 2));
		moves.add(new Move(-1, -2));
		moves.add(new Move(2, 1));
		moves.add(new Move(-2, 1));
		moves.add(new Move(2, -1));
		moves.add(new Move(-2, -1));
	}

	@Override
	public List<Move> validMoves() {
		// validar
		List<Move> validMoves = new ArrayList<Move>();
		for(Move m: moves) {
			if (getBoard().isValid(newPosition(getPosition(), m)))
				validMoves.add(m);
		}
		return validMoves;
	}

}
