package game.player;

import game.system.Actions;
import game.system.Board;
import game.system.Direction;
import java.util.Random;

public class AIBehaviour implements Behaviour{
	private Difficulty AIdifficult;
	private Actions actions = new Actions();
	private Direction lastDir = Direction.NONE;

	public AIBehaviour(Difficulty AIdifficult) {
		this.AIdifficult = AIdifficult;
	}

	public void easy(Board b,Player AI, int randomNumber) {
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
		setDirection(nextDir);
	}
	
	private void setDirection(Direction dir) {
		lastDir = dir;
	}
	
	public Direction getDirection() {
		return lastDir;
	}
}
