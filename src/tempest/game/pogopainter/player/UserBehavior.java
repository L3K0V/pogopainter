package tempest.game.pogopainter.player;

import tempest.game.pogopainter.graphics.Panel;
import tempest.game.pogopainter.system.Direction;

public class UserBehavior implements Behavior {
	private Panel panel;
	private Direction lastDir = Direction.NONE;
	
	public UserBehavior(Panel p) {
		this.panel = p;
	}
	public void setPlayer(Player pl) {
	}

	public Direction getNextDirection() {
		lastDir = panel.getDirection();
		return panel.getDirection();
	}
	public Direction getPreviousDirection() {
		return lastDir;
	}
}
