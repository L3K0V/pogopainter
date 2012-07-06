package tempest.game.pogopainter.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tempest.game.pogopainter.bonuses.BonusObject;
import tempest.game.pogopainter.gametypes.Game;
import tempest.game.pogopainter.system.Cell;
import tempest.game.pogopainter.system.Direction;
import tempest.game.pogopainter.system.Score;

public class AIBehavior implements Behavior {
	private Game actions;
	private Direction nextDirection      = Direction.NONE;

	private Random rnd        = new Random();
	private Player ai;

	public AIBehavior(Difficulty AIdifficult, Game game) {
		this.actions = game;
	}

	public void setPlayer(Player pl) {
		this.ai = pl;
	}
	
	public Direction getNextDirection() {
		List<BonusObject> bonuses = new ArrayList<BonusObject>();
		bonuses.addAll(actions.getBonusHandler().getCheckpoints());
		bonuses.addAll(actions.getBonusHandler().getOtherBonuses());
		double distance = 0.0, max = 0.0;
		int px = 0, py = 0;

		for (Direction dir : Direction.values()) {
			if (actions.checkDir(dir, ai)) {
				switch (dir) {
				case LEFT:
					px = ai.getX() +1;
					break;
				case RIGHT:
					px = ai.getX() -1;
					break;
				case UP:
					py = ai.getY() -1;
					break;
				case DOWN:
					py = ai.getY() +1;
					break;
				default:
					break;
				}
				
				for (BonusObject bonus : bonuses) {
					distance = calcDistance(px, py, bonus);
					calcBonusScore(bonus, distance);
					
					if (calcBonusScore(bonus, distance) > max) {
						getNextDirectionToBonus(bonus);
					}
				}
			}
		}
		return nextDirection;
	}
	
	private double calcDistance(int x, int y, Cell destination) {
		double distance = Math.sqrt(Math.pow((x - destination.getX()), 2) + 
				Math.pow((y - destination.getY()), 2));
		return distance;
	}
	
	private double calcDistance(int px, int py, BonusObject bonus) {
		return Math.abs(px - bonus.getX()) + Math.abs(py - bonus.getY());
	}
	
	private double calcBonusScore(BonusObject bonus, Double distance) {
		Score score = new Score(actions.getBoard(), ai);
		return ((score.Calculate() + distance) * bonus.getWeight() ) / distance;
	}

	private void getNextDirectionToBonus(BonusObject bonus) {
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
}