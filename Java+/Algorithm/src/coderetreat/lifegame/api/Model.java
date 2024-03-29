package coderetreat.lifegame.api;

public interface Model {

	int getSize();

	boolean isAlive(int x, int y);

	void setAlive(int x, int y, boolean alive);
	
	int aliveNeiboursCount(int x, int y);
	
	Model clone();
}
