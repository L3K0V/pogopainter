package game.bonuses;

import game.player.Player;
import game.system.Board;

public class Teleport extends BonusObject {

	public Teleport() {
		type = Bonuses.TELEPORT;
	}
	
	@Override
	public void setBonusEffect(Player p, Board b) {}
	
	public void setBonusEffect(Player p, Board b, Checkpoint bonus) {
		p.setX(bonus.getX());
		p.setY(bonus.getY());
		b.setPlayerColorOnCell(p);
		
		p.setBonus(b.getCellAt(p.getX(), p.getY()).getBonus());
		p.getBonus().setBonusEffect(p, b);
		
		b.getCellAt(p.getX(), p.getY()).clearBonus();
	}

}
