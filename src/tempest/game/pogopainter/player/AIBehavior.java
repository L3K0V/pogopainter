package tempest.game.pogopainter.player;

import java.util.Vector;

import tempest.app.neurons.base.NeuronNetwork;
import tempest.game.pogopainter.system.Direction;

public class AIBehavior implements Behavior {
	
	private Vector<Integer> inputData;
	private Vector<Integer> outputData;
	private NeuronNetwork  brain;
	
	public void setPlayer(Player pl) {
		// TODO Auto-generated method stub
	}
	
	public AIBehavior() {
		brain      = new NeuronNetwork(22, 2, 2, 11);
		inputData  = new Vector<Integer>(22);
		outputData = new Vector<Integer>();
	}
	
	public void refreshInputData(Vector<Integer> input) {
		inputData.clear();
		inputData = input;
		outputData = brain.Update(input);
		System.out.println(input);
	}

	public Direction getNextDirection() {
		Direction next = Direction.NONE;
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
			next = Direction.NONE;
		}
		return next;
	}
	
}