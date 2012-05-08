package tempest.game.pogopainter.gametypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import tempeset.game.pogopainter.R;
import tempest.game.pogopainter.activities.CanvasActivity;
import tempest.game.pogopainter.bonuses.BonusHandler;
import tempest.game.pogopainter.bonuses.BonusObject;
import tempest.game.pogopainter.bonuses.Bonuses;
import tempest.game.pogopainter.bonuses.Checkpoint;
import tempest.game.pogopainter.bonuses.Teleport;
import tempest.game.pogopainter.graphics.Panel;
import tempest.game.pogopainter.player.AIBehavior;
import tempest.game.pogopainter.player.Difficulty;
import tempest.game.pogopainter.player.Player;
import tempest.game.pogopainter.player.PlayerState;
import tempest.game.pogopainter.player.UserBehavior;
import tempest.game.pogopainter.system.Board;
import tempest.game.pogopainter.system.Cell;
import tempest.game.pogopainter.system.Direction;
import tempest.game.pogopainter.system.Music;

import android.graphics.Color;

public abstract class Game {
	protected static int STUN_TIME;
	protected Board board;
	protected Panel panel;
	protected int time;
	protected BonusHandler bHandler;
	protected Music	music;
	protected List<Player> PLAYERS;
	protected List<Player> movedPlayers;
	protected boolean ifSounds;
	protected boolean ifBackground;
	protected int stunTimer = -1;
	
	protected abstract void initFields();
	
	protected void initPlayerColors(int classicCellNumber, int playerColor) {
		switch (playerColor) {
		case Color.RED:
			initRedUser(classicCellNumber);
			break;
		case Color.BLUE:
			initBlueUser(classicCellNumber);
			break;
		case Color.GREEN:
			initGreenUser(classicCellNumber);
			break;
		case Color.YELLOW:
			initYellowUser(classicCellNumber);
			break;
		default:
			initRedUser(classicCellNumber);
			break;
		}
	}

	private void initYellowUser(int classicCellNumber) {
		PLAYERS.add(new Player(classicCellNumber - 1, 0, Color.YELLOW,
				0, new UserBehavior(panel)));
		PLAYERS.add(new Player(0, classicCellNumber - 1, Color.RED,
				0, new AIBehavior(Difficulty.EASY, this)));
		PLAYERS.add(new Player(classicCellNumber-1, classicCellNumber-1, Color.BLUE,
				0, new AIBehavior(Difficulty.EASY, this)));
		PLAYERS.add(new Player(0, 0, Color.GREEN,
				0, new AIBehavior(Difficulty.EASY, this)));
	}

	private void initGreenUser(int classicCellNumber) {
		PLAYERS.add(new Player(0, 0, Color.GREEN,
				0, new UserBehavior(panel)));
		PLAYERS.add(new Player(0, classicCellNumber - 1, Color.RED,
				0, new AIBehavior(Difficulty.EASY, this)));
		PLAYERS.add(new Player(classicCellNumber-1, classicCellNumber-1, Color.BLUE,
				0, new AIBehavior(Difficulty.EASY, this)));
		PLAYERS.add(new Player(classicCellNumber - 1, 0, Color.YELLOW,
				0, new AIBehavior(Difficulty.EASY, this)));
	}

	private void initBlueUser(int classicCellNumber) {
		PLAYERS.add(new Player(classicCellNumber-1, classicCellNumber-1, Color.BLUE,
				0, new UserBehavior(panel)));
		PLAYERS.add(new Player(0, classicCellNumber - 1, Color.RED,
				0, new AIBehavior(Difficulty.EASY, this)));
		PLAYERS.add(new Player(0, 0, Color.GREEN,
				0, new AIBehavior(Difficulty.EASY, this)));
		PLAYERS.add(new Player(classicCellNumber - 1,
				0, Color.YELLOW, 0, new AIBehavior(Difficulty.EASY, this)));
	}

	private void initRedUser(int classicCellNumber) {
		PLAYERS.add(new Player(0, classicCellNumber - 1, Color.RED,
				0, new UserBehavior(panel)));
		PLAYERS.add(new Player(classicCellNumber-1, classicCellNumber-1, Color.BLUE, 
				0, new AIBehavior(Difficulty.EASY, this)));
		PLAYERS.add(new Player(0, 0, Color.GREEN, 
				0, new AIBehavior(Difficulty.EASY, this)));
		PLAYERS.add(new Player(classicCellNumber - 1, 0, Color.YELLOW, 
				0, new AIBehavior(Difficulty.EASY, this)));
	}
	
	public void move(Board board, Player player, Direction dir) {
		if (player.getState() == PlayerState.STUN) {
			return;
		}
		movedPlayers.add(player);
		switch (dir) {
		case RIGHT:
			if (checkDir(dir, player)) {
				player.startMoving(dir, board);
				player.setX(player.getX() + 1);
			}
			break;
		case UP:
			if (checkDir(dir, player)) {
				player.startMoving(dir, board);
				player.setY(player.getY() -1);
			}
			break;
		case LEFT:
			if (checkDir(dir, player)) {
				player.startMoving(dir, board);
				player.setX(player.getX() -1);
			}
			break;
		case DOWN:
			if (checkDir(dir, player)) {
				player.startMoving(dir, board);
				player.setY(player.getY() +1);	
			}
			break;
		}
		applyBonusEffect(player, board);
	}
	
	public boolean checkDir(Direction dir, Player player) {
		boolean res = false;
		int boardSize = board.getBoardSize();
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
		}
	}

	public void triggerBonus(Player player, BonusObject bonus) {
		if (bonus == null) {
			return;
		}
		playBonusMusic(bonus);
		if (bonus.getType() == Bonuses.TELEPORT) {
			Random rnd = new Random();
			Checkpoint cp;
			List<Checkpoint> checkpoints = bHandler.getCheckpoints();
			if (checkpoints.size() > 0) {
				cp = checkpoints.get(rnd.nextInt(checkpoints.size()));
				Teleport tp = (Teleport) bonus;
				tp.setBonusEffect(player, board, cp);
				checkpoints.remove(cp);
				player.setBonus(null);
			}
		} else if (bonus.getType() == Bonuses.CLEANER) {
			bonus.setBonusEffect(movedPlayers, board);
		} else if (bonus.getType() == Bonuses.STUN) {
			List<Player> stunPl = new ArrayList<Player>(PLAYERS);
			stunPl.remove(player);
			bonus.setBonusEffect(stunPl, board);
			stunTimer = 0;
		} else {
			bonus.setBonusEffect(player, board);
		}
	}

	private void playBonusMusic(BonusObject bonus) {
		switch(bonus.getType()) {
		case TELEPORT :
			music.playSound(R.raw.teleport);
			break;
		case ARROW :
			music.playSound(R.raw.arrow);
			break;
		case CHECKPOINT :
			music.playSound(R.raw.checkpoint);
			break;
		case CLEANER :
			music.playSound(R.raw.cleaner);
			break;
		case STUN :
			music.playSound(R.raw.stun);
			break;
		}
	}

	private void clearBonus(Player p, Board b) {
		b.getCellAt(p.getX(), p.getY()).clearBonus();
		for (Checkpoint cp : bHandler.getCheckpoints()) {
			if (p.getX() == cp.getX() && p.getY() == cp.getY()) {
				bHandler.removeBonus(BonusHandler.CheckQueue, cp);
				break;
			}
		}
		
		for (BonusObject other : bHandler.getOtherBonuses()) {
			if (p.getX() == other.getX() && p.getY() == other.getY()) {
				bHandler.removeBonus(BonusHandler.OtherQueue, other);
				break;
			}
		}
	}
	
	public Board getBoard() {
		return board;
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
	
	public Music getMusic() {
		return music;
	}
	
	private void manageStun() {
		if (stunTimer >= 0) {
			stunTimer++;
			if (stunTimer == STUN_TIME) {
				for(Player pl : PLAYERS) {
					pl.setState(PlayerState.NORMAL);
				}
				stunTimer = -1;
			}
		}
	}

	public void update() {
		if (time == 0) {
			finishGame();
		} else {
			for (Player pl: PLAYERS) {
				move(board, pl, pl.getBehaviour().getNextDirection());
			}
			movedPlayers.clear();
			bHandler.update();
		}
		time--;
		manageStun();
	}

	private void finishGame() {
		panel.stopThreads();
		panel.surfaceDestroyed(panel.getHolder());
		panel.clearFocus();
		CanvasActivity.showResults = true;
		panel.getOwner().finish();
	}
}