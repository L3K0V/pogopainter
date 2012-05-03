package tempest.game.pogopainter.player;

import tempest.game.pogopainter.graphics.Panel;
import tempest.game.pogopainter.system.Direction;

public class UserBehavior implements Behavior {
	private Panel panel;
	
	public UserBehavior(Panel p) {
		this.panel = p;
	}
	public void setPlayer(Player pl) {
	}

	public Direction getNextDirection() {
		return panel.getDirection();
	}
}
