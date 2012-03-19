package game.player;

import game.system.Board;
import game.system.Direction;

public class Actions {

	public void move(Board b, Player player, Direction dir) {
		boolean res = false;

		switch (dir) {
		case RIGHT:
			if (player.getX() < b.getBoardSize()) {
				player.setX(player.getX() + 1);
				b.setPlayerColorOnCell(player);
			}
			break;
		case UP:
			if (player.getY() < b.getBoardSize()) {
				player.setY(player.getY() -1);
				b.setPlayerColorOnCell(player);
			}
			break;
		case LEFT:
			if (player.getX() > 1) {
				player.setX(player.getX() -1);
				b.setPlayerColorOnCell(player);
			}
			break;
		case DOWN:
			if (player.getY() < b.getBoardSize()) {
				player.setY(player.getY() +1);
				b.setPlayerColorOnCell(player);
			}
			break;
		}
	}
}
