package game.gametypes;

import game.bonuses.BonusHandler;
import game.bonuses.Bonuses;
import game.graphics.Panel;
import game.player.Player;
import game.system.Board;
import game.system.Metrics;
import java.util.ArrayList;
import java.util.Random;

public class ClassicGame extends Game {

	public ClassicGame(Panel panel) {
		this._panel = panel;
		initFields();
	}
	
	@Override
	protected void initFields() {
		PLAYERS = new ArrayList<Player>();
		Metrics m = new Metrics();
		int classicCellNumber = m.getClassicCellNumber();
		int playerColor = m.getPlayerColor();
		b = new Board(classicCellNumber);
		time = m.getClassicGameTime();

		initPlayerColors(classicCellNumber, playerColor);
		for (Player players: PLAYERS) {
			b.setPlayerColorOnCell(players);
		}
		Random rnd = new Random();
		aiNumber = rnd.nextInt(2)+1;
		bHandler = new BonusHandler(b, PLAYERS);
		Bonuses[] bon = {Bonuses.CHECKPOINT, Bonuses.ARROW};
		bHandler.seedBonuses(bon);
	}
}
