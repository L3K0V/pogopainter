package game.bonuses;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import game.player.Player;
import game.system.Board;

public class BonusHandler {
	private boolean ifCheckpoints = false;
	private boolean ifArrows = false;
	private boolean ifSpeedUps = false;
	private boolean ifShoots = false;

	private Board board;
	private List<Player> players;

	private List<Checkpoint> CHECKPOINTS;
	private List<Arrow> ARROWS;

	private int checkpointLimit = 3;
	private int otherBonusLimit = 3;

	private int bonusRandomNumber;

	public BonusHandler(Board b, List<Player> pl) {
		this.board = b;
		this.players = pl;

		Random rnd = new Random();
		bonusRandomNumber = rnd.nextInt(5) + 1;
	}

	public void seedBonuses(Bonuses[] bon) {
		for (int i = 0; i < bon.length; i++) {
			switch (bon[i]) {
			case CHECKPOINT:
				ifCheckpoints = true;
				CHECKPOINTS = new ArrayList<Checkpoint>();
				break;
			case ARROW:
				ifArrows = true;
				ARROWS = new ArrayList<Arrow>();
				break;
			case SPEEDUP:
				ifSpeedUps = true;
				// need to init array
				break;
			case SHOOT:
				ifShoots = true;
				// need to init array
				break;
			}
		}
	}

	public void update() {
		putBonus(board, bonusRandomNumber);
		for (Arrow aw : ARROWS) {
			aw.changeState();
		}
	}

	public void putBonus(Board b, int chance) {
		int checkChance = 0;
		int x;
		int y;
		Random rnd = new Random();
		int bonusChance = 0;
		if (ifCheckpoints) {
			checkChance = rnd.nextInt(5) + 1;
			if (checkChance == chance && CHECKPOINTS.size() < checkpointLimit) {
				while (true) {
	
					x = rnd.nextInt(b.getBoardSize());
					y = rnd.nextInt(b.getBoardSize());
	
					if (b.getCellAt(x, y).getBonus() == null) {
						Checkpoint bonus = new Checkpoint();
						b.getCellAt(x, y).setBonus(bonus);
						CHECKPOINTS.add(bonus);
						break;
					}
				}
			} else if (ifArrows){
				checkChance = rnd.nextInt(5) + 1;
				bonusChance = rnd.nextInt(5) + 1;
	
				if (checkChance == bonusChance && ARROWS.size() < otherBonusLimit) {
					while (true) {
	
						x = rnd.nextInt(b.getBoardSize());
						y = rnd.nextInt(b.getBoardSize());
	
						if (b.getCellAt(x, y).getBonus() == null) {
							BonusObject bonus = new Arrow();
							b.getCellAt(x, y).setBonus(bonus);
							ARROWS.add((Arrow) bonus);
							break;
						}
					}
				}
			}
		}
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
}
