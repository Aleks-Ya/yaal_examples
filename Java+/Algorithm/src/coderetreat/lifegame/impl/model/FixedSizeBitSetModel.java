package coderetreat.lifegame.impl.model;

import java.util.BitSet;

import coderetreat.lifegame.api.Model;

public class FixedSizeBitSetModel implements Model {
	private final BitSet data;
	private final int size;

	public FixedSizeBitSetModel(int size) {
		if (size < 3) {
			throw new IllegalArgumentException("Incorrect size: " + size);
		}
		this.size = size;
		data = new BitSet(size * size);
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public int aliveNeiboursCount(int x, int y) {
		return (isAliveSafe(x - 1, y - 1) + isAliveSafe(x - 1, y) + isAliveSafe(x - 1, y + 1) + isAliveSafe(x, y - 1)
				+ isAliveSafe(x, y + 1) + isAliveSafe(x + 1, y - 1) + isAliveSafe(x + 1, y)
				+ isAliveSafe(x + 1, y + 1));
	}

	private int isAliveSafe(int x, int y) {
		if (x < 0 || x > size - 1 || y < 0 || y > size - 1) {
			return 0;
		}
		return data.get(coordinatesToIndex(x, y)) ? 1 : 0;
	}

	@Override
	public void setAlive(int x, int y, boolean alive) {
		int index = coordinatesToIndex(x, y);
		if (isBorderCell(x, y)) {
			data.set(index, false);
		} else {
			data.set(index, alive);
		}
	}

	private int coordinatesToIndex(int x, int y) {
		int e = x * getSize() + y;
		return e;
	}

	@Override
	public boolean isAlive(int x, int y) {
		return data.get(coordinatesToIndex(x, y));
	}

	private boolean isBorderCell(int x, int y) {
		return x == 0 || x == size - 1 || y == 0 || y == size;
	}

}
