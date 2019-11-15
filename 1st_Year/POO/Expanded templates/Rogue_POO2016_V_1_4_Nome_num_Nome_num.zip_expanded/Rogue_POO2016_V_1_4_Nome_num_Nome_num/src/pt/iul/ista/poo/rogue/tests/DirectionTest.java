package pt.iul.ista.poo.rogue.tests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import pt.iul.ista.poo.rogue.utils.Direction;
import pt.iul.ista.poo.rogue.utils.Vector2D;

public class DirectionTest {

	@Test
	public void testAsVector() {
		
		Assert.assertEquals(Direction.UP.asVector(), new Vector2D(0, -1));
		Assert.assertEquals(Direction.DOWN.asVector(), new Vector2D(0, 1));
		Assert.assertEquals(Direction.LEFT.asVector(), new Vector2D(-1, 0));
		Assert.assertEquals(Direction.RIGHT.asVector(), new Vector2D(1, 0));
	}

}
