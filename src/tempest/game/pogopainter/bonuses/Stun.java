package tempest.game.pogopainter.bonuses;

import java.util.List;

import tempest.game.pogopainter.player.Player;
import tempest.game.pogopainter.player.PlayerState;
import tempest.game.pogopainter.system.Board;

public class Stun extends BonusObject{
	
	public Stun() {
		type = Bonuses.STUN;
	}

	@Override
	public void setBonusEffect(Player player, Board board) {}

	@Override
	public void setBonusEffect(List<Player> players, Board board) {
		for(Player pl: players) {
			pl.setState(PlayerState.STUN);
		}
	}
}