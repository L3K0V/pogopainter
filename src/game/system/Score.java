package game.system;

import game.player.Player;

import java.util.ArrayList;
import java.util.List;


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
			int row = 0;
			int col = 0;
			boolean full = false;
			int cells = field.getBoardSize();
			
			for (int y = 0; y < cells; y++) {
				for (int x = 0; x < cells; x++) {
					if (field.getCellAt(x, y).getColor() == playerColor) {
						playerCells.add(field.getCellAt(x, y));
						row++;
					}
					if ((y < cells-1) && (field.getCellAt(x, y).getColor()) == field.getCellAt(x, y+1).getColor()) {
						col++;
						if (col % cells == 0) {
							score += 2;
						}
						if (col % (cells * 2)== 0) {
							score += 5;
						}
					}
				}
				if ((row > 0 ) && (row % cells == 0)) {
					if (!full) {
						score += 2;
						full = true;
					} else {
						score +=7;
						full = false;
					}
				} else if (full) {
					full = false;
				}
				row = 0;
				col = 0;

			}
			return score += getPSize();
		}
		
		public void reset() {
			for (int x = 0; x < getPSize(); x++) {
				for (int y = 0; y < getPSize(); y++) {
					field.getCellAt(playerCells.get(y).getX(), playerCells.get(y).getY()).setColor(0);
				}
			}
		}
}
