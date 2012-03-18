package game.gametypes;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;

import game.player.Player;
import game.system.Board;
import game.system.Metrics;

public class ClassicGameType {
	private Board b;
	private List<Player> AI = new ArrayList<Player>();
	private List<Player> USER = new ArrayList<Player>();
	private int time;
	
	public ClassicGameType() {
		Metrics m = new Metrics();
		int classicCellNumber = m.getClassicCellNumber();
		int playerColor = m.getPlayerColor();
		b = new Board(classicCellNumber);
		time = m.getClassicGameTime();

		switch (playerColor) {
		case Color.RED:
			USER.add(new Player(0, classicCellNumber - 1, Color.RED, 0, null));
			AI.add(new Player(classicCellNumber-1, classicCellNumber-1, Color.BLUE, 0, null));
			AI.add(new Player(0, 0, Color.GREEN, 0, null));
			AI.add(new Player(classicCellNumber - 1, 0, Color.YELLOW, 0, null));
			break;
		case Color.BLUE:
			AI.add(new Player(0, classicCellNumber - 1, Color.RED, 0, null));
			USER.add(new Player(classicCellNumber-1, classicCellNumber-1, Color.BLUE, 0, null));
			AI.add(new Player(0, 0, Color.GREEN, 0, null));
			AI.add(new Player(classicCellNumber - 1, 0, Color.YELLOW, 0, null));
			break;
		case Color.GREEN:
			AI.add(new Player(0, classicCellNumber - 1, Color.RED, 0, null));
			AI.add(new Player(classicCellNumber-1, classicCellNumber-1, Color.BLUE, 0, null));
			USER.add(new Player(0, 0, Color.GREEN, 0, null));
			AI.add(new Player(classicCellNumber - 1, 0, Color.YELLOW, 0, null));
			break;
		case Color.YELLOW:
			AI.add(new Player(0, classicCellNumber - 1, Color.RED, 0, null));
			AI.add(new Player(classicCellNumber-1, classicCellNumber-1, Color.BLUE, 0, null));
			AI.add(new Player(0, 0, Color.GREEN, 0, null));
			USER.add(new Player(classicCellNumber - 1, 0, Color.YELLOW, 0, null));
			break;
		default:
			USER.add(new Player(0, classicCellNumber - 1, Color.RED, 0, null));
			AI.add(new Player(classicCellNumber-1, classicCellNumber-1, Color.BLUE, 0, null));
			AI.add(new Player(0, 0, Color.GREEN, 0, null));
			AI.add(new Player(classicCellNumber - 1, 0, Color.YELLOW, 0, null));
			break;
		}
		for (Player ai: AI) {
			b.setPlayerColorOnCell(ai);
		}
		for (Player user: USER) {
			b.setPlayerColorOnCell(user);
		}
	}

	public Board getBoard() {
		return b;
	}

	public List<Player> getAIs() {
		return AI;
	}
	
	public int getTime() {
		return time;
	}
	
	public List<Player> getUser() {
		return USER;
	}
}
