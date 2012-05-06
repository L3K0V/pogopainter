package tempest.game.pogopainter.bonuses;

import java.util.List;

import tempest.game.pogopainter.player.Player;
import tempest.game.pogopainter.system.Board;

public class Teleport extends BonusObject {

	public Teleport() {
		type = Bonuses.TELEPORT;
	}
	
	@Override
	public void setBonusEffect(Player player, Board board) {}
	
	public void setBonusEffect(Player player, Board board, Checkpoint bonus) {
		player.setX(bonus.getX());
		player.setY(bonus.getY());
		board.setPlayerColorOnCell(player);
		
		player.setBonus(board.getCellAt(player.getX(), player.getY()).getBonus());
		player.getBonus().setBonusEffect(player, board);
		
		board.getCellAt(player.getX(), player.getY()).clearBonus();
	}

	@Override
	public void setBonusEffect(List<Player> players, Board board) {}

}
