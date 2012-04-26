package tempest.game.pogopainter.player;


import java.util.List;
import java.util.Random;

import tempeset.game.pogopainter.bonuses.Arrow;
import tempeset.game.pogopainter.bonuses.BonusObject;
import tempeset.game.pogopainter.bonuses.Checkpoint;
import tempest.game.pogopainter.gametypes.Game;
import tempest.game.pogopainter.system.Board;
import tempest.game.pogopainter.system.Cell;
import tempest.game.pogopainter.system.Direction;
import tempest.game.pogopainter.system.Score;

/**
 * 
 * @author Asen Lekov <asenlekoff@gmai.com>
 * @version 3
 * @since 29/03/2012
 * 
 * Basic artificial intelligence (AI)
 * 
 *  Simple implementation of AI that can get points by getting
 *  different bonuses and move around the game board.
 *
 */

public class AIBehavior implements Behavior {
	private Difficulty AIdifficult;
	private Game actions;
	private Direction lastDir    = Direction.NONE;
	private Checkpoint random    = null;
	private Arrow arrow          = null;
	private BonusObject followCP = null;

	private Random rnd = new Random();
	private boolean following = false;
	private Score score       = null;
	private Player AI;

	/**
	 * Use {@link #AIBehavior(Difficulty, Game)} to attach behavior to AI
	 * 
	 * @param AIdifficult behavior difficulty (easy, normal, hard)
	 * @param game game that provides access to all game components such players, bonuses, board e.g.
	 */

	public AIBehavior(Difficulty AIdifficult, Game game) {
		this.AIdifficult = AIdifficult;
		this.actions = game;
	}

	public void setPlayer(Player pl) {
		this.AI = pl;
	}

	/**
	 * Use {@link #easy(Board, Player, int)} to define attached behavior to EASY
	 * 
	 * @param b board AI play on
	 * @param AI player
	 * @param randomNumber random number, chance to take some bonuses
	 */

	public void easy(Board b,Player AI, int randomNumber) {
		Direction nextDir = Direction.NONE;
		score = new Score(b, AI);

		//		int check = rnd.nextInt(2)+1;
		//		int goTo  = rnd.nextInt(2)+1;

		int pointsToFollow = 5;

		List<Checkpoint> checkpoints = actions.getBonusHandler().getCheckpoints();
		List<Arrow> arrows = actions.getBonusHandler().getArrows();

		if (checkpoints.size() > 0 && !checkpoints.contains(random)) {
			random = checkpoints.get(rnd.nextInt(checkpoints.size()));
			followCP = random;
		}
		if (arrows.size() > 0 && !arrows.contains(arrow)) {
			arrow = arrows.get(rnd.nextInt(arrows.size()));
		}

		nextDir = Direction.values()[rnd.nextInt(4)+1];

		if (!actions.checkDir(nextDir, AI, 8) && !following) {
			getNewFreshDirection(b, AI, nextDir);
		}
		if (random != null && actions.getBonusHandler().checkPlayerOnBonus(AI, random)) {
			following = false;
		}
		
		// CHECKPOINTS
		if (checkpoints.size() > 1 && !following && score.Calculate() >= pointsToFollow) {
			for (Player p : actions.getPlayers()) {
				if (calcDistance(p, random.getX(), random.getY()) < calcDistance(AI, random.getX(), random.getX())) {
					while (random == followCP) {
						followCP = checkpoints.get(rnd.nextInt(checkpoints.size()));
					}
					random = (Checkpoint) followCP;
				}
			}
			nextDir = getNextDirectionToBonus(AI, random);
		} else if (checkpoints.size() > 0 && !actions.getBonusHandler().checkPlayerOnBonus(AI, random) && score.Calculate() >= pointsToFollow) {
			boolean teleport2cp = false;
			for (Player p : actions.getPlayers()) {
				if (calcDistance(p, random.getX(), random.getY()) < calcDistance(AI, random.getX(), random.getY())) {
					teleport2cp = true;
				}
			}
			if (teleport2cp && AI.getBonus() != null && score.Calculate() >= 10) {
				actions.triggerBonus(AI, AI.getBonus());
				nextDir = getNewFreshDirection(b, AI, nextDir);
			}
			nextDir = getNextDirectionToBonus(AI, random);
			following  = true;
			
		// ARROWS
		} else if (arrows.size() > 1) {
			for (Arrow arrow : arrows) {
				if (calcDistance(AI, this.arrow.getX(), this.arrow.getY()) > calcDistance(AI, arrow.getX(), arrow.getY())) {
					this.arrow = arrow;
				}
			}
			if (shouldAIGetArrow(AI, arrow)) {
				nextDir = getNextDirectionToBonus(AI, arrow);
			}
		} else if (arrows.size() > 0) {
			if (shouldAIGetArrow(AI, arrow)) {
				nextDir = getNextDirectionToBonus(AI, arrow);
			}
		} else {
			getNewFreshDirection(b, AI, nextDir);
		}
		setDirection(nextDir);
	}

	private void setDirection(Direction dir) {
		lastDir = dir;
	}

	/**
	 * @author Asen Lekov <asenlekoff@gmail.com>
	 * Use {@link #easy(Board, Player, int)} for AI result for getting Arrow bonus
	 * 
	 * @param AI who will take arrow bonus
	 * @param arrow which arrow bonus will be taken
	 * @return true if player when get Arrow bonus will take new cells, otherwise false
	 */

	private boolean shouldAIGetArrow(Player AI, Arrow arrow) {
		boolean isReached = false;
		for (Direction dir : Direction.values()) {
			boolean checkDir = actions.checkDir(dir, AI, 8);
			switch (dir) {
			case LEFT:
				if (checkDir && actions.getBonusHandler().checkArrowForGivingPoints(arrow)) {isReached = true;}
				break;
			case RIGHT:
				if (checkDir && actions.getBonusHandler().checkArrowForGivingPoints(arrow)) {isReached = true;}
				break;
			case UP:
				if (checkDir && actions.getBonusHandler().checkArrowForGivingPoints(arrow)) {isReached = true;}
				break;
			case DOWN:
				if (checkDir && actions.getBonusHandler().checkArrowForGivingPoints(arrow)) {isReached = true;}
				break;
			}
		}
		return isReached;
	}

	/**
	 * @author Asen Lekov <asenlekoff@gmail.com>
	 * 
	 * @param b Board that player play on
	 * @param p Target player
	 * @param l Last direction of palyer
	 * @return new direction different from last & available
	 */

	private Direction getNewFreshDirection(Board b, Player p, Direction l) {
		Direction newDir = Direction.NONE;
		while(!actions.checkDir(l, p, b.getBoardSize()) && (l == newDir || newDir == Direction.NONE)) {
			newDir = Direction.values()[rnd.nextInt(4)+1];
			setDirection(newDir);
		}
		return newDir;
	}

	/**
	 * @author Asen Lekov <asenlekoff@gmai.com>
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
	 * @author Asen Lekov <asenlekoff@gmai.com>
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
	 * @author Asen Lekov <asenlekoff@gmai.com>
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

	private Direction getNextDirectionToBonus(Player AI, BonusObject bonus) {
		Direction nextDirection = Direction.NONE;
		int x = AI.getX();
		int y = AI.getY();
		Cell destination = new Cell(bonus.getX(), bonus.getY());
		double distance = calcDistance(x,y , destination);

		for (Direction dir : Direction.values()) {
			boolean checkDir = actions.checkDir(dir, AI, 8);

			switch (dir) {
			case LEFT:
				if (checkDir && distance > calcDistance(x-1, y, destination)) {
					distance = calcDistance(x-1, y, destination);
					nextDirection = dir;
				}
				break;
			case RIGHT:
				if (checkDir && distance > calcDistance(x+1, y, destination)) {
					distance = calcDistance(x+1, y, destination);
					nextDirection = dir;
				}
				break;
			case DOWN:
				if (checkDir && distance > calcDistance(x, y+1, destination)) {
					distance = calcDistance(x, y+1, destination);
					nextDirection = dir;
				}
				break;
			case UP:
				if (checkDir && distance > calcDistance(x, y-1, destination)) {
					distance = calcDistance(x, y-1, destination);
					nextDirection = dir;
				}
				break;
			}
		}

		return nextDirection;
	}

	public Direction getNextDirection() {
		easy(actions.getBoard(), AI, rnd.nextInt(2) + 1);
		return lastDir;
	}
}