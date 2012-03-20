package game.player;

import game.system.Board;
import game.system.Direction;

public class Actions {

	public void move(Board b, Player player, Direction dir) {
		int boardSize = b.getBoardSize();

		switch (dir) {
		case RIGHT:
			if (checkDir(dir, player, boardSize)) {
				player.setX(player.getX() + 1);
				b.setPlayerColorOnCell(player);
			}
			break;
		case UP:
			if (checkDir(dir, player, boardSize)) {
				player.setY(player.getY() -1);
				b.setPlayerColorOnCell(player);
			}
			break;
		case LEFT:
			if (checkDir(dir, player, boardSize)) {
				player.setX(player.getX() -1);
				b.setPlayerColorOnCell(player);
			}
			break;
		case DOWN:
			if (checkDir(dir, player, boardSize)) {
				player.setY(player.getY() +1);
				b.setPlayerColorOnCell(player);
			}
			break;
		}
	}
	
	public boolean checkDir(Direction dir, Player player, int boardSize) {
		boolean res = false;
		boardSize --;
		switch(dir) {
		case RIGHT:
			if (player.getX() < boardSize) {
				res = true;
			}
			break;
		case UP:
			if(player.getY() > 0) {
				res = true;
			}
			break;
		case LEFT:
			if (player.getX() > 0) {
				res = true;
			}
			break;
		case DOWN:
			if (player.getY() < boardSize) {
				res = true;
			}
			break;
		}
		return res;
	}
}
