package game.player;

import game.system.Board;
import game.system.Cell;
import game.system.Direction;

public class Actions {

	public void move(Board b, Player player, Direction dir) {
		int boardSize = b.getBoardSize();

		switch (dir) {
		case RIGHT:
			if (checkDir(dir, player, boardSize)) {
				player.setX(player.getX() + 1);
				b.setPlayerColorOnCell(player);
				applyBonusEffect(player, b);
			}
			break;
		case UP:
			if (checkDir(dir, player, boardSize)) {
				player.setY(player.getY() -1);
				b.setPlayerColorOnCell(player);
				applyBonusEffect(player, b);
			}
			break;
		case LEFT:
			if (checkDir(dir, player, boardSize)) {
				player.setX(player.getX() -1);
				b.setPlayerColorOnCell(player);
				applyBonusEffect(player, b);
			}
			break;
		case DOWN:
			if (checkDir(dir, player, boardSize)) {
				player.setY(player.getY() +1);
				b.setPlayerColorOnCell(player);
				applyBonusEffect(player, b);
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
	
	public boolean checkForBonus(Player p, Board b) {
		boolean res = false;
		Cell thisCell = b.getCellAt(p.getX(), p.getY());
		
		if (thisCell.getBonus() != null) {
			p.setBonus(thisCell.getBonus());
			//p.getBonus().setBonusEffect(p, b);
			res = true;
		}
		
		return res;
	}
	
	public void applyBonusEffect(Player p, Board b) {
		if (checkForBonus(p, b)) {
			p.getBonus().setBonusEffect(p, b);
			clearBonus(p, b);
		}
	}
	
	private void clearBonus(Player p, Board b) {
		p.setBonus(null);
		b.getCellAt(p.getX(), p.getY()).clearBonus();
	}
}
