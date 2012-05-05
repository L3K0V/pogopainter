package tempest.game.pogopainter.gametypes;

import java.util.ArrayList;
import tempest.game.pogopainter.bonuses.BonusHandler;
import tempest.game.pogopainter.graphics.Panel;
import tempest.game.pogopainter.player.Player;
import tempest.game.pogopainter.system.Board;
import tempest.game.pogopainter.system.Metrics;
import tempest.game.pogopainter.system.Music;

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
		ifSounds = m.isSounds();
		ifBackground = m.isMusic(); 
		music = new Music(ifBackground, ifSounds);
		board = new Board(classicCellNumber);
		time = m.getClassicGameTime();

		initPlayerColors(classicCellNumber, playerColor);
		for (Player players: PLAYERS) {
			board.setPlayerColorOnCell(players);
		}
		bHandler = new BonusHandler(board, PLAYERS, 2, 2);
		bHandler.initialBonuses();
	}
}