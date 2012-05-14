package tempest.game.pogopainter.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Color;

import tempest.game.pogopainter.bonuses.Arrow;
import tempest.game.pogopainter.bonuses.BonusObject;
import tempest.game.pogopainter.bonuses.Bonuses;
import tempest.game.pogopainter.bonuses.Checkpoint;
import tempest.game.pogopainter.gametypes.Game;
import tempest.game.pogopainter.system.Board;
import tempest.game.pogopainter.system.Cell;
import tempest.game.pogopainter.system.Direction;
import tempest.game.pogopainter.system.Score;

/**
 * 
 * @author Asen Lekov <asenlekoff@gmail.com>
 * @version 5
 * @since 29/03/2012
 * 
 * Basic artificial intelligence (AI)
 * 
 *  Simple implementation of AI that can get points by getting
 *  different bonuses and move around the game board.
 *
 */

public class AIBehavior implements Behavior {
	private Game actions;
	private Direction currentDirection   = Direction.NONE;
	private Direction nextDirection      = Direction.NONE;
	private Direction arrowNextDirection = Direction.NONE;
	private BonusObject bonus = null;

	private Random rnd        = new Random();
	private boolean following = false;
	private Score score       = null;
	private Player ai;

	private List<BonusObject> checkpoints;
	private List<BonusObject> arrows;
	private List<BonusObject> teleports;
	private List<BonusObject> allBonuses;
	private List<BonusObject> cleaners;
	private List<BonusObject> stuns;

	/**
	 * Use {@link #AIBehavior(Difficulty, Game)} to attach behavior to AI
	 * 
	 * @param game game that provides access to all game components such players, bonuses, board e.g.
	 */

	public AIBehavior(Difficulty AIdifficult, Game game) {
		this.actions = game;
	}

	public void setPlayer(Player pl) {
		this.ai = pl;
	}

	/**
	 * Use {@link #easy(Board, Player, int)} to define attached behavior to EASY
	 * 
	 * @param b board AI play on
	 * @param AI player
	 */

	public void easy(Board b,Player AI) {
		score = new Score(b, AI);

		initBonusLists();
		refreshBonusLists();
		chooseBonus(AI);

		if (bonus != null && !isAINearestToBonus(bonus)) {
			getNearestBonus(allBonuses);
			following = true;
		}

		if ((bonus != null && bonus.getType() == Bonuses.ARROW) && !shouldAIGetArrow(AI, (Arrow) bonus)) {
			following = false;
			getNewFreshDirection(b, AI, arrowNextDirection);
			return;
		}

		if (following && allBonuses.contains(bonus)) {
			getNextDirectionToBonus();
		} else {
			following = false;
			getNewFreshDirection(b, AI, currentDirection);
		}
	}

	private void chooseBonus(Player AI) {
		if (score.Calculate() >= 6 && checkpoints.size() > 0) {
			if (AI.getBonus() != null && AI.getBonus().getType() == Bonuses.TELEPORT) {
				getNearestBonus(checkpoints);
				if (!isAINearestToBonus(bonus)) {
					actions.triggerBonus(AI, AI.getBonus());
				}
			}
			getNearestBonus(checkpoints);
			following = true;
		} else if (teleports.size() > 0) {
			getNearestBonus(teleports);
			following = true;
		} else if (stuns.size() > 0) {
			getNearestBonus(stuns);
			following = true;
		} else if (arrows.size() > 0) {
			getNearestBonus(arrows);
			following = true;
		} else if (naturalCells() >= 18 && cleaners.size() > 0) {
			getNearestBonus(cleaners);
			following = true;
		}
	}

	private void initBonusLists() {
		if (checkpoints == null) {
			checkpoints = new ArrayList<BonusObject>();
			arrows = new ArrayList<BonusObject>();
			teleports = new ArrayList<BonusObject>();
			allBonuses = new ArrayList<BonusObject>();
			cleaners = new ArrayList<BonusObject>();
			stuns = new ArrayList<BonusObject>();
		}
	}

	private boolean isAINearestToBonus(BonusObject bonus) {
		boolean result = true;
		List<Player> otherPlayers = new ArrayList<Player>(actions.getPlayers());
		otherPlayers.remove(ai);

		for (Player otherAI: otherPlayers) {
			if (calcDistance(ai, bonus.getX(), bonus.getY()) > 
			calcDistance(otherAI, bonus.getX(), bonus.getY())) {
				result = false;
			}
		}
		return result;
	}

	private int naturalCells() {
		int result = actions.getBoard().getBoardSize();
		for (int x = 0; x < actions.getBoard().getBoardSize(); x++) {
			for (int y = 0; y < actions.getBoard().getBoardSize(); y++) {
				if (actions.getBoard().getCellAt(x, y).getColor() == Color.GRAY) {
					result++;
				}
			}
		}
		return result;
	}

	public boolean checkPlayerOnBonus(Player player, BonusObject bonus) {
		boolean sure = false;
		if (bonus == null) {
			sure =  false;
		} else if (player.getX() == bonus.getX() && player.getY() == bonus.getY()) {
			sure = true;
		}
		return sure;
	}

	public boolean checkArrowForGivingPoints(Arrow arrow) {
		boolean sure = true;
		int x = arrow.getX();
		int y = arrow.getY();
		switch (arrow.getState()) {
		case 1:
			if (x == 7) {sure = false;}break;
		case 2:
			if (y == 7) {sure = false;}break;
		case 3:
			if (x == 0) {sure = false;}break;
		case 4:
			if (y == 0) {sure = false;}break;
		}
		return sure;	
	}

	private void refreshBonusLists() {
		clearAllLists();
		for (BonusObject bo : actions.getBonusHandler().getOtherBonuses()) {
			switch (bo.getType()) {
			case ARROW:
				arrows.add(bo);
				break;
			case TELEPORT:
				teleports.add(bo);
				break;
			case CLEANER:
				cleaners.add(bo);
				break;
			case STUN:
				stuns.add(bo);
				break;
			}
		}
		for (BonusObject bo : actions.getBonusHandler().getCheckpoints()) {
			checkpoints.add((Checkpoint) bo);
		}
		mergeAllLists();
	}

	private void mergeAllLists() {
		allBonuses.addAll(arrows);
		allBonuses.addAll(teleports);
		allBonuses.addAll(cleaners);
		allBonuses.addAll(stuns);
	}

	private void clearAllLists() {
		arrows.clear();
		teleports.clear();
		allBonuses.clear();
		checkpoints.clear();
		cleaners.clear();
		stuns.clear();
	}

	private void getNearestBonus(List<BonusObject> bonuses) {
		BonusObject nearestBonus = null;

		double minDistance = 1000;

		for (BonusObject bo : bonuses) {
			double calcDistance = calcDistance(ai, bo.getX(), bo.getY());
			if (calcDistance < minDistance) {
				minDistance = calcDistance;
				nearestBonus = bo;
			}
		}
		bonus = nearestBonus;
	}

	/**
	 * @author Asen Lekov <asenlekoff@gmail.com>
	 * @version 1
	 * @since 04/14/2012
	 * 
	 * 	Use {@link #shouldAIGetArrow(Player, Arrow)} to get result it`s okay AI getting Arrow bonus
	 * 
	 * @param AI who will take arrow bonus
	 * @param arrow which arrow bonus will be taken
	 * @return true if player when get Arrow bonus will take new cells, otherwise false
	 */

	private boolean shouldAIGetArrow(Player AI, Arrow arrow) {
		boolean isReached = false;
		boolean checkArrowForGivingPoints = checkArrowForGivingPoints(arrow);
		
		if (checkArrowForGivingPoints) {
			isReached = true;
		} else {
			getNextDirectionToBonus();
			arrowNextDirection = nextDirection;
		}
		
		return isReached;
	}

	/**
	 * @author Asen Lekov <asenlekoff@gmail.com>
	 * @version 1
	 * @since 05/04/2012
	 * 
	 * 	Getting new fresh direction, different from last direction
	 * 
	 * @param b Board that player play on
	 * @param p Target player
	 * @param l Last direction of palyer
	 * @return new direction different from last & available
	 */

	private void getNewFreshDirection(Board b, Player p, Direction l) {
		while(actions.checkDir(l, p)) {
			if (nextDirection == Direction.NONE || nextDirection == l) {
				nextDirection = Direction.values()[rnd.nextInt(4)+1];
			}
			currentDirection = nextDirection;
			break;
		}
	}

	/**
	 * @author Asen Lekov <asenlekoff@gmail.com>
	 * @version 1
	 * @since 29/03/2012
	 * 
	 * 	Simple implementation of A* pathfinding
	 * 
	 * @param x x cord of player
	 * @param y y cord of player
	 * @param destination Destination cell
	 * @return A* distance from player to target cell
	 */

	private double calcDistance(int x, int y, Cell destination) {
		double distance = Math.sqrt(Math.pow((x - destination.getX()), 2) + 
				Math.pow((y - destination.getY()), 2));
		return distance;
	}

	/**
	 * @author Asen Lekov <asenlekoff@gmail.com>
	 * @version 1
	 * @since 29/03/2012
	 * 
	 * 	Simple implementation of A* pathfinding
	 * 
	 * @param AI Player distance we want FROM
	 * @param x	 Target x cord
	 * @param y  Target y cord
	 * @return A* distance from player to target cell
	 */

	private double calcDistance(Player AI, int x, int y) {
		double distance = Math.sqrt(Math.pow((AI.getX() - x), 2) + 
				Math.pow((AI.getY() - y), 2));
		return distance;
	}


	/**
	 * @author Asen Lekov <asenlekoff@gmail.com>
	 * @version 1
	 * @since 14/04/2012
	 * 	
	 * 	Calculating what is shortest direction from player to bonus
	 * 
	 * @param AI Player who will get bonus
	 * @param bonus Target bonus
	 * @return Shortest direction based on A*
	 * 
	 * Uses {@link #calcDistance(int, int, Cell)} & {@link #calcDistance(Player, int, int)}
	 */

	private void getNextDirectionToBonus() {
		int x = ai.getX();
		int y = ai.getY();
		Cell destination = new Cell(bonus.getX(), bonus.getY());
		double distance = calcDistance(x,y , destination);
		for (Direction dir : Direction.values()) {
			boolean checkDir = actions.checkDir(dir, ai);
			switch (dir) {
			case LEFT:
				if (checkDir && distance > calcDistance(x-1, y, destination)) {
					distance = calcDistance(x-1, y, destination);
					nextDirection = dir;
				} break;
			case RIGHT:
				if (checkDir && distance > calcDistance(x+1, y, destination)) {
					distance = calcDistance(x+1, y, destination);
					nextDirection = dir;
				} break;
			case DOWN:
				if (checkDir && distance > calcDistance(x, y+1, destination)) {
					distance = calcDistance(x, y+1, destination);
					nextDirection = dir;
				} break;
			case UP:
				if (checkDir && distance > calcDistance(x, y-1, destination)) {
					distance = calcDistance(x, y-1, destination);
					nextDirection = dir;
				} break;
			}
		}
	}

	public Direction getNextDirection() {
		easy(actions.getBoard(), ai);
		return nextDirection;
	}
}