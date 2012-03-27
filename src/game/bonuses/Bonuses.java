package game.bonuses;

public enum Bonuses {
	CHECKPOINT(0), SPEEDUP(1), SHOOT(2);
	
	private int bonus;
	
	private Bonuses(int bonus) {
		this.bonus = bonus;
	}
	
	public int getBonusInt() {
		return bonus;
	}
}
