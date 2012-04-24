package tempest.game.pogopainter.system;


import java.util.ArrayList;
import java.util.List;

import tempest.game.pogopainter.player.Player;


public class Board {
	private List<Cell> field = new ArrayList<Cell>();
	private int cells;
	
	public Board(int cell) {
		this.cells = cell;
		for (int y = 0; y < cells; y++) {
			for (int x = 0; x < cells; x++) {
				field.add(new Cell(x, y));
			}
		}
	}
	
	public int getBoardSize() {
		return cells;
	}
	
	public Cell getCellAt(int x, int y) {
		Cell cell = null;
		if ((x < (cells-1) || (x > 0)) || (y < (cells-1) || (y > 0)))		
			cell = field.get(x + y*cells);
		return cell;
	}
	
	public void setPlayerColorOnCell(Player player) {
		int x = player.getX();
		int y = player.getY();
		this.getCellAt(x, y).setColor(player.getColor());
	}
}
