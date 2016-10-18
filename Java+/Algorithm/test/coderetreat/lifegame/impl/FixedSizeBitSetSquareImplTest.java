package coderetreat.lifegame.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import coderetreat.lifegame.api.Square;

public class FixedSizeBitSetSquareImplTest {

	@Test(expected = IllegalArgumentException.class)
	public void incorrectSize() {
		int size = 2;
		new FixedSizeBitSetSquareImpl(size);
	}

	@Test
	public void testSize() {
		int size = 5;
		Square s = new FixedSizeBitSetSquareImpl(size);
		assertEquals(size, s.size());
	}

	@Test
	public void testIsAlive() {
		Square s = new FixedSizeBitSetSquareImpl(5);
		assertFalse(s.isAlive(1, 1));
	}

	@Test
	public void testAliveNeiboursCount() {
		Square s = new FixedSizeBitSetSquareImpl(5);
		assertEquals(0, s.aliveNeiboursCount(1, 1));
		s.setAlive(1, 2, true);
		assertEquals(1, s.aliveNeiboursCount(1, 1));
	}

	/**
	 * Can set alive not a border cell.
	 */
	@Test
	public void testSetAlive() {
		Square s = new FixedSizeBitSetSquareImpl(5);
		int x = 1;
		int y = 2;
		assertFalse(s.isAlive(x, y));
		s.setAlive(x, y, true);
		assertTrue(s.isAlive(x, y));
	}

	/**
	 * Can't set alive a border cell.
	 */
	@Test
	public void testSetAliveBorderCell() {
		Square s = new FixedSizeBitSetSquareImpl(5);
		int x = 0;
		int y = 1;
		assertFalse(s.isAlive(x, y));
		s.setAlive(x, y, true);
		assertFalse(s.isAlive(x, y));
	}

}
