package pt.iul.ista.poo.utils.JUnit;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import pt.iul.ista.poo.utils.Vector2D;

public class Vector2DTest {
	
	private static Vector2D v;
	
	@BeforeClass
	public static void startInstance() {
		v = new Vector2D(3, 5);
	}

	@Test
	public void testVector2D() {
		Vector2D ve = new Vector2D(7, 0);
		assertNotNull(ve);
		assertEquals(7, ve.getX());
		assertEquals(0, ve.getY());
	}

	@Test
	public void testGetX() {
		assertEquals(3, v.getX());
	}

	@Test
	public void testGetY() {
		assertEquals(5, v.getY());
	}

	@Test
	public void testPlus() {
		assertEquals(new Vector2D(3, 5).getY(), v.plus(new Vector2D(0, 0)).getY());
		assertEquals(new Vector2D(-2, 3).getX(), v.plus(new Vector2D(-5, -2)).getX());
		assertEquals(new Vector2D(2, 7).getY(), v.plus(new Vector2D(-1, 2)).getY());
		assertEquals(new Vector2D(4, 3).getX(), v.plus(new Vector2D(1, -2)).getX());
	}

	@Test
	public void testMinus() {
		assertEquals(new Vector2D(3, 5).getY(), v.minus(new Vector2D(0, 0)).getY());
		assertEquals(new Vector2D(8, 7).getX(), v.minus(new Vector2D(-5, -2)).getX());
		assertEquals(new Vector2D(4, 3).getY(), v.minus(new Vector2D(-1, 2)).getY());
		assertEquals(new Vector2D(2, 7).getX(), v.minus(new Vector2D(1, -2)).getX());
	}

	@Test
	public void testToString() {
		assertEquals("<3, 5>", v.toString());
	}

}
