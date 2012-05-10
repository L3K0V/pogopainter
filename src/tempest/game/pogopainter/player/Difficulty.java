package tempest.game.pogopainter.player;

public enum Difficulty {
	EASY(0), NORMAL(2), HARD(3);

	private int value;
	
	private Difficulty(int value) {
		this.value = value;
	}

	public int getDirectionInt() {
		return value;
	}
}
