package game.system;

public enum Direction {
	LEFT(0), RIGHT(1), UP(2), DOWN(3);
	
	private int dir;
	
	private Direction(int dir) {
		this.dir = dir;
	}
}
