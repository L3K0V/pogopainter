package tempest.game.pogopainter.bonuses;

import java.util.List;

import android.graphics.Color;

import tempest.game.pogopainter.player.Player;
import tempest.game.pogopainter.system.Board;

public class Cleaner extends BonusObject {

	@Override
	public void setBonusEffect(Player p, Board b) {
	}
	
	public void setBonusEffect(List<Player> players, Board b) {
		for (int x = 0; x < b.getBoardSize(); x++) {
			for (int y = 0; y < b.getBoardSize(); y++) {
				b.getCellAt(x, y).setColor(Color.GRAY);
			}
		}
		for (Player p : players) {
			b.setPlayerColorOnCell(p);
		}
	}

}
