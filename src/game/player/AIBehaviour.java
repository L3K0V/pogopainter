package game.player;

import game.system.Board;
import game.system.Direction;

import java.util.Random;

public class AIBehaviour implements Behaviour{
	private Difficult AIdifficult;
	private Actions actions = new Actions();
	private Direction lastDir = Direction.NONE;

	public AIBehaviour(Difficult AIdifficult) {
		this.AIdifficult = AIdifficult;
	}

	public Direction easy(Board b,Player AI, int randomNumber) {
		Direction nextDir = Direction.NONE;
		Random rnd = new Random();
		int check = rnd.nextInt(4)+1;

		if (check == randomNumber) {
			while(!actions.checkDir(nextDir, AI, b.getBoardSize())) {
				nextDir = Direction.values()[rnd.nextInt(4)+1];
				lastDir = nextDir;
			}
		} else {
			nextDir = lastDir;
		}
		return nextDir;
	}
}
