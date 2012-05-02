package tempest.game.pogopainter.gametypes;

import java.util.ArrayList;

import tempest.game.pogopainter.bonuses.BonusHandler;
import tempest.game.pogopainter.bonuses.Bonuses;
import tempest.game.pogopainter.graphics.Panel;
import tempest.game.pogopainter.player.Player;
import tempest.game.pogopainter.system.Board;
import tempest.game.pogopainter.system.Metrics;

public class ClassicGame extends Game {

	public ClassicGame(Panel panel) {
		this._panel = panel;
		initFields();
	}
	
	@Override
	protected void initFields() {
		PLAYERS = new ArrayList<Player>();
		movedPlayers = new ArrayList<Player>();
		Metrics m = new Metrics();
		int classicCellNumber = m.getClassicCellNumber();
		int playerColor = m.getPlayerColor();
		b = new Board(classicCellNumber);
		time = 10;//m.getClassicGameTime();

		initPlayerColors(classicCellNumber, playerColor);
		for (Player players: PLAYERS) {
			b.setPlayerColorOnCell(players);
		}
		bHandler = new BonusHandler(b, PLAYERS, 2, 2);
		Bonuses[] bon = {Bonuses.CHECKPOINT, Bonuses.ARROW, Bonuses.TELEPORT};
		bHandler.seedBonuses(bon);
		bHandler.initialBonuses();
	}
}
