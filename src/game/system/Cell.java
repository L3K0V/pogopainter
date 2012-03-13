package game.system;

import android.graphics.Color;

public class Cell {

	private int x;
	private int y;
	private int color;
	private BonusObject bonus;
	private boolean playerOnCell;
	
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
		this.setColor(Color.GRAY);
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setColor(int color) {
		this.color = color;
	}
	
	public void setBonus(BonusObject bonus) {
		this.bonus = bonus;
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
	
	public BonusObject getBonus() {
		return this.bonus;
	}

	public boolean isPlayerOnCell() {
		return playerOnCell;
	}

	public void setPlayerOnCell(boolean playerOnCell) {
		this.playerOnCell = playerOnCell;
	}
}
