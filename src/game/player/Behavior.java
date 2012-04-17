package game.player;

import game.system.Direction;

public interface Behavior {
	public void setPlayer(Player pl);
	public Direction getNextDirection();
}
