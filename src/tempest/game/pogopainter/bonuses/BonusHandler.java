package tempest.game.pogopainter.bonuses;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import tempest.game.pogopainter.player.Player;
import tempest.game.pogopainter.system.Board;

public class BonusHandler {
	private Board board;
	private List<Player> players;

	private BonusQueues queues;
    private List<Checkpoint> checkpoints;
    private List<BonusObject> otherBonuses;
    
	private Random rnd;
	
    private int checkpointLimit;
    private int otherBonusLimit;
    
    public static final boolean CHECK_QUEUE = true;
    public static final boolean OTHER_QUEUE = false;

	public BonusHandler(Board board, List<Player> pl, int cpLimit, int otherBLimit) {
		this.board = board;
		this.players = pl;
		rnd = new Random();
		checkpointLimit = cpLimit;
		otherBonusLimit = otherBLimit;
		seedBonuses();
	}

	private void fillQueues() {
		while (!queues.full(CHECK_QUEUE)) {
			queues.push(CHECK_QUEUE, new Checkpoint());
		}
		while (!queues.full(OTHER_QUEUE)) {
			queues.push(OTHER_QUEUE, getRandomBonus());
		}
	}

	private BonusObject getRandomBonus() {
		BonusObject res = null;
		int ran = rnd.nextInt(10);
		switch (ran) {
		case 0: case 1: case 2:
			res = new Arrow();
			break;
		case 3:
			res = new Teleport();
			break;
		case 4: case 5:
			res = new Stun();
			break;
		case 6: case 7:
			res = new Cleaner();
			break;
		case 8: case 9:
			res = new SpeedUp();
		}
		return res;
	}

	private void seedBonuses() {
		checkpoints   = new ArrayList<Checkpoint>();
		otherBonuses = new ArrayList<BonusObject>();
		this.queues = new BonusQueues(checkpointLimit, otherBonusLimit);
		fillQueues();
	}

	public void update() {
		putBonus();
		fillQueues();
		for (BonusObject bo : otherBonuses) {
			if (bo.getType() == Bonuses.ARROW) {
				Arrow aw = (Arrow) bo;
				aw.changeState();
			}
		}
	}
	
	public void initialBonuses() {
		while ((checkpoints.size() != checkpointLimit) 
				&& (otherBonuses.size() != otherBonusLimit)) {
			putCheckpointBonus();
			putOtherBonus();
		}
	}

	public void putBonus() {
		putCheckpointBonus();
		putOtherBonus();
	}
	
	private void putCheckpointBonus() {
		int x = 0;
		int y = 0;
		
		if (!(checkpoints.size() == checkpointLimit)) {
			if (queues.canTakeBonus(CHECK_QUEUE)) {
				while (true) {
					x = rnd.nextInt(board.getBoardSize());
					y = rnd.nextInt(board.getBoardSize());
	
					if ((board.getCellAt(x, y).getBonus() == null) 
							&& checkCurrentPosition(x, y)) {
						Checkpoint bonus = (Checkpoint) queues.pop(CHECK_QUEUE);
						board.getCellAt(x, y).setBonus(bonus);
						checkpoints.add(bonus);
						break;
					}
				}
			}
		}
	}
	
	private void putOtherBonus() {
		int x = 0;
		int y = 0;
		if (!(otherBonuses.size() == otherBonusLimit)) {
			if (queues.canTakeBonus(OTHER_QUEUE)) {
				while (true) {
					x = rnd.nextInt(board.getBoardSize());
					y = rnd.nextInt(board.getBoardSize());

					if ((board.getCellAt(x, y).getBonus() == null) 
							&& checkCurrentPosition(x, y)) {
						BonusObject bonus = queues.pop(OTHER_QUEUE);
						board.getCellAt(x, y).setBonus(bonus);
						otherBonuses.add(bonus);
						break;
					}
				}
			}
		}
	}

	private boolean checkCurrentPosition(int x, int y) {
		boolean canPutBonus = true;
		for (Player p : players) {
			if (p.getX() == x && p.getY() == y) {
				canPutBonus = false;
			}
		}
		return canPutBonus;
	}

	public List<Checkpoint> getCheckpoints() {
		return checkpoints;
	}

	public List<BonusObject> getOtherBonuses() {
		return otherBonuses;
	}
	
	public void removeBonus(boolean ifCP, BonusObject bonus) {
		if (ifCP) {
			if (checkpoints.size() == checkpointLimit) {
				queues.increaseTime(1);
			} else if (checkpoints.size() == (checkpointLimit - 1)){
				queues.increaseTime(2);
			}
			checkpoints.remove(bonus);
		} else {
			if (otherBonuses.size() == otherBonusLimit) {
				queues.increaseTime(3);
			} else if (otherBonuses.size() == (otherBonusLimit - 1)){
				queues.increaseTime(4);
			}
			otherBonuses.remove(bonus);
		}
	}
}

class BonusQueues {
	private LinkedList<Checkpoint> checkpoints;
	private LinkedList<BonusObject> otherBonuses;
	
	private int checkpointsLimit;
	private int otherBonusesLimit;
	
	private int canTakeCp1 = 0;
	private int canTakeCp2 = 0;
	private int canTakeOth1 = 0;
	private int canTakeOth2 = 0;
	private final int timeDelay = 4;
	
	public BonusQueues(int cpLimit, int otherLimit) {
		checkpoints = new LinkedList<Checkpoint>();
		otherBonuses = new LinkedList<BonusObject>();
		this.checkpointsLimit = cpLimit;
		this.otherBonusesLimit = otherLimit;
	}
	
	public BonusObject pop(boolean ifCP) {
		BonusObject bonus = null;
		if (ifCP) {
			bonus = checkpoints.remove();
		} else {
			bonus = otherBonuses.remove();
		}
		return bonus;
	}
	
	public void push(boolean ifCP, BonusObject bonus) {
		if (ifCP) {
			checkpoints.add((Checkpoint) bonus);
		} else {
			otherBonuses.add(bonus);
		}
	}
	
	public boolean full(boolean ifCP) {
		boolean res = false;
		if (ifCP) {
			if (checkpoints.size() == checkpointsLimit) {
				res = true;
			}
		} else {
			if (otherBonuses.size() == otherBonusesLimit) {
				res = true;
			}
		}
		return res;
	}
	
	public boolean canTakeBonus(boolean ifCP) {
		boolean res = false;
		if (ifCP) {
			canTakeCp1 ++;
			if (canTakeCp1 == timeDelay) {
				res = true;
				canTakeCp1 = canTakeCp2;
				canTakeCp2 = 0;
			}
		} else {
			canTakeOth1 ++;
			if (canTakeOth1 == timeDelay) {
				res = true;
				canTakeOth1 = canTakeOth2;
				canTakeOth2 = 0;
			}
		}
		return res;
	}
	
	public void increaseTime(int bonus) {
		switch (bonus) {
		case 1:
			if (canTakeCp1 != timeDelay) { canTakeCp1++; } break;
		case 2:
			if (canTakeCp2 != timeDelay) { canTakeCp2++; } break;
		case 3:
			if (canTakeOth1 != timeDelay) { canTakeOth1++; } break;
		case 4:
			if (canTakeOth2 != timeDelay) { canTakeOth2++; } break;
		}
	}
	
	public LinkedList<Checkpoint> getCheckpoints() {
		return checkpoints;
	}
	
	public LinkedList<BonusObject> getOtherBonuses() {
		return otherBonuses;
	}
}