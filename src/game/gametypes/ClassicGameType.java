package game.gametypes;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;

import game.player.Player;
import game.system.Board;
import game.system.Metrics;

public class ClassicGameType {
	private Board b;
	private List<Player> AIs = new ArrayList<Player>();
	private Player user;
	private int time;
	
	public ClassicGameType() {
		Metrics m = new Metrics();
		int classicCellNumber = m.getClassicCellNumber();
		int playerColor = m.getPlayerColor();
		b = new Board(classicCellNumber);
		time = m.getClassicGameTime();

		switch (playerColor) {
		case Color.RED:
			user = new Player(0, classicCellNumber - 1, Color.RED, 0, null);
			AIs.add(new Player(classicCellNumber-1, classicCellNumber-1, Color.BLUE, 0, null));
			AIs.add(new Player(0, 0, Color.GREEN, 0, null));
			AIs.add(new Player(classicCellNumber - 1, 0, Color.YELLOW, 0, null));
			break;
		case Color.BLUE:
			AIs.add(new Player(0, classicCellNumber - 1, Color.RED, 0, null));
			user = new Player(classicCellNumber-1, classicCellNumber-1, Color.BLUE, 0, null);
			AIs.add(new Player(0, 0, Color.GREEN, 0, null));
			AIs.add(new Player(classicCellNumber - 1, 0, Color.YELLOW, 0, null));
			break;
		case Color.GREEN:
			AIs.add(new Player(0, classicCellNumber - 1, Color.RED, 0, null));
			AIs.add(new Player(classicCellNumber-1, classicCellNumber-1, Color.BLUE, 0, null));
			user = new Player(0, 0, Color.GREEN, 0, null);
			AIs.add(new Player(classicCellNumber - 1, 0, Color.YELLOW, 0, null));
			break;
		case Color.YELLOW:
			AIs.add(new Player(0, classicCellNumber - 1, Color.RED, 0, null));
			AIs.add(new Player(classicCellNumber-1, classicCellNumber-1, Color.BLUE, 0, null));
			AIs.add(new Player(0, 0, Color.GREEN, 0, null));
			user = new Player(classicCellNumber - 1, 0, Color.YELLOW, 0, null);
			break;
		default:
			user = new Player(0, classicCellNumber - 1, Color.RED, 0, null);
			AIs.add(new Player(classicCellNumber-1, classicCellNumber-1, Color.BLUE, 0, null));
			AIs.add(new Player(0, 0, Color.GREEN, 0, null));
			AIs.add(new Player(classicCellNumber - 1, 0, Color.YELLOW, 0, null));
			break;
		}
		
		b.setPlayerColorOnCell(AIs.get(0));
		b.setPlayerColorOnCell(AIs.get(1));
		b.setPlayerColorOnCell(AIs.get(2));
		b.setPlayerColorOnCell(user);
	}

	public Board getBoard() {
		return b;
	}

	public List<Player> getAIs() {
		return AIs;
	}
	
	public int getTime() {
		return time;
	}
	
	public Player getUser() {
		return user;
	}
}
