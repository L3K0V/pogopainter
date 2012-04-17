package game.player;

import game.graphics.Panel;
import game.system.Direction;

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
