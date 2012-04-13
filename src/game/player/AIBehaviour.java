package game.player;

import game.bonuses.Arrow;
import game.bonuses.BonusObject;
import game.bonuses.Checkpoint;
import game.gametypes.Game;
import game.system.Board;
import game.system.Cell;
import game.system.Direction;

import java.util.List;
import java.util.Random;

public class AIBehaviour implements Behaviour {
	private Difficulty AIdifficult;
	private Game actions;
	private Direction lastDir = Direction.NONE;
	private Checkpoint random = null;
	private Arrow arrow = null;
	private Random rnd = new Random();

	public AIBehaviour(Difficulty AIdifficult, Game actions) {
		this.AIdifficult = AIdifficult;
		this.actions = actions;
	}

	public void easy(Board b,Player AI, int randomNumber) {
		Direction nextDir = Direction.NONE;
		int check = rnd.nextInt(2)+1;
		int goTo = rnd.nextInt(2)+1;

		List<Checkpoint> checkpoints = actions.getBonusHandler().getCheckpoints();
		List<Arrow> arrows = actions.getBonusHandler().getArrows();
		
		if (checkpoints.size() > 0 && !checkpoints.contains(random)) {
			random = checkpoints.get(rnd.nextInt(checkpoints.size()));
		}
		if (arrows.size() > 0 && !arrows.contains(arrow)) {
			arrow = arrows.get(rnd.nextInt(arrows.size()));
		}

		if (checkpoints.size() > 0) {
			nextDir = getNextDirectionToCheckpoint(AI, random);
		} else if (check == randomNumber) {
			if (arrows.size() > 0 && goTo == 2) {
				nextDir = getNextDirectionToCheckpoint(AI, arrow);
			} else {
				while(!actions.checkDir(nextDir, AI, b.getBoardSize())) {
					nextDir = Direction.values()[rnd.nextInt(4)+1];
					lastDir = nextDir;
				}
			}
		} else {
			nextDir = lastDir;
		}
		setDirection(nextDir);
	}

	private void setDirection(Direction dir) {
		lastDir = dir;
	}

	public Direction getDirection() {
		return lastDir;
	}

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
	
	private Direction getNewFreshDirection(Board b, Player p, Direction l) {
		Direction newDir = Direction.NONE;
		while(!actions.checkDir(l, p, b.getBoardSize()) && (l == newDir || newDir == Direction.NONE)) {
			newDir = Direction.values()[rnd.nextInt(4)+1];
		}
		return newDir;
	}

	private double calcDistance(int x, int y, Cell destination) {
		double distance = Math.sqrt(Math.pow((x - destination.getX()), 2) + 
				Math.pow((y - destination.getY()), 2));
		return distance;
	}

	private double calcDistance(Player AI, int x, int y) {
		double distance = Math.sqrt(Math.pow((AI.getX() - x), 2) + 
				Math.pow((AI.getY() - y), 2));
		return distance;
	}
	
	private Direction getNextDirectionToCheckpoint(Player AI, BonusObject bonus) {
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
}
