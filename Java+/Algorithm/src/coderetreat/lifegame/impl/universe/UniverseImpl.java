package coderetreat.lifegame.impl.universe;

import coderetreat.lifegame.api.Model;
import coderetreat.lifegame.api.Universe;

public class UniverseImpl implements Universe {
	private Model model;
	private int generation = 1;

	public UniverseImpl(Model model) {
		this.model = model;
	}

	@Override
	public void tick() {
		Model newModel = model.clone();
		final int size = model.getSize();
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				boolean isAlive = model.isAlive(x, y);
				int n = model.aliveNeiboursCount(x, y);
				boolean newAlive;
				if (isAlive) {
					if (n < 2 || n > 3) {
						newAlive = false;
					} else {
						newAlive = true;
					}
				} else {
					newAlive = (n == 3);
				}
				newModel.setAlive(x, y, newAlive);
			}
		}
		model = newModel;
		generation++;
	}

	@Override
	public Model getModel() {
		return model;
	}

	@Override
	public int getGeneration() {
		return generation;
	}

}
