package tempest.game.pogopainter.gametypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tempest.game.pogopainter.activities.CanvasActivity;
import tempest.game.pogopainter.bonuses.Arrow;
import tempest.game.pogopainter.bonuses.BonusHandler;
import tempest.game.pogopainter.bonuses.BonusObject;
import tempest.game.pogopainter.bonuses.Bonuses;
import tempest.game.pogopainter.bonuses.Checkpoint;
import tempest.game.pogopainter.bonuses.Teleport;
import tempest.game.pogopainter.graphics.Panel;
import tempest.game.pogopainter.player.AIBehavior;
import tempest.game.pogopainter.player.Difficulty;
import tempest.game.pogopainter.player.Player;
import tempest.game.pogopainter.player.UserBehavior;
import tempest.game.pogopainter.system.Board;
import tempest.game.pogopainter.system.Cell;
import tempest.game.pogopainter.system.Direction;

import android.graphics.Color;

public abstract class Game {
	protected Board b;
	protected Panel _panel;
	protected int time = 1000;
	protected BonusHandler bHandler;
	
	protected List<Player> PLAYERS;
	protected List<Player> movedPlayers;
	
	protected abstract void initFields();
	
	protected void initPlayerColors(int classicCellNumber, int playerColor) {
		switch (playerColor) {
		case Color.RED:
			PLAYERS.add(new Player(0, classicCellNumber - 1, Color.RED, 0, new UserBehavior(_panel)));
			PLAYERS.add(new Player(classicCellNumber-1, classicCellNumber-1, Color.BLUE, 0, new AIBehavior(Difficulty.EASY, this)));
			PLAYERS.add(new Player(0, 0, Color.GREEN, 0, new AIBehavior(Difficulty.EASY, this)));
			PLAYERS.add(new Player(classicCellNumber - 1, 0, Color.YELLOW, 0, new AIBehavior(Difficulty.EASY, this)));
			break;
		case Color.BLUE:
			PLAYERS.add(new Player(classicCellNumber-1, classicCellNumber-1, Color.BLUE, 0, new UserBehavior(_panel)));
			PLAYERS.add(new Player(0, classicCellNumber - 1, Color.RED, 0, new AIBehavior(Difficulty.EASY, this)));
			PLAYERS.add(new Player(0, 0, Color.GREEN, 0, new AIBehavior(Difficulty.EASY, this)));
			PLAYERS.add(new Player(classicCellNumber - 1, 0, Color.YELLOW, 0, new AIBehavior(Difficulty.EASY, this)));
			break;
		case Color.GREEN:
			PLAYERS.add(new Player(0, 0, Color.GREEN, 0, new UserBehavior(_panel)));
			PLAYERS.add(new Player(0, classicCellNumber - 1, Color.RED, 0, new AIBehavior(Difficulty.EASY, this)));
			PLAYERS.add(new Player(classicCellNumber-1, classicCellNumber-1, Color.BLUE, 0, new AIBehavior(Difficulty.EASY, this)));
			PLAYERS.add(new Player(classicCellNumber - 1, 0, Color.YELLOW, 0, new AIBehavior(Difficulty.EASY, this)));
			break;
		case Color.YELLOW:
			PLAYERS.add(new Player(classicCellNumber - 1, 0, Color.YELLOW, 0, new UserBehavior(_panel)));
			PLAYERS.add(new Player(0, classicCellNumber - 1, Color.RED, 0, new AIBehavior(Difficulty.EASY, this)));
			PLAYERS.add(new Player(classicCellNumber-1, classicCellNumber-1, Color.BLUE, 0, new AIBehavior(Difficulty.EASY, this)));
			PLAYERS.add(new Player(0, 0, Color.GREEN, 0, new AIBehavior(Difficulty.EASY, this)));
			break;
		default:
			PLAYERS.add(new Player(0, classicCellNumber - 1, Color.RED, 0, new UserBehavior(_panel)));
			PLAYERS.add(new Player(classicCellNumber-1, classicCellNumber-1, Color.BLUE, 0, new AIBehavior(Difficulty.EASY, this)));
			PLAYERS.add(new Player(0, 0, Color.GREEN, 0, new AIBehavior(Difficulty.EASY, this)));
			PLAYERS.add(new Player(classicCellNumber - 1, 0, Color.YELLOW, 0, new AIBehavior(Difficulty.EASY, this)));
			break;
		}
	}
	
	public void move(Board b, Player player, Direction dir) {
		int boardSize = b.getBoardSize();

		switch (dir) {
		case RIGHT:
			if (checkDir(dir, player, boardSize)) {
				player.setX(player.getX() + 1);
			}
			break;
		case UP:
			if (checkDir(dir, player, boardSize)) {
				player.setY(player.getY() -1);
			}
			break;
		case LEFT:
			if (checkDir(dir, player, boardSize)) {
				player.setX(player.getX() -1);
			}
			break;
		case DOWN:
			if (checkDir(dir, player, boardSize)) {
				player.setY(player.getY() +1);	
			}
			break;
		}
		b.setPlayerColorOnCell(player);
		applyBonusEffect(player, b);
		movedPlayers.add(player);
	}
	
	public boolean checkDir(Direction dir, Player player, int boardSize) {
		boolean res = false;
		boardSize --;
		switch(dir) {
		case RIGHT:
			if (player.getX() < boardSize && !checkIfPlayer(player.getX() + 1, player.getY())) {
				res = true;
			}
			break;
		case UP:
			if(player.getY() > 0 && !checkIfPlayer(player.getX(), player.getY() - 1)) {
				res = true;
			}
			break;
		case LEFT:
			if (player.getX() > 0 && !checkIfPlayer(player.getX() - 1, player.getY())) {
				res = true;
			}
			break;
		case DOWN:
			if (player.getY() < boardSize && !checkIfPlayer(player.getX(), player.getY() + 1)) {
				res = true;
			}
			break;
		}
		return res;
	}

	private boolean checkIfPlayer(int x, int y) {
		boolean res = false;
		for (Player pl : movedPlayers) {
			if (pl.getX() == x && pl.getY() == y) {
				res = true;
			}
		}	
		return res;
	}

	public BonusObject checkForBonus(Player p, Board b) {
		BonusObject res;
		Cell thisCell = b.getCellAt(p.getX(), p.getY());
		res = thisCell.getBonus();
		return res;
	}

	public void applyBonusEffect(Player p, Board b) {
		BonusObject bonus;
		if ((bonus = checkForBonus(p, b)) != null) {
			if (!(bonus.getType() == Bonuses.TELEPORT)) {
				triggerBonus(p, bonus);
			} else {
				p.setBonus(bonus);
			}
			clearBonus(p, b);
			for (Checkpoint cp : bHandler.getCheckpoints()) {
				if (p.getX() == cp.getX() && p.getY() == cp.getY()) {
					bHandler.getCheckpoints().remove(cp);
					break;
				}
			}
			
			for (Arrow aw : bHandler.getArrows()) {
				if (p.getX() == aw.getX() && p.getY() == aw.getY()) {
					bHandler.getArrows().remove(aw);
					break;
				}
			}
			
			for (Teleport tp : bHandler.getTeleports()) {
				if (p.getX() == tp.getX() && p.getY() == tp.getY()) {
					bHandler.getTeleports().remove(tp);
					break;
				}
			}
		}
	}

	public void triggerBonus(Player player, BonusObject bonus) {
		if (bonus == null) {
			return;
		}
		if (bonus.getType() == Bonuses.TELEPORT) {
			Random rnd = new Random();
			Checkpoint cp;
			List<Checkpoint> checkpoints = bHandler.getCheckpoints();
			if (checkpoints.size() > 0) {
				cp = checkpoints.get(rnd.nextInt(checkpoints.size()));
				Teleport tp = (Teleport) bonus;
				tp.setBonusEffect(player, b, cp);
				checkpoints.remove(cp);
				player.setBonus(null);
			}
		} else {
			bonus.setBonusEffect(player, b);
		}
	}

	protected void clearBonus(Player p, Board b) {
		b.getCellAt(p.getX(), p.getY()).clearBonus();
	}
	
	public Board getBoard() {
		return b;
	}

	public int getTime() {
		return time;
	}

	public Player getUser() {
		return PLAYERS.get(0);
	}

	public List<Player> getAIs() {
		List<Player> res = new ArrayList<Player>();
		for (int i = 1; i < PLAYERS.size(); i++) {
			res.add(PLAYERS.get(i));
		}
		return res;
	}
	public List<Player> getPlayers() {
		return PLAYERS;
	}
	public BonusHandler getBonusHandler() {
		return bHandler;
	}
	
	public void update() {
		if (time == 0) {
			finishGame();
		} else {
			for (Player pl: PLAYERS) {
				move(b, pl, pl.getBehaviour().getNextDirection());
			}
			movedPlayers.clear();
			bHandler.update();
		}
		time--;
	}

	private void finishGame() {
		_panel.stopThreads();
		_panel.surfaceDestroyed(_panel.getHolder());
		_panel.clearFocus();
		CanvasActivity.showResults = true;
		_panel.getOwner().finish();
	}
}