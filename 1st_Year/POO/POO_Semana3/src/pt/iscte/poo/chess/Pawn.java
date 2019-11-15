package pt.iscte.poo.chess;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {


	public Pawn(Board board, Position position, Side side) {
		super(board, position, side, "Pawn");
	}

	@Override
	public List<Move> validMoves() {
		List<Move> moves = new ArrayList<Move>();
		if(getPosition().getY() == 1){
			moves.add(new Move(0, 2));
		}
		if (getBoard().isValid(newPosition(getPosition(), new Move(0, 1))))
			moves.add(new Move(0, 1));
		return moves;
	}


}
