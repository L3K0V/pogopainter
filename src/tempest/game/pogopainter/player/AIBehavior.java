package tempest.game.pogopainter.player;

import java.util.Random;
import java.util.Vector;

import tempest.app.neurons.base.NeuronNetwork;
import tempest.game.pogopainter.system.Direction;

public class AIBehavior implements Behavior {
	
	private Vector<Integer> inputData;
	private Vector<Integer> outputData;
	private NeuronNetwork  brain;

	public void setBrain(NeuronNetwork brain) {
		this.brain = brain;
	}

	public void setPlayer(Player pl) {
		// TODO Auto-generated method stub
	}
	
	public AIBehavior() {
		brain      = new NeuronNetwork(24, 2, 2, 11);
		inputData  = new Vector<Integer>(24);
		outputData = new Vector<Integer>(2);
	}
	
	public AIBehavior(NeuronNetwork network) {
		this.brain = network;
		inputData  = new Vector<Integer>(24);
		outputData = new Vector<Integer>(2);
	}
	
	public void refreshInputData(Vector<Integer> input) {
		inputData.clear();
		inputData = input;
		outputData = brain.Update(input);
		System.out.println(input);
	}

	public Direction getNextDirection() {
		Direction next = Direction.NONE;
		System.out.println(outputData.toString());
		if (outputData.firstElement() == 0) {
			if (outputData.elementAt(1) == 1) {
				next = Direction.RIGHT;
			} else {
				next = Direction.DOWN;
			}
		} else if (outputData.firstElement() == 1) {
			if (outputData.get(1) == 0) {
				next = Direction.LEFT;
			} else {
				next = Direction.UP;
			}
		} else {
			Random rnd = new Random();
			while (next == Direction.NONE) {
				next = Direction.values()[rnd.nextInt(4)+1];
			}
		}
		return next;
	}
	
	public NeuronNetwork getBrain() {
		return brain;
	}
	
}