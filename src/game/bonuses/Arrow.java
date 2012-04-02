package game.bonuses;

import game.player.Player;
import game.system.Board;

public class Arrow extends BonusObject {
	int state = 0;

	public Arrow() {
		changeState();
		type = Bonuses.ARROW;
	}

	@Override
	public void setBonusEffect(Player p, Board b) {
		int playerX = p.getX();
		int playerY = p.getY();
		int bSize = b.getBoardSize();

		switch (state) {
		case 1:
			//RIGHT
			for (; playerX < bSize; playerX++) {
				b.getCellAt(playerX, playerY).setColor(p.getColor());
			}
			break;
		case 2:
			//DOWN
			for (; playerY < bSize; playerY++) {
				b.getCellAt(playerX, playerY).setColor(p.getColor());
			}
			break;
		case 3:
			//LEFT
			for (; playerX >= 0; playerX--) {
				b.getCellAt(playerX, playerY).setColor(p.getColor());
			}
			break;
		case 4:
			//UP
			for (; playerY >= 0; playerY--) {
				b.getCellAt(playerX, playerY).setColor(p.getColor());
			}
			break;
		}
	}

	public void changeState() {
		this.state++;
		if (state == 5) {
			state = 1;
		}
	}

	public int getState() {
		return this.state;
	}

}
