package tempest.game.pogopainter.system;

import java.util.ArrayList;

import tempest.game.pogopainter.gametypes.Game;

public class TimeManager {
	private ArrayList<TimeListener> listeners;
	private ArrayList<Long> timesBeforeUpdae;
	private Game game;
	
	public TimeManager(Game game) {
		listeners = new ArrayList<TimeListener>();
		timesBeforeUpdae = new ArrayList<Long>();
		this.game = game;
	}
	
	public void addListener(TimeListener l, long time) {
		listeners.add(l);
		timesBeforeUpdae.add(time);
	}
	
	public void update(long time) {
		for(int i = 0; i < listeners.size(); i++) {
			long update = timesBeforeUpdae.get(i);
			update -= time;
			if (update <= 0) {
				update = listeners.get(i).wakeUp(game);
			}
			timesBeforeUpdae.set(i, update);
		}
	}
}
