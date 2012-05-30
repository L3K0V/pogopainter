package tempest.game.pogopainter.player;

import java.util.Vector;

import tempest.app.neurons.base.NeuronNetwork;
import tempest.game.pogopainter.graphics.Panel;
import tempest.game.pogopainter.system.Direction;

public class UserBehavior implements Behavior {
	private Panel panel;
	
	public UserBehavior(Panel panel) {
		this.panel = panel;
	}
	public void setPlayer(Player pl) {
	}

	public Direction getNextDirection() {
		return panel.getDirection();
	}
	public void refreshInputData(Vector<Integer> input) {
		// TODO Auto-generated method stub
		
	}
	public NeuronNetwork getBrain() {
		// TODO Auto-generated method stub
		return null;
	}
}
