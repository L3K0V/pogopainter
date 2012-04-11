package game.gametypes;

import game.bonuses.Arrow;
import game.bonuses.BonusObject;
import game.bonuses.Checkpoint;
import game.graphics.Panel;
import game.player.AIBehaviour;
import game.player.Difficulty;
import game.player.Player;
import game.pogopainter.CanvasActivity;
import game.system.Board;
import game.system.Cell;
import game.system.Direction;

import java.util.List;
import java.util.Random;

import android.graphics.Color;

public abstract class Game {
	protected Board b;
	protected int bonusRandomNumber;
	protected int aiNumber;
	protected Panel _panel;
	protected int time = 1000;
	
	protected List<Player> AI;
	protected List<Player> USER;
	protected List<Checkpoint> CHECKPOINTS;
	protected List<Arrow> ARROWS;

	protected int checkpointLimit;
	protected int arrowsLimit;
	
	protected abstract void initFields();
	
	protected void initPlayerColors(int classicCellNumber, int playerColor) {
		switch (playerColor) {
		case Color.RED:
			USER.add(new Player(0, classicCellNumber - 1, Color.RED, 0, null));
			AI.add(new Player(classicCellNumber-1, classicCellNumber-1, Color.BLUE, 0, new AIBehaviour(Difficulty.EASY, this)));
			AI.add(new Player(0, 0, Color.GREEN, 0, new AIBehaviour(Difficulty.EASY, this)));
			AI.add(new Player(classicCellNumber - 1, 0, Color.YELLOW, 0, new AIBehaviour(Difficulty.EASY, this)));
			break;
		case Color.BLUE:
			AI.add(new Player(0, classicCellNumber - 1, Color.RED, 0, new AIBehaviour(Difficulty.EASY, this)));
			USER.add(new Player(classicCellNumber-1, classicCellNumber-1, Color.BLUE, 0, null));
			AI.add(new Player(0, 0, Color.GREEN, 0, new AIBehaviour(Difficulty.EASY, this)));
			AI.add(new Player(classicCellNumber - 1, 0, Color.YELLOW, 0, new AIBehaviour(Difficulty.EASY, this)));
			break;
		case Color.GREEN:
			AI.add(new Player(0, classicCellNumber - 1, Color.RED, 0, new AIBehaviour(Difficulty.EASY, this)));
			AI.add(new Player(classicCellNumber-1, classicCellNumber-1, Color.BLUE, 0, new AIBehaviour(Difficulty.EASY, this)));
			USER.add(new Player(0, 0, Color.GREEN, 0, null));
			AI.add(new Player(classicCellNumber - 1, 0, Color.YELLOW, 0, new AIBehaviour(Difficulty.EASY, this)));
			break;
		case Color.YELLOW:
			AI.add(new Player(0, classicCellNumber - 1, Color.RED, 0, new AIBehaviour(Difficulty.EASY, this)));
			AI.add(new Player(classicCellNumber-1, classicCellNumber-1, Color.BLUE, 0, new AIBehaviour(Difficulty.EASY, this)));
			AI.add(new Player(0, 0, Color.GREEN, 0, new AIBehaviour(Difficulty.EASY, this)));
			USER.add(new Player(classicCellNumber - 1, 0, Color.YELLOW, 0, null));
			break;
		default:
			USER.add(new Player(0, classicCellNumber - 1, Color.RED, 0, null));
			AI.add(new Player(classicCellNumber-1, classicCellNumber-1, Color.BLUE, 0, new AIBehaviour(Difficulty.EASY, this)));
			AI.add(new Player(0, 0, Color.GREEN, 0, new AIBehaviour(Difficulty.EASY, this)));
			AI.add(new Player(classicCellNumber - 1, 0, Color.YELLOW, 0, new AIBehaviour(Difficulty.EASY, this)));
			break;
		}
	}
	
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
			for (Checkpoint cp : CHECKPOINTS) {
				if (p.getX() == cp.getX() && p.getY() == cp.getY()) {
					CHECKPOINTS.remove(cp);
					break;
				}
			}
			for (Arrow aw : ARROWS) {
				if (p.getX() == aw.getX() && p.getY() == aw.getY()) {
					ARROWS.remove(aw);
					break;
				}
			}
		}
	}

	protected void clearBonus(Player p, Board b) {
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

		if (checkChance == chance && CHECKPOINTS.size() < checkpointLimit) {
			while(true) {
				
				x = rnd.nextInt(b.getBoardSize());
				y = rnd.nextInt(b.getBoardSize());
				
				if (b.getCellAt(x, y).getBonus() == null) {
					Checkpoint bonus = new Checkpoint();
					b.getCellAt(x, y).setBonus(bonus);
					CHECKPOINTS.add(bonus);
					break;
				}
			}
		} else {
			checkChance = rnd.nextInt(5)+1;
			bonusChance = rnd.nextInt(5)+1;
			
			if (checkChance == bonusChance && ARROWS.size() < arrowsLimit) {
				while(true) {
					
					x = rnd.nextInt(b.getBoardSize());
					y = rnd.nextInt(b.getBoardSize());
					
					if (b.getCellAt(x, y).getBonus() == null) {
						BonusObject bonus = new Arrow();
						b.getCellAt(x, y).setBonus(bonus);
						ARROWS.add((Arrow) bonus);
						break;
					}
				}
			}
		}
	}
	
	
	public Board getBoard() {
		return b;
	}

	public int getTime() {
		return time;
	}

	public List<Player> getUser() {
		return USER;
	}

	public List<Player> getAIs() {
		return AI;
	}

	public List<Checkpoint> getCheckpoints() {
		return CHECKPOINTS;
	}
	
	public List<Arrow> getArrows() {
		return ARROWS;
	}
	
	public void update() {
		if (time == 0) {
			_panel.stopThreads();
			_panel.surfaceDestroyed(_panel.getHolder());
			_panel.clearFocus();
			CanvasActivity.showResults = true;
			_panel.getOwner().finish();
		} else {
			seedBonus(b, bonusRandomNumber);
			Direction dir = _panel.getDirection();
			move(b, USER.get(0), dir);
			for (Player AI : this.AI) {
				AI.getBehaviour().easy(b, AI, aiNumber);
				Direction ai = AI.getBehaviour().getDirection();
				move(b, AI, ai);
			}
			for (Arrow aw : ARROWS) {
				aw.changeState();
			}
		}
		time--;
	}
}
