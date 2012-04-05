package game.player;

import game.bonuses.Arrow;
import game.bonuses.BonusObject;
import game.bonuses.Checkpoint;
import game.system.Actions;
import game.system.Board;
import game.system.Cell;
import game.system.Direction;

import java.util.List;
import java.util.Random;

public class AIBehaviour implements Behaviour {
	private Difficulty AIdifficult;
	private Actions actions;
	private Direction lastDir = Direction.NONE;
	private Checkpoint random = null;
	private Arrow arrow = null;

	public AIBehaviour(Difficulty AIdifficult, Actions actions) {
		this.AIdifficult = AIdifficult;
		this.actions = actions;
	}

	public void easy(Board b,Player AI, int randomNumber) {
		Direction nextDir = Direction.NONE;
		Random rnd = new Random();
		int check = rnd.nextInt(2)+1;
		int goTo = rnd.nextInt(2)+1;

		List<Checkpoint> checkpoints = actions.getCheckpoints();
		List<Arrow> arrows = actions.getArrows();
		
		if (checkpoints.size() > 0 && !checkpoints.contains(random)) {
			random = checkpoints.get(rnd.nextInt(checkpoints.size()));
		}
		if (arrows.size() > 0 && !arrows.contains(arrow)) {
			arrow = arrows.get(rnd.nextInt(arrows.size()));
		}

		if (checkpoints.size() > 0 && goTo == 1) {
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

	private double calcDistance(int x, int y, Cell destination) {
		double distance = Math.sqrt(Math.pow((x - destination.getX()), 2) + 
				Math.pow((y - destination.getY()), 2));

		return distance;
	}

	//TODO: check every neighbour cell distance to one of the checkpoints and 
	//		return the direction with shortest distance to update!

	private Direction getNextDirectionToCheckpoint(Player AI, BonusObject bonus) {
		Direction nextDirection = Direction.NONE;
		int x = AI.getX();
		int y = AI.getY();
		Cell destination = new Cell(bonus.getX(), bonus.getY());
		double distance = calcDistance(x,y , destination);

		for (Direction dir : Direction.values()) {
			switch (dir) {
			case LEFT:
				if (actions.checkDir(dir, AI, 8) && distance > calcDistance(x-1, y, destination)) {
					distance = calcDistance(x-1, y, destination);
					nextDirection = dir;
				}
				break;
			case RIGHT:
				if (actions.checkDir(dir, AI, 8) && distance > calcDistance(x+1, y, destination)) {
					distance = calcDistance(x+1, y, destination);
					nextDirection = dir;
				}

				break;
			case DOWN:
				if (actions.checkDir(dir, AI, 8) && distance > calcDistance(x, y+1, destination)) {
					distance = calcDistance(x, y+1, destination);
					nextDirection = dir;
				}
				break;
			case UP:
				if (actions.checkDir(dir, AI, 8) && distance > calcDistance(x, y-1, destination)) {
					distance = calcDistance(x, y-1, destination);
					nextDirection = dir;
				}
				break;
			}
		}

		return nextDirection;
	}
}
