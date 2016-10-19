package coderetreat.lifegame.impl.destination;

import coderetreat.lifegame.api.Model;
import coderetreat.lifegame.api.Universe;
import coderetreat.lifegame.api.Destination;

public class ConsoleDestination implements Destination {

	@Override
	public void update(Universe universe) {
		Model model = universe.getModel();
		System.out.print("\nGeneration: " + universe.getGeneration());
		final int size = model.getSize();
		for (int x = 0; x < size; x++) {
			System.out.println();
			for (int y = 0; y < size; y++) {
				System.out.print(model.isAlive(x, y) ? "X" : "-");
			}
		}
		System.out.println();
	}
}
