package coderetreat.lifegame.api;

public interface Square {

	int size();

	int aliveNeiboursCount(int x, int y);

	boolean isAlive(int x, int y);

	void setAlive(int x, int y, boolean alive);
}
