package tempest.game.pogopainter.bonuses;

import java.util.List;

import tempest.game.pogopainter.player.Player;
import tempest.game.pogopainter.system.Board;
import tempest.game.pogopainter.system.Score;

public class Checkpoint extends BonusObject {
	
	private int checkpointScore = 0;
	
	public Checkpoint() {
		type = Bonuses.CHECKPOINT;
	}

	@Override
	public void setBonusEffect(Player player, Board board) {
		Score score = new Score(board, player);
		
		checkpointScore = score.Calculate();
		player.changeScore(checkpointScore);
		score.reset();
		board.setPlayerColorOnCell(player);
	}

	@Override
	public void setBonusEffect(List<Player> players, Board board) {}
	
}
