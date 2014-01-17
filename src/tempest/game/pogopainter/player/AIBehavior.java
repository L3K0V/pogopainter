package tempest.game.pogopainter.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tempest.game.pogopainter.bonuses.Arrow;
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

		List<Player> otherPlayers = new ArrayList<Player>();
		otherPlayers.addAll(actions.getPlayers());
		otherPlayers.remove(ai);
		double distance = 0.0, enemyDistance = 0.0, max = 0.0;
		int px = 0, py = 0, ex = 0, ey = 0;

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
					for (Player player : otherPlayers) {
						if (actions.checkDir(dir, player)) {
							switch (dir) {
							case LEFT:
								px = player.getX() +1;
								break;
							case RIGHT:
								px = player.getX() -1;
								break;
							case UP:
								py = player.getY() -1;
								break;
							case DOWN:
								py = player.getY() +1;
								break;
							default:
								break;
							}
						}
						enemyDistance = calcDistance(ex, ey, bonus);

						if (calcBonusScore(bonus, enemyDistance, player) > max) {
							max = calcBonusScore(bonus, enemyDistance, player);
						}
					}
					if (calcBonusScore(bonus, distance, ai) > max) {
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

	private double calcBonusScore(BonusObject bonus, Double distance, Player pl) {
		double result = 0.0;
		Score score = new Score(actions.getBoard(), pl);
		switch (bonus.getType()) {
		case CHECKPOINT:
			result = ((score.Calculate() + distance) * bonus.getWeight() ) / distance;
			break;
		case ARROW:
			int cells = 0;
			Arrow arrow = (Arrow) bonus;
			switch (arrow.getState()) {
			case 1:
				for (int i = arrow.getX(); i < 8; i++) {
					cells++;
				}
				break;
			case 2:
				for (int i = arrow.getY(); i < 8; i++) {
					cells++;
				}
				break;
			case 3:
				for (int i = arrow.getX(); i < -1; i++) {
					cells++;
				}
				break;
			case 4:
				for (int i = arrow.getX(); i < -1; i++) {
					cells++;
				}
				break;
			}
			result = ((distance + cells) * bonus.getWeight() ) / distance;
			break;
		}

		return result;
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