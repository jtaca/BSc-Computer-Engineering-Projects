package pt.iul.ista.poo.rogue.utils;

/**
 * @author POO2016
 * 
 * Cardinal directions
 *
 */
public enum Direction {
	LEFT(new Vector2D(-1,0)), UP(new Vector2D(0, -1)), RIGHT(new Vector2D(1, 0)), DOWN(new Vector2D(0, 1));
	
	private Vector2D vector;
	
	Direction(Vector2D vector){
		this.vector = vector;
	}

	public Vector2D asVector() {
		return vector;
	}
}
