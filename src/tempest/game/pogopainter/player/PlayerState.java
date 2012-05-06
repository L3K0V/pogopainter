package tempest.game.pogopainter.player;

public enum PlayerState {
	STUN(0), NORMAL(1);
	
	private int state;
	
	private PlayerState(int state) {
		this.state = state;
	}
	
	public int getStateInt() {
		return state;
	}
}
