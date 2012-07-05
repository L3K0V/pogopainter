package tempest.game.pogopainter.bonuses;

import java.util.List;

import tempest.game.pogopainter.player.Player;
import tempest.game.pogopainter.player.PlayerState;
import tempest.game.pogopainter.system.Board;

public class SpeedUp extends BonusObject {

	public SpeedUp() {
		this.type = Bonuses.SPEEDUP;
	}
	
	@Override
	public void setBonusEffect(Player player, Board board) {
		player.setSpeed(5);
		player.setState(PlayerState.SPEED);
	}

	@Override
	public void setBonusEffect(List<Player> players, Board board) {
	}

}
