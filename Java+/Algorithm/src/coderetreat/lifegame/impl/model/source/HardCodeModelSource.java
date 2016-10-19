package coderetreat.lifegame.impl.model.source;

import coderetreat.lifegame.api.Model;
import coderetreat.lifegame.api.ModelSource;
import coderetreat.lifegame.impl.model.FixedSizeBitSetModel;

public class HardCodeModelSource implements ModelSource {
	private final Model model;
	
	public HardCodeModelSource() {
		model = new FixedSizeBitSetModel(6);
		model.setAlive(2, 2, true);
		model.setAlive(3, 2, true);
		model.setAlive(2, 1, true);
		model.setAlive(2, 3, true);
	}
	
	@Override
	public Model getModel() {
		return model;
	}

}
