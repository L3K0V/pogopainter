package tempest.game.pogopainter.player;

import tempest.game.pogopainter.system.Direction;

public interface Behavior {
	public void setPlayer(Player pl);
	public Direction getNextDirection();
}
