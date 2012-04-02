package game.player;

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

	public AIBehaviour(Difficulty AIdifficult, Actions actions) {
		this.AIdifficult = AIdifficult;
		this.actions = actions;
	}

	public void easy(Board b,Player AI, int randomNumber) {
		Direction nextDir = Direction.NONE;
		Random rnd = new Random();
		int check = rnd.nextInt(4)+1;

		List<Checkpoint> checkpoints = actions.getCheckpoints();
		
		if (checkpoints.size() > 0 && !checkpoints.contains(random)) {
			random = checkpoints.get(rnd.nextInt(checkpoints.size()));
		}

		if (checkpoints.size() > 0) {
			nextDir = getNextDirectionToCheckpoint(AI, random);
		} else if (check == randomNumber) {
			while(!actions.checkDir(nextDir, AI, b.getBoardSize())) {
				nextDir = Direction.values()[rnd.nextInt(4)+1];
				lastDir = nextDir;
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

	private Direction getNextDirectionToCheckpoint(Player AI, Checkpoint checkpoint) {
		Direction nextDirection = Direction.NONE;
		int x = AI.getX();
		int y = AI.getY();
		Cell destination = new Cell(checkpoint.getX(), checkpoint.getY());
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
