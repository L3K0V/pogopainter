package tempest.game.pogopainter.bonuses;

import java.util.List;

import android.graphics.Color;
import tempest.game.pogopainter.player.Player;
import tempest.game.pogopainter.system.Board;

public class Cleaner extends BonusObject {
	
	public Cleaner() {
		type = Bonuses.CLEANER;
	}

	@Override
	public void setBonusEffect(Player player, Board board) {}

	@Override
	public void setBonusEffect(List<Player> players, Board board) {
		int playerColor = 0;
		for (Player pl: players) {
			if ((pl.getX() == x) && (pl.getY() == y)) {
				playerColor = pl.getColor();
			}
		}
		
		for (int x = 0; x < board.getBoardSize(); x++) {
			for (int y = 0; y < board.getBoardSize(); y++) {
				if (playerColor != board.getCellAt(x, y).getColor()) {
					board.getCellAt(x, y).setColor(Color.GRAY);
				}
			}
		}
		for (Player pl: players) {
			board.setPlayerColorOnCell(pl);
		}
	}
}