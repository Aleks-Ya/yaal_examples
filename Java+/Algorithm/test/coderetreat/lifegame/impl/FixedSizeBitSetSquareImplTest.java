package coderetreat.lifegame.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import coderetreat.lifegame.api.Model;
import coderetreat.lifegame.impl.model.FixedSizeBitSetModel;

public class FixedSizeBitSetSquareImplTest {

	@Test(expected = IllegalArgumentException.class)
	public void incorrectSize() {
		int size = 2;
		new FixedSizeBitSetModel(size);
	}

	@Test
	public void testSize() {
		int size = 5;
		Model m = new FixedSizeBitSetModel(size);
		assertEquals(size, m.getSize());
	}

	@Test
	public void testIsAlive() {
		Model m = new FixedSizeBitSetModel(5);
		assertFalse(m.isAlive(1, 1));
	}

	@Test
	public void testAliveNeiboursCount() {
		Model m = new FixedSizeBitSetModel(5);
		assertEquals(0, m.aliveNeiboursCount(1, 1));
		m.setAlive(1, 2, true);
		assertEquals(1, m.aliveNeiboursCount(1, 1));
	}

	/**
	 * Can set alive not a border cell.
	 */
	@Test
	public void testSetAlive() {
		Model m = new FixedSizeBitSetModel(5);
		int x = 1;
		int y = 2;
		assertFalse(m.isAlive(x, y));
		m.setAlive(x, y, true);
		assertTrue(m.isAlive(x, y));
	}

	/**
	 * Can't set alive a border cell.
	 */
	@Test
	public void testSetAliveBorderCell() {
		Model m = new FixedSizeBitSetModel(5);
		int x = 0;
		int y = 1;
		assertFalse(m.isAlive(x, y));
		m.setAlive(x, y, true);
		assertFalse(m.isAlive(x, y));
	}

}
