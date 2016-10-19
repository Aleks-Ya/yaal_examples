package coderetreat.lifegame.api;

public interface Universe {
	void tick();

	Model getModel();

	int getGeneration();
}
