package game.system;

public enum Direction {
	NONE(0), LEFT(1), RIGHT(2), UP(3), DOWN(4);
	
	private int dir;
	
	private Direction(int dir) {
		this.dir = dir;
	}
	
	public int getDirectionInt() {
		return dir;
	}
}
