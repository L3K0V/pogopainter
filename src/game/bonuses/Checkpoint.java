package game.bonuses;

import game.player.Player;
import game.system.Board;
import game.system.Score;

public class Checkpoint extends BonusObject {
	
	private int checkpointScore = 0;
	
	public Checkpoint() {
		type = Bonuses.CHECKPOINT;
	}

	@Override
	public void setBonusEffect(Player p, Board b) {
		Score score = new Score(b, p);
		
		checkpointScore = score.Calculate();
		p.changeScore(checkpointScore);
		score.reset();
	}

	public int getScoreFromCheckpoint() {
		return checkpointScore;
	}
}
