package tempest.game.pogopainter.player;

import java.util.Random;
import java.util.Vector;

import tempest.app.neurons.base.Constants;
import tempest.app.neurons.base.NeuronNetwork;
import tempest.game.pogopainter.gametypes.Game;
import tempest.game.pogopainter.system.Direction;

public class AIBehavior implements Behavior {
	private Vector<Integer> inputData;
	private Vector<Integer> outputData;
	private NeuronNetwork  brain;
	private Game game;
	private Player owner;

	public void setBrain(NeuronNetwork brain) {
		this.brain = brain;
	}

	public void setPlayer(Player pl) {
		owner = pl;
	}
	
	/* TODO: REMOVE */
	public AIBehavior() {
		brain      = new NeuronNetwork(Constants.networkInputs, Constants.networkOutputs, Constants.networkHiddenLayers, Constants.networkHiddenNeurons);
		inputData  = new Vector<Integer>(Constants.networkInputs);
		outputData = new Vector<Integer>(Constants.networkOutputs);
	}
	
	public AIBehavior(NeuronNetwork network, Game game) {
		this.brain = network;
		inputData  = new Vector<Integer>(Constants.networkInputs);
		outputData = new Vector<Integer>(Constants.networkOutputs);
		this.game = game;
	}
	
	public void refreshInputData(Vector<Integer> input) {
		System.out.println(input);
		inputData.clear();
		inputData = input;
		outputData = brain.Update(input);
	}

	public Direction getNextDirection() {
		Direction next = Direction.NONE;
		System.out.println(outputData.toString());
		if (outputData.firstElement() == 0) {
			if (outputData.elementAt(1) == 1) {
				if (game.checkDir(Direction.RIGHT, owner)) {
					next = Direction.RIGHT;
				} else {
					next = getRandomDirection(next);
				}
			} else {
				if (game.checkDir(Direction.DOWN, owner)) {
					next = Direction.DOWN;
				} else {
					next = getRandomDirection(next);
				}	
			}
		} else if (outputData.firstElement() == 1) {
			if (outputData.get(1) == 0) {
				if (game.checkDir(Direction.LEFT, owner)) {
					next = Direction.LEFT;
				} else {
					next = getRandomDirection(next);
				}
			} else {
				if (game.checkDir(Direction.UP, owner)) {
					next = Direction.UP;
				} else {
					next = getRandomDirection(next);
				}
			}
		} else {
			next = getRandomDirection(next);
		}
		return next;
	}

	private Direction getRandomDirection(Direction next) {
		Random rnd = new Random();
		while (next == Direction.NONE || !game.checkDir(next, owner)) {
			next = Direction.values()[rnd.nextInt(4)+1];
		}
		return next;
	}
	
	public NeuronNetwork getBrain() {
		return brain;
	}
}