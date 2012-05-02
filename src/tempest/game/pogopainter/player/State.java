package tempest.game.pogopainter.player;

public enum State {
	STUN(0), JUMP(1), RUN(2);
	
	private int state;
	
	private State(int state) {
		this.state = state;
	}
	
	public int getStateInt() {
		return state;
	}
}
