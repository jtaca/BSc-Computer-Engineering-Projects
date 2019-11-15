package pt.iscte.poo.chess;
import java.util.ArrayList;
import java.util.List;

public class ChessTest {

	public static void main(String[] args) {
		Board b = new Board();
		List<Piece> pieces = new ArrayList<Piece>();
		pieces.add(new Horse(b, new Position(1, 0), Side.WHITE));
		pieces.add(new Pawn(b, new Position(7, 1), Side.BLACK));
		
		for (Piece a: pieces) {
			System.out.println(a.validMoves());
		}
		

	}

}
