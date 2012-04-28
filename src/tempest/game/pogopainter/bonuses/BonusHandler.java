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
    private List<Checkpoint> CHECKPOINTS;
    private List<BonusObject> OTHERBONUSES;
	
	private int bonusRandomNumber;
	private Random rnd;
	
    private int checkpointLimit;
    private int otherBonusLimit;
    
    private final boolean CheckQueue = true;
    private final boolean OtherQueue = false;

	public BonusHandler(Board b, List<Player> pl, int cpLimit, int otherBLimit) {
		this.board = b;
		this.players = pl;
		rnd = new Random();
		checkpointLimit = cpLimit;
		otherBonusLimit = otherBLimit;
	}

	private void fillQueues() {
		while (!queues.full(CheckQueue)) {
			queues.push(CheckQueue, new Checkpoint());
		}
		while (!queues.full(OtherQueue)) {
			queues.push(OtherQueue, getRandomBonus());
		}
	}

	private BonusObject getRandomBonus() {
		/*
		 * random number form 1 to 10
		 * bonus with least chance activates only from 1 number
		 * bonus with second least chance activates only from 2 numbers
		 * bonus with second highest chance activates only from 3 numbers
		 * bonus with highest chance activates only from 4 numbers
		 */
		BonusObject res = null;
		if (bonusRandomNumber != 0) {
			int ran = rnd.nextInt(bonusRandomNumber);
			switch (ran) {
			case 0:
				res = new Arrow();
				break;
			case 1:
				res = new Teleport();
				break;
			}
		}
		return res;
	}

	public void seedBonuses(Bonuses[] bon) {
		int numberOfBonuses = 0;
		for (int i = 0; i < bon.length; i++) {
			switch (bon[i]) {
			case CHECKPOINT:
				 CHECKPOINTS   = new ArrayList<Checkpoint>();
				break;
			case ARROW:
				numberOfBonuses++;
				break;
			case TELEPORT:
				numberOfBonuses++;
				break;
			}
		}
		bonusRandomNumber = numberOfBonuses;
		OTHERBONUSES = new ArrayList<BonusObject>();
		this.queues = new BonusQueues(checkpointLimit, otherBonusLimit);
		fillQueues();
	}

	public void update() {
		putBonus();
		fillQueues();
		for (BonusObject bo : OTHERBONUSES) {
			if (bo.getType() == Bonuses.ARROW) {
				Arrow aw = (Arrow) bo;
				aw.changeState();
			}
		}
	}
	
	public void initialBonuses() {
		while ((CHECKPOINTS.size() != checkpointLimit) && (OTHERBONUSES.size() != otherBonusLimit)) {
			while (true) {
				int x = rnd.nextInt(board.getBoardSize());
				int y = rnd.nextInt(board.getBoardSize());

				if ((board.getCellAt(x, y).getBonus() == null) && checkCurrentPosition(x, y)) {
					Checkpoint bonus = (Checkpoint) queues.pop(CheckQueue);
					board.getCellAt(x, y).setBonus(bonus);
					CHECKPOINTS.add(bonus);
					break;
				}
			}
			while (true) {
				int x = rnd.nextInt(board.getBoardSize());
				int y = rnd.nextInt(board.getBoardSize());

				if ((board.getCellAt(x, y).getBonus() == null) && checkCurrentPosition(x, y)) {
					BonusObject bonus = queues.pop(OtherQueue);
					board.getCellAt(x, y).setBonus(bonus);
					OTHERBONUSES.add(bonus);
					break;
				}
			}
		}
	}

	public void putBonus() {
		int x;
		int y;
		
		if (!(CHECKPOINTS.size() == checkpointLimit)) {
			if (queues.canTakeBonus(CheckQueue)) {
				while (true) {
					x = rnd.nextInt(board.getBoardSize());
					y = rnd.nextInt(board.getBoardSize());
	
					if ((board.getCellAt(x, y).getBonus() == null) && checkCurrentPosition(x, y)) {
						Checkpoint bonus = (Checkpoint) queues.pop(CheckQueue);
						board.getCellAt(x, y).setBonus(bonus);
						CHECKPOINTS.add(bonus);
						break;
					}
				}
			}
		}
			
		if (!(OTHERBONUSES.size() == otherBonusLimit)) {
			if (queues.canTakeBonus(OtherQueue)) {
				while (true) {
					x = rnd.nextInt(board.getBoardSize());
					y = rnd.nextInt(board.getBoardSize());

					if ((board.getCellAt(x, y).getBonus() == null) && checkCurrentPosition(x, y)) {
						BonusObject bonus = queues.pop(OtherQueue);
						board.getCellAt(x, y).setBonus(bonus);
						OTHERBONUSES.add(bonus);
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

	public boolean checkPlayerOnBonus(Player player, BonusObject bonus) {
		boolean sure = false;
		if (player.getX() == bonus.getX() && player.getY() == bonus.getY()) {
			sure = true;
		}
		return sure;
	}

	public boolean checkArrowForGivingPoints(Arrow arrow) {
		boolean sure = true;
		int x = arrow.getX();
		int y = arrow.getY();
		switch (arrow.state) {
		case 1:
			if (x == 7) {sure = false;}break;
		case 2:
			if (y == 7) {sure = false;}break;
		case 3:
			if (x == 0) {sure = false;}break;
		case 4:
			if (y == 0) {sure = false;}break;
		}
		return sure;	
	}

	public List<Checkpoint> getCheckpoints() {
		return CHECKPOINTS;
	}

	public List<BonusObject> getOtherBonuses() {
		return OTHERBONUSES;
	}
	
	public void removeBonus(boolean ifCP, BonusObject bonus) {
		if (ifCP) {
			if (CHECKPOINTS.size() == checkpointLimit) {
				queues.increaseTime(1);
			} else if (CHECKPOINTS.size() == (checkpointLimit - 1)){
				queues.increaseTime(2);
			}
			CHECKPOINTS.remove(bonus);
		} else {
			if (OTHERBONUSES.size() == otherBonusLimit) {
				queues.increaseTime(3);
			} else if (OTHERBONUSES.size() == (otherBonusLimit - 1)){
				queues.increaseTime(4);
			}
			OTHERBONUSES.remove(bonus);
		}
	}
}

class BonusQueues {
	private LinkedList<Checkpoint> CHECKPOINTS;
	private LinkedList<BonusObject> OtherBONUSES;
	
	private int checkpointsLimit;
	private int otherBonusesLimit;
	
	private int canTakeCp1 = 0;
	private int canTakeCp2 = 0;
	private int canTakeOth1 = 0;
	private int canTakeOth2 = 0;
	private final int timeDelay = 4;
	
	public BonusQueues(int cpLimit, int otherLimit) {
		CHECKPOINTS = new LinkedList<Checkpoint>();
		OtherBONUSES = new LinkedList<BonusObject>();
		this.checkpointsLimit = cpLimit;
		this.otherBonusesLimit = otherLimit;
	}
	
	public BonusObject pop(boolean ifCP) {
		BonusObject bonus = null;
		if (ifCP) {
			bonus = CHECKPOINTS.remove();
		} else {
			bonus = OtherBONUSES.remove();
		}
		return bonus;
	}
	
	public void push(boolean ifCP, BonusObject bonus) {
		if (ifCP) {
			CHECKPOINTS.add((Checkpoint) bonus);
		} else {
			OtherBONUSES.add(bonus);
		}
	}
	
	public boolean full(boolean ifCP) {
		boolean res = false;
		if (ifCP) {
			if (CHECKPOINTS.size() == checkpointsLimit) {
				res = true;
			}
		} else {
			if (OtherBONUSES.size() == otherBonusesLimit) {
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
		return CHECKPOINTS;
	}
	
	public LinkedList<BonusObject> getOtherBonuses() {
		return OtherBONUSES;
	}
}
