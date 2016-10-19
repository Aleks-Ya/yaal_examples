package coderetreat.lifegame;

import coderetreat.lifegame.api.Model;
import coderetreat.lifegame.api.Destination;
import coderetreat.lifegame.api.ModelSource;
import coderetreat.lifegame.api.Universe;
import coderetreat.lifegame.impl.destination.ConsoleDestination;
import coderetreat.lifegame.impl.model.source.HardCodeModelSource;
import coderetreat.lifegame.impl.universe.UniverseImpl;

public class GameOfLifeMain {

	public static void main(String[] args) {
		ModelSource ms = new HardCodeModelSource();
		Model m = ms.getModel();
		Universe u = new UniverseImpl(m);
		Destination d = new ConsoleDestination();
		final int generationCount = 10;
		for (int i = 0; i < generationCount; i++) {
			d.update(u);
			u.tick();
		}
	}

}
