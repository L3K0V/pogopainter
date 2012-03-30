package game.gametypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Color;
import game.bonuses.Checkpoint;
import game.graphics.Panel;
import game.player.AIBehaviour;
import game.player.Behaviour;
import game.player.Difficulty;
import game.player.Player;
import game.system.Actions;
import game.system.Board;
import game.system.Direction;
import game.system.Metrics;

public class ClassicGameType {
	private Board b;
	private Behaviour AIs = new AIBehaviour(Difficulty.EASY);
	private int bonusRandomNumber;
	private int aiNumber;
	private List<Player> AI = new ArrayList<Player>();
	private List<Player> USER = new ArrayList<Player>();
	private Actions ACTIONS = new Actions();
	private List<Checkpoint> CHECKPOINTS = new ArrayList<Checkpoint>();
	
	private Panel _panel;
	private int time;
	
	public ClassicGameType(Panel panel) {
		this._panel = panel;
		Metrics m = new Metrics();
		int classicCellNumber = m.getClassicCellNumber();
		int playerColor = m.getPlayerColor();
		b = new Board(classicCellNumber);
		time = m.getClassicGameTime();
		
		initPlayerColors(classicCellNumber, playerColor);
		for (Player ai: AI) {
			b.setPlayerColorOnCell(ai);
		}
		for (Player user: USER) {
			b.setPlayerColorOnCell(user);
		}
		
		Random rnd = new Random();
		bonusRandomNumber = rnd.nextInt(b.getBoardSize())+1;
		aiNumber = rnd.nextInt(4)+1;
		

	}

	private void initPlayerColors(int classicCellNumber, int playerColor) {
		switch (playerColor) {
		case Color.RED:
			USER.add(new Player(0, classicCellNumber - 1, Color.RED, 0, null));
			AI.add(new Player(classicCellNumber-1, classicCellNumber-1, Color.BLUE, 0, AIs));
			AI.add(new Player(0, 0, Color.GREEN, 0, AIs));
			AI.add(new Player(classicCellNumber - 1, 0, Color.YELLOW, 0, AIs));
			break;
		case Color.BLUE:
			AI.add(new Player(0, classicCellNumber - 1, Color.RED, 0, AIs));
			USER.add(new Player(classicCellNumber-1, classicCellNumber-1, Color.BLUE, 0, null));
			AI.add(new Player(0, 0, Color.GREEN, 0, AIs));
			AI.add(new Player(classicCellNumber - 1, 0, Color.YELLOW, 0, AIs));
			break;
		case Color.GREEN:
			AI.add(new Player(0, classicCellNumber - 1, Color.RED, 0, AIs));
			AI.add(new Player(classicCellNumber-1, classicCellNumber-1, Color.BLUE, 0, AIs));
			USER.add(new Player(0, 0, Color.GREEN, 0, null));
			AI.add(new Player(classicCellNumber - 1, 0, Color.YELLOW, 0, AIs));
			break;
		case Color.YELLOW:
			AI.add(new Player(0, classicCellNumber - 1, Color.RED, 0, AIs));
			AI.add(new Player(classicCellNumber-1, classicCellNumber-1, Color.BLUE, 0, AIs));
			AI.add(new Player(0, 0, Color.GREEN, 0, AIs));
			USER.add(new Player(classicCellNumber - 1, 0, Color.YELLOW, 0, null));
			break;
		default:
			USER.add(new Player(0, classicCellNumber - 1, Color.RED, 0, null));
			AI.add(new Player(classicCellNumber-1, classicCellNumber-1, Color.BLUE, 0, AIs));
			AI.add(new Player(0, 0, Color.GREEN, 0, AIs));
			AI.add(new Player(classicCellNumber - 1, 0, Color.YELLOW, 0, AIs));
			break;
		}
	}
	
	public Board getBoard() {
		return b;
	}

	public List<Player> getAIs() {
		return AI;
	}
	
	public int getTime() {
		return time;
	}
	
	public List<Player> getUser() {
		return USER;
	}
	
	public Actions getActions() {
		return this.ACTIONS;
	}
	
	public void update() {
		if (time == 0) {
			_panel.stopThreads();
		}
		time--;
		Direction dir = _panel.getDirection();
		ACTIONS.move(b, USER.get(0), dir);
		for (Player AI : this.AI) {
			AI.getBehaviour().easy(b, AI, aiNumber);
			Direction ai = AI.getBehaviour().getDirection();
			ACTIONS.move(b, AI, ai);
		}
		ACTIONS.seedBonus(b, bonusRandomNumber);
	}
}
