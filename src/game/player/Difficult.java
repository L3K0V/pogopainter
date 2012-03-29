package game.player;

public enum Difficult {
	EASY(0), NORMAL(2), HARD(3);

	private int value;
	
	private Difficult(int value) {
		this.value = value;
	}

	public int getDirectionInt() {
		return value;
	}
}
