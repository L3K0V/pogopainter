package tempest.game.pogopainter.bonuses;

import tempest.game.pogopainter.player.Player;
import tempest.game.pogopainter.system.Board;

public abstract class BonusObject {
	protected int x;
	protected int y;
	protected Bonuses type;

	public final int getX() {
		return x;
	}

	public final void setX(int x) {
		this.x = x;
	}

	public final int getY() {
		return y;
	}

	public final void setY(int y) {
		this.y = y;
	}

	public final Bonuses getType() {
		return type;
	}
	
	public abstract void setBonusEffect(Player p, Board b);
}
