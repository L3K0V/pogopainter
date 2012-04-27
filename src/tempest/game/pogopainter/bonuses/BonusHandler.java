package tempest.game.pogopainter.bonuses;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import android.text.GetChars;
import tempest.game.pogopainter.player.Player;
import tempest.game.pogopainter.system.Board;

public class BonusHandler {
	private boolean ifCheckpoints = false;
	private boolean ifArrows      = false;
	//private boolean ifSpeedUps  = false;
	//private boolean ifShoots 	  = false;
	private boolean ifTeleport    = false;

	private Board board;
	private List<Player> players;

	private BonusQueues queues;
    private List<Checkpoint> CHECKPOINTS;
    private List<Arrow>      ARROWS;
    private List<Teleport>   TELEPORTS;
    private List<BonusObject> OTHERBONUSES;
	
	private int bonusRandomNumber;
	private Random rnd;
	
    private int checkpointLimit;
    private int otherBonusLimit;

	public BonusHandler(Board b, List<Player> pl, int cpLimit, int otherBLimit) {
		this.board = b;
		this.players = pl;
		rnd = new Random();
		checkpointLimit = cpLimit;
		otherBonusLimit = otherBLimit;
	}

	private void fillQueues() {
		while (!queues.full(true)) {
			queues.push(true, new Checkpoint());
		}
		while (!queues.full(false)) {
			queues.push(false, getRandomBonus());
		}
	}

	private BonusObject getRandomBonus() {
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
				ifCheckpoints = true;
				 CHECKPOINTS   = new ArrayList<Checkpoint>();
				break;
			case ARROW:
				ifArrows      = true;
				ARROWS        = new ArrayList<Arrow>();
				numberOfBonuses++;
				break;
			case TELEPORT:
				ifTeleport    = true;
				TELEPORTS     = new ArrayList<Teleport>();
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
		putBonus(board);
		fillQueues();
		for (Arrow aw : ARROWS) {
			aw.changeState();
		}
	}

	public void putBonus(Board b) {
		int x;
		int y;
		
		if (ifCheckpoints) {
			if (!(CHECKPOINTS.size() == checkpointLimit)) {
				if (queues.getCheckpoints().getFirst().getTime() == 0) {
					while (true) {
						x = rnd.nextInt(b.getBoardSize());
						y = rnd.nextInt(b.getBoardSize());
		
						if ((b.getCellAt(x, y).getBonus() == null) && checkCurrentPosition(x, y)) {
							Checkpoint bonus = (Checkpoint) queues.pop(true);
							b.getCellAt(x, y).setBonus(bonus);
							CHECKPOINTS.add(bonus);
							break;
						}
					}
				} else {
					queues.getCheckpoints().getFirst().decreaseTime();
				}
			}
			if (queues.getCheckpoints().getLast().getTime() != 4) {
				queues.getCheckpoints().getLast().decreaseTime();
			}
		}
			
		if (bonusRandomNumber != 0) {
			if (!(OTHERBONUSES.size() == otherBonusLimit)) {
				if (queues.getOtherBonuses().getFirst().getTime() == 0) {
					while (true) {
						x = rnd.nextInt(b.getBoardSize());
						y = rnd.nextInt(b.getBoardSize());
	
						if ((b.getCellAt(x, y).getBonus() == null) && checkCurrentPosition(x, y)) {
							BonusObject bonus = queues.pop(false);
							b.getCellAt(x, y).setBonus(bonus);
							OTHERBONUSES.add(bonus);
							if (bonus.getType() == Bonuses.ARROW) {
								ARROWS.add((Arrow) bonus);
							} else {
								TELEPORTS.add((Teleport) bonus);
							}
							break;
						}
					}
				} else {
					queues.getOtherBonuses().getFirst().decreaseTime();
				}
			}
			if (queues.getOtherBonuses().getLast().getTime() != 4) {
				queues.getOtherBonuses().getLast().decreaseTime();
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

	public List<Arrow> getArrows() {
		return ARROWS;
	}
	
	public List<Teleport> getTeleports() {
		return TELEPORTS;
	}
	public List<BonusObject> getOtherBonuses() {
		return OTHERBONUSES;
	}
	
	public void removeBonus(boolean ifCP, BonusObject bonus) {
		if (ifCP) {
			CHECKPOINTS.remove(bonus);
			if (queues.getCheckpoints().getFirst().getTime() == 4) {
				queues.getCheckpoints().getFirst().decreaseTime();
			} else if(queues.getCheckpoints().getLast().getTime() == 4){
				queues.getCheckpoints().getLast().decreaseTime();
			}
		} else {
			OTHERBONUSES.remove(bonus);
			if (queues.getOtherBonuses().getFirst().getTime() == 4) {
				queues.getOtherBonuses().getFirst().decreaseTime();
			} else if(queues.getOtherBonuses().getLast().getTime() == 4){
				queues.getOtherBonuses().getLast().decreaseTime();
			}
			if (bonus.getType() == Bonuses.ARROW) {
				ARROWS.remove(bonus);
			} else {
				TELEPORTS.remove(bonus);
			}
		}
	}
}

class BonusQueues {
	private LinkedList<Checkpoint> CHECKPOINTS;
	private LinkedList<BonusObject> OtherBONUSES;
	
	private int checkpointsLimit;
	private int otherBonusesLimit;
	
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
	
	public LinkedList<Checkpoint> getCheckpoints() {
		return CHECKPOINTS;
	}
	
	public LinkedList<BonusObject> getOtherBonuses() {
		return OtherBONUSES;
	}
}
