package tempeset.game.pogopainter.bonuses;

import tempest.game.pogopainter.player.Player;
import tempest.game.pogopainter.system.Board;

public abstract class BonusObject {
	protected int x;
	protected int y;
	protected Bonuses type;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Bonuses getType() {
		return type;
	}
	
	public abstract void setBonusEffect(Player p, Board b);
}
