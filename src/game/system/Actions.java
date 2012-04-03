package game.system;

import java.util.List;
import java.util.Random;

import game.bonuses.Arrow;
import game.bonuses.BonusObject;
import game.bonuses.Checkpoint;
import game.player.Player;

public class Actions {

	private int checkpointLimit = 5;
	private int arrowsLimit = 3;
	private List<Checkpoint> checkpoints;
	private List<Arrow> arrows;
	
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
			res = true;
		}

		return res;
	}

	public void applyBonusEffect(Player p, Board b) {
		if (checkForBonus(p, b)) {
			p.getBonus().setBonusEffect(p, b);
			clearBonus(p, b);
			for (Checkpoint cp : checkpoints) {
				if (p.getX() == cp.getX() && p.getY() == cp.getY()) {
					checkpoints.remove(cp);
					break;
				}
			}
			for (Arrow aw : arrows) {
				if (p.getX() == aw.getX() && p.getY() == aw.getY()) {
					arrows.remove(aw);
					break;
				}
			}
		}
	}

	private void clearBonus(Player p, Board b) {
		p.setBonus(null);
		b.getCellAt(p.getX(), p.getY()).clearBonus();
	}

	public void seedBonus(Board b, int chance) {
		int checkChance = 0;
		int x;
		int y;
		Random rnd = new Random();
		int bonusChance = 0;

		checkChance = rnd.nextInt(checkpointLimit)+1;

		if (checkChance == chance && checkpoints.size() < checkpointLimit) {
			while(true) {
				
				x = rnd.nextInt(b.getBoardSize());
				y = rnd.nextInt(b.getBoardSize());
				
				if (b.getCellAt(x, y).getBonus() == null) {
					Checkpoint bonus = new Checkpoint();
					b.getCellAt(x, y).setBonus(bonus);
					checkpoints.add(bonus);
					break;
				}
			}
		} else {
			checkChance = rnd.nextInt(5)+1;
			bonusChance = rnd.nextInt(5)+1;
			
			if (checkChance == bonusChance && arrows.size() < arrowsLimit) {
				while(true) {
					
					x = rnd.nextInt(b.getBoardSize());
					y = rnd.nextInt(b.getBoardSize());
					
					if (b.getCellAt(x, y).getBonus() == null) {
						BonusObject bonus = new Arrow();
						b.getCellAt(x, y).setBonus(bonus);
						arrows.add((Arrow) bonus);
						break;
					}
				}
			}
		}
	}

	public List<Checkpoint> getCheckpoints() {
		return checkpoints;
	}

	public void setCheckpoints(List<Checkpoint> checkpoints) {
		this.checkpoints = checkpoints;
	}
	
	public List<Arrow> getArrows() {
		return arrows;
	}

	public void setArrows(List<Arrow> arrows) {
		this.arrows = arrows;
	}
}
