package game.gametypes;

import game.player.Player;
import game.system.Board;
import game.system.Metrics;

import java.util.List;

public class DeathmatchGameType {
	private Board b;
	private List<Player> players;

	public DeathmatchGameType() {
		Metrics m = new Metrics();
		int deathmatchGameType = m.getClassicCellNumber();
		int playerColor = m.getPlayerColor();
		int deathmatchPoints = m.getDeathmatchRoundPoints();
		b = new Board(deathmatchGameType);

		switch (playerColor) {
		case 1:
			players.add(new Player(deathmatchGameType-1, 0, playerColor, deathmatchPoints, null));
			players.add(new Player(deathmatchGameType-1, deathmatchGameType-1, 2, deathmatchPoints, null));
			players.add(new Player(0, 0, 3, deathmatchPoints, null));
			players.add(new Player(0, deathmatchGameType-1, 4, deathmatchPoints, null));
			break;
		case 2:
			players.add(new Player(deathmatchGameType-1, 0, 1, deathmatchPoints, null));
			players.add(new Player(deathmatchGameType-1, deathmatchGameType-1, playerColor, deathmatchPoints, null));
			players.add(new Player(0, 0, 3, deathmatchPoints, null));
			players.add(new Player(0, deathmatchGameType-1, 4, deathmatchPoints, null));
			break;
		case 3:
			players.add(new Player(deathmatchGameType-1, 0, 1, deathmatchPoints, null));
			players.add(new Player(deathmatchGameType-1, deathmatchGameType-1, 2, deathmatchPoints, null));
			players.add(new Player(0, 0, playerColor, deathmatchPoints, null));
			players.add(new Player(0, deathmatchGameType-1, 4, deathmatchPoints, null));
			break;
		case 4:
			players.add(new Player(deathmatchGameType-1, 0, 1, deathmatchPoints, null));
			players.add(new Player(deathmatchGameType-1, deathmatchGameType-1, 2, deathmatchPoints, null));
			players.add(new Player(0, 0, 3, deathmatchPoints, null));
			players.add(new Player(0, deathmatchGameType-1, playerColor, deathmatchPoints, null));
			break;

		default:
			players.add(new Player(deathmatchGameType-1, 0, playerColor, deathmatchPoints, null));
			players.add(new Player(deathmatchGameType-1, deathmatchGameType-1, 2, deathmatchPoints, null));
			players.add(new Player(0, 0, 3, deathmatchPoints, null));
			players.add(new Player(0, deathmatchGameType-1, 4, deathmatchPoints, null));
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
}
