package pt.iul.ista.poo.utils;

/**
 * The Class Vector2D.
 * @author Alexandre Mendes e Joao Costa
 * @version 1.0
 */
public class Vector2D {
	
	/** Represents the x value. */
	private int x;
	
	/** Represents the y value. */
	private int y;
	
	/**
	 * Instantiates a new Vector 2D.
	 *
	 * @param x the x value
	 * @param y the y value
	 */
	public Vector2D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets the x value.
	 *
	 * @return the x value
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Gets the y value.
	 *
	 * @return the y value
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Sum this Vector 2D with the recieved Vector 2D, making a new Instance of this object with the vectors summed.
	 *
	 * @param v the v represents a Vector 2D.
	 * @return a new instance of Vector 2D.
	 */
	public Vector2D plus(Vector2D v) {
		return new Vector2D(getX() + v.getX(), getY() + v.getY());
	}

	/**
	 * Subtract this Vector 2D with the recieved Vector 2D, making a new Instance of this object with the vectors subtracted.
	 *
	 * @param v the v represents a Vector 2D.
	 * @return a new instance of Vector 2D.
	 */
	public Vector2D minus(Vector2D v) {
		return new Vector2D(getX() - v.getX(), getY() - v.getY());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "<" + x + ", " + y + ">";
	}

}
