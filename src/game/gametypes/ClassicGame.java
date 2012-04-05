package game.gametypes;

import game.bonuses.Arrow;
import game.bonuses.Checkpoint;
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
		AI = new ArrayList<Player>();
		USER = new ArrayList<Player>();
		CHECKPOINTS = new ArrayList<Checkpoint>();
		ARROWS = new ArrayList<Arrow>();
		checkpointLimit = 5;
		arrowsLimit = 3;
		Metrics m = new Metrics();
		int classicCellNumber = m.getClassicCellNumber();
		int playerColor = m.getPlayerColor();
		b = new Board(classicCellNumber);
		time = m.getClassicGameTime();

		initPlayerColors(classicCellNumber, playerColor);
		for (Player ai: AI) {
			b.setPlayerColorOnCell(ai);
		}
		for (Player user: USER) {
			b.setPlayerColorOnCell(user);
		}

		Random rnd = new Random();
		bonusRandomNumber = rnd.nextInt(5)+1;
		aiNumber = rnd.nextInt(2)+1;
	}
}
