package pt.iscte.poo.chess;
import java.util.List;

public abstract class Piece {

	private Board board;
	private Position position;
	private Side side;
	private String name;
	
	public Piece(Board board, Position position, Side side, String name) {
		super();
		this. board = board;
		this.position = position;
		this.side = side;
		this.name = name;
	}

	public abstract List<Move> validMoves();

	
	protected Position newPosition(Position position, Move move) {
		return new Position(position.getX() + move.getX(), position.getY() + move.getY());
	}

	public Position getPosition() {
		return position;
	}

	public Side getSide() {
		return side;
	}

	public String getName() {
		return name;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	
	
}
