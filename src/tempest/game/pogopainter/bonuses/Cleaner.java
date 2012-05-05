package tempest.game.pogopainter.bonuses;

import android.graphics.Color;
import tempest.game.pogopainter.player.Player;
import tempest.game.pogopainter.system.Board;

public class Cleaner extends BonusObject {
	
	public Cleaner() {
		type = Bonuses.CLEANER;
	}

	@Override
	public void setBonusEffect(Player player, Board board) {
		for (int x = 0; x < board.getBoardSize(); x++) {
			for (int y = 0; y < board.getBoardSize(); y++) {
				board.getCellAt(x, y).setColor(Color.GRAY);
			}
		}
		board.setPlayerColorOnCell(player);
	}
}