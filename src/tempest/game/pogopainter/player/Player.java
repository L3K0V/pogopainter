package tempest.game.pogopainter.player;

import tempeset.game.pogopainter.bonuses.BonusObject;

public class Player {
	private int x;
	private int y;
	private int color;
	private BonusObject bonus;
	private int speed;
	private Behavior behaviour;
	private int points;
	
	public Player(int x, int y, int color,int points, Behavior control) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.speed = 1;
		this.behaviour = control;
		this.points = points;
		behaviour.setPlayer(this);
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getColor() {
		return this.color;
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	public void changeScore(int score) {
		this.points += score;
	}
	
	public int getPoints() {
		return this.points;
	}
	
	public void setBonus(BonusObject b) {
		bonus = b;
	}
	
	public BonusObject getBonus() {
		return bonus;
	}
	
	public Behavior getBehaviour() {
		return behaviour;
	}
}
