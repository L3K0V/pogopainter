package game.gametypes;

import java.util.List;

import game.player.Player;
import game.system.Board;
import game.system.Metrics;

public class ClassicGameType {
	private Board b;
	private List<Player> players;
	private int time;
	
	public ClassicGameType() {
		Metrics m = new Metrics();
		int classicCellNumber = m.getClassicCellNumber();
		int playerColor = m.getPlayerColor();
		b = new Board(classicCellNumber);
		time = m.getClassicGameTime();

		switch (playerColor) {
		case 1:
			players.add(new Player(classicCellNumber-1, 0, playerColor, 0, null));
			players.add(new Player(classicCellNumber-1, classicCellNumber-1, 2, 0, null));
			players.add(new Player(0, 0, 3, 0, null));
			players.add(new Player(0, classicCellNumber-1, 4, 0, null));
			break;
		case 2:
			players.add(new Player(classicCellNumber-1, 0, 1, 0, null));
			players.add(new Player(classicCellNumber-1, classicCellNumber-1, playerColor, 0, null));
			players.add(new Player(0, 0, 3, 0, null));
			players.add(new Player(0, classicCellNumber-1, 4, 0, null));
			break;
		case 3:
			players.add(new Player(classicCellNumber-1, 0, 1, 0, null));
			players.add(new Player(classicCellNumber-1, classicCellNumber-1, 2, 0, null));
			players.add(new Player(0, 0, playerColor, 0, null));
			players.add(new Player(0, classicCellNumber-1, 4, 0, null));
			break;
		case 4:
			players.add(new Player(classicCellNumber-1, 0, 1, 0, null));
			players.add(new Player(classicCellNumber-1, classicCellNumber-1, 2, 0, null));
			players.add(new Player(0, 0, 3, 0, null));
			players.add(new Player(0, classicCellNumber-1, playerColor, 0, null));
			break;

		default:
			players.add(new Player(classicCellNumber-1, 0, playerColor, 0, null));
			players.add(new Player(classicCellNumber-1, classicCellNumber-1, 2, 0, null));
			players.add(new Player(0, 0, 3, 0, null));
			players.add(new Player(0, classicCellNumber-1, 4, 0, null));
			break;
		}

		b.setPlayerColorOnCell(players.get(0));
		b.setPlayerColorOnCell(players.get(1));
		b.setPlayerColorOnCell(players.get(2));
		b.setPlayerColorOnCell(players.get(3));

		// 1 - red
		// 2 - blue
		// 3 - green
		// 4 - yellow
	}

	public Board getBoard() {
		return b;
	}

	public List<Player> getPlayers() {
		return players;
	}
	
	public int getTime() {
		return time;
	}
}
