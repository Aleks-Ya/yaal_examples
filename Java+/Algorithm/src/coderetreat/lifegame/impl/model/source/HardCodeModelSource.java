package coderetreat.lifegame.impl.model.source;

import coderetreat.lifegame.api.Model;
import coderetreat.lifegame.api.ModelSource;
import coderetreat.lifegame.impl.model.FixedSizeBitSetModel;

public class HardCodeModelSource implements ModelSource {
	private final Model model;
	
	public HardCodeModelSource() {
		model = new FixedSizeBitSetModel(5);
	}
	
	@Override
	public Model getModel() {
		return model;
	}

}
