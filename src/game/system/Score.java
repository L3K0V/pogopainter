package game.system;

import game.player.Player;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;


public class Score {

	private Board field;
	private Player player;
	private Cell playerPosition;
	private int playerColor;
	private int score;
	private List<Cell> playerCells = new ArrayList<Cell>();

	private int getPSize() {
		return playerCells.size();
	}

	public Score(Board b, Player p) {
		this.field  = b;
		this.player = p;
		this.playerPosition = new Cell(p.getX(), p.getY());
		this.playerColor = p.getColor();
	}

	public int Calculate() {
		int cells = field.getBoardSize();
		int score = 0;

		int colScore = 0;
		int col = 0;
		boolean fullCol = false;

		int rowScore = 0;
		int row = 0;
		boolean fullRow = false;

		for (int y = 0; y < cells; y++) {
			row = 0;
			for (int x = 0; x < cells; x++) {
				if (field.getCellAt(x, y).getColor() == playerColor) {
					playerCells.add(field.getCellAt(x, y));
					row++;
				}
			}
			
			if (row / cells == 1) {
				rowScore += 2;
				if (fullRow) {
					rowScore += 5;
					fullRow = false;
				} else {
					fullRow = true;
				}
			}
		}
		
		for (int x = 0; x < cells; x++) {
			col = 0;
			for (int y = 0; y < cells; y++) {
				if (field.getCellAt(x, y).getColor() == playerColor) {
					col++;
				}
			}
			
			if (col / cells == 1) {
				colScore += 2;
				if (fullCol) {
					colScore += 5;
					fullCol = false;
				} else {
					fullCol = true;
				}
			}
		}
		
		score += rowScore + colScore;
		return playerCells.size() + score;
	}

	public void reset() {
		for (int x = 0; x < getPSize(); x++) {
			for (int y = 0; y < getPSize(); y++) {
				field.getCellAt(playerCells.get(y).getX(), playerCells.get(y).getY()).setColor(Color.GRAY);
			}
		}
	}
}
