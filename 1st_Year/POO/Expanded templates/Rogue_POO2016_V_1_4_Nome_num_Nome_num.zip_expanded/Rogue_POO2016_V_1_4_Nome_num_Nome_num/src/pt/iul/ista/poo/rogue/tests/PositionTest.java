package pt.iul.ista.poo.rogue.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.iul.ista.poo.rogue.utils.Position;
import pt.iul.ista.poo.rogue.utils.Vector2D;

public class PositionTest {

	private Position position = new Position(2, 3);
	
	@Before
	public void prepare() {
		
	}
	
	@Test
	public void testConstructor() {
		Assert.assertEquals(2, position.getX());
		Assert.assertEquals(3, position.getY());
	}

	@Test
	public void testPlus() {
		
		Assert.assertEquals(new Position(5, 5), position.plus(new Vector2D(3, 2)));
		Assert.assertEquals(new Position(0, 0), position.plus(new Vector2D(-2, -3)));
	}

}
