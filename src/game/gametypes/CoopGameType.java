package game.gametypes;

import game.player.Player;
import game.system.Board;
import game.system.Metrics;

import java.util.List;

public class CoopGameType {
	private Board b;
	private List<Player> players;
	private int time;
	
	public CoopGameType() {
		Metrics m = new Metrics();
		int coopCellNumber = m.getCoopCellNumber();
		int playerColor = m.getPlayerColor();
		time = m.getCoopGameTime();
		
		// TODO: set player 2 color & position
		// Default: player 1 = red
		//			player 2 = green or yellow
		
		b = new Board(coopCellNumber);

		switch (playerColor) {
		case 1:
			players.add(new Player(coopCellNumber-1, 0, playerColor, 0, null));
			players.add(new Player(coopCellNumber-1, coopCellNumber-1, 2, 0, null));
			players.add(new Player(0, 0, 3, 0, null));
			players.add(new Player(0, coopCellNumber-1, 4, 0, null));
			break;
		case 2:
			players.add(new Player(coopCellNumber-1, 0, 1, 0, null));
			players.add(new Player(coopCellNumber-1, coopCellNumber-1, playerColor, 0, null));
			players.add(new Player(0, 0, 3, 0, null));
			players.add(new Player(0, coopCellNumber-1, 4, 0, null));
			break;
		case 3:
			players.add(new Player(coopCellNumber-1, 0, 1, 0, null));
			players.add(new Player(coopCellNumber-1, coopCellNumber-1, 2, 0, null));
			players.add(new Player(0, 0, playerColor, 0, null));
			players.add(new Player(0, coopCellNumber-1, 4, 0, null));
			break;
		case 4:
			players.add(new Player(coopCellNumber-1, 0, 1, 0, null));
			players.add(new Player(coopCellNumber-1, coopCellNumber-1, 2, 0, null));
			players.add(new Player(0, 0, 3, 0, null));
			players.add(new Player(0, coopCellNumber-1, playerColor, 0, null));
			break;

		default:
			players.add(new Player(coopCellNumber-1, 0, playerColor, 0, null));
			players.add(new Player(coopCellNumber-1, coopCellNumber-1, 2, 0, null));
			players.add(new Player(0, 0, 3, 0, null));
			players.add(new Player(0, coopCellNumber-1, 4, 0, null));
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
