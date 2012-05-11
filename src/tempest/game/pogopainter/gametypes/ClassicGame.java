package tempest.game.pogopainter.gametypes;

import java.util.ArrayList;
import tempest.game.pogopainter.bonuses.BonusHandler;
import tempest.game.pogopainter.graphics.Panel;
import tempest.game.pogopainter.player.Player;
import tempest.game.pogopainter.system.Board;
import tempest.game.pogopainter.system.Metrics;
import tempest.game.pogopainter.system.Music;

public class ClassicGame extends Game {
	static {
		STUN_TIME = 4;
	}

	public ClassicGame(Panel panel) {
		this.panel = panel;
		initFields();
	}
	
	@Override
	protected void initFields() {
		Metrics m = new Metrics();
		this.players = new ArrayList<Player>();
		this.movedPlayers = new ArrayList<Player>();
		int classicCellNumber = m.getClassicCellNumber();
		int playerColor = m.getPlayerColor();
		boolean ifSounds = m.isSounds();
		boolean ifBackground = m.isMusic(); 
		this.music = new Music(ifBackground, ifSounds);
		this.board = new Board(classicCellNumber);
		this.time = m.getClassicGameTime();

		initPlayerColors(classicCellNumber, playerColor);
		for (Player pl: players) {
			this.board.setPlayerColorOnCell(pl);
		}
		this.bHandler = new BonusHandler(board, players, 2, 2);
		this.bHandler.initialBonuses();
	}
}