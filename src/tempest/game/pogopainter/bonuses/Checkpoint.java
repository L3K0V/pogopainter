package tempest.game.pogopainter.bonuses;

import tempest.game.pogopainter.player.Player;
import tempest.game.pogopainter.system.Board;
import tempest.game.pogopainter.system.Score;

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
		b.setPlayerColorOnCell(p);
	}

	public int getScoreFromCheckpoint() {
		return checkpointScore;
	}
}
